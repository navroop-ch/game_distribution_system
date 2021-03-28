import java.util.ArrayList;

public class FullStandardUser extends User{

    protected FullStandardUser(String username, double credit, ArrayList<Game> gameOwned){
        super(username,FULL_USER_TYPE, credit, gameOwned);
    }

}
