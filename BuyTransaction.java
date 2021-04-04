public class BuyTransaction extends Transaction{

    protected String sellerUsername;
    protected String gameTitle;

    protected BuyTransaction(String code, String gameTitle,String sellerUsername, String buyerUsername) {
        super(buyerUsername);
        if (transactionValidate(code, sellerUsername, gameTitle) && validTransaction){
            this.transactionCode = code;
            this.sellerUsername = sellerUsername;
            this.gameTitle = gameTitle;
        }
        else this.validTransaction = false;
    }

    private Boolean buyCodeValidation(String code){
        return code.equals(data_base.buyCode) && codeValidation(code);
    }

    protected Boolean transactionValidate(String code, String sellerUsername, String gameTitle){
        return usernameValidation(sellerUsername) && buyCodeValidation(code) && gameTitleValidation(gameTitle);
    }

    @Override
    protected Boolean execute(Session session) {
        User user = session.getUser(this.transactionUsername);
        if (user != null) {
            user.buy(this.gameTitle, this.sellerUsername);
            return true;
        }
        else {
            return false;
        }
    }
}
