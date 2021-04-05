public class Game {
    private final String title;
    private Double price = 0.00;
    private Boolean forSale = false;
    private boolean bought;
    private double discount;

    /**
     * Constructor class that instantiates a Game using the title only
     * @param title name of the game
     */
    public Game(String title){
        this.title =title;
    }

    /**
     * Constructor class that instantiates a Game using the title, price and forSale values.
     * @param title name of the game
     * @param price price of the game
     * @param forSale if the game is for sale
     */
    public Game(String title, Double price, Boolean forSale){
        this(title);
        this.price = price;
        this.forSale = forSale;
        this.bought = false;
        this.discount = 0.0;
    }

    /**
     * Sets price of the game
     * @param price game's price
     */
    protected void setPrice(Double price){ this.price = price;}

    /**
     * Sets the status of forSale
     * @param forSale boolean parameter which is true for games that are for sale
     */
    protected void setForSale(Boolean forSale){this.forSale = forSale;}

    /**
     * Returns game's name
     * @return title of the game
     */
    protected String getTitle(){return this.title;}

    /**
     * Returns price of a game
     * @return game's price
     */
    protected Double getPrice(){return this.price;}

    /**
     * Returns the whether a game is for sale
     * @return true if a game is for sale, false otherwise
     */
    protected Boolean isForSale(){return this.forSale;}

    /**
     * Sets a game's status to bought
     * @param bought game's status of being bought
     */
    protected void setBought(boolean bought){this.bought = bought;}

    /**
     * Returns whether a game is already bought
     * @return true if a game is bought, false otherwise
     */
    protected boolean isBought(){return this.bought;}

    /**
     * Sets the discount on a game
     * @param discount discount on a game
     */
    protected void setDiscount(double discount){this.discount = discount;}

    /**
     * Returns whether a game has a discount
     * @return true if a discount is applied on a game, false otherwise
     */
    protected double getDiscount(){return this.discount;}


    /**
     * Takes a string of the format below a returns a game object based on that string
     *
     *  Pre-condition: String must be of the following format
     *
     *              IIIIIIIIIIIIIIIIIII PPPPPP S
     *                 I: Title       P: price S: ForSale(0/1)
     * @return game object
     */
    protected static Game stringToGame(String gameString){

        if (gameString.equals("null")){ return null;}
        String[] gameData = gameString.split(data_base.SEPARATOR);

        int titleIndexStart = 0;
        int titleIndexEnd = data_base.TITLE_LENGTH;
        int priceIndexStart = data_base.TITLE_LENGTH + 1;
        int priceIndexEnd = priceIndexStart + data_base.PRICE_LENGTH;

        String title = gameString.substring(titleIndexStart, titleIndexEnd);
        Double price = Double.parseDouble(gameString.substring(priceIndexStart, priceIndexEnd));
        Boolean forSale = Boolean.parseBoolean(gameData[gameData.length - 1]);

        Game game = new Game(title, price, forSale);
        return game;
    }

    /**
     * Returns a string that represents the game object as a tuple with format:
     *
     *                  (title price forSale)
     *
     * Note: This wil be used in the storage of the game in a user's data base
     *
     * @return string representation
     */
    public String toString(){
        String padded_title = data_base.stringPadding(
                this.title, data_base.BLANK_CHAR, data_base.TITLE_LENGTH);
        String padded_price = data_base.stringPadding(
                this.price.toString(), data_base.ZERO_CHAR, -data_base.PRICE_LENGTH);
        return String.format("%s %s %s", padded_title, padded_price, this.forSale);
    }

    public static void main(String[] args){
        Game g = new Game("Fortnite", 350.34,true);
        Game g1 = new Game("Rsix siege", 550.34,true);
        Game g2 = new Game("C");

        System.out.println(g);
        System.out.println(g1);
        System.out.println(g2);
    }
}
