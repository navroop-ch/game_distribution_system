import java.util.Arrays;
import java.util.List;

/**
 * A class for transactions for removing and gifting a game
 */
public class ExtraTransaction extends Transaction {
    protected final List<String> ExtraTransactionCodes = Arrays.asList(
            data_base.giftCode, data_base.removeGameCode);
    protected String gameOwner;
    protected String gameReceiver;
    protected String gameGift;

    protected ExtraTransaction(String code, String gameGift, String gameReceiver, String gameOwner) {
        super(gameOwner);

        if (transactionValidate(code, gameGift, gameReceiver, gameOwner) && validTransaction) {
            this.gameOwner = gameOwner;
            this.gameReceiver = gameReceiver;
            this.gameGift = gameGift;
        }
        else this.validTransaction = false;
    }

    @Override
    protected Boolean execute(Session session) {
        switch (this.transactionCode){
            case data_base.giftCode:
                return executeGift(session);
            case data_base.removeGameCode:
                return executeRemoveGame(session);
        }
        return false;
    }

    /**
     * Removes a game from the list of games owned for a user
     * @param session session objects that keeps track of all the logged in users
     * @return true if a game is removed, false otherwise and print an error msg
     */
    private Boolean executeRemoveGame(Session session) {
        User loggedIn = session.getUserLoggedIn();
        User receiver = session.getUser(this.gameReceiver);
        User owner = session.getUser(this.gameOwner);
        Game game = owner.owned(this.gameGift);

        if (loggedIn.getType().equals(User.ADMIN_USER_TYPE)) {
            Admin admin = (Admin)loggedIn;
            if (this.gameGift.equals(owner.owned(this.gameGift).getTitle())) {

                admin.removeGame(this.gameGift, this.gameOwner, this.gameReceiver);
                return true;
            }
        } else if (receiver != null && owner.owned(gameGift).getTitle().equals(gameGift)){
            receiver.removeGame(game);
        }
        System.out.println("Error: User doesn't exist. Remove game transaction Failed !");
        return false;
    }

    /**
     * Gifts a game to a user
     * @param session session objects that keeps track of all the logged in users
     * @return true if gift transaction is executed successfully, false otherwise
     */
    protected Boolean executeGift(Session session) {
        User loggedIn = session.getUserLoggedIn();
        User owner = session.getUser(this.gameOwner);
        User receiver = session.getUser(this.gameReceiver);

        if (loggedIn.getType().equals(User.ADMIN_USER_TYPE)) {
            Admin admin = (Admin) loggedIn;
            if (this.gameGift.equals(owner.owned(this.gameGift).getTitle())) {

                admin.giftGame(this.gameGift, this.gameOwner, this.gameReceiver);
                return true;
            }
            return true;
        } else if (receiver != null) {
            if (gameGift.equals(loggedIn.owned(gameGift).getTitle())) {
                Game game = loggedIn.owned(gameGift);
                loggedIn.removeGame(game);
                receiver.addOwnedGame(game);
            }
            return true;
        } else {
            System.out.println("Error: User does not exist. Gift game transaction failed !");
            return false;
        }
    }
    
    /**
     * Checks format and transaction code
     * @param code user code
     * @param gameGift name of the game to be gifted
     * @param gameReceiver username of game's receiver
     * @param gameOwner username of game's owner
     * @return true if all the constraints are met, otherwise false
     */
    protected Boolean transactionValidate(String code, String gameGift, String gameReceiver, String gameOwner) {
        return gameTitleValidation(gameGift) && usernameValidation(gameReceiver) && (code.equals(data_base.giftCode)
                || code.equals(data_base.removeGameCode));

    }
}
