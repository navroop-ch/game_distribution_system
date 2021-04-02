import java.util.ArrayList;


public class Buyer extends User{

    protected Buyer(String username, double credit, ArrayList<Game>gameOwned){
        super(username,BUYER_USER_TYPE, credit, gameOwned);
    }

    @Override
    protected void sell(String title, Double price, Double saleDiscount){
        //Todo: Send warning via an exception
        System.out.println("Your user type is not allowed to sell games!");
    }
}
