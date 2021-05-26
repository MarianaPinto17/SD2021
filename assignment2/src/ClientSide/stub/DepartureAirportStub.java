package ClientSide.stub;

import commonInfrastructures.*;
import ClientSide.entities.*;

public class DepartureAirportStub {
    /**
     *  Name of the platform where is located the Departure Airport server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *   Instantiation of a stub to the Departure Airport.
     *
     *     @param serverHostName name of the platform where is located the Departure Airport server
     *     @param serverPortNumb port number for listening to service requests
     */
    public DepartureAirportStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    public void informPlaneReadyForBoarding() {
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.INFORM_PLANE_READY_FOR_BOARDING, ((Pilot) Thread.currentThread()).getCurrentState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IPRFB || inMessage.getState() < PilotStates.AT_TRANSFER_GATE.value || inMessage.getState() > PilotStates.FLYING_BACK.value){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void prepareForPassBoarding() {
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.PREPARE_FOR_PASS_BOARDING);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_PFPB || inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT.value || inMessage.getState() > HostessStates.READY_TO_FLY.value) {
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void waitInQueue(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.WAIT_IN_QUEUE, ((Passenger) Thread.currentThread()).getCurrentState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WIQ){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getPassId() != ((Passenger) Thread.currentThread()).getID()){
            System.out.println("Invalid return passenger id!!");
            System.exit(1);
        }
        if (inMessage.getState() < PassengerStates.GOING_TO_AIRPORT.value || inMessage.getState() > PassengerStates.AT_DESTINATION.value){
            System.out.println("Invalid return passenger state!!");
            System.exit(1);
        }

        ((Passenger) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void checkDocuments(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.CHECK_DOCUMENTS, ((Hostess) Thread.currentThread()).getCurrentState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_CD){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT.value || inMessage.getState() > HostessStates.READY_TO_FLY.value){
            System.out.println("Invalid return hostess state!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void showDocuments(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.SHOW_DOCUMENTS, ((Passenger) Thread.currentThread()).getCurrentState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_SD){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        com.close();
    }

    public void waitForNextPassenger(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.WAIT_FOR_NEXT_PASSENGER, ((Hostess) Thread.currentThread()).getCurrentState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFNP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT.value || inMessage.getState() > HostessStates.READY_TO_FLY.value){
            System.out.println("Invalid return hostess state!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void boardThePlane(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.BOARD_THE_PLANE,  ((Passenger) Thread.currentThread()).getCurrentState(), ((Passenger) Thread.currentThread()).getID());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_BTP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getPassId() != ((Passenger) Thread.currentThread()).getID()){
            System.out.println("Invalid return passenger id!!");
            System.exit(1);
        }
        if (inMessage.getState() < PassengerStates.GOING_TO_AIRPORT.value || inMessage.getState() > PassengerStates.AT_DESTINATION.value){
            System.out.println("Invalid return passenger state!!");
            System.exit(1);
        }

        ((Passenger) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void waitForNextFlight(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.WAIT_FOR_NEXT_FLIGHT, ((Hostess) Thread.currentThread()).getCurrentState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFNF){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getState() < HostessStates.WAIT_FOR_FLIGHT.value || inMessage.getState() > HostessStates.READY_TO_FLY.value){
            System.out.println("Invalid return hostess state!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public void parkAtTransferGate(){
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.PARK_AT_TRANSFER_GATE, ((Pilot) Thread.currentThread()).getCurrentState());
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_PATG){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }
        if (inMessage.getState() < PilotStates.AT_TRANSFER_GATE.value || inMessage.getState() > PilotStates.FLYING_BACK.value){
            System.out.println("Invalid return pilot state!!");
            System.exit(1);
        }



        ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getState());

        com.close();
    }

    public boolean isInformPlane() {
        ClientCom com = new ClientCom(serverHostName, serverPortNumb);  // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        while (!com.open()) // open the connection
        {
            try {
                Thread.currentThread().sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(MessageType.IS_INFORM_PLANE);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IIP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        boolean informPlane = inMessage.isInformPlane();

        com.close();

        return informPlane;

    }
}