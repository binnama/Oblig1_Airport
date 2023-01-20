package org.airport.module;

import java.io.*;
import java.util.*;

public class Airport {

    int runwayFree;
    int maxServiceTime;
    int maxTicks;
    float planeRatio;
    
    public airport(String name) {
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Welcome to " + name + "airport");
        System.out.println("How long should the airport be open: ");
        maxTicks = scanner.nextInt();
        System.out.println("Average number of plane each hour: ");
        planeRatio = scanner.nextFloat();
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

    public void simulate() {
        
        int sumPlaneTime    = 0; // Total waitingtime to use the runway
        int sumPlanes       = 0; // Sum all plains entering the airport
        int sumRunwayFree   = 0; // All the times the runway was free
        
        // Making a queue
        Queue<landingQueue> lq = new LinkedList<landingQueue>();
        Queue<takeoffQueue> tq = new LinkedList<takeoffQueue>();


        // Makes an array for the runway.
        runway[] open = new runway[runwayFree];

        // Initates empty runway
        for (int i = 0; i < runwayFree; i++) {
            open[i] = new runway();
        }

        Random R = new Random();

        // Simulating for each tick
        for (int time = 0; time < maxTicks; i++) {

            if(R.nextDouble() < planeRatio) {
                
            }
          
            if (mean < planeRatio) {
                runwayQueue.add();
            }


            for (int i = 0; i < lq && i < tq; i++) {
                if (landing[i].isAvailable(time)) {
                    if (!landing.isEmpty()) {
                        takeoff to = takeoff.remove();
                        
                        // Markes bussy runway
                        runway.setPlaneTime(time);
                        sumPlaneTime += to.queueTime(time);
                        sumPlanes++;
                    }
                    else if (!landing !=) {
                        
                    }
                }
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
