import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * An data base class that handles every interaction with the daily.txt or our userName.txt (database)
 *
 */

//Todo: change name of file to DataBase
public class data_base{
    private static data_base instance = null;
    protected final String ERROR_TOKEN = "E";
    protected static final String SEPARATOR = " ";
    protected static final String GAME_SEPARATOR = "#";
    protected static final String COMMA_SEPARATOR = ",";
    protected static final Character BLANK_CHAR = ' ';
    protected static final Character ZERO_CHAR = '0';
    private static DecimalFormat TWO_DECIMAL = new DecimalFormat("#.##");

    protected static final int USERNAME_LENGTH = 15;
    protected static final int TITLE_LENGTH = 19;
    protected static final int CREDIT_LENGTH = 9;
    protected static final int DISCOUNT_LENGTH = 5;
    protected static final int PRICE_LENGTH = 6;
    protected static final int CODE_LENGTH = 2;
    protected static final int TYPE_LENGTH = 2;

    protected String userData;
    protected String dailyData;

    protected static final String logInCode = "00";
    protected static final String createCode = "01";
    protected static final String deleteCode = "02";
    protected static final String sellCode = "03";
    protected static final String buyCode = "04";
    protected static final String refundCode = "05";
    protected static final String addCreditCode = "06";
    protected static final String logOutCode = "10";
    protected static final String giftCode = "09";
    protected static final String removeGameCode = "08";


    private data_base() {
        this.userData = "a2-a-2-g-a-n-g" + File.separator + "DataFiles" + File.separator + "userName.txt";
        this.dailyData = "a2-a-2-g-a-n-g" + File.separator + "DataFiles" + File.separator + "daily.txt";
    }

    private data_base(String userPath, String dailyPath){
        this.userData = userPath;
        this.dailyData = dailyPath;
    }

    protected static data_base getInstance(byte[] key) {
        // Authenticates key to verify the Session object has called the function
        if (Session.authenticateKey(key)) {
            if (instance == null)
                instance = new data_base();
            return instance;
        }
        else return null;
    }

    /**
     * Pads the string input, with the character passed in, so that the string's length is equal to the length passed in
     *
     * You can change left and right padding based on the sign of the length integer passed in.
     *      Right padding: If length > 0.
     *      Left padding: if length < 0.
     *
     * @param input String to pad
     * @param character character to pad string with
     * @param length length of the output string after padding
     */
    protected static String stringPadding(String input, char character, int length){
        length = -length;
        return String.format("%" + length + "s", input).replace(' ', character);
    }

    /**
     * This method writes all basic transactions to daily.txt with the following syntax:
     *
     *             XX UUUUUUUUUUUUUUU TT CCCCCCCCC
     *
     * Possible transactions: 00-login, 01-create, 02-delete, 06-addCredit, 10-logout
     *
     * It ensure the following constraints are met:
     *          - Username is of the length USERNAME_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - Credit is of the length CREDIT_LENGTH and is padded on the left with ZERO_CHAR('0')
     *          - All data fields are separated with the SEPARATOR(" ")
     *          - data is written to dailyData
     * @param code transaction code
     * @param username username associated with the transaction
     * @param usertype  type of account
     * @param cred credit in account
     */
    protected void writeBasicTransaction(String code, String username, String usertype, Double cred){
        username = stringPadding(username, BLANK_CHAR, USERNAME_LENGTH);
        String credit = stringPadding(cred.toString(), ZERO_CHAR, -CREDIT_LENGTH);
        String message = String.join(SEPARATOR, code, username, usertype, credit);
        System.out.println(message);
        appendData(message, dailyData);
    }

    /** Writes transaction when a user buys a game
     *
     * It ensures the following constraints are met:
     *          - Title of the game is of length TITLE_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - Usernames are of the length USERNAME_LENGTH and are padded on the right with BLANK_CHAR(' ')
     *          - All data fields are separated with the SEPARATOR(" ")
     *          - data is written to dailyData
     *
     *
     * @param title Name of the game
     * @param seller username of the seller
     * @param buyer  username of the buyer
     */
    protected void writeBuyTransaction(String title, String seller, String buyer){
        seller = stringPadding(seller, BLANK_CHAR, USERNAME_LENGTH);
        buyer = stringPadding(buyer, BLANK_CHAR, USERNAME_LENGTH);
        title = stringPadding(title, BLANK_CHAR, TITLE_LENGTH);
        String message = String.join(SEPARATOR,buyCode, title, seller, buyer);
        System.out.println(message);
        appendData(message, dailyData);
    }

