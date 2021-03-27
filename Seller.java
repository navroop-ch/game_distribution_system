import java.util.ArrayList;

public class Seller extends User{

    protected Seller(String username, double credit, ArrayList <String> gameOwned){
        super(username, credit, gameOwned);
        this.type = SELLER_USER_TYPE;
    }

    @Override
    protected void buy(String title, String sellerName){
        //Todo: Return warning via exception
        System.out.println("Your user type cannot buy games!");
    }
}
