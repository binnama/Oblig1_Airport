package org.airport.module;

import java.io.*;
import java.util.*;

public class Airport {

    int runwayFree;
    int rw = 1;
    int maxServiceTime;
    int maxTicks = 20;
    //float toRatio;
    //float arRatio;
    //float ratio;
    double toRatio;
    double arRatio;
    int plane;

    public Airport(String name) {

        //Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to " + name + " airport. This is the controltower.");
        /*
        System.out.println("How many hours should the airport be open: ");
        maxTicks = scanner.nextInt();
        System.out.println("Average number of arrivals each hour: ");
        arRatio = scanner.nextFloat();
        System.out.println("Average number of takeoff each hour: ");
        toRatio = scanner.nextFloat();
        */

        arRatio = 0.5;
        toRatio = 5;
    }

    // Saves when the plane arrives
    private class airplane {
        int plane               = 0;
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

        int sumPlaneTime = 0; // Total waitingtime to use the runway
        int planeLandingQ = 0;
        int planeTakeoffQ = 0;
        int sumPlanes = 0; // Sum all plains entering the airport
        int sumRunwayFree = 0; // All the times the runway was free
        int sumRejectedPlanes = 0; // Counting plains arriving when  the landinqueue is full
        int runway = 0;


        Queue<airplane> lq = new LinkedList<airplane>();
        Queue<airplane> tq = new LinkedList<airplane>();
        Queue<airplane> gateQueue = new LinkedList<airplane>();

        // Makes an array for the runway
        runway[] open = new runway[rw];

        // Initates empty runway
        for (int i = 0; i < rw; i++) {
            open[i] = new runway();
        }

        // Simulating for each tick
        for (int time = 0; time < maxTicks; time++) {
            System.out.println("Tick: " + (time + 1));

            // Plane for landing

            //Possion:
            int arrivingPlanes = getPoissonRandom(arRatio);

            if (arrivingPlanes < arRatio) {
                for (int j = 0; j <= arrivingPlanes; j++) {
                    if (lq.size() > 5) {
                        sumRejectedPlanes++;
                        sumPlanes++;
                        System.out.println("Plane " + plane + ", our landinqueue is full, please move on.");
                    } else {
                        plane++;
                        lq.add(new airplane(time, plane));
                        sumPlanes++;

                        System.out.println("Plane " + plane + " is preparing to land.");
                    }
                }
                System.out.println("It is " + lq.size() + " plane(s) in the landingqueue");
            }
            System.out.println("test3: sumPlanes: " + sumPlanes);

            // Plane for takeoff
            //Possion:
            int takeoffPlanes = getPoissonRandom(toRatio);

            if (takeoffPlanes < toRatio) {
                System.out.println("test 2.2: print leaving planes: " + takeoffPlanes);
                for (int k = 0; k <= takeoffPlanes; k++) {
                    if (tq.size() > 10) {
                        plane++;
                        sumPlanes++;
                        gateQueue.add(new airplane(time, plane));
                        System.out.println("Plane " + plane + ", our takeoff-queue is full. Please wait at your gate.");
                    } else {
                        plane++;
                        tq.add(new airplane(time, plane));
                        sumPlanes++;
                        System.out.println("Plane " + plane + " is preparing for takeoff");
                    }
                }
                System.out.println("It is " + tq.size() + " plane(s) in the takeoffqueue");
            }
            System.out.println("test3.1: sumPlanes: " + sumPlanes);

            // Checks if the runway is clear
            for (int i = 0; i < rw; i++) {
                if (open[i].isAvailable(time)) {
                    if (!lq.isEmpty()) { // Planes can land
                        airplane apl = lq.poll();
                        System.out.println("Plane " + apl + " landed.");

                        // Makes busy runway
                        open[i].setPlaneTime(time); //
                        planeLandingQ += apl.queueTime(time);
                        runway++;
                        System.out.println("Test3.5: Runway-lq: " + open.length);
                    } else if (lq.isEmpty() && !tq.isEmpty()) {
                        airplane apt = tq.poll();
                        System.out.println("Plane " + apt + " left the airport.");

                        // Makes busy runway
                        open[i].setPlaneTime(time);
                        planeTakeoffQ += apt.queueTime(time);
                        runway++;
                        System.out.println("Test3.5: Runway-tq: " + open.length);
                    } else {
                        sumRunwayFree++;
                        System.out.println("test4: runwayFree: " + sumRunwayFree);
                        System.out.println("Hour " + time + ": The airport is empty.");
                    }
                }

                System.out.println("tqSize: " + tq.size());
                while (tq.size() <= 10 && !gateQueue.isEmpty()) {
                    airplane gate = gateQueue.poll();
                    tq.add(gate);
                    System.out.println("Plane " + gate + " left the gate and entered the takeoffqueue.");
                }
            }

            System.out.println("The airport is now closed.");
            System.out.println("The airport was open for " + maxTicks + " hours today.");
            System.out.println("Times the runway was in use: " + runway);
            System.out.println("Planes still in landingqueue: " + lq.size());
            System.out.println("Planes still in takeoffQueue: " + tq.size());
            System.out.println("Planes asked to move along: " + sumRejectedPlanes);
            System.out.println("Average time in landingqueue: " + ((float) planeLandingQ / (float) sumPlanes));
            System.out.println("Average time in takeoffqueue: " + ((float) planeTakeoffQ / (float) sumPlanes));
            System.out.println("The runway was empty " + (((float) sumRunwayFree / (float) maxTicks) * 100) + "% of the time");
        }
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