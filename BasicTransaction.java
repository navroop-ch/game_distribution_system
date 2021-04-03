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

    // check formatting
    private boolean basicCodeValidation(String code){
        return codeValidation(code) && BasicTransactionCodes.contains(code);
    }


    @Override
    protected Boolean execute() {
        return null;
    }

    protected Boolean transactionValidate(String code, String type, String credit) {
        return typeValidation(type) && creditValidation(credit) && basicCodeValidation(code);
    }
}
