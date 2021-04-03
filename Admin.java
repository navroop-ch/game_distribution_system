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
    public void createUser(String userName, String type, Double credit) throws IOException {
        if(!data_base.ifUserExist(userName, dataBase.userData)) {

            // Create user object
            ArrayList<Game> gamesOwned = new ArrayList<>();
            User user = dataBase.generateUser(userName, type, credit, gamesOwned);

            // Store User object
            dataBase.writeUser(user);
            dataBase.writeBasicTransaction(data_base.createCode, userName, type, credit);
        }else{
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
    public void deleteUser(String userName) throws IOException {
        if(data_base.ifUserExist(userName, dataBase.userData)){
            System.out.println("In delete user, user exists");

            // Writing to daily.txt
            String[] UserData = dataBase.getUserData(userName,dataBase.userData).split(data_base.SEPARATOR);
            String type = UserData[1];
            Double credit = Double.parseDouble(UserData[2]);
            dataBase.writeBasicTransaction(data_base.deleteCode, userName, type, credit);

            // removing from database
            data_base.removeUserData(userName,dataBase.userData);
        }
    }

    /**
     * Adds credit to a certain user's account.
     * @param username username of the account to add credits to
     * @param credit the amount of credits
     */
    public void addCredit(String username, double credit){
        User user = dataBase.getUser(username);
        user.addCredit(credit);
        //Todo: write to transaction file.
    }

    /**
     * Activates the discounts on all games for sale
     * If an auctionsale is already on, this should conclude the auctionsale and disable the discounts
     */
    public void auctionSale(){
        if (Game.getAuctionStatus()){
            Game.setAutionStatus(false);
        }
        Game.setAutionStatus(true);
    }

    /**
     * Issue a credit from the buyer's account to the seller's account
     * @param buy_username username of the account to add transfer_amt to
     * @param sell_username username of the account to remove transer_amt from
     * @param transfer_amt the amount to be refunded
     */
    protected void refund(String buy_username, String sell_username, double transfer_amt){
        User buyer = dataBase.getUser(buy_username);
        User seller = dataBase.getUser(sell_username);
        if (transfer_amt < 0.0){
            System.out.println("Amount to be refunded cannot be negative!")
        }
        else if (seller.credit < transfer_amt){
            System.out.println("Seller doesn't have enough credit. Refund Failed!")
        }
        else if (buyer.credit + transfer_amt > MAX_ALLOWED_CREDIT){
            System.out.println("The maximum credit has been exceeded. Refund Failed!")
        }
       // Check if they are current users, i.e. they exist in the users' list
        else if (!data_base.ifUserExist(buy_username, buyer.dataBase.userData) || !data_base.ifUserExist(sell_username,
                seller.dataBase.userData)){
            System.out.println("Not a current user. Refund Failed!")
        }
        else {
            seller.credit -= transfer_amt;
            buyer.credit += transfer_amt; // there's no refund limit
            //TODO: write to daily.txt
            System.out.println("Refund completed.")
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
        data_base dataBase = new data_base();

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

        System.out.println(admin0);

        dataBase.writeUser(admin0);

        admin0.login();

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
