public class BuyTransaction extends Transaction{

    protected String sellerUsername;
    protected String gameTitle;

    protected BuyTransaction(String code, String buyerUsername, String sellerUsername, String gameTitle) {
        super(buyerUsername);
        if (transactionValidate(code, sellerUsername, gameTitle)){
            this.transactionCode = code;
            this.sellerUsername = sellerUsername;
            this.gameTitle = gameTitle;
            this.validTransaction = true;
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
    protected Boolean execute() {
        return null;
    }
}
