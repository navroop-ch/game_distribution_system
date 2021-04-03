import java.util.ArrayList;

public class giftTrans extends Transaction{
    protected User gameOwner;
    protected User gameReceiver;
    protected Game gameGift;

    protected giftTrans(String code, Game giftGame, User gameReceiver, User gameOwner){
        super(Session.getInstance().getLoggedInUserName());
        this.gameOwner= gameOwner;
        this.gameReceiver=gameReceiver;
        this.gameGift= giftGame;


    }
    @Override
    protected Boolean execute() {
        if(gameGift.equals(gameOwner.owned(gameGift.getTitle()))) {
            gameOwner.removeGame(gameGift);
            gameReceiver.addOwnedGame(gameGift);
            return true;
        }

        return false;
    }


}
