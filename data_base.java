import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

}


