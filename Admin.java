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
    public Admin(String username, double credit, ArrayList <String> gameOwned){
        super(username, credit, gameOwned);
        this.type = ADMIN_USER_TYPE;

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
        if(!data_base.ifUserExist(userName, data_base.userData)) {

            String userData = userName + " " + type + " " + credit;
            data_base.appendData(userData, data_base.userData);

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
        if(data_base.ifUserExist(userName, data_base.userData)){
            System.out.println("In delete user, user exists");

            // Writing to daily.txt
            String[] UserData = dataBase.getUserData(userName,data_base.userData).split(data_base.SEPARATOR);
            String type = UserData[1];
            Double credit = Double.parseDouble(UserData[2]);
            dataBase.writeBasicTransaction(data_base.deleteCode, userName, type, credit);

            // removing from database
            data_base.removeUserData(userName,data_base.userData);
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
