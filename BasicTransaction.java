import java.util.Arrays;
import java.util.List;

public class BasicTransaction extends Transaction{

    protected final List<String> BasicTransactionCodes= Arrays.asList(
            data_base.addCreditCode, data_base.logInCode, data_base.logOutCode, data_base.createCode,
            data_base.deleteCode);
    protected String type;
    protected double credit;


    protected BasicTransaction(String code, String username, String type, String credit) {
        super(username);

        if (transactionValidate(code, type, credit)){
            this.transactionCode = code;
            this.credit = Double.parseDouble(credit);
            this.type = type;
            this.validTransaction = true;
        }
        else this.validTransaction = false;
    }

    private boolean basicCodeValidation(String code){
        return codeValidation(code) && BasicTransactionCodes.contains(code);
    }


    @Override
    protected Boolean execute(Session session) {
        switch (this.transactionCode){

            case data_base.logInCode:
                return executeLogin(session);
            case data_base.logOutCode:
                break;

        }
        return false;
    }


    private Boolean executeLogin(Session session) {
        User user = session.getUser(this.transactionUsername);
        if (user != null){
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
            //Todo: figure out appropriate error return
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
        User user = session.getUser(this.transactionUsername);
        if (user != null && user.getType().equals(User.ADMIN_USER_TYPE)){
            Admin admin = (Admin) session.getUser(this.transactionUsername);

            // Todo: admin.createUser(); Where is the new user data!?
            // just call admin.createUser() and the user data is stored in session (userList).

            return true;
        }
        else {
            //Todo: figure out appropriate error return
            //System.out.println("Error: User does not exist");
            return false;
        }
    }

    private Boolean executeDeleteUser(){
        // Todo: implement this
        return null;
    }

    protected Boolean transactionValidate(String code, String type, String credit) {
        return typeValidation(type) && creditValidation(credit) && basicCodeValidation(code);
    }
}
