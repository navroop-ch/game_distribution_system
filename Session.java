import java.util.ArrayList;

// A session object that controls exchange of data between User objects and the database. A session must be logged into
// for a user to get access to the database to perform any transactions.

public class Session {

    private static Session instance = null; // For singleton

    private final data_base dataBase;
    private ArrayList<User> userList;
    private User userLoggedIn = null;
    private boolean loginStatus;

    private Session(data_base db){
        this.dataBase = db;
        // Todo: load users into userList by creating a method for it in data_base.java
        this.userList = this.dataBase.loadUsers(db.userData);

    }

    // Singleton implementation
    protected static Session getInstance() {
        // lazy initialization
        if (instance == null) {
            data_base dataBase = new data_base();
            instance = new Session(dataBase);
        }
        return instance;
    }

    protected data_base getDataBase(User user){
        if (this.userLoggedIn == user){ return this.dataBase;}
        else {return null;}
    }

    protected void sessionLogin(User user){
        this.userLoggedIn = user;
        this.loginStatus = true;
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
