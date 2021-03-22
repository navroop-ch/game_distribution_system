import java.util.ArrayList;


public abstract class User {
    protected String userName;
    protected String type;
    protected double credit;
    protected ArrayList<String> gameOwned;

    //Todo: addedCredit needs to be set to 0 at the start(or the end) of each day!
    protected double addedCredit = 0;  //tracks credit added for each day


    public static final double MAX_ALLOWED_CREDIT = 999999;
    public static final String ADMIN_USER_TYPE = "AA";
    public static final String FULL_USER_TYPE = "FS";
    public static final String BUYER_USER_TYPE = "BS";
    public static final String SELLER_USER_TYPE = "SS";

    public static final String logInCode = "00";
    public static final String createCode = "01";
    public static final String deleteCode = "02";
    public static final String addCreditCode = "06";
    public static final String logOutCode = "10";

    public abstract String getUserName();
    public abstract String getType();
    public abstract double getCredit();
    public abstract ArrayList <String> getOwnedGame();
    public abstract void addOwnedGame(String game);

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
        }
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
