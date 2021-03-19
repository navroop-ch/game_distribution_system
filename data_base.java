import java.io.*;

public class data_base {
    public static String userData = "userName.txt";
    public static String dailyData = "daily.txt";

    public static void appendData(String data, String filePath){
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

    public static boolean ifUserExist (String userName, String filePath) throws IOException {
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

    public static String getUserData (String userName, String filePath) throws IOException {
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

    public static void removeUserData(String data, String filePath) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(" ");
            if(tokens[0].equals(data)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
        System.out.println(successful);
    }
    


}


