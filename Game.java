public class Game {
    private final String title;
    private Double price = 0.00;
    private Boolean forSale = false;
    private boolean selling;
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
        this.selling = false;
        this.bought = false;
        this.discount = 0.0;
    }

    protected void setPrice(Double price){ this.price = price;}
    protected void setForSale(Boolean forSale){this.forSale = forSale;}
    protected String getTitle(){return this.title;}
    protected Double getPrice(){return this.price;}
    protected Boolean isForSale(){return this.forSale;}

    protected void setBought(boolean bought){this.bought = bought;}
    protected boolean isBought(){return this.bought;}

    protected void setDiscount(double discount){this.discount = discount;}
    protected double getDiscount(){return this.discount;}

    protected void setSelling(boolean selling){this.selling = selling;}
    protected boolean getSelling(){return this.selling;}


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
