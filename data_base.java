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
    


}


