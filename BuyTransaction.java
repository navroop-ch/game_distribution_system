public class BuyTransaction extends Transaction{
    /**
     * A Buy transaction class for buying a game from a seller
     */
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

    /**
     * Checks the string for valid buy transaction code
     * @param code string to be checked
     * @return true if the string matches, false otherwise
     */
    private Boolean buyCodeValidation(String code){
        return code.equals(data_base.buyCode) && codeValidation(code);
    }

    /**
     * Checks the user code, seller username and game's name for constraints
     * @param code user code
     * @param sellerUsername seller's username
     * @param gameTitle game's name
     * @return true if code, sellerUsername and gameTitle fulfill the constraints, false otherwise
     */
    protected Boolean transactionValidate(String code, String sellerUsername, String gameTitle){
        return usernameValidation(sellerUsername) && buyCodeValidation(code) && gameTitleValidation(gameTitle);
    }

    /**
     * Executes buy transaction for a user
     * @param session session objects that stores all the logged in users
     * @return true if buy transaction is executed, false otherwise
     */
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
