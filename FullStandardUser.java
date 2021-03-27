import java.util.ArrayList;

public class FullStandardUser extends User{

    protected FullStandardUser(String username, double credit, ArrayList <String> gameOwned){
        super(username, credit, gameOwned);
        this.type = FULL_USER_TYPE;
    }

}
