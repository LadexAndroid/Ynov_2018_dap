package fr.ynov.dap.client;
import fr.ynov.dap.client.manager.NetworkManager;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This is the entry point class of the client application.
 */
public class Launcher {

    /**
     * The logger of the fr.ynov.dap.client.Launcher class.
     */
    private static Logger logger = Logger.getLogger(Launcher.class.getName());

    /**
     * This method is used as the entry point of the client application.
     * @param args The different arguments passed into the command line used to execute the jar.
     */
    public static void main(String[] args) {
        try {
            checkCommandParameters(args);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * This method checks the different argument passed during the execution of the jar file.
     * @param arguments The different arguments passed into the command line used to execute the jar.
     * @throws IOException The exception which is thrown.
     */
    private static void checkCommandParameters(String[] arguments) throws IOException {
            if (arguments.length > 0) {

            if (arguments[0].equals("view")){
                if (arguments.length > 1){
                    getUserData(arguments[1]);
                } else {
                    logger.info("Please provide the userKey after the 'view' command keyword and try again;\tThank you");
                }
            } else if (arguments[0].equals("add")){
                if (arguments.length > 1){
                    NetworkManager.addUser(arguments[1]);
                } else {
                    logger.info("Please provide the userKey after the 'add' command keyword and try again;\tThank you");
                }
            } else {
                logger.info("Hello dear user. \t Please provide a command keyword and try again; Thank you");
            }
        }
    }

    /**
     * This method returns all the information to the currently connected client user.
     * @param userKey This is the userKey of the currently authenticated user.
     * @throws IOException In case the NetworkingManager methods throws, we handle the error.
     */
    private static void getUserData(String userKey) throws IOException {
        NetworkManager.getNumberOfUnreadEmails(userKey);
        NetworkManager.getNumberOfContacts(userKey);
        NetworkManager.getUpComingEvent(userKey);
    }

}
