import java.lang.reflect.Array;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Arrays;

// A session object that controls exchange of data between User objects and the database. A session must be logged into
// for a user to get access to the database to perform any transactions.

public class Session {

    private static Session instance = null; // For singleton

    private static byte[] dataBaseKey;
    private static final SecureRandom random = new SecureRandom();
    private static data_base dataBase;
    private static ArrayList<User> userList; // static so that all sessions have the same userList?
    private User userLoggedIn = null;
    private boolean loginStatus;
    static boolean auctionStatus = false;
    static Statistics stats = new Statistics(userList);

    private Session() {
        // Todo: load users into userList by creating a method for it in data_base.java
        // Loads users into session object only once. If users
        if (userList == null) {
            userList = dataBase.loadUsers(dataBase.userData);
        }
    }

    private static void generateKey() {
        dataBaseKey = random.generateSeed(10);
    }

    protected static boolean authenticateKey(byte[] key) {
        return Arrays.equals(key, dataBaseKey);
    }


    private static void setDataBase(data_base db) {
        dataBase = db;
    }

    // Singleton implementation
    protected static Session getInstance() {
        if (instance == null) {
            instance = new Session();

            // Authentication: generates a new key, stores it, and then passes it for authentication
            generateKey();
            data_base dataBase = data_base.getInstance(dataBaseKey);
            setDataBase(dataBase);
        }
        return instance;
    }
    
    protected void setAutionStatus(Boolean autionStatus){auctionStatus = autionStatus;}

    protected Boolean getAuctionStatus(){return auctionStatus;}

    protected data_base getDataBase(User user) {
        if (this.userLoggedIn == user) {
            return dataBase;
        } else {
            return null;
        }
    }

    protected void sessionLogin(User user) {
        if (this.userLoggedIn == null) {
            this.userLoggedIn = user;
            this.loginStatus = true;
        }
    }

    protected void sessionLogout(User user) {
        if (this.userLoggedIn == user) {
            this.userLoggedIn = null;
            this.loginStatus = false;
        }
    }

    protected User getUser(String username) {
        for (User user : this.userList) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    protected ArrayList<User> getUserList(){
        return userList;
    }


    protected boolean getLoginStatus() {
        return loginStatus;
    }

    private void executeBackend() {

        //Get transaction objects from daily.txt
        ArrayList<Transaction> transactions = dataBase.getTransactions();

        int transIndex = 0;

        while (transIndex < transactions.size()) {
            // Look for a login transaction and login a user
            transIndex = logUserIn(transactions, transIndex);

            // Go through valid transactions until the user logs out
            transIndex = executeUserTransactions(transactions, transIndex);
        }

        // Todo: call method to save data to database
        dataBase.updateDataBase();

    }

    private int executeUserTransactions(ArrayList<Transaction> transactions, int transIndex) {
        while (transIndex < transactions.size() && loginStatus) {

            Transaction transaction = transactions.get(transIndex);
            if (transaction.transactionUsername.equals(this.userLoggedIn.getUserName()) && transaction.validTransaction)
            {
                transaction.execute(this);
            }
            transIndex++;
        }
        return transIndex;
    }

    private int logUserIn(ArrayList<Transaction> transactions, int transIndex) {
        while (true) {
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

    public User getUserLoggedIn() {
        return this.userLoggedIn;
    }
}
