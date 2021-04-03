public class SellTransaction extends Transaction{

    protected String gameTitle;
    protected Double discount;
    protected Double salePrice;

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

    private Boolean sellCodeValidation(String code){
        return code.equals(data_base.sellCode) && codeValidation(code);
    }

    private boolean transactionValidate(String username, String code, String gameTitle, String discount, String sale){
        return sellCodeValidation(code) && gameTitleValidation(gameTitle)
                && discountValidation(discount) && salePriceValidation(sale);
    }

    @Override
    protected Boolean execute(Session session) {
        User user = session.getUser(this.transactionUsername);
        return null;
    }
}