    /** Writes transactions when a user is gifting a game
     *
     * It ensures the following constraints are met:
     *          - Title of the game is of length TITLE_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - Usernames are of the length USERNAME_LENGTH and are padded on the right with BLANK_CHAR(' ')
     *          - All data fields are separated with the SEPARATOR(" ")
     *          - data is written to dailyData
     *
     * @param gameN name of the game
     * @param gameReceiver username of receiver of the game
     * @param gameOwner username of owner of the game
     */

    protected void writeGiftTransaction(String gameN, String gameReceiver, String gameOwner ){
        gameN = stringPadding(gameN, BLANK_CHAR, TITLE_LENGTH);
        gameReceiver = stringPadding(gameReceiver, BLANK_CHAR, USERNAME_LENGTH);
        gameOwner = stringPadding(gameOwner, BLANK_CHAR, USERNAME_LENGTH);
        String message = String.join(SEPARATOR, giftCode, gameN, gameReceiver, gameOwner);
        System.out.println(message);
        appendData(message, dailyData);
    }


    /** Writes sell transaction to daily.txt when a user puts up a game for sale
     *
     * It ensures the following constraints are met:
     *          - Title of the game is of length TITLE_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - Username is of the length USERNAME_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - discount percentage is of the length Discount_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - price is of the length PRICE_LENGTH and is padded on the right with BLANK_CHAR(' ')
     *          - All data fields are separated with the SEPARATOR(" ")
     *          - data is written to dailyData
     *
     * @param title Name of the game
     * @param seller username of the seller
     * @param disc discount percentage
     * @param price sale price
     */
    protected void writeSellTransaction(String title, String seller, Double disc, Double price){
        title = stringPadding(title, BLANK_CHAR, TITLE_LENGTH);
        seller = stringPadding(seller, BLANK_CHAR, USERNAME_LENGTH);
        String discount = stringPadding(disc.toString(), ZERO_CHAR, DISCOUNT_LENGTH);
        String salePrice = stringPadding(price.toString(), ZERO_CHAR, PRICE_LENGTH);
        String message = String.join(SEPARATOR, sellCode, title, seller, discount, salePrice);
        System.out.println(message);
        appendData(message, dailyData);
    }

    /**
     * Writes refund transaction to daily.txt when a user gets refunded
     *
     * It ensures the following constraints are met:
     *          - Usernames are of the length USERNAME_LENGTH and are padded on the right with BLANK_CHAR(' ')
     *          - Amount refunded is of the length CREDIT_LENGTH and is padded on the left with ZERO_CHAR('0')
     *          - All data fields are separated with the SEPARATOR(" ")
     *          - data is written to dailyData
     *
     * @param buyer username of the buyer
     * @param seller username of the seller
     * @param refundCred amount refunded
     */
    protected void writeRefundTransaction(String buyer, String seller, Double refundCred){
        buyer = stringPadding(buyer, BLANK_CHAR, USERNAME_LENGTH);
        seller = stringPadding(seller, BLANK_CHAR, USERNAME_LENGTH);
        String refund = stringPadding(refundCred.toString(), ZERO_CHAR, -CREDIT_LENGTH);
        String message = String.join(SEPARATOR, refundCode, buyer, seller, refund);
        System.out.println(message);
        appendData(message, this.dailyData);
    }

    /**
     * Writes the data of a user object to user.txt in the following format:
     *
     *          username SEPARATOR type SEPARATOR credit
     *
     *          UUUUUUUUUUUUUUU TT CCCCCCCCC COMMA_SEPARATOR IIIIIIIIIIIIIIIIIII PPPPP
     *
     * @param user User object
     */
    protected void writeUser(User user){
        String profile = String.join(SEPARATOR, stringPadding(user.getUserName(),' ', USERNAME_LENGTH),
                user.getType(), stringPadding(user.getCredit().toString(),' ', CREDIT_LENGTH));
        String gamesOwned = user.gamesOwnedToString();
        String data = String.join(COMMA_SEPARATOR, profile, gamesOwned);
        this.appendData(data, this.userData);
    }

