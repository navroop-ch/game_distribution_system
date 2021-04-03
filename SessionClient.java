import java.util.ArrayList;

public class SessionClient {

    public static void main(String[] args){

        Session session = Session.getInstance();

        ArrayList<Game> ownedGames = new ArrayList<Game>();
        ownedGames.add(new Game("Fortnite", 350.34,true));
        ownedGames.add(new Game("Rsix siege", 550.34,true));
        ownedGames.add(new Game("Call of duty", 550.34,true));
        Admin admin0 = new Admin("Fred", 999000, ownedGames);

        FullStandardUser u = new FullStandardUser("David", 2344.00, ownedGames);

        session.executeBackend();
    }
}
