import java.util.Arrays;
import java.util.List;

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

    // check formatting
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

    private Boolean executeLogin(Session session) {

        User user = session.getUser(this.transactionUsername);
        if (user != null && dataSatisfiesDatabase(user)){
            user.login();
            return true;
        }
        else {
            //Todo: figure out appropriate error return
            System.out.println("Error: User does not exist");
            return false;
        }
    }

    private Boolean executeLogout(Session session){
        User user = session.getUser(this.transactionUsername);
        if (user != null){
            user.logout();
            return true;
        }
        else {
            //System.out.println("Error: User does not exist");
            return false;
        }
    }

    private Boolean executeAddCredit(Session session){

        if(!validTransaction){return false;}

        User user = session.getUser(this.transactionUsername);
        if (user != null){
            user.addCredit(this.credit);
            return true;
        }
        else {
            //Todo: figure out appropriate error return
            //System.out.println("Error: User does not exist");
            return false;
        }
    }

    private Boolean executeCreateUser(Session session){
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

    protected Boolean transactionValidate(String code, String type, String credit) {
        return typeValidation(type) && creditValidation(credit) && basicCodeValidation(code);
    }

    private boolean dataSatisfiesDatabase(User databaseUser){ //Todo: record errors
        if (!(databaseUser.getCredit().equals(this.credit) && databaseUser.getType().equals(this.type))){
            System.out.println(ERROR_INCONSISTENT_DATA);
            return false;
        }
        return true;
    }
}
