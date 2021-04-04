import java.util.ArrayList;

public class giftTrans extends Transaction{
    protected String gameOwner;
    protected String gameReceiver;
    protected String gameGift;

    protected giftTrans(String code, String giftGame, String gameReceiver, String gameOwner){
        super(Session.getInstance().getLoggedInUserName());
        this.gameOwner= gameOwner;
        this.gameReceiver=gameReceiver;
        this.gameGift= giftGame;


    }
    @Override
    protected Boolean execute(Session session) {
        User owner = session.getUser(this.gameOwner);
        User receiver = session.getUser(this.gameReceiver);
        if (owner != null && receiver!= null){

            return true;
        }
        else {
            //Todo: figure out appropriate error return
            System.out.println("Error: User does not exist");
            return false;
        }
        if (gameGift.equals(owner.owned(gameGift).getTitle())) {
            Game game = owner.owned(gameGift);
            owner.removeGame(game);
            receiver.addOwnedGame(game);
            return true;
        }

        return false;
    }

    //check format and correct trans code
    protected Boolean transactionValidate(String code, String type, String credit) {
        return typeValidation(type) && creditValidation(credit) && code==data_base.giftCode;
    }



}
