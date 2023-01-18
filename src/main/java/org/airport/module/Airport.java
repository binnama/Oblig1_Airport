package org.airport.module;

import java.util.Arrays;
import java.util.Random;

public class Airport {

    boolean runwayOcupied = false;
    int landingQueue = 0;
    int takeoffQueue = 0;

    int arrivalTime;

    public Airport() {
        createQueue();
        createLanding();
        createTakeoff();
    }

    public static Integer createQueue() {
        int[] queue = {};

    }

    public static Integer createLanding() {
        int b = 2;
        return b;
    }

    public static void createTakeoff() {
        int three = 3;
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
