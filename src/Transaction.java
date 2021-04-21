package src;

import java.util.Arrays;
import java.util.List;

/**
 * A transaction class for executing all the transactions - sell, buy, remove game, gift game, refund game
 */
public abstract class Transaction{

    protected static final int BASIC_TRANSACTION_PARAM = 4;
    protected static final int SELL_TRANSACTION_PARAM = 5;
    protected static final int BUY_TRANSACTION_PARAM = 4;
    protected static final int REFUND_TRANSACTION_PARAM = 4;
    protected static final String CONSTRAINT_ERROR = Session.CONSTRAINT_ERROR;

    protected String transactionCode;
    protected String transactionUsername;
    protected boolean validTransaction;
    protected List<String> TYPES = Arrays.asList(
            User.ADMIN_USER_TYPE, User.BUYER_USER_TYPE, User.FULL_USER_TYPE, User.SELLER_USER_TYPE);


    /**
     * Checks if a string username is valid
     * @param username username to check
     */
    protected Transaction(String username){
        // checks code length and username length
        if (usernameValidation(username)){
            this.transactionUsername = username.strip(); // Todo:   REMOVE EXTRA SPACES IN TRANSACTION OBJECTS
            this.validTransaction = true;
        }
        else this.validTransaction = false;
    }

    protected abstract Boolean execute(Session session);


    /**
     * Checks the length of the username entered
     * @param username string to be checked
     * @return true if this username's length matches the length stored in database (15)
     */
    protected boolean usernameValidation(String username){
        return username.length() == data_base.USERNAME_LENGTH;
    }

    /**
     * Validates the code by checking its length and type
     * @param code string to be checked
     * @return true if the length of code matches the CODE_LENGTH in src.data_base and code is numeric
     */
    protected boolean codeValidation(String code){
        return code.length() == data_base.CODE_LENGTH && integerValidation(code);
    }

    /**
     * Validates the type of user
     * @param type the type of user (admin, seller std, buyer std, full-std)
     * @return true if type's length matches the TYPE_LENGTH (2) and it exists in the list storing user types.
     */
    protected boolean typeValidation(String type){
        // Check length and valid type
        return type.length() == data_base.TYPE_LENGTH && TYPES.contains(type) ;
    }

    /**
     * Validates the credit amount
     * @param credit the amount string to be checked
     * @return true if credit's length is 9 and its a doublem otherwise false
     */
    protected boolean creditValidation(String credit){
        return credit.length() == data_base.CREDIT_LENGTH && doubleValidation(credit);
    }

    /**
     * Validates the discount string
     * @param discount string to be checked
     * @return true if discount's length matches DISCOUNT_LENGTH (5) and its a double, otherwise false
     */
    protected boolean discountValidation(String discount){
        return discount.length() == data_base.DISCOUNT_LENGTH && doubleValidation(discount);
    }

    /**
     * Validates a game's name or title
     * @param title game's name
     * @return true if title's length matches TITLE_LENGTH (25), otherwise false
     */
    protected boolean gameTitleValidation(String title){
        return title.length() == data_base.TITLE_LENGTH;
    }

    /**
     * Validates the game's price string
     * @param price game's price
     * @return true if price's length is PRICE_LENGTH (6) and its a double
     */
    protected boolean salePriceValidation(String price){
        return price.length() == data_base.PRICE_LENGTH && doubleValidation(price);
    }

    /**
     * Validates a string of integers
     * @param strInt an integer string
     * @return true if the string is made of integers, false otherwise
     */
    protected boolean integerValidation(String strInt){
        try {
            Integer.parseInt(strInt);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Validates a string of double
     * @param strD a string containing double
     * @return true if string is made of double, false otherwise
     */
    protected boolean doubleValidation(String strD){
        try {
            Double.parseDouble(strD);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}