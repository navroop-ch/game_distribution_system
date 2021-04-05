public class RefundTransaction extends Transaction{

    /**
     * A class for refund transaction between users
     */
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

    /**
     * Checks if the refund transaction code is valid
     * @param code transaction code
     * @return if the transaction code matches the refund transaction code, false otherwise
     */
    protected boolean refundCodeValidate(String code){
        return code.equals(data_base.refundCode) && codeValidation(code);
    }

    /**
     * Checks if seller's username, transaction code and the refund credit is valid
     * @param code transaction code
     * @param sellerUsername username of seller
     * @param refundCredit the amount to be refunded
     * @return true if the parameters are valid, false otherwise
     */
    protected boolean transactionValidate(String code, String sellerUsername, String refundCredit){
        return refundCodeValidate(code) && usernameValidation(sellerUsername) && creditValidation(refundCredit);
    }

    /**
     * Executes refund transaction
     * @param session session object that keeps track of the logged in users
     * @return true if the refund transaction was successful, false otherwise
     */
    @Override
    protected Boolean execute(Session session) {
        Admin user = (Admin)session.getUserLoggedIn();
        if (user != null && user.getType().equals("AA")) {
            user.refund(this.transactionUsername, this.sellerUsername, this.refundCredit);
            return true;
        } else {
            System.out.println("Error: User doesn't exist. Refund Failed !");
            return false;
        }
    }
}
