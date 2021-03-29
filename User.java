import java.util.ArrayList;


public abstract class User {
    private final String userName;
    private final String type;
    private Double credit;
    private ArrayList<Game> gameOwned;

    //Todo: addedCredit needs to be set to 0 at the start(or the end) of each day!
    protected double addedCredit = 0;  //tracks credit added for each day

    protected Boolean loginStatus;

    protected static final double MAX_ALLOWED_CREDIT = 999999;
    protected static final String ADMIN_USER_TYPE = "AA";
    protected static final String FULL_USER_TYPE = "FS";
    protected static final String BUYER_USER_TYPE = "BS";
    protected static final String SELLER_USER_TYPE = "SS";

    protected Session session;
    protected data_base dataBase;

    protected User(String username, String type, double credit, ArrayList<Game> gameOwned){
        this.userName = username;
        this.type = type;
        this.credit = credit;
        this.gameOwned = gameOwned;
        this.loginStatus = false;
    }


    /**
     * Sets the instance variable for the database
     * @param session instance of the database object
     */
    private void setSession(Session session){
        this.session = session;
        this.dataBase = session.getDataBase();
    }

    /**
     * Returns the current user's username.
     *
     * @return userName of this user
     */
    protected String getUserName(){
        return this.userName;
    }

    /**
     * Returns the current user's type.
     *
     * @return String type of user
     */
    protected String getType(){
        return this.type;
    }

    /**
     * Returns the current user's current credit.
     *
     * @return credits
     */
    protected Double getCredit(){
        return this.credit;
    }

    /** Logs the user into a session
     *
     */
    protected void login(){
        this.setSession(Session.getInstance());
        this.session.login(this);
        this.loginStatus = true;

        this.dataBase.writeBasicTransaction(data_base.logInCode, this.userName, this.type, this.credit);
    }

    /** Logs the user out of the session logged in
     *
     */
    protected void logout(){
        this.setSession(null);
        this.loginStatus = false;

        this.dataBase.writeBasicTransaction(data_base.logOutCode, this.userName, this.type, this.credit);
    }

    /**
     * Adds credit to the user object
     * @param cred amount of credit
     */
    protected void addCredit(double cred){
        if (cred < 0.00){
            System.out.println("Amount cannot be less than 0!");
        }
        else if(this.addedCredit + cred > 1000.00){
            System.out.println("Daily limit exceeded: A maximum of $1000.00 can be added each day.");
        }
        else if(this.credit + cred > MAX_ALLOWED_CREDIT){
            this.credit = MAX_ALLOWED_CREDIT;
            System.out.println("Warning: Your account balance has maxed out; some credit may not have been added!");
        }
        else {
            this.credit += cred;
            this.addedCredit += cred;

            System.out.println("Credit added");

            //TODO: update credits in userdata file
            // write to daily.txt
            this.dataBase.writeBasicTransaction(data_base.addCreditCode, this.userName, this.type, this.credit);
        }
    }

    /**
     * Returns the current admin's game inventory.
     *
     * @return an ArrayList of String for the game name the admin own.
     */
    protected ArrayList<Game> getOwnedGame(){
        return this.gameOwned;
    }

    protected String gamesOwnedToString(){
        StringBuilder gamesString = new StringBuilder();
        gamesString.append(this.gameOwned.get(0));
        for (int i = 1; i < this.gameOwned.size(); i++){
            gamesString.append(data_base.GAME_SEPARATOR).append(this.gameOwned.get(i));
        }
        return gamesString.toString();
    }


    /**
     * @param game  The name of a new game.
     *
     * It will add the game to the ArrayList
     */
    public void addOwnedGame(Game game){
        this.gameOwned.add(game);
    }

    protected void sell(String title, Double price, Double saleDiscount){
        //Todo: implement this method
    }

    protected void buy(String title, String sellerName){
        //Todo: implement this method
    }

    /**
     * String representation for the user object
     * @return String generated to represent the user.
     */
    public String toString() {
        return "---------\n" +
                this.userName + "\n" +
                "Account type: " + this.type + "\n" +
                "Credit: " + this.credit + "\n" +
                "Games: " + this.gamesOwnedToString() + "\n" +
                "Logged in: " + this.loginStatus.toString() +
                "\n---------\n";

    }

}
