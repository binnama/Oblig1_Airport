package org.airport.module;

import java.util.*;
public class Airport {

    int rw = 1;
    int maxTicks;
    float averageTakeoff;
    float averageArrival;
    //double averageTakeoff = 1;
    //double averageArrival = 1;
    int plane;

    public Airport(String name) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to " + name + " airport. This is the controltower.");

        System.out.print("How many hours should the airport be open: ");
        maxTicks = scanner.nextInt();
        System.out.print("Average number of arrivals each hour: ");
        averageArrival = scanner.nextFloat();
        System.out.print("Average number of takeoff each hour: ");
        averageTakeoff = scanner.nextFloat();

    }

    // Saves when the plane arrives
    private class airplane {
        int plane = 0;
        private int arrival;
        public airplane (int arrival, int plane) {
            this.arrival = arrival;
            this.plane = plane;
        }
        public int queueTime (int time) {
            return time - arrival;
        }
        @Override
        public String toString() {
        return plane + "";
        }
    }

    // Saves when the runway is available
    private class runway {
        private int planeTime; 
        public runway() {
            planeTime = 0;
        }
        public void setPlaneTime(int time) {
            planeTime = time;
        }
        public boolean isAvailable(int time) {
            return time >= planeTime;
        }
    }

    public void simulate()
        throws IllegalStateException {

        int planeLandingQ       = 0;
        int planeTakeoffQ       = 0;
        int sumPlanes           = 0; // Sum all plains entering the airport
        int sumRunwayFree       = 0; // All the times the runway was free
        int sumRejectedPlanes   = 0; // Counting plains arriving when  the landinqueue is full
        int runway              = 0;
        int planesLanding       = 0;
        int planesTakeoff       = 0;


        Queue<airplane> landingQueue = new LinkedList<airplane>();
        Queue<airplane> takeoffQueue = new LinkedList<airplane>();

        // Queue to hold the planes when the takeoff-queue is full
        // Keeps the planes at their gates
        Queue<airplane> gateQueue = new LinkedList<airplane>();

        // Makes an array for the runway
        runway[] open = new runway[rw];

        // Initates empty runway
        for (int i = 0; i < rw; i++) {
            open[i] = new runway();
        }

        // Simulating for each tick
        for (int time = 0; time < maxTicks; time++) {
            System.out.println("Hour: " + (time + 1));

            // Plane for landing
            //Possion:
            int arrivingPlanes = getPoissonRandom(averageArrival);

            if (arrivingPlanes < averageArrival) {
                for (int j = 0; j <= arrivingPlanes; j++) {
                    if (landingQueue.size() > 5) {
                        sumRejectedPlanes++;
                        sumPlanes++;
                        System.out.println("Plane " + plane + ", our landinqueue is full, please move on.");
                    } else {
                        plane++;
                        landingQueue.add(new airplane(time, plane));
                        sumPlanes++;

                        System.out.println("Plane " + plane + " is preparing to land.");
                    }
                }
                System.out.println("It is " + landingQueue.size() + " plane(s) in the landingqueue");
            }

            // Plane for takeoff
            //Possion:
            int takeoffPlanes = getPoissonRandom(averageTakeoff);

            if (takeoffPlanes < averageTakeoff) {
                for (int k = 0; k <= takeoffPlanes; k++) {
                    if (takeoffQueue.size() >= 10) {
                        plane++;
                        sumPlanes++;
                        gateQueue.add(new airplane(time, plane));
                        System.out.println("Plane " + plane + ", our takeoff-queue is full. Please wait at your gate.");
                    } else {
                        plane++;
                        takeoffQueue.add(new airplane(time, plane));
                        sumPlanes++;
                        System.out.println("Plane " + plane + " is preparing for takeoff");
                    }
                }
                System.out.println("It is " + takeoffQueue.size() + " plane(s) in the takeoffqueue");
            }

            // Checks if the runway is clear
            for (int i = 0; i < rw; i++) {
                if (open[i].isAvailable(time)) {
                    if (!landingQueue.isEmpty()) { // Planes can land
                        airplane apl = landingQueue.poll();
                        System.out.println("Plane " + apl + " landed.");

                        // Makes busy runway
                        open[i].setPlaneTime(time); //
                        planeLandingQ += apl.queueTime(time);
                        runway++;
                        planesLanding++;
                    } else if (landingQueue.isEmpty() && !takeoffQueue.isEmpty()) {
                        airplane apt = takeoffQueue.poll();
                        System.out.println("Plane " + apt + " left the airport.");

                        // Makes busy runway
                        open[i].setPlaneTime(time);
                        planeTakeoffQ += apt.queueTime(time);
                        runway++;
                        planesTakeoff++;
                    } else {
                        // Counts amount of times the runway is not used
                        sumRunwayFree++;
                        System.out.println("Hour " + time + ": The airport is empty.");
                    }
                }

                // Checks if takeoff-queue have less than 10 planes.
                // Moves a plane from the gate- to takeoff-queue if conditions are met
                while (takeoffQueue.size() <= 10 && !gateQueue.isEmpty()) {
                    airplane gate = gateQueue.poll();
                    takeoffQueue.add(gate);
                    System.out.println("Plane " + gate + " left the gate and entered the takeoffqueue.");
                }
            }
        }
            System.out.println("\nThe airport is now closed.");
            System.out.println("The airport was open for " + maxTicks + " hours today.");
            System.out.println("Total amount of planes handeled: " + sumPlanes);
            System.out.println("Total planes landing " + planesLanding);
            System.out.println("Total planes taking off " + planesTakeoff);
            System.out.println("Times the runway was in use: " + runway);
            System.out.println("Planes still in landingqueue: " + landingQueue.size());
            System.out.println("Planes still in takeoffQueue: " + takeoffQueue.size());
            System.out.println("Planes asked to move along: " + sumRejectedPlanes);
            System.out.println("Planes still at the gate: " + gateQueue.size());
            if (sumPlanes > 0) {
                System.out.println("Average time in landingqueue: " + ((float) planeLandingQ / (float) sumPlanes) + " hours");
                System.out.println("Average time in takeoffqueue: " + ((float) planeTakeoffQ / (float) sumPlanes) + " hours");
            }
            System.out.println("The runway was empty " + (((float) sumRunwayFree / (float) maxTicks) * 100) + "% of the time");
        }

    private static int getPoissonRandom(double mean)
    {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}