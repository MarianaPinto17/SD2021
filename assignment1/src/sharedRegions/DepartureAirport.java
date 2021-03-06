package sharedRegions;

import commonInfrastructures.*;
import entities.*;
import main.SimulPar;

/**
 * Shared Region Departure Airport.
 */
public class DepartureAirport {
    /**
     * If a plane is ready to start boarding (controlled by the pilot).
     */
    private boolean readyForBoarding;

    /**
     * number of passengers at Queue.
     */
    private MemFIFO<Integer> passengersAtQueue;

    /**
     * plane at departure gate.
     */
    private boolean planeAtDeparture;

    /**
     * number of passengers in queue.
     */
    private int nPassengers;

    /**
     * number of checked passengers in queue.
     */
    private int nCheckedPassengers;

    /**
     * General repossitory of information.
     * @serialField repos
     */
    private GeneralRepository repos ;

    /**
     * Reference to passenger threads.
     */
    private final Passenger [] pass;

    /**
     * Reference to pilot.
     */
    private Pilot pi;

    /**
     * Reference to hostess.
     */
    private Hostess ho;

    /**
     * array of passengers who checked documents.
     */
    private boolean [] checkedPass;

    /**
     * if a passenger showed the documents to a hostess.
     */
    private boolean docShow;

    /**
     * if a passenger can board a plane.
     */
    private boolean canBoard;

    /**
     * if a plane is ready to take off.
     */
    private boolean informPlane;

    /**
     * false if hostess is WAIT_FOR_FLIGHT
     */
    private boolean waitPassengers;

    /**
     * Departure Airport constructor.
     * @param repos general repository of information
     */
    public DepartureAirport(GeneralRepository repos){
        nPassengers = 0;
        nCheckedPassengers = 0;
        pass = new Passenger[SimulPar.N];

        try{
            passengersAtQueue = new MemFIFO<>(new Integer[SimulPar.N]);
        }catch (MemException e){
            System.out.println ("Instantiation of waiting FIFO failed: " + e.getMessage ());
            passengersAtQueue = null;
            System.exit (1);
        }

        this.checkedPass = new boolean[SimulPar.N];
        for (int i = 0; i < SimulPar.N; i++) {
            this.checkedPass[i] = false;
        }

        docShow = false;
        canBoard = false;
        informPlane = false;
        waitPassengers = false;

        planeAtDeparture = true;

        this.repos = repos;
    }

    /**
     * Pilot function - says to hostess boarding can start.
     */
    public synchronized void informPlaneReadyForBoarding(){
        pi = (Pilot) Thread.currentThread();
        // pilot is at transfer gate and ready for boarding
        repos.setnFlights(repos.getnFlights()+1);
        pi.setCurrentState(PilotStates.READY_FOR_BOARDING);
        repos.setPilotState(PilotStates.READY_FOR_BOARDING);
        System.out.println("Plane ready for boarding.");

        // hostess can start boarding
        setReadyForBoarding(true);
        notifyAll();
    }

