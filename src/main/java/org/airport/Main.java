package org.airport;

import org.airport.module.Airport;

public class Main {
    public static void main(String[] args) {

        Airport ap = new Airport("LA");
        ap.simulate();
    }
}
