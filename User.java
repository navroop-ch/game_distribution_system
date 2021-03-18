import java.util.ArrayList;

public interface User {
    public static final String logInCode = "00";
    public static final String createCode = "01";
    public static final String deleteCode = "02";
    public static final String addCreditCode = "06";
    public static final String logOutCode = "10";

    public String getUserName();
    public String getType();
    public String getCredit();
    public ArrayList<String> getOwnedGame();

    public void setCredit();
    public void setOwnedGame(ArrayList<String> game);

}
