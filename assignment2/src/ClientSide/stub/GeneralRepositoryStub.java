package ClientSide.stub;

import ClientSide.entities.Hostess;
import ClientSide.entities.HostessStates;
import ClientSide.entities.PassengerStates;
import ClientSide.entities.PilotStates;
import commonInfrastructures.*;

public class GeneralRepositoryStub {

    /**
     * Name of the computational system where the server is located.
     */
    private final String serverHostName;

    /**
     * Number of the listening port at the computational system where the server
     * is located.
     */
    private final int serverPortNumb;

    /**
     * Instantiation of a remote reference
     *
     * @param hostName name of the computational system where the server is
     * located
     * @param portNumb number of the listening port at the computational system
     * where the server is located
     */
    public GeneralRepositoryStub(String hostName, int portNumb) {
        serverHostName = hostName;
        serverPortNumb = portNumb;
    }

    /**
     *   Operation initialization of the simulation.
     *
     *     @param fileName logging file name
     */

    public void initSimul (String fileName)
    {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SETNFIC, fileName);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_NFIC)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Pilot state.
     *
     * @param state pilot state
     */
    public void setPilotState(PilotStates state){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_PILOT_STATE, state.value);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SPiS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Hostess state.
     *
     * @param state hostess state
     */
    public void setHostessState(HostessStates state){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_HOSTESS_STATE, state.value);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SHS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Hostess state and prints to log the passenger who checked.
     *
     * @param state hostess state
     * @param pid passenger id
     */
    public void setHostessState(HostessStates state, int pid){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_HOSTESS_STATE, state.value, pid);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SHS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     * Set Passenger state.
     *
     * @param state passenger state
     * @param pid passenger id
     */
    public void setPassengerState(PassengerStates state, int pid){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SET_PASSENGER_STATE, state.value, pid);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_SPaS)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public void shutdown ()
    {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SHUT);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.DONE_S)
        { System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            System.out.println(inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }
}
