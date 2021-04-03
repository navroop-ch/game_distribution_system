import java.util.Arrays;
import java.util.List;

public abstract class Transaction{

    protected String transactionCode;
    protected String transactionUsername;
    protected boolean validTransaction;
    protected List<String> TYPES = Arrays.asList(
            User.ADMIN_USER_TYPE, User.BUYER_USER_TYPE, User.FULL_USER_TYPE, User.SELLER_USER_TYPE);


    // reading daily.txt

    protected Transaction(String username){
        // checks code length and username length
        if (usernameValidation(username)){
            this.transactionUsername = username;
        }
        else this.validTransaction = false;
    }



    protected abstract Boolean execute();


    protected boolean usernameValidation(String username){
        return username.length() == data_base.USERNAME_LENGTH;
    }

    protected boolean codeValidation(String code){
        return code.length() == data_base.CODE_LENGTH && integerValidation(code);
    }

    protected boolean typeValidation(String type){
        // Check length and valid type
        return type.length() == data_base.TYPE_LENGTH && TYPES.contains(type);
    }

    protected boolean creditValidation(String credit){
        return credit.length() == data_base.CREDIT_LENGTH && doubleValidation(credit);
    }

    protected boolean discountValidation(String discount){
        return discount.length() == data_base.DISCOUNT_LENGTH && doubleValidation(discount);
    }

    protected boolean gameTitleValidation(String title){
        return title.length() == data_base.TITLE_LENGTH;
    }

    protected boolean salePriceValidation(String price){
        return price.length() == data_base.PRICE_LENGTH && doubleValidation(price);
    }



    protected boolean integerValidation(String strInt){
        try {
            Integer.parseInt(strInt);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    protected boolean doubleValidation(String strD){
        try {
            Double.parseDouble(strD);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}


