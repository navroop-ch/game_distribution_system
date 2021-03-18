import java.util.Scanner;

/**
 * An Abstract class with the common functionality to login/logout and input String/Float values.
 * The addCredits that all users have in common is overriden in AdminFrontEnd to cater more functionality.
 */
public abstract class FrontEnd{

    //Todo: create a variable to store a session object
    // private Session session;
    protected static boolean AuctionSale = false;
    private static final Scanner stdin =  new Scanner(System.in);
    private static final String INVALID_INPUT_MESSAGE = "Invalid input entered";

    public FrontEnd(){
        //TODO: Implement this after a session object is created
        //this.session = session;       //Avoid A1 mistake!
    }

    private boolean login(){
        //TODO: Implement this method
        return false;
    };

    private boolean logout(){
        //TODO: Implement this method
        return false;};

    // Remember: A maximum of $1000.00 can be added to an account in a given day!
    protected boolean callAddCredit(){
        Float amount = this.getFloatInput("Enter amount to be added to account: ");

        //Todo: call add credit method in user
        return false;
    }

    protected String getStringInput(String message){
        try {
            System.out.print(message);
            return FrontEnd.stdin.nextLine();
        } catch (Exception e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            stdin.next();
        }
        return null;
    }

    protected Float getFloatInput(String message){
        try {
            System.out.print(message);
            return FrontEnd.stdin.nextFloat();
        } catch (Exception e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            stdin.next();
        }
        return null;
    }

}