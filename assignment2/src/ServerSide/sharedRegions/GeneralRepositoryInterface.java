package ServerSide.sharedRegions;
import ServerSide.main.*;
import ServerSide.entities.*;
import commonInfrastructures.*;

/**
 *  Interface to the General Repository of Information.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralRepositoryInterface {

    /**
     *  Reference to the general repository.
     */

    private final GeneralRepository repos;

    /**
     *  Instantiation of an interface to the general repository.
     *
     *    @param repos reference to the general repository
     */

    public GeneralRepositoryInterface (GeneralRepository repos)
    {
        this.repos = repos;
    }

    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *
     *    @param inMessage service request
     *    @return service reply
     *    @throws MessageException if the incoming message is not valid
     */

    public Message processAndReply (Message inMessage) throws MessageException
    {
        Message outMessage = null;                                     // mensagem de resposta

        /* validation of the incoming message */

        switch (inMessage.getMsgType ()) {
            case SETNFIC:
                if (inMessage.getFilename() == null)
                    throw new MessageException ("Name of the logging file is not present!", inMessage);
                break;

            case SET_PILOT_STATE:
                if ((inMessage.getState() < PilotStates.AT_TRANSFER_GATE.value) || (inMessage.getState() > PilotStates.FLYING_BACK.value))
                    throw new MessageException ("Invalid Pilot state!", inMessage);
                break;

            case SET_HOSTESS_STATE:
                if ((inMessage.getState () < HostessStates.WAIT_FOR_FLIGHT.value) && (inMessage.getState() != HostessStates.READY_TO_FLY.value))
                    throw new MessageException ("Invalid Hostess state!", inMessage);
                break;

            case SET_PASSENGER_STATE:
                if ((inMessage.getPassId() < 0) || (inMessage.getPassId() >= SimulPar.N))
                    throw new MessageException ("Invalid passenger id!", inMessage);
                else if ((inMessage.getState() < PassengerStates.GOING_TO_AIRPORT.value) || (inMessage.getState() > PassengerStates.AT_DESTINATION.value))
                    throw new MessageException ("Invalid passenger state!", inMessage);
                break;

            case SHUT:     // check nothing
                break;
            default:
                throw new MessageException ("Invalid message type!", inMessage);
        }

        /* processing */

        switch (inMessage.getMsgType ()) {
            case SETNFIC:
                repos.initSimul(inMessage.getFilename());
                outMessage = new Message (MessageType.DONE_NFIC);
                break;
            case SET_PILOT_STATE:

                repos.setPilotState(inMessage.getState());
                outMessage = new Message (MessageType.DONE_SPiS);
                break;

            case SET_HOSTESS_STATE:
                if(inMessage.getPassId() >= 0)
                    repos.setHostessState(inMessage.getState(), inMessage.getPassId());
                else
                    repos.setHostessState(inMessage.getState());
                outMessage = new Message (MessageType.DONE_SHS);
                break;
            case SET_PASSENGER_STATE:
                repos.setPassengerState(inMessage.getPassId(), inMessage.getState());
                outMessage = new Message (MessageType.DONE_SPaS);
                break;
            case SHUT:
                repos.shutdown ();
                outMessage = new Message (MessageType.DONE_S);
                break;
        }

        return (outMessage);
    }
}