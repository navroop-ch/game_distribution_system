import java.io.*;
import java.lang.*;
import java.util.ArrayList;

/**
 * An data base class that handles every interaction with the daily.txt or our userName.txt (database)
 *
 */

//Todo: change name of file to DataBase
public class data_base {

    protected final String ERROR_TOKEN = "E";
    protected static final String SEPARATOR = " ";
    protected static final Character BLANK_CHAR = ' ';
    protected static final Character ZERO_CHAR = '0';
    protected static final int USERNAME_LENGTH = 15;
    protected static final int TITLE_LENGTH = 19;
    protected static final int CREDIT_LENGTH = 9;
    protected static final int DISCOUNT_LENGTH = 5;
    protected static final int PRICE_LENGTH = 6;

    protected String userData = "userName.txt";
    protected String dailyData = "daily.txt";

    protected static final String logInCode = "00";
    protected static final String createCode = "01";
    protected static final String deleteCode = "02";
    protected static final String sellCode = "03";
    protected static final String buyCode = "04";
    protected static final String refundCode = "05";
    protected static final String addCreditCode = "06";
    protected static final String logOutCode = "10";

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

    protected void writeBasicTransaction(String code, String username, String usertype, Double cred){
        username = stringPadding(username, ' ', USERNAME_LENGTH);
        String credit = stringPadding(cred.toString(), ZERO_CHAR, -CREDIT_LENGTH);
        String message = String.join(SEPARATOR, code, username, usertype, credit);
        System.out.println(message);
        appendData(message, dailyData);
    }

    protected void writeBuyTransaction(String title, String seller, String buyer){
        seller = stringPadding(seller, BLANK_CHAR, USERNAME_LENGTH);
        buyer = stringPadding(buyer, BLANK_CHAR, USERNAME_LENGTH);
        title = stringPadding(title, BLANK_CHAR, TITLE_LENGTH);
        String message = String.join(SEPARATOR,buyCode, title, seller, buyer);
        System.out.println(message);
        appendData(message, dailyData);
    }

    protected void writeSellTransaction(String title, String seller, Double disc, Double price){
        title = stringPadding(title, BLANK_CHAR, TITLE_LENGTH);
        seller = stringPadding(seller, BLANK_CHAR, USERNAME_LENGTH);
        String discount = stringPadding(disc.toString(), ZERO_CHAR, DISCOUNT_LENGTH);
        String salePrice = stringPadding(price.toString(), ZERO_CHAR, PRICE_LENGTH);
        String message = String.join(SEPARATOR, sellCode, title, seller, discount, salePrice);
        System.out.println(message);
        appendData(message, dailyData);
    }

    protected void writeRefundTransaction(String buyer, String seller, Double refundCred){
        buyer = stringPadding(buyer, BLANK_CHAR, USERNAME_LENGTH);
        seller = stringPadding(seller, BLANK_CHAR, USERNAME_LENGTH);
        String refund = stringPadding(refundCred.toString(), ZERO_CHAR, -CREDIT_LENGTH);
        String message = String.join(SEPARATOR, refundCode, buyer, seller, refund);
        System.out.println(message);
        appendData(message, this.dailyData);
    }

    protected void writeUser(User user){
        String profile = String.join(SEPARATOR, user.getUserName(), user.getType(), user.getCredit().toString());
        String gamesOwned = user.toStringGamesOwned();
        String data = String.join(SEPARATOR, profile, gamesOwned);
        this.appendData(data, this.userData);
    }

    /**
     * Loads all users from database into an array list.
     * @param file The file path to database.txt
     * @return An array list containing all users
     */
    protected ArrayList<User> loadUsers(String file){
        try {
            File inputFile = new File(file);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;

            ArrayList<User> users = new ArrayList<>();
            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(",");
                User tempUser;
                String type = tokens[0];
                String username = tokens[1];
                double credit = Double.parseDouble(tokens[2]);
                ArrayList<Game> gameOwned = new ArrayList<>();

                // not entirely sure how the games toString looks...
                String[] gameTokens = tokens[3].split("#");
                for (int i = 0; i < gameTokens.length; i++) {
                    String[] temp = gameTokens[i].split(" ");
                    gameOwned.add(new Game(temp[0], Double.parseDouble(temp[1]), Boolean.parseBoolean(temp[2])));
                }
                tempUser = generateUser(username, type, credit, gameOwned);
                if (tempUser != null) {
                    users.add(tempUser);
                }
            }
            reader.close();
            return users;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found error");
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
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
     * This method returns a User object based on the data associated with the given username in the database. It
     * processes the data from the database and passes it into generateUser.
     *
     * @param username username of the account
     * @return User object if the username exists in database otherwise it's null.
     */
    protected User getUser(String username) {
        User user = null;
        String[] userData = getUserData(username, this.userData).split(SEPARATOR);
        if (!userData[0].equals(ERROR_TOKEN)) {

            Double credit = Double.parseDouble(userData[2]);
            ArrayList<Game> dummyGamesOwned = new ArrayList<Game>();  //Todo: fix this dummy variable!
            user = generateUser(userData[0], userData[1], credit, dummyGamesOwned);
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
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     * @return A line of space splited string with all the information in the format of:
     * "UserName Type Credit GameTheyOwn"
     */
    protected String getUserData(String userName, String filePath){
        try {
            File inputFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;


            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(SEPARATOR);
                if (tokens[0].equals(userName)) {
                    return currentLine;
                }
            }
            reader.close();
            return "E ERROR: \\<User not found\\>";
        }catch (IOException e){
            return "E Error: \\<reading file>\\";
        }
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

        public static String
        rightPadding(String input, char ch, int L)
        {

            String result = String.format("%" + L + "s", input).replace(' ', ch);

            // Return the resultant string
            return result;
        }

    public static void main(String[] args){

            data_base dataBase = new data_base();
            dataBase.writeBasicTransaction("04", "Kentucky Fried", "AA", 34.02);
            dataBase.writeBasicTransaction("05", "Kentucky", "AA", 34.02);
            dataBase.writeBasicTransaction("09", "Kent", "AA", 34.02);


    }
}


