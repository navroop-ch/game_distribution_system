public class Game {
    private String title;
    private Double price;
    private Boolean forSale;
    // To differentiate between games that can and can't be sold
    private boolean bought;
    private double discount;

    public Game(String title, Double price, Boolean forSale){
        this.title = title;
        this.price = price;
        this.forSale = forSale;
        this.bought = false;
        this.discount = 0.0;

    }

    protected void setPrice(Double price){ this.price = price;}
    protected void setForSale(Boolean forSale){this.forSale = forSale;}
    protected void setBought(boolean bought){this.bought = bought;}
    protected String getTitle(){return this.title;}
    protected Double getPrice(){return this.price;}
    protected Boolean isForSale(){return this.forSale;}
    protected boolean isBought(){return this.bought;}

    protected void setDiscount(double discount){this.discount = discount;}
    protected double getDiscount(){return this.discount;}

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
        return String.format("(%s %s %s)", this.title, this.price, this.forSale);
    }

}
