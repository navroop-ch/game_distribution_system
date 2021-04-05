import java.util.ArrayList;

/**
 * A Buyer user class with username, credit and games owned.
 */
public class Buyer extends User{

    protected Buyer(String username, double credit, ArrayList<Game>gameOwned){
        super(username,BUYER_USER_TYPE, credit, gameOwned);
    }

    @Override
    protected String sell(String title, Double price, Double saleDiscount){

        return  "Your user type is not allowed to sell games!";

    }
}
