import java.util.Arrays;
import java.util.List;

/**
 * Checks the transaction format for add credit, login, logout, create user and delete user.
 */
public class BasicTransaction extends Transaction{

    public static final String ERROR_INCONSISTENT_DATA = "Error: Data in transaction does not match data in user database.";
    protected final List<String> BasicTransactionCodes= Arrays.asList(
            data_base.addCreditCode, data_base.logInCode, data_base.logOutCode, data_base.createCode,
            data_base.deleteCode);
    protected String type;
    protected double credit;

    protected BasicTransaction(String code, String username, String type, String credit) {
        super(username);

        if (transactionValidate(code, type, credit) && validTransaction){
            this.transactionCode = code;
            this.credit = Double.parseDouble(credit);
            this.type = type;
            this.validTransaction = true;
        }
        else this.validTransaction = false;
    }

    /**
     * Tests a string for basic transaction
     * @param code the string to be checked
     * @return returns true if the string is valid, otherwise returns false
     */
    private boolean basicCodeValidation(String code){
        return codeValidation(code) && BasicTransactionCodes.contains(code);
    }

    @Override
    protected Boolean execute(Session session) {
        switch (this.transactionCode){

            case data_base.logInCode:
                return executeLogin(session);
            case data_base.logOutCode:
                return executeLogout(session);
            case data_base.createCode:
                return executeCreateUser(session);
            case data_base.deleteCode:
                return executeDeleteUser(session);
            case data_base.addCreditCode:
                return executeAddCredit(session);

        }
        return false;
    }

    /**
     * Executes login transaction
     * @param session session object that keeps track of currently logged in users
     * @return true if the user was able to login, otherwise return false and print error msg
     */
    private Boolean executeLogin(Session session) {

        User user = session.getUser(this.transactionUsername);
        if (user != null && dataSatisfiesDatabase(user)){
            user.login();
            return true;
        }
        else {
            System.out.println("Error: User does not exist");
            return false;
        }
    }

    /**
     * Executes logout transaction
     * @param session session object that keeps track of currently logged in users
     * @return return true if user logs out, otherwise return false and print an error message
     */
    private Boolean executeLogout(Session session){
        User user = session.getUser(this.transactionUsername);
        if (user != null){
            user.logout();
            return true;
        }
        else {
            System.out.println("Error: User does not exist");
            return false;
        }
    }

    /**
     * Executes add credit transaction
     * @param session session object that keeps track of currently logged in users
     * @return returns true if user object exists and adds credit to user's account, otherwise return false
     */
    private Boolean executeAddCredit(Session session){

        if(!validTransaction){return false;}

        User user = session.getUser(this.transactionUsername);
        if (user != null){
            user.addCredit(this.credit);
            return true;
        }
        else {
            System.out.println("Error: User does not exist");
            return false;
        }
    }

    /**
     * Creates a new user
     * @param session session object that keeps track of currently logged in users
     * @return returns true after creating a new user, otherwise false and prints error message
     */
    private Boolean executeCreateUser(Session session){
        System.out.println("\nRead here: \n");
        User toBeCreated = session.getUser(this.transactionUsername);
        User LoggedInUser = session.getUserLoggedIn();
        if(toBeCreated==null && LoggedInUser != null && LoggedInUser.getType().equals(User.ADMIN_USER_TYPE)){

            Admin admin = (Admin)LoggedInUser;
            admin.createUser(this.transactionUsername,this.type,this.credit);
            return true;
        }
        else {
            System.out.println("Error: User already exist");
            return false;
        }
    }

    /**
     * Deletes a user
     * @param session session object that keeps track of currently logged in users
     * @return true if a user is deleted, false otherwise
     */
    private Boolean executeDeleteUser(Session session){
        User toBeDeleted = session.getUser(this.transactionUsername);
        User LoggedInUser = session.getUserLoggedIn();
        if(toBeDeleted!=null && LoggedInUser != null && LoggedInUser.getType().equals(User.ADMIN_USER_TYPE)){
            Admin admin = (Admin)LoggedInUser;
            admin.deleteUser(this.transactionUsername);
            return true;
        }

        System.out.println("Error: User does not exist");
        return false;
    }

    /**
     * Checks the format for transaction code, type of user and credit
     * @param code transaction code
     * @param type type of user
     * @param credit credit amount
     * @return true if the format of code, type and credit matches, otherwise return false
     */
    protected Boolean transactionValidate(String code, String type, String credit) {
        return typeValidation(type) && creditValidation(credit) && basicCodeValidation(code);
    }

    /**
     * Checks the user data for credit and type of user
     * @param databaseUser user object
     * @return true if user data matches, otherwise false
     */
    private boolean dataSatisfiesDatabase(User databaseUser){
        if (!(databaseUser.getCredit().equals(this.credit) && databaseUser.getType().equals(this.type))){
            System.out.println(ERROR_INCONSISTENT_DATA);
            return false;
        }
        return true;
    }
}
