/**
 * A transaction class for selling a game
 */
public class SellTransaction extends Transaction{

    protected String gameTitle;
    protected Double discount;
    protected Double salePrice;

    /**
     * Initialising the fields of sell transaction
     * @param code transasction code
     * @param gameTitle title/ name of the game
     * @param username username of the game's owner
     * @param discount discount on the game
     * @param sale sale price
     */
    protected SellTransaction(String code, String gameTitle, String username, String discount, String sale) {
        super(username);
        if (transactionValidate(username, code, gameTitle, discount, sale)){
            this.transactionCode = code;
            this.gameTitle = gameTitle;
            this.discount = Double.parseDouble(discount);
            this.salePrice = Double.parseDouble(sale);
            this.validTransaction = true;
        }
        else this.validTransaction = false;
    }

    /**
     * Returns whether a transaction code is valid or not
     * @param code transaction code
     * @return true if the transaction code matches the sell code and is valid, flase otherwise
     */
    private Boolean sellCodeValidation(String code){
        return code.equals(data_base.sellCode) && codeValidation(code);
    }

    /**
     * Returns whether a given transaction is a valid sell transaction
     * @param username usename of the game's owner
     * @param code transaction code
     * @param gameTitle game's name
     * @param discount discount on the game
     * @param sale sale price of the game
     * @return true if all the parameter are valid and follow the format constraints of a sell transaction, false
     * otherwise
     */
    private boolean transactionValidate(String username, String code, String gameTitle, String discount, String sale){
        return sellCodeValidation(code) && gameTitleValidation(gameTitle)
                && discountValidation(discount) && salePriceValidation(sale);
    }

    /**
     * Sells a game to a user
     * @param session session object that keeps track of all logged in users
     * @return true if a game was sold to a user, false otherwise
     */
    @Override
    protected Boolean execute(Session session) {
        User user = session.getUser(this.transactionUsername);

        if (user != null) {
            // Todo: A game is up for sale the next day according to ReadMe; How do we ensure that?
            user.sell(this.gameTitle, this.salePrice, this.discount);
            return true;
        }
        else {
            System.out.println("Error: The user does not exist. Sell transaction Failed !");
            return false;
        }

    }
}
