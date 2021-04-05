import java.util.Arrays;
import java.util.List;

public class ExtraTransaction extends Transaction {
    protected final List<String> ExtraTransactionCodes = Arrays.asList(
            data_base.giftCode, data_base.removeGameCode);
    protected String gameOwner;
    protected String gameReceiver;
    protected String gameGift;

    protected ExtraTransaction(String code, String gameGift, String gameOwner, String gameReceiver) {
        super(gameOwner);

        if (transactionValidate(code, gameGift, gameReceiver, gameOwner) && validTransaction) {
            this.transactionCode = code;
            this.gameOwner = gameOwner.strip();
            this.gameReceiver = gameReceiver.strip();
            this.gameGift = gameGift.strip();
        }
        else this.validTransaction = false;
        System.out.printf("%s %s %s %s", code, gameGift, gameOwner, gameReceiver);
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

    private Boolean executeRemoveGame(Session session) {
        User owner = session.getUser(this.gameOwner);
        if (session.getUserLoggedIn().getType().equals(User.ADMIN_USER_TYPE)) {
            Admin loggedIn = (Admin) session.getUserLoggedIn();
            if (this.gameGift.equals(owner.owned(this.gameGift).getTitle())) {
                loggedIn.removeGame(this.gameGift, this.gameOwner, this.gameReceiver);
                return true;
            }
        }
        else if (owner == session.getUserLoggedIn() && owner.owned(gameGift)!=null) {
            if (owner.owned(gameGift).getTitle().equals(gameGift)) {
                Game game = owner.owned(this.gameGift);
                String message = owner.removeGameTrans(game);
                if (message == null) {
                    System.out.printf("%s: removed %s\n", owner.getUserName(), game.getTitle());
                } else {
                    System.out.println(message);
                }
            }
        }
        else {System.out.printf("Error: %s user does not have game or username in transaction is invalid.\n", CONSTRAINT_ERROR);}
        return false;
    }

    protected Boolean executeGift(Session session) {
        User owner = session.getUser(this.gameOwner);
        User receiver = session.getUser(this.gameReceiver);
        if (owner != null && receiver!= null){
            Game game = owner.owned(gameGift);
            if (game!= null) {
                if (gameGift.equals(game.getTitle())) {
                    owner.removeGame(game);
                    receiver.addOwnedGame(game);
                    System.out.printf("%s: gifted %s to %s\n", owner.getUserName(), game.getTitle(), receiver.getUserName());
                    return true;
                }
            }
            System.out.printf("Error: %s game is not owned by the user trying to gift.\n", CONSTRAINT_ERROR);
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
        return gameTitleValidation(gameGift) && usernameValidation(gameReceiver) && (code.equals(data_base.giftCode)
                || code.equals(data_base.removeGameCode));

    }
}
