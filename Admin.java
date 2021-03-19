import java.util.ArrayList;

public class Admin implements User{
    private String userName;
    private String type;
    private double credit;
    private ArrayList<String> gameOwned;

    public Admin(String username, String type, double credit, ArrayList <String> gameOwned){
        this.userName = username;
        this.type = type;
        this.credit = credit;
        this.gameOwned = gameOwned;

    }
    @Override
    public String getUserName(){
        return this.userName;
    }

    @Override
    public String getType(){
        return this.type;
    }

    @Override
    public double getCredit(){
        return this.credit;
    }

    @Override
    public ArrayList <String> getOwnedGame(){
        return this.gameOwned;
    }

    @Override
    public void addCredit(double credit){
        this.credit+=credit;
    }

    @Override
    public void addOwnedGame(String game){
        this.gameOwned.add(game);
    }
}
