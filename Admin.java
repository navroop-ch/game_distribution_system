import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * An Admin class consist of all the functions that an Admin can do.
 *
 */

public class Admin extends User{


    /**
     * Constructs an Admin that pass in existing admin information
     * from data base and allocate memory to it while the admin logged in.
     *
     * All parameters are loaded in from database.
     *
     * @param username	String representing the username
     * @param credit	A double representing the admin's credit for the moment they
     *                  logged in
     * @param gameOwned  An ArrayList that store all the game owning by the admin.
     */
    public Admin(String username, double credit, ArrayList<Game> gameOwned){
        super(username, ADMIN_USER_TYPE, credit, gameOwned);
    }

    /**
     * @param userName The front end will ask for the new username
     * @param type The front end will ask for the type of user
     *             AA=admin,
     *             FS=full-standard,
     *             BS=buy-standard,
     *             SS=sell-standard
     * @param credit The front end will ask for the initial account balance of the new user
     * This information is saved to the daily transaction file and our data base
     *
     * Since only Admin can call this method, it can only be called by an admin constructor
     *
     * NOTE: This method WILL NOT check the following constraints
     *        1. new user name is limited to at most 15 characters
     *        2. maximum credit can be 999,999
     */
    public void createUser(String userName, String type, Double credit) {
        if (session.getUser(userName) == null) {

            // Create user object
            ArrayList<Game> gamesOwned = new ArrayList<>();
            User user = dataBase.generateUser(userName, type, credit, gamesOwned);
            session.getUserList().add(user);
            System.out.printf("%s: Created new user %s! \n", this.getUserName(), user.getUserName());

        } else{
            System.out.println("User exists");
        }
    }


    /**
     * @param userName The front end will ask for the new username
     *
     * Since only Admin can call this method, it can only be called by an admin constructor
     *
     * The user with the corresponding user name will be removed from our data base
     * and this action will be written to the daily.txt
     *
     * All constrains will be checked in this method except for further transaction
     */
    public void deleteUser(String userName) {
        if (session.getUser(userName) != null) {
            session.getUserList().remove(session.getUser(userName));
            System.out.printf("%s: Deleted user %s! \n", this.getUserName(), userName);

        }else {
            System.out.println("User does not exist");
        }
    }

    /**
     * Adds credit to a certain user's account.
     * @param username username of the account to add credits to
     * @param credit the amount of credits
     */
    public void addCredit(String username, double credit){
        User user = session.getUser(username);
        user.addCredit(credit);
    }

    /**
     * Activates the discounts on all games for sale
     * If an auctionsale is already on, this should conclude the auctionsale and disable the discounts
     */
    public void auctionSale(){
        session.setAuctionStatus(!session.getAuctionStatus());
    }

    /**
     * Issue a credit from the buyer's account to the seller's account
     * @param buy_username username of the account to add transfer_amt to
     * @param sell_username username of the account to remove transer_amt from
     * @param transfer_amt the amount to be refunded
     */
    protected void refund(String buy_username, String sell_username, double transfer_amt){
        User buyer = session.getUser(buy_username);
        User seller = session.getUser(sell_username);
        double buy_crd = buyer.getCredit();
        double sell_crd = seller.getCredit();

        if (transfer_amt < 0.0){
            System.out.println("Amount to be refunded cannot be negative!");
        }
        else if (sell_crd < transfer_amt){
            System.out.println("Seller doesn't have enough credit. Refund Failed!");
        }
        else if (buy_crd + transfer_amt > MAX_ALLOWED_CREDIT){
            System.out.println("The maximum credit has been exceeded. Refund Failed!");
        }
        else if (!session.getUserList().contains(buyer) || !session.getUserList().contains(seller)){
            System.out.println("Not a current user. Refund Failed!");
        }
        else {
            seller.changeCredit(transfer_amt, "sub");
            buyer.changeCredit(transfer_amt, "add");
            System.out.println("Refund completed.");
        }

    }

    /**
     * In Admin mode removes a game from owner's and receiver's inventory
     * @param title name of the game
     * @param owner username of game's owner
     * @param receiver username of game's receiver
     */
    protected void removeGame(String title, String owner, String receiver) {
        User buyer = session.getUser(owner);
        User seller = session.getUser(receiver);
        Game game = buyer.owned(title);

        if (seller != null && buyer != null && game != null && seller.owned(title).isForSale()) {
            buyer.removeGame(game);
            seller.removeGame(game);
        }
        else {
            System.out.println("Error: Could not remove the game");
        }

    }

    /**
     * In Admin mode gifts a game titled 'title' from owner_name to receiver_name
     * @param title name of the game
     * @param owner_name username of the game's owner
     * @param receiver_name username of the game's receiver
     */

    protected void giftGame(String title, String owner_name, String receiver_name){
        User owner = session.getUser(owner_name);
        User receiver = session.getUser(receiver_name);
        Game game1 = owner.owned(title);
        Game game2 = receiver.owned(title);



        if (owner != null && receiver != null && game1 != null && !game1.getTitle().equals(game2.getTitle())){
            owner.removeGame(game1);
            receiver.addOwnedGame(game1);
        }
        else {
            System.out.println("Error: Couldn't gift the game");
        }
    }

    /**
     * @param userName The front end will ask for the new username
     *
     * Since the admin can get info of different users. This function will provide all the info of the user.
     */
    public String getInfo(String userName){

        return " ";
    }


    public static void main(String[] args){

        ArrayList<Game> ownedGames = new ArrayList<Game>();
        ownedGames.add(new Game("Fortnite", 350.34,true));
        ownedGames.add(new Game("Rsix siege", 550.34,true));
        ownedGames.add(new Game("Call of duty", 550.34,true));
        Admin admin0 = new Admin("David", 999000, ownedGames);

        FullStandardUser u = new FullStandardUser("David", 2344.00, ownedGames);

        String[] games = admin0.gamesOwnedToString().split("#");
        for (String g: games){
            System.out.println(g);
        }

        Session session = Session.getInstance();
        System.out.println(admin0);
        admin0.login();
        data_base dataBase = session.getDataBase(admin0);
        dataBase.writeUser(admin0);


        System.out.println(admin0);

        admin0.addCredit(400);
        System.out.println(admin0);

        admin0.addCredit(500);
        System.out.println(admin0);

        admin0.addCredit(100);
        System.out.println(admin0);

        admin0.addCredit(100);
        System.out.println(admin0);



    }

}
