import java.io.IOException;
import java.util.ArrayList;


/**
 * An Admin class consist of all the functions that an Admin can do.
 *
 */

public class Admin implements User{
    private String userName;
    private String type="AA";
    private double credit;
    private ArrayList<String> gameOwned;

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
        this.credit = credit;
        this.gameOwned = gameOwned;

    }

    /**
     * Returns the current admin's username.
     *
     * @return userName of this admin
     */
    @Override
    public String getUserName(){
        return this.userName;
    }


    /**
     * Returns the current admin's user type.
     *
     * @return "AA" for Admin
     */
    @Override
    public String getType(){
        return this.type;
    }


    /**
     * Returns the current admin's current credit.
     *
     * @return credits
     */
    @Override
    public double getCredit(){
        return this.credit;
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
     * @para  the change in the amount of credit.
     * if the user has their credit reduced, the parameter should be a negative number
     *
     */
    @Override
    public void addCredit(double credit){
        this.credit+=credit;
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

}
