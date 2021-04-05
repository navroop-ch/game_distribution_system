import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestUserTransactions {

    Session session;
    Admin admin;
    Buyer buyer;
    Seller seller;
    FullStandardUser fullst;
    // ==== Helper  ====
    private ArrayList<Game> createGameArray() {
        ArrayList<Game> game = new ArrayList<>();
        // int r = new Random().nextInt(3);
        int r = 4;
        for (int i = 0; i <= r; i++) {
            if (i%2 == 0)
                game.add(new Game(String.valueOf(i), 1.0, true));
            else
                game.add(new Game(String.valueOf(i), 1.0, false));
        }
        return game;
    }


    @BeforeEach
    public void setUp() {
        // Fresh session for each test
        session = Session.getInstance();
        ArrayList<Game> games = createGameArray();
        admin = new Admin("A", 1, games);
        buyer = new Buyer("B", 1, games);
        seller = new Seller("S", 1, games);
        fullst = new FullStandardUser("F", 1, games);

        session.getUserList().clear();
        session.getUserList().add(admin);
        session.getUserList().add(buyer);
        session.getUserList().add(seller);
        session.getUserList().add(fullst);

        // Log in the admin
        // BasicTransaction trans = new BasicTransaction(data_base.logInCode,admin.getUserName(),
        //        admin.getType(), String.valueOf(admin.getCredit()));

        // trans.execute(session);
        admin.login();
    }

    @Test
    public void testLogin() {
        // Check if login works
        assertEquals(session.getUserLoggedIn(), admin);

        // Can't login when there is already a user logged in
        BasicTransaction trans = new BasicTransaction(data_base.logInCode,buyer.getUserName().concat(" ".repeat(14)),
                buyer.getType(), String.valueOf(buyer.getCredit()).concat("0".repeat(6)));

        trans.execute(session);
        session.sessionLogin(buyer);
        assertEquals(buyer.loginStatus, false);

        admin.logout();
    }


    @Test
    public void testLogout() {

        BasicTransaction trans = new BasicTransaction(data_base.logOutCode,admin.getUserName().concat(" ".repeat(14)),
                admin.getType(), String.valueOf(admin.getCredit()).concat("0".repeat(6)));

        // Logout execute works
        trans.execute(session);
        assertEquals(session.getLoginStatus(), false);

        // Can't logout if you aren't logged in
        trans = new BasicTransaction(data_base.logOutCode,admin.getUserName().concat(" ".repeat(14)),
                admin.getType(), String.valueOf(admin.getCredit()).concat("0".repeat(6)));
        trans.execute(session);
        assertEquals(session.getLoginStatus(), false);

        admin.logout();
    }

    @Test
    public void testaddCredit() {

        double credit = 1.0;
        double old = buyer.getCredit();
        double actual = buyer.getCredit() + credit;

        // Adding works
        BasicTransaction trans = new BasicTransaction(data_base.addCreditCode,buyer.getUserName().concat(" ".repeat(14)),
                buyer.getType(), String.valueOf(credit).concat("0".repeat(6)));

        trans.execute(session);
        assertEquals(buyer.getCredit(), actual);

        // Adding an negative value
        trans = new BasicTransaction(data_base.addCreditCode,buyer.getUserName().concat(" ".repeat(14)),
                buyer.getType(), String.valueOf(-credit).concat("0".repeat(6)));

        trans.execute(session);
        assertEquals(buyer.getCredit(),old);

        admin.logout();
    }

    @Test
    public void testBuy() {
        Random r = new Random();
        Game game = admin.getOwnedGame().get(r.nextInt(admin.getOwnedGame().size()));
        String title = game.getTitle();
        double oldCredit = admin.getCredit();
        double actual = admin.getCredit() + game.getPrice();

        // Buying an existing game works if it is forSale
        BuyTransaction trans = new BuyTransaction(data_base.buyCode,title.concat(" ".repeat(17)),admin.getUserName().concat(" ".repeat(14)),
                buyer.getUserName().concat(" ".repeat(14)));
        trans.execute(session);
        if (game.isForSale())
            assertEquals(admin.getCredit(), oldCredit);
        else
            assertEquals(admin.getCredit(), actual);

        // Buying a non-existent game doesn't work
        trans = new BuyTransaction(data_base.buyCode,"t".concat(" ".repeat(data_base.TITLE_LENGTH - 1)),
                admin.getUserName().concat(" ".repeat(data_base.USERNAME_LENGTH - 1)),
                buyer.getUserName().concat(" ".repeat(data_base.USERNAME_LENGTH - 1)));
        assertEquals(trans.execute(session), false);

        admin.logout();
    }

    @Test
    public void testSell() {
        Game g = new Game("5", 1.0, false);
        SellTransaction trans = new SellTransaction(data_base.sellCode,"5".concat(" ".repeat(data_base.TITLE_LENGTH - 1)),
                seller.getUserName().concat(" ".repeat(14)),
                "1.0".concat("0".repeat(data_base.DISCOUNT_LENGTH - 3)), "false");
        trans.execute(session);

        assertEquals(seller.owned(g.getTitle()).isForSale(), true);

        admin.logout();
    }

    @Test
    public void testRefund() {

    }

    @Test
    public void testRemoveGame() {

    }

    @Test
    public void testGift() {

    }

    @Test
    public void testAuctionSale() {

    }
}