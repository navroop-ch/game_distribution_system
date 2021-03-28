import java.util.ArrayList;

// A session object that controls exchange of data between User objects and the database. A session must be logged into
// to e able to perform any transactions.

public class Session {

    private static Session instance = null; // For singleton

    private data_base dataBase;
    private ArrayList<User> userList;
    private User userLoggedIn = null;
    private boolean loginStatus;

    private Session(data_base db){
        this.dataBase = db;
        // Todo: load users into userList by creating a method for it in data_base.java

    }

    // Singleton implementation
    public static Session getInstance() {
        // lazy initialization
        if (instance == null) {
            data_base dataBase = new data_base();
            instance = new Session(dataBase);
        }
        return instance;
    }

    protected data_base getDataBase(){
        return this.dataBase;
    }

    private void login(User user){
        this.userLoggedIn = user;
    }
}
