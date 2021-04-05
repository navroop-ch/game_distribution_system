import java.util.ArrayList;

/**
 * A transaction class for gifting a game to a user
 */
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
            System.out.println("Error: User does not exist. Gift transaction Failed !");
            return false;
        }
        
    }


    /**
     * Checks if the transaction  is valid by checking its format and transaction code
     * @param code transaction code
     * @param gameGift game's title/name
     * @param gameReceiver username of the game's receiver
     * @param gameOwner username of the game's owner
     * @return true if the format of transaction string is correct, false otherwise
     */
    protected Boolean transactionValidate(String code, String gameGift, String gameReceiver, String gameOwner) {
        return gameTitleValidation(gameGift) && usernameValidation(gameReceiver) && code.equals(data_base.giftCode);

    }



}
