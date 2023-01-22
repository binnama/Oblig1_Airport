package org.airport.module;

import java.io.*;
import java.util.*;

public class Airport {

    int runwayFree;
    int maxServiceTime;
    int maxTicks;
    float toRatio;
    float arRatio;
    float ratio;
    
    public Airport(String name) {
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Welcome to " + name + "airport");
        System.out.println("How long should the airport be open: ");
        maxTicks = scanner.nextInt();
        System.out.println("Average number of arrivals each hour: ");
        arRatio = scanner.nextFloat();
        System.out.println("Average number of takeoff each hour: ");
        toRatio = scanner.nextFloat();
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
        int sumPlanes           = 0; // Sum all plains entering the airport
        int sumRunwayFree       = 0; // All the times the runway was free
        int sumRejectedPlanes   = 0; // Counting plains arriving when  the landinqueue is full
        

        Queue<airplane> lq = new LinkedList<airplane>();
        Queue<airplane> tq = new LinkedList<airplane>();

        // Makes an array for the runway.
        runway[] open = new runway[runwayFree];

        // Initates empty runway
        for (int i = 0; i < runwayFree; i++) {
            open[i] = new runway();
        }

        // Simulating for each tick
        for (int time = 0; time < maxTicks; time++) {

            if(getPoissonRandom(ratio) < toRatio) {
                lq.add(new airplane(time));
            }
            if(getPoissonRandom(ratio) < arRatio) {
                tq.add(new airplane(time));
            }
          
            //for (int i = 0; ; i++) {
                if (open[time].isAvailable(time)) {
                    if (!lq.isEmpty()) { //Planes can take off
                        airplane ap = tq.remove();
                        
                        // Markes bussy runway
                        open[time].setPlaneTime(time);
                        sumPlaneTime += ap.queueTime(time);
                        sumPlanes++;
                    }
                    else if (lq.size() > 0 ) {
                        airplane ap = lq.remove();

                        open[time].setPlaneTime(time);
                        sumPlaneTime += ap.queueTime(time);
                        sumPlanes++;
                    }
                    else if (lq.size() > 5) {
                        System.out.println("Sorry, please find another airport.");
                        sumRejectedPlanes++;
                    }
                }
                else {
                sumRunwayFree++;
                System.out.println("Hei");
            }
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
/*
    private class landingQueue {
        private int landingTime;
        public landingQueue () {
            landingTime = 0;
        }
        public void setPlaneTime(int time) {
            landingTime = time;
        }
    }

    private class takeoffQueue {
        private int takeoffTime;
        public takeoffQueue () {
            takeoffTime = 0;
        }
        public void setPlaneTime(int time) {
            takeoffTime = time;
        }
    }
*/
