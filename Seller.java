import java.util.ArrayList;

/**
 * A user of type sell standard with username, credit and an array of games owned.
 */
public class Seller extends User{

    protected Seller(String username, double credit, ArrayList<Game> gameOwned){
        super(username, SELLER_USER_TYPE, credit, gameOwned);
    }

    /**
     * A sell-standard user can't buy a game
     * @param title The title of the game.
     * @param sellerName The username of the seller.
     */
    @Override
    protected void buy(String title, String sellerName){
        System.out.println("Your user type cannot buy games!");
    }
}
