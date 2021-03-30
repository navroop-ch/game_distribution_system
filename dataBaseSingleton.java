import java.io.*;
public class dataBaseSingleton {
    private static dataBaseSingleton instance = null;
    public static String userData = "userName.txt";
    public static String dailyData = "daily.txt";

    private dataBaseSingleton() {

    }

    public static dataBaseSingleton getInstance() {
        if (instance == null)
            instance = new dataBaseSingleton();

        return instance;
    }

    /**
     * This function will append new data to current existing txt, i.e our database
     *
     *
     * @param data The data to append to the file
     *             Eg. If admin call createUser(), then data = "01 username SS 37463"
     *                 with the format of "XX UUUUUUUUUUUUUUU TT CCCCCCCCC"
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     *
     * To use this function, for example, Admin wants to create user, we will do both
     *                 appendData("XX UUUUUUUUUUUUUUU TT CCCCCCCCC", dailyData) <- update daily.txt
     *                 and
     *                 appendData("UserName type credit", userData) <- update our user data base
     */

    public void appendData(String data, String filePath){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(data);
            writer.newLine();
            writer.flush();
            writer.close();
            System.out.println(filePath);
        }catch(IOException e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    /**
     * This function will check if the passed in userName already exist in our user database
     *
     *
     * @param userName The userName we want to check
     *
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     *
     *
     * To use this function, for example, Admin wants to know user_1 exist or not
     *                 we can call data_base.ifUserExist("UserName", userData)
     *                 or admin can simply call getInfo(String userName) in the Admin class
     */
    public boolean ifUserExist (String userName, String filePath) throws IOException {
        File inputFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(" ");
            if(tokens[0].equals(userName)){
                return true;
            }
        }
        reader.close();
        return false;
    }

    /**
     * This function will get all the information we have in our database about the passed in userName
     *
     *
     * @param userName The userName we want to get
     *
     * @param filePath In our data base, we have two existing filePath. Either userName.txt or
     *                 daily.txt which has been created as a static String variable in this class.
     *
     * @return A line of space splited string with all the information in the format of:
     *                  "UserName Type Credit GameTheyOwn"
     *
     *
     */
    public String getUserData (String userName, String filePath) throws IOException {
        File inputFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String currentLine;


        while((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(" ");
            if(tokens[0].equals(userName)){
                return currentLine;
            }
        }
        reader.close();
        return "ERROR: \\<User not found\\>";
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

    public void removeUserData(String UserName, String filePath) throws IOException {
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