    /**
     * This function will append new data to current existing txt, i.e our database
     *
     * @param data     The data to append to the file
     *                 Eg. If admin call createUser(), then data = "01 username SS 37463"
     *                 with the format of "XX UUUUUUUUUUUUUUU TT CCCCCCCCC"
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     *                 <p>
     *                 To use this function, for example, Admin wants to create user, we will do both
     *                 appendData("XX UUUUUUUUUUUUUUU TT CCCCCCCCC", dailyData) <- update daily.txt
     *                 and
     *                 appendData("UserName type credit", userData) <- update our user data base
     */
    private void appendData(String data, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(data);
            writer.newLine();
            writer.flush();
            writer.close();
            //System.out.println(filePath);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Updates the data base file with the updated users. Preferably use at the end of the day.
     */
    protected void updateDataBase(ArrayList<User> userList){
        clear(userData);
        if (userList != null) {
            for (User user : userList) {
                writeUser(user);
            }
        }
    }

    /**
     * Clears the file given from filePath
     * @param filePath the path to the file.
     */
    private void clear(String filePath) {
        try {
            new FileOutputStream(filePath).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all users from database into an array list.
     * @param filePath The file path to database.
     * @return An array list containing all users
     */
    protected ArrayList<User> loadUsers(String filePath) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<String> lines = readFile(filePath);
        for (String currentLine : lines) {
            User user = getUser(currentLine);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    private String[] userSubString(String line){

        String[] profile = line.split(COMMA_SEPARATOR);
        try {
            int start = USERNAME_LENGTH + SEPARATOR.length();
            int mid = start + CODE_LENGTH + SEPARATOR.length();
            int end = start + CREDIT_LENGTH + SEPARATOR.length();
            String username = profile[0].substring(0, start).strip();
            String type = profile[0].substring(start, mid).strip();
            String credit = profile[0].substring(mid, end).strip();

            return new String[]{username, type, credit, profile[1]};
        }catch (Exception e){
            return null;
        }

    }

    /**
     * This function will check if the passed in userName already exist in our user database
     *
     * @param userName The userName we want to check
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     *                 <p>
     *                 <p>
     *                 To use this function, for example, Admin wants to know user_1 exist or not
     *                 we can call data_base.ifUserExist("UserName", userData)
     *                 or admin can simply call getInfo(String userName) in the Admin class
     */
    protected static boolean ifUserExist(String userName, String filePath) throws IOException {
        File inputFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(SEPARATOR);
            if (tokens[0].equals(userName)) {
                return true;
            }
        }
        reader.close();
        return false;
    }

    /**
     * This method returns a User object based on the data associated with the given line in the database. It
     * processes the data from the database and passes it into generateUser.
     *
     * @param line The current line.
     * @return User object if the username exists in database otherwise it's null.
     */
    protected User getUser(String line) {
        User user = null;
        String[] profile = userSubString(line);

        if (profile!=null) {
            String[] gamesOwned = profile[3].split(GAME_SEPARATOR);
            String username = profile[0];
            String userType = profile[1];
            Double credit = Double.parseDouble(profile[2]);

            // Create games list
            ArrayList<Game> gamesList = new ArrayList<>();
            for (String game : gamesOwned){
                gamesList.add(Game.stringToGame(game));
            }

            user = generateUser(username, userType, credit, gamesList);
        }
        return user;
    }


    /**
     * This method simply forms the appropriate user object based on the type given.
     *
     * @param username username of the user
     * @param type user type
     * @param credit credits in user's account
     * @param gamesOwned an ArrayList of the games the user owns.
     * @return a user object
     */
    protected User generateUser(String username, String type, Double credit, ArrayList<Game> gamesOwned) {
        User user = null;
        switch (type) {
            case User.ADMIN_USER_TYPE:
                user = new Admin(username, credit, gamesOwned);
                break;
            case User.FULL_USER_TYPE:
                user = new FullStandardUser(username, credit, gamesOwned);
                break;
            case User.BUYER_USER_TYPE:
                user = new Buyer(username, credit, gamesOwned);
                break;
            case User.SELLER_USER_TYPE:
                user = new Seller(username, credit, gamesOwned);
                break;
        }
        return user;
    }


    /**
     * This function will get all the information we have in our database about the passed in userName
     *
     * @param userName The userName we want to get
     * @return A line of space splited string with all the information in the format of:
     * "UserName Type Credit GameTheyOwn"
     */
    protected String getUserData(String userName){
        ArrayList<String> lines = readFile(userData);

        for (String line : lines) {
            String profile = line.split(COMMA_SEPARATOR)[0];
            String[] tokens = profile.split(SEPARATOR);
            if (tokens[0].equals(userName)) {
                return line;
            }
        }
        return "Error: \\<user not found>\\";
    }

    protected ArrayList<Transaction> getTransactions(){
        ArrayList<String> stringTransactions = readFile(dailyData);
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (String stringTransaction : stringTransactions){
            String[] tokens = stringTransaction.split(SEPARATOR);
            switch (tokens[0]){
                case logInCode:
                case logOutCode:
                case createCode:
                case deleteCode:
                case giftCode:
                case addCreditCode:
                    String[] subStrings = basicTransactionSubstring(stringTransaction);
                    transactions.add(new BasicTransaction(tokens[0], subStrings[0], subStrings[1], subStrings[2]));
                    break;
                case buyCode:
                    String[] buySubStrings = buyTransactionSubString(stringTransaction);
                    transactions.add(new BuyTransaction(tokens[0], buySubStrings[0], buySubStrings[1], buySubStrings[2]));
                    break;
                case sellCode:
                    String[] sellSubStrings = sellTransactionSubString(stringTransaction);
                    transactions.add(new SellTransaction(tokens[0], sellSubStrings[0],
                            sellSubStrings[1], sellSubStrings[2], sellSubStrings[3]));
                    break;
                case refundCode:
                    transactions.add(new RefundTransaction(tokens[0], tokens[1], tokens[2], tokens[3]));
                    break;
            }
        }
        return transactions;
    }

    private String[] basicTransactionSubstring(String line){
        int start = CODE_LENGTH + SEPARATOR.length();
        int mid = start + USERNAME_LENGTH + SEPARATOR.length();
        int end = mid + TYPE_LENGTH + SEPARATOR.length();
        String username = line.substring(start, start + USERNAME_LENGTH);
        String type = line.substring(mid, mid + TYPE_LENGTH);
        String credit = line.substring(end, end + CREDIT_LENGTH);

        return new String[] {username, type, credit};
    }

    private String[] buyTransactionSubString(String line){
        int start = CODE_LENGTH + SEPARATOR.length();
        int mid = start + TITLE_LENGTH + SEPARATOR.length();
        int end = mid + USERNAME_LENGTH + SEPARATOR.length();
        String title = line.substring(start, start + TITLE_LENGTH);
        String seller = line.substring(mid, mid + USERNAME_LENGTH);
        String buyer = line.substring(end, end + USERNAME_LENGTH);

        return new String[] {title, seller, buyer};
    }

    private String[] sellTransactionSubString(String line){
        int start = CODE_LENGTH + SEPARATOR.length();
        int mid1 = start + TITLE_LENGTH + SEPARATOR.length();
        int mid2 = mid1 + USERNAME_LENGTH + SEPARATOR.length();
        int end = mid2 + DISCOUNT_LENGTH + SEPARATOR.length();
        String title = line.substring(start, start + TITLE_LENGTH);
        String seller = line.substring(mid1, mid1 + USERNAME_LENGTH);
        String discount = line.substring(mid2, mid2 + DISCOUNT_LENGTH);
        String price = line.substring(end, end + PRICE_LENGTH);

        return new String[] {title, seller, discount, price};
    }

    private String[] refundTransactionSubString(String line){
        int start = CODE_LENGTH + SEPARATOR.length();
        int mid1 = start + USERNAME_LENGTH + SEPARATOR.length();
        int end = mid1 + USERNAME_LENGTH + SEPARATOR.length();
        String buyer = line.substring(start, start + USERNAME_LENGTH);
        String seller = line.substring(mid1, mid1 + USERNAME_LENGTH);
        String credit = line.substring(end, end + CREDIT_LENGTH);

        return new String[] {buyer, seller, credit};
    }


    private String[] extraTransactionSubstring(String line){
        int start = CODE_LENGTH + SEPARATOR.length();
        int mid1 = start + TITLE_LENGTH + SEPARATOR.length();
        int end = mid1 + USERNAME_LENGTH + SEPARATOR.length();
        String title = line.substring(start, start + TITLE_LENGTH);
        String owner = line.substring(mid1, mid1 + USERNAME_LENGTH);
        String receiver = line.substring(end, end + USERNAME_LENGTH);

        return new String[] {title, owner, receiver};
    }

    protected ArrayList<String> readFile(String filePath){
        ArrayList<String> lines = new ArrayList<>();
        try {
            File inputFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;


            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
            reader.close();
        }catch (IOException e){
            //Todo: review this error display part
            System.out.println(e.getMessage());
            System.out.println("Error: \\<reading file>\\");
        }
        return lines;
    }

    /**
     * This function will remove all the data of the passed in UserName from our database
     * and write the record to the daily.txt
     *
     *
     * @param UserName The userName we want to remove from our data base
     *
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     *
     * Admin will call deleteUser() and the deleteUser() will call this function. This function should not be called directly
     * since Admin is the only type of user that can perform this action.
     *
     *
     */

    public static void removeUserData(String UserName, String filePath) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(SEPARATOR);
            if (tokens[0].equals(UserName)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
        System.out.println(successful);
    }


    public static void main(String[] args) throws IOException {
        data_base dataBase = new data_base();
        /*
        dataBase.writeBasicTransaction("04", "Kentucky Fried", "AA", 34.02);
        dataBase.writeBasicTransaction("05", "Kentucky", "AA", 34.02);
        dataBase.writeBasicTransaction("09", "Kent", "AA", 34.02);*/

        // User user = dataBase.getUser("David");
        // System.out.println(user);

        String a = "00";
        String b = "02";


        ArrayList<User> arrayList = dataBase.loadUsers("daily.txt");

        for (User u: arrayList) {
            System.out.println(u.getUserName());
        }
    }
}
