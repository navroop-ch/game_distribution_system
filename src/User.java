package src;

import java.util.ArrayList;


public abstract class User {
    private final String userName;
    private final String type;
    private Double credit;
    private ArrayList<Game> gameOwned;

    //Todo: addedCredit needs to be set to 0 at the start(or the end) of each day!
    protected double addedCredit = 0;  //tracks credit added for each day

    protected static final double MAX_ALLOWED_CREDIT = 999999;
    protected static final String ADMIN_USER_TYPE = "AA";
    protected static final String FULL_USER_TYPE = "FS";
    protected static final String BUYER_USER_TYPE = "BS";
    protected static final String SELLER_USER_TYPE = "SS";
    protected static final String CONSTRAINT_ERROR = Session.CONSTRAINT_ERROR;

    protected Boolean loginStatus;
    protected Session session;
    protected data_base dataBase;

    protected User(String username, String type, double credit, ArrayList<Game> gameOwned){
        this.userName = username;
        this.type = type;
        this.credit = credit;
        this.gameOwned = gameOwned;
        this.loginStatus = false;
    }


    private void setDataBase(){
        this.dataBase = this.session.getDataBase(this);
    }

    /**
     * Returns the current user's username.
     *
     * @return userName of this user
     */
    protected String getUserName(){
        return this.userName;
    }

    /**
     * Returns the current user's type.
     *
     * @return String type of user
     */
    protected String getType(){
        return this.type;
    }

    /**
     * Returns the current user's current credit.
     *
     * @return credits
     */
    protected Double getCredit(){
        return this.credit;
    }

    /** Logs the user into a session
     *
     */
    protected void login(){
        //Todo: Login user only if he is in the data base
        this.session = Session.getInstance();
        if (!session.getLoginStatus()){
            this.session.sessionLogin(this);
            this.dataBase = this.session.getDataBase(this);
            this.loginStatus = true;
            String message = this.userName + ": logged in!"; // Todo: Complete functions.
            System.out.println(message);
        }

        else {
            String message = this.userName + ": A user is already logged in!";
            System.out.println(message);
        }

    }

    /** Logs the user out of the session logged in
     *
     */
    protected void logout(){
        this.session = Session.getInstance();
        if(loginStatus.equals(true)){
            this.loginStatus = false;
            this.session.sessionLogout(this);
            this.dataBase = null;
            String message = this.userName + ": logged out!"; // Todo: Complete functions.
            System.out.println(message);
        }

        else{
            System.out.printf("%s: You have not logged in yet!", this.getUserName());
        }


    }

    /**
     * Adds credit to the user object
     * @param cred amount of credit
     */
    protected void addCredit(double cred){
        if (cred < 0.00){
            System.out.println("Amount cannot be less than 0!");
        }
        else if(this.addedCredit + cred > 1000.00){
            System.out.println("Daily limit exceeded: A maximum of $1000.00 can be added each day.");
        }
        else if(this.credit + cred > MAX_ALLOWED_CREDIT){
            this.credit = MAX_ALLOWED_CREDIT;
            System.out.println("Warning: Your account balance has maxed out; some credit may not have been added!");
            System.out.printf("%s: your account balance is %s%n", this.userName ,this.credit);
        }
        else {
            this.credit += cred;
            this.addedCredit += cred;

            System.out.println("Credit added");

        }
    }

    /**
     * Adds or Removes credit from the user object
     * @param credit amount of credit
     * @param change string for adding or subtracting the credit
     */
    protected void changeCredit(double credit, String change){
        if (change.equals("add")){
            this.credit += credit;
        }
        else if (change.equals("sub")){
            this.credit -= credit;
        }
        else {
            System.out.println("Invalid input!");
        }
    }


    /**
     * Returns the current admin's game inventory.
     *
     * @return an ArrayList of String for the game name the admin own.
     */
    protected ArrayList<Game> getOwnedGame(){
        return this.gameOwned;
    }

    /**
     * Returns a string where the game objects are separated using a GAME_SEPARATOR
     * @return String that represents list of the games owned
     */
    protected String gamesOwnedToString(){
        if(this.gameOwned.isEmpty()){return "";}
        StringBuilder gamesString = new StringBuilder();
        gamesString.append(this.gameOwned.get(0));
        for (int i = 1; i < this.gameOwned.size(); i++){
            gamesString.append(data_base.GAME_SEPARATOR).append(this.gameOwned.get(i));
        }
        return gamesString.toString();
    }


