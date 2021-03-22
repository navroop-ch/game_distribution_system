import java.io.*;
import java.util.ArrayList;

/**
 * An data base class that handles every interaction with the daily.txt or our userName.txt (database)
 *
 */

//Todo: change name of file to DataBase
public class data_base {

    public final String ERROR_TOKEN = "E";

    public static String userData = "userName.txt";
    public static String dailyData = "daily.txt";

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
    public static void appendData(String data, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(data);
            writer.newLine();
            writer.flush();
            writer.close();
            System.out.println(filePath);
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
    public static boolean ifUserExist(String userName, String filePath) throws IOException {
        File inputFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(" ");
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
        String[] userData = getUserData(username, this.userData).split(" ");
        if (userData[0] != ERROR_TOKEN) {

            Double credit = Double.parseDouble(userData[2]);
            ArrayList<String> dummyGamesOwned = new ArrayList<String>();  //Todo: fix this dummy variable!
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
    protected User generateUser(String username, String type, Double credit, ArrayList<String> gamesOwned) {
        //Todo: complete this method after the appropriate classes are made
        User user = null;
        switch (type) {
            case User.ADMIN_USER_TYPE:
                user = new Admin(username, credit, gamesOwned);
                break;
            case User.FULL_USER_TYPE:

                break;
            case User.BUYER_USER_TYPE:

                break;
            case User.SELLER_USER_TYPE:

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
    public static String getUserData(String userName, String filePath){
        try {
            File inputFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;


            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(" ");
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
                String[] tokens = currentLine.split(" ");
                if (tokens[0].equals(UserName)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
            System.out.println(successful);
        }


}


