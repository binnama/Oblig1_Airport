package org.airport.module;

import java.io.*;
import java.util.*;

public class Airport {

    int maxServiceTime;
    int maxTicks;
    float planeRatio;
    
    public airport(String name) {
        Scanner scanner = new Scanner(System.in); scanner = new Scanner(System.in); scanner = new Scanner(System.in);
            System.out.println("Welcome to " + name + "airport");
            System.out.println("How long should the airport be open: ");
            maxTicks = scanner.nextInt();
            System.out.println("Average number of plane each hour: ");
            planeRatio = scanner.nextFloat();        
    }

    private class airplane {
        
    }

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

    // 1 klasse selv om det skal være 2 køer
    //  -ankomstTid, -
    // 1 rullebane
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
