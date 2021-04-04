import java.util.ArrayList;

public class giftTrans extends Transaction{
    protected String gameOwner;
    protected String gameReceiver;
    protected String gameGift;

    protected giftTrans(String code, String giftGame, String gameReceiver, String gameOwner){
        super(gameOwner);

        if (transactionValidate(code, gameGift, gameReceiver, gameOwner) && validTransaction) {
            this.gameOwner = gameOwner;
            this.gameReceiver = gameReceiver;
            this.gameGift = giftGame;
        }
        else this.validTransaction = false;
    }
    @Override
    protected Boolean execute(Session session) {
        User owner = session.getUser(this.gameOwner);
        User receiver = session.getUser(this.gameReceiver);
        if (owner != null && receiver!= null){
            if (gameGift.equals(owner.owned(gameGift).getTitle())) {
                Game game = owner.owned(gameGift);
                owner.removeGame(game);
                receiver.addOwnedGame(game);
                return true;
            }
            return true;
        }
        else {
            //Todo: figure out appropriate error return
            System.out.println("Error: User does not exist");
            return false;
        }
        
    }

    //check format and correct trans code
    protected Boolean transactionValidate(String code, String gameGift, String gameReceiver, String gameOwner) {
        return gameTitleValidation(gameGift) && usernameValidation(gameReceiver) && code.equals(data_base.giftCode);

    }



}