    /**
     * Hostess Function - prepare to start boarding passengers on the plane.
     */
    public synchronized void prepareForPassBoarding(){
        ho = (Hostess) Thread.currentThread();
        while(!isReadyForBoarding()){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        nCheckedPassengers = 0;
        ho.setCurrentState(HostessStates.WAIT_FOR_PASSENGER);
        repos.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        waitPassengers = true;

        notifyAll();

    }

    /**
     * Passenger function - passenger waits in queue to board the plane.
     */
    public synchronized void waitInQueue(){
        while(!waitPassengers){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        int passengerID = ((Passenger) Thread.currentThread()).getID();
        pass[passengerID] = (Passenger) Thread.currentThread();
        nPassengers++;
        repos.setInQ(nPassengers);
        pass[passengerID].setCurrentState(PassengerStates.IN_QUEUE);
        repos.setPassengerState(passengerID, PassengerStates.IN_QUEUE);
        // number of passengers in queue increases

        try{
            passengersAtQueue.write(passengerID);
        }catch (MemException e){
            System.out.println("Insertion of customer id in FIFO failed: " +e.getMessage());
            System.exit(1);
        }
        notifyAll();
    }

    /**
     * Hostess function - if a passenger in queue checks documents, waits for passenger to show documents.
     */
    public synchronized void checkDocuments(){
        while(nPassengers == 0){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        int checkDocID;
        try{
            checkDocID = passengersAtQueue.read();
        }catch (MemException e){
            System.out.println("Retrieval of passsenger ID from wainting FIFO failed." +e.getMessage());
            checkDocID = -1;
            System.exit(1);
        }

        nPassengers--;
        repos.setInQ(repos.getInQ() - 1);
        ho.setCurrentState(HostessStates.CHECK_PASSENGER);
        repos.setHostessState(HostessStates.CHECK_PASSENGER, checkDocID);

        this.checkedPass[checkDocID] = true;

        notifyAll();

        while (!docShow){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        docShow = false;
    }

    /**
     * Passenger function - passenger shows documents to hostess.
     */
    public synchronized void showDocuments() {
        while (!this.checkedPass[((Passenger) Thread.currentThread()).getID()]){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        docShow = true;

        notifyAll();

        while (!canBoard){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        canBoard = false;
    }

    /**
     *  Hostess function - hostess waits for passengers if plane not full and not min and passenger in queue.
     */
    public synchronized void waitForNextPassenger(){
        ho.setCurrentState(HostessStates.WAIT_FOR_PASSENGER);
        repos.setHostessState(HostessStates.WAIT_FOR_PASSENGER);

        this.canBoard = true;
        nCheckedPassengers++;

//        notifyAll();

        int passInDep = (SimulPar.N - repos.getPTAL());

        notifyAll();
        while(passengersAtQueue.isEmpty() && passInDep >=5 || (nCheckedPassengers != passInDep) && (passInDep < 5)){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        if((nCheckedPassengers >= 5 && passengersAtQueue.isEmpty()) || nCheckedPassengers == 10 || passInDep < 5){
            informPlane = true;
            planeAtDeparture = false;
        }

    }

    /**
     * Passenger function - passenger boards the plane.
     */
    public synchronized void boardThePlane() {
        int passengerID = ((Passenger) Thread.currentThread()).getID();

        repos.setInF(repos.getInF() + 1);
        pass[passengerID].setCurrentState(PassengerStates.IN_FLIGHT);
        repos.setPassengerState(passengerID, PassengerStates.IN_FLIGHT);

        notifyAll();
    }

    /**
     * Hostess function - hostess waits for the next flight of the day.
     */

    public synchronized void waitForNextFlight() {
        ho.setCurrentState(HostessStates.WAIT_FOR_FLIGHT);
        repos.setHostessState(HostessStates.WAIT_FOR_FLIGHT);

        informPlane = false;
        while(!planeAtDeparture){
            try{
                wait();
            } catch (InterruptedException e){}
        }

        notifyAll();

    }

    /**
     * Pilot function - when the pilot parks the plane at the Transfer gate.
     */
    public synchronized void parkAtTransferGate() {
        planeAtDeparture = true;

        pi.setCurrentState(PilotStates.AT_TRANSFER_GATE);
        repos.setPilotState(PilotStates.AT_TRANSFER_GATE);

        if(SimulPar.N == repos.getPTAL()){
            pi.setEndOfLife(true);
            ho.setEndOfLife(true);
        }

        notifyAll();

    }

    /**
     * Getters and setters.
     */

    /**
     * Checks if a plane is ready for boarding.
     * @return true if is ready.
     */
    public boolean isReadyForBoarding() {
        return readyForBoarding;
    }

    /**
     * Sets a plane ready for boarding.
     * @param readyForBoarding changes status of readyForBoarding.
     */
    public void setReadyForBoarding(boolean readyForBoarding) {
        this.readyForBoarding = readyForBoarding;
    }

    /**
     * Checks if a plane is ready for flying.
     * @return true if the boarding is over.
     */
    public boolean isInformPlane() {
        return informPlane;
    }

    /**
     * Sets the plane ready for flying.
     * @param informPlane changes status of informPlane.
     */
    public void setInformPlane(boolean informPlane) {
        this.informPlane = informPlane;
    }
}
