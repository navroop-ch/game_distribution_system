public class RefundTransaction extends Transaction {

    protected String sellerUsername;
    protected Double refundCredit;

    protected RefundTransaction(String code, String buyerUsername, String sellerUsername, String refundCredit) {
        super(buyerUsername);

        if (transactionValidate(code, sellerUsername, refundCredit)) {
            this.transactionCode = code;
            this.sellerUsername = sellerUsername.strip();
            this.refundCredit = Double.parseDouble(refundCredit);
            this.validTransaction = true;
        } else this.validTransaction = false;
    }

    protected boolean refundCodeValidate(String code) {
        return code.equals(data_base.refundCode) && codeValidation(code);
    }

    protected boolean transactionValidate(String code, String sellerUsername, String refundCredit) {
        return refundCodeValidate(code) && usernameValidation(sellerUsername) && creditValidation(refundCredit);
    }

    @Override
    protected Boolean execute(Session session) {
        User user = session.getUserLoggedIn();
        if (user != null && user.getType().equals("AA")) {
            Admin admin = (Admin) user;
            String message = admin.refund(this.transactionUsername, this.sellerUsername, this.refundCredit);
            if (message == null) {
                System.out.printf("%s: refunded %s credit %s", this.sellerUsername, this.transactionUsername, this.refundCredit);
            } else {
                System.out.println(message);
            }
            return true;
        } else {
            System.out.println("ERROR: You are not admin");
            return false;
        }
    }
}
