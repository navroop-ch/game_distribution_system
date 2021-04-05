import java.util.ArrayList;

/**
 * Creates a session instance and execute the backend.
 */
public class SessionClient {

    public static void main(String[] args){

        Session session = Session.getInstance();
        session.executeBackend();
    }
}
