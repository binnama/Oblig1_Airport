package org.airport.module;

import java.io.*;
import java.util.*;

public class Airport {

    int runwayFree = 1;
    int maxServiceTime;
    int maxTicks = 2;
    //float toRatio;
    //float arRatio;
    //float ratio;
    double toRatio;
    double arRatio;
    double ratio;

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

        arRatio = 0.6;
        toRatio = 0.3;
        ratio = (arRatio + toRatio) / 2;

    }
    // Saves when the plane arrives
    private class airplane {
        private int arrival;
        public airplane (int arrival) {
            this.arrival = arrival;
        }
        public int queueTime (int time) {
            return time - arrival;
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

    public void simulate() {

        int sumPlaneTime        = 0; // Total waitingtime to use the runway
        int planeLandingQ       = 0;
        int planeTakeoffQ       = 0;
        int sumPlanes           = 0; // Sum all plains entering the airport
        int sumRunwayFree       = 0; // All the times the runway was free
        int sumRejectedPlanes   = 0; // Counting plains arriving when  the landinqueue is full
        int runway              = 0;


        Queue<airplane> lq = new LinkedList<airplane>();
        Queue<airplane> tq = new LinkedList<airplane>();

        // Makes an array for the runway
        runway[] open = new runway[runwayFree];

        // Initates empty runway
        for (int i = 0; i < runwayFree; i++) {
            open[i] = new runway();
        }

        // Simulating for each tick
        for (int time = 0; time < maxTicks; time++) {
            System.out.println("Tick: " + time);

            // Plane for landing
            if(getPoissonRandom(ratio) < toRatio) {
                System.out.println("test 2.1: print ratio: " + ratio);
                if (lq.size() > 5) {
                    sumRejectedPlanes++;
                    sumPlanes++;
                    System.out.println("Our landinqueue is full, please move on.");
                }
                else {
                    lq.add(new airplane(time));
                    sumPlanes++;

                    System.out.println("Plane " + time + " is preparing to land");
                    System.out.println("test2.lq: " + lq.size());
                }
            }
            System.out.println("test3: sumPlanes: " + sumPlanes);

            // Plane for takeoff
            if(getPoissonRandom(ratio) < arRatio) {
                System.out.println("test 2.2: print ratio: " + ratio);
                if(tq.size() > 10) {
                    sumRejectedPlanes++;
                    sumPlanes++;
                    System.out.println("Our takeoff-queue is full. Come back later");
                }
                tq.add(new airplane(time));
                sumPlanes++;
                System.out.println("Plane " + time + " is preparing for takeoff");
                System.out.println("test2.tq: " + tq.size());
            }
            System.out.println("test3.1: sumPlanes: " + sumPlanes);

            // Checks if the runway is clear
            if (open[time].isAvailable(time)) {
                if (!lq.isEmpty()) { // Planes can land
                    airplane ap = lq.remove();
                    System.out.println("Plane " + time + " removed from lq.");

                    // Makes busy runway
                    open[time].setPlaneTime(time);
                    sumPlaneTime += ap.queueTime(time);
                    runway++;
                }
                else{
                    airplane ap = tq.remove();
                    System.out.println("Plane " + time + " removed from tq.");

                    // Makes busy runway
                    open[time].setPlaneTime(time);
                    sumPlaneTime += ap.queueTime(time);
                    runway++;
                }
            }
            else {
                sumRunwayFree++;
                System.out.println("test4: runwayFree: " + runwayFree);
            }
        }

        System.out.println("The airport is now closed.");
        System.out.println("The airport was open for " + maxTicks + " hours today.");
        System.out.println("Times the runway was in use: " + runway);
        System.out.println("Planes still in landingqueue: " + lq.size());
        System.out.println("Planes still in takeoffQueue: " + tq.size());
        System.out.println("Planes asked to move along: " + sumRejectedPlanes);
        System.out.println("Average time in queue: " + sumPlaneTime);
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
