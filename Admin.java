import java.io.IOException;
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

    public void createUser(String userName, String type, String credit) throws IOException {
        if(!data_base.ifUserExist(userName, data_base.userData)) {
            String userData = userName + " " + type+" "+credit;
            String transaction = User.createCode + " " + userName + " " + type + " " + credit;

            data_base.appendData(userData, data_base.userData);
            data_base.appendData(transaction, data_base.dailyData);
        }else{
            System.out.println("User exists");
        }

    }

    public void deleteUser(String userName) throws IOException {

        if(data_base.ifUserExist(userName, data_base.userData)){
            System.out.println("In delete user, user exists");
            String transaction = User.deleteCode+" "+ data_base.getUserData(userName, data_base.userData);
            data_base.removeUserData(userName,data_base.userData);
            System.out.println(transaction);
            data_base.appendData(transaction, data_base.dailyData);
        }
    }

}
