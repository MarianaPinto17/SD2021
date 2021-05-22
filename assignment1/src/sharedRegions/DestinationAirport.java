package sharedRegions;

import entities.*;
import main.SimulPar;

/**
 * Shared Region Destination Airport
 */
public class DestinationAirport {

    /**
     *
     */
    private final Passenger[] pass;

    /**
     *
     */
    private Pilot pi;

    /**
     *
     */
    private final GeneralRepository repos;
    /**
     *
     */
    private boolean arrivedDestination;

    /**
     * Destination Airport constructor
     * @param repos general repository of information
     */

    public DestinationAirport(GeneralRepository repos){
        pass = new Passenger[SimulPar.N];
        this.repos = repos;
    }

    /**
     * Pilot function - pilot announces that the plane arrived at destination
     */
    public synchronized void announceArrival(){
        pi = (Pilot) Thread.currentThread();
        pi.setCurrentState(PilotStates.DEBOARDING);
        repos.setPilotState(PilotStates.DEBOARDING);

        System.out.println(repos.getInF());

        notifyAll();

        while (repos.getInF() > 0){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    /**
     * Passenger function - when the plane arrives at destination the passenger exits the plane.
     */
    public synchronized void leaveThePlane(){
        int passengerID = ((Passenger) Thread.currentThread()).getID();

        pass[passengerID] = (Passenger) Thread.currentThread();

        repos.setInF(repos.getInF() - 1);
        repos.setPTAL(repos.getPTAL() + 1);

        if(repos.getInF() == 0){
            repos.setArrivedAtDest(false);
            repos.setEmptyPlaneDest(true);
        }
        pass[passengerID].setCurrentState(PassengerStates.AT_DESTINATION);
        repos.setPassengerState(passengerID, PassengerStates.AT_DESTINATION);

        notifyAll();
    }
}
