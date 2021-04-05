import java.util.ArrayList;

/**
 * A class for user of type full standard that has username, credit and an array of games owned
 */
public class FullStandardUser extends User{

    protected FullStandardUser(String username, double credit, ArrayList<Game> gameOwned){
        super(username,FULL_USER_TYPE, credit, gameOwned);
    }

}
