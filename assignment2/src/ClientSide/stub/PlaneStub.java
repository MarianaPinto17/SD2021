package ClientSide.stub;

import commonInfrastructures.*;
import ClientSide.entities.*;

public class PlaneStub {

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
    public PlaneStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    public void waitForAllInBoard(){
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

        outMessage = new Message(MessageType.WAIT_FOR_ALL_IN_BOARD);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFAIB){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getPilotState());

        com.close();
    }

    public void flyToDestinationPoint(){
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

        outMessage = new Message(MessageType.FLY_TO_DESTINATION_POINT);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_FTDesP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getPilotState());

        com.close();
    }

    public void waitForEndOfFlight(){
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

        outMessage = new Message(MessageType.WAIT_FOR_END_OF_FLIGHT);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_WFEOF){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        com.close();
    }

    public void flyToDeparturePoint(){
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

        outMessage = new Message(MessageType.FLY_TO_DEPARTURE_POINT);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_FTDepP){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Pilot) Thread.currentThread()).setCurrentState(inMessage.getPilotState());

        com.close();
    }

    public void informPlaneReadyToTakeOff(){
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

        outMessage = new Message(MessageType.INFORM_PLANE_READY_TO_TAKEOFF);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();

        if (inMessage.getMsgType() != MessageType.DONE_IPRTT){
            System.out.println("Invalid return message from server!!");
            System.exit(1);
        }

        ((Hostess) Thread.currentThread()).setCurrentState(inMessage.getHostessState());

        com.close();
    }


}