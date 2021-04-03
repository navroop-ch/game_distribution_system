import java.util.ArrayList;

public class gift extends Transaction{
    protected User gameOwner;
    protected User gameReceiver;
    protected Game gameGift;

    protected gift(String code, Game giftGame, User gameReceiver, User gameOwner){
        this.gameOwner= gameOwner;
        this.gameReceiver=gameReceiver;
        this.gameGift= giftGame;


    }
    @Override
    protected Boolean execute() {
        if(gameOwner.getUserName().equalsIgnoreCase(Session.getInstance().getLoggedInUserName())){

        }

        return null;
    }

    protected void removeGame(Game game, User user){

    }

    protected void addGame(Game game, User user){
        user.addOwnedGame(game);
    }
}