    /**
     * @param game  The name of a new game.
     *
     * It will add the game to the ArrayList
     */
    public void addOwnedGame(Game game){
        this.gameOwned.add(game);
    }

    public void removeGame(Game game){
        this.gameOwned.remove(game);
    }

    /**
     * Gifts a game to another user from user's own inventory.
     *
     * @param title name of the game
     * @param receiver_uname username of the game's receiver
     */
    protected void giftGame(String title, String receiver_uname){

        Game game1 = owned(title);
        User receiver = session.getUser(receiver_uname);
        Game game2 = receiver.owned(title);

        if (this.gameOwned.contains(game1) && !game1.getTitle().equals(game2.getTitle())){
            this.gameOwned.remove(game1);
            receiver.gameOwned.add(game1);
        }
        else {System.out.println("Can't gift the game!");}

    }

    /**
     * Places the game that the user wants to sell for sale.
     * @param title The title of the game that the user wants to sell
     * @param price The price of the game the user wants to sell
     * @param saleDiscount The discount on the game if there's a sale taking place.
     *                     If there is no sale, then discount is 0.0
     */
    protected String sell(String title, Double price, Double saleDiscount){
        // Checks if the game is already in owned games
        Game game = owned(title);
        // Creates a new game if the given game doesn't exist in gameOwned,
        if (game == null) {
            game = new Game(title, price, false);
            game.setDiscount(saleDiscount);
            game.setForSale(true);
            addOwnedGame(game);
            game.switchPreSale();
            return null;
        }
        else if (!game.getPreSale() && !game.isForSale()){
            if (!game.isBought()) {
                game.setForSale(true);
                game.setDiscount(saleDiscount);
                game.switchPreSale();
                return null;
            }
            else {
                return String.format("Error: %s game can not be sold as it was bought the same day.", CONSTRAINT_ERROR);
            }
        }
        else {
            return String.format("Error: %s This game is already up for sale.", CONSTRAINT_ERROR);
        }

    }

    /**
     * Buys the game that the user wants to buy from give seller. If the game does not exist, there isn't enough credit
     * or the user already owns the game, then the transaction does not take place.
     * @param title The title of the game.
     * @param sellerName The username of the seller.
     */
    protected String buy(String title, String sellerName){
        User seller = session.getUser(sellerName);
        Game game = seller.owned(title);
        // If seller is selling and buyer doesn't own the game.
        if (game != null && game.isForSale() && !game.getPreSale() && this.owned(title) == null) {
            double price = game.getPrice();
            if (session.getAuctionStatus())
                price = game.getPrice() + (game.getPrice() * game.getDiscount());
            if (canBuy(price)) {
                this.credit -= price;
                seller.addCredit(price);
                seller.removeGame(game);
                this.addOwnedGame(game);
                game.setBought(true);
                return null;
            }
            else {
                return CONSTRAINT_ERROR + " Not enough credit to buy game.";
            }
        }
        else {
            return  CONSTRAINT_ERROR + " the game is either already owned, not for sale yet, or does not exist.";
        }

    }

    /**
     * Checks if a user can buy a certain game.
     * @param price The price that is being checked.
     * @return True if sufficient credit is available to buy, false otherwise
     */
    protected boolean canBuy(double price) {
        return this.credit - price >= 0.0;
    }

    /**
     * Checks if the given game title is a game owned by user.
     * @param title Title of the game
     * @return The game object corresponding to title if it exists, otherwise null.
     */
    protected Game owned(String title){
        for (Game g: this.gameOwned) {
            if (g.getTitle().equals(title)) {
                return g;
            }
        }
        return null;
    }


    /**
     * String representation for the user object
     * @return String generated to represent the user.
     */
    public String toString() {
        return "---------\n" +
                this.userName + "\n" +
                "Account type: " + this.type + "\n" +
                "Credit: " + this.credit + "\n" +
                "Games: " + this.gamesOwnedToString() + "\n" +
                "Logged in: " + this.loginStatus.toString() +
                "\n---------\n";

    }

}
