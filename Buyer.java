import java.util.ArrayList;

public class Buyer extends User{

    protected Buyer(String username, double credit, ArrayList<String> gameOwned){
        super(username, credit, gameOwned);
        this.type = BUYER_USER_TYPE;
    }

    @Override
    protected void sell(String title, Double price, Double saleDiscount){
        //Todo: Send warning via an exception
        System.out.println("Your user type is not allowed to sell games!");
    }
}
