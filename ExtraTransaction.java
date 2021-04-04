import java.util.Arrays;
import java.util.List;

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

    private Boolean executeRemoveGame(Session session) {
        User owner = session.getUser(this.gameOwner);
        User seller = session.getUser(this.gameReceiver);
        if (owner != null && seller != null) {
            if (gameGift.equals(owner.owned(gameGift).getTitle())) {
                Game game = owner.owned(gameGift);
                // ToDo: Are these implemented yet?
                owner.removeGame(game);
                seller.removeGame(game);
                return true;
            }
        }
        // Todo: Appropriate error return statement
        return false;
    }

    protected Boolean executeGift(Session session) {
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
        return gameTitleValidation(gameGift) && usernameValidation(gameReceiver) && (code.equals(data_base.giftCode)
                || code.equals(data_base.removeGameCode));

    }
}