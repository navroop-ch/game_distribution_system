import java.util.ArrayList;


public abstract class User {
    protected String userName;
    protected String type;
    protected double credit;
    protected ArrayList<String> gameOwned;

    //Todo: addedCredit needs to be set to 0 at the start(or the end) of each day!
    protected double addedCredit = 0;  //tracks credit added for each day


    protected static final double MAX_ALLOWED_CREDIT = 999999;
    protected static final String ADMIN_USER_TYPE = "AA";
    protected static final String FULL_USER_TYPE = "FS";
    protected static final String BUYER_USER_TYPE = "BS";
    protected static final String SELLER_USER_TYPE = "SS";

    // Todo: create a Session object and use an instance of that instead of data_base
    protected data_base dataBase;

    protected User(String username, double credit, ArrayList <String> gameOwned){
        this.userName = username;
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
    protected double getCredit(){
        return this.credit;
    }

    /** Logs the user into a session
     *
     */
    private void login(){

    }

    /** Logs the user out of the session logged in
     *
     */
    private void logout(){

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
    public ArrayList <String> getOwnedGame(){
        return this.gameOwned;
    }

    /**
     * @para  The name of a new game.
     *
     * It will add the game to the ArrayList
     */
    public void addOwnedGame(String game){
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

        //Todo: Improve this method. (Represent games owned)
        return "---------\n" +
                this.userName + "\n" +
                "Account type: " + this.type + "\n" +
                "Credit: " + this.credit +
                "\n---------\n";

    }

}
