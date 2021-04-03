public class RefundTransaction extends Transaction{

    protected String sellerUsername;
    protected Double refundCredit;

    protected RefundTransaction(String code, String buyerUsername, String sellerUsername, String refundCredit) {
        super(buyerUsername);

        if (transactionValidate(code, sellerUsername, refundCredit)){
            this.transactionCode = code;
            this.sellerUsername = sellerUsername;
            this.refundCredit = Double.parseDouble(refundCredit);
            this.validTransaction = true;
        }
        else this.validTransaction = false;
    }

    protected boolean refundCodeValidate(String code){
        return code.equals(data_base.refundCode) && codeValidation(code);
    }

    protected boolean transactionValidate(String code, String sellerUsername, String refundCredit){
        return refundCodeValidate(code) && usernameValidation(sellerUsername) && creditValidation(refundCredit);
    }

    @Override
    protected Boolean execute(Session session) {
        Admin user = (Admin)session.getUserLoggedIn();
        if (user != null && user.getType().equals("AA")) {
            user.refund(this.transactionUsername, this.sellerUsername, this.refundCredit);
            return true;
        } else {
            return false;
        }
    }
}
