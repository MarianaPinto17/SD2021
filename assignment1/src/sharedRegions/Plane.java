package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.*;

/**
 * Shared Region Plane
 */
public class Plane {

    /**
     *  Number of passengers waiting to enter plane.
     */

    private int nPassengersWait;

    /**
     * Reference to Passenger threads.
     */

    private final Passenger[] pass;


    /**
     *  Waiting passengers in departure airport.
     */

    private MemFIFO<Integer> waitingPassenger;


    /**
     * Reference to general repository.
     */

    private final GeneralRepository repos;

    /**
     * Plane instantiation.
     * @param repos reference to general repository.
     */

    public Plane(GeneralRepository repos) {
        nPassengersWait = 0;

        pass = new Passenger[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++)
                pass[i] = null;

        try {
            waitingPassenger = new MemFIFO<> (new Integer [SimulPar.N]);
        } catch (MemException e) {
            System.out.println ("Instantiation of waiting FIFO failed: " + e.getMessage ());
            waitingPassenger = null;
            System.exit (1);
        }

        this.repos = repos;

    }

    /**
     * Passenger function - passenger waits for the flight to end
     */
    public synchronized void waitForEndOfFlight(){

    }


    /**
     * Pilot function - Pilot flies to destination.
     */

    public synchronized void flyToDestinationPoint(){

    }

    /**
     * Pilot function - Pilot flies back to departure.
     */

    public synchronized void flyToDeparturePoint(){

    }
}
