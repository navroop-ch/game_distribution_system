import java.lang.reflect.Array;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Arrays;

// A session object that controls exchange of data between User objects and the database. A session must be logged into
// for a user to get access to the database to perform any transactions.

public class Session {

    private static Session instance = null; // For singleton

    private static byte[] dataBaseKey;
    private static SecureRandom random = new SecureRandom();
    private static data_base dataBase;
    // private ArrayList<User> userList;
    private User userLoggedIn = null;
    private boolean loginStatus;

    private Session(){
        // Todo: load users into userList by creating a method for it in data_base.java
    }

    private static void generateKey(){
        dataBaseKey = random.generateSeed(10);
    }

    protected static boolean authenticateKey(byte[] key){
        return Arrays.equals(key, dataBaseKey);
    }


    private static void setDataBase(data_base db){
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

    protected data_base getDataBase(User user){
        if (this.userLoggedIn == user){ return dataBase;}
        else {return null;}
    }

    protected void sessionLogin(User user){
        if(this.userLoggedIn == null) {
            this.userLoggedIn = user;
            this.loginStatus = true;
        }
    }

    protected void sessionLogout(User user){
        if (this.userLoggedIn == user){
            this.userLoggedIn = null;
            this.loginStatus = false;
        }
    }

    protected boolean getLoginStatus(){
        return loginStatus;
    }
}