import java.lang.reflect.Array;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * A session object that controls exchange of data between User objects and the database. A session must be logged into
 * for a user to get access to the database to perform any transactions.
 */
public class Session {

    protected static final String CONSTRAINT_ERROR = "| Constraint Error | ";

    private static Session instance = null; // For singleton

    private static byte[] dataBaseKey;
    private static final SecureRandom random = new SecureRandom();
    private static data_base dataBase;
    private static ArrayList<User> userList; // static so that all sessions have the same userList?
    private User userLoggedIn = null;
    private boolean loginStatus;
    static boolean auctionStatus = false;

    /**
     * Initializes a new session and loads all users from data base to userList
     */
    private Session() {
        if (userList == null) {
            userList = dataBase.loadUsers(dataBase.userData);
        }
    }

    /**
     * Generates a key for accessing a data base to ensure only one session object is created
     */
    private static void generateKey() {
        dataBaseKey = random.generateSeed(10);
    }

    /**
     * Checks whether a key is dataBaseKey
     * @param key session key
     * @return true if list key equals dataBaseKey
     */
    protected static boolean authenticateKey(byte[] key) {
        return Arrays.equals(key, dataBaseKey);
    }

    /**
     *  Sets the database
     * @param db database
     */
    private static void setDataBase(data_base db) {
        dataBase = db;
    }

    // Singleton implementation

    /**
     * Creates a new instance of session key, stores it and then passes it for authentication
     * @return instance of session key
     */
    protected static Session getInstance() {
        if (instance == null) {
            generateKey();
            data_base dataBase = data_base.getInstance(dataBaseKey);
            setDataBase(dataBase);
            instance = new Session();
        }
        return instance;
    }

    /**
     * Sets the auction status of game
     * @param auctionStatus status of the discount auction on games
     */
    protected void setAuctionStatus(Boolean auctionStatus){auctionStatus = auctionStatus;}

    /**
     * Returns the auction status of game
     * @return auction status of a game
     */
    protected Boolean getAuctionStatus(){return auctionStatus;}

    /**
     * Returns the data base for session
     * @param user a user object
     * @return data_base if the user is logged in, false otherwise
     */
    protected data_base getDataBase(User user) {
        if (this.userLoggedIn == user) {
            return dataBase;
        } else {
            return null;
        }
    }

    /**
     * Logs in a user
     * @param user a user object
     */
    protected void sessionLogin(User user) {
        if (this.userLoggedIn == null) {
            this.userLoggedIn = user;
            this.loginStatus = true;
        }
    }

    /**
     * Logs out a user
     * @param user a user object
     */
    protected void sessionLogout(User user) {
        if (this.userLoggedIn == user) {
            this.userLoggedIn = null;
            this.loginStatus = false;
        }
    }

    /**
     * Checks if the user is in the userList and returns the user
     * @param username username of a user
     * @return the user onject
     */
    protected User getUser(String username) {
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the list of users
     * @return userList - a list of user objects
     */
    protected ArrayList<User> getUserList(){
        return userList;
    }

    /**
     * Return whether a user is logged in or not
     * @return the login status of a user
     */
    protected boolean getLoginStatus() {
        return loginStatus;
    }

    /**
     * Goes through all transactions and excutes each one and updates the data base in the end.
     */
    protected void executeBackend() {

        //Get transaction objects from daily.txt
        ArrayList<Transaction> transactions = dataBase.getTransactions();

        int transIndex = 0;
        //System.out.println("Starting:");
        while (transIndex < transactions.size()) {
            // Look for a login transaction and logs in a user
            transIndex = logUserIn(transactions, transIndex);

            // Go through valid transactions until the user logs out
            transIndex = executeUserTransactions(transactions, transIndex);
        }
        dataBase.updateDataBase(userList);

    }

    /**
     * Adds a user to the userList
     * @param user a user
     */
    protected void addUserList(User user){
        userList.add(user);
    }

    /**
     * Executes the transactions in the transactions array list
     * @param transactions array of transactions
     * @param transIndex index in the transactions list
     * @return the index in transaction list where the transaction fails to execute
     */
    private int executeUserTransactions(ArrayList<Transaction> transactions, int transIndex) {
        while (transIndex < transactions.size() && loginStatus) {

            Transaction transaction = transactions.get(transIndex);
            if (!transaction.validTransaction){continue;}
            transaction.execute(this);
            transIndex++;
        }
        return transIndex;
    }

    /**
     * Finds the login transaction inside transactions array list
     * @param transactions array of transactions
     * @param transIndex index
     * @return index of the login transaction in the array list of transactions
     */
    private int logUserIn(ArrayList<Transaction> transactions, int transIndex) {
        while (transIndex < transactions.size()) {
            Transaction transaction = transactions.get(transIndex);
            if (transaction.validTransaction) {
                if (transaction.transactionCode.equals(data_base.logInCode)) {
                    transaction.execute(this);
                    transIndex++;
                    break;
                }
            }
            transIndex++;
        }
        return transIndex;
    }


    /**
     * Returns the username of logged in user
     * @return username of logged in user
     */
    protected String getLoggedInUserName(){
        return this.userLoggedIn.getUserName();
    }

    /**
     * Returns the user object of a logged in user
     * @return returns the user object
     */
    protected User getUserLoggedIn(){
        return this.userLoggedIn;
    }

    /**
     * Removes a user from the userList
     * @param user user object
     */
    protected void removeFromUserList(User user) {
        userList.add(user);
    }
}

