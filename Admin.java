import java.io.IOException;
import java.util.ArrayList;


/**
 * An Admin class consist of all the functions that an Admin can do.
 *
 */

public class Admin extends User{

    // Todo: create a Session object and use an instance of that instead of data_base
    private data_base dataBase;

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
    public Admin(String username, double credit, ArrayList <String> gameOwned){
        this.userName = username;
        this.type = ADMIN_USER_TYPE;
        this.credit = credit;
        this.gameOwned = gameOwned;

    }

    /**
     * Sets the instance variable for the database
     * @param dataBase instance of the database object
     */
    private void setDataBase(data_base dataBase){
        this.dataBase = dataBase;
    }


    /**
     * Returns the current admin's game inventory.
     *
     * @return an ArrayList of String for the game name the admin own.
     */
    @Override
    public ArrayList <String> getOwnedGame(){
        return this.gameOwned;
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
     * @para  The name of a new game.
     *
     * It will add the game to the ArrayList
     */
    @Override
    public void addOwnedGame(String game){
        this.gameOwned.add(game);
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
    public void createUser(String userName, String type, String credit) throws IOException {
        if(!data_base.ifUserExist(userName, data_base.userData)) {
            String userData = userName + " " + type+" "+credit;
            String transaction = User.createCode + " " + userName + " " + type + " " + credit;

            data_base.appendData(userData, data_base.userData);
            data_base.appendData(transaction, data_base.dailyData);
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
        if(data_base.ifUserExist(userName, data_base.userData)){
            System.out.println("In delete user, user exists");
            String transaction = User.deleteCode+" "+ data_base.getUserData(userName, data_base.userData);
            data_base.removeUserData(userName,data_base.userData);
            System.out.println(transaction);
            data_base.appendData(transaction, data_base.dailyData);
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
        ArrayList<String> ownedGames = new ArrayList<String>();
        Admin admin0 = new Admin("George", 999000, ownedGames);
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
