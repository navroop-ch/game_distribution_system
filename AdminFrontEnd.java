
public class AdminFrontEnd extends FrontEnd {

    /**
     * Inputs a String username and a Float amount and adds the aomount to the user's credit that is associated with the
     * username added.
     *
     * Ensures:
     *      - username entered is associated with a user
     *      - amount entered is >=0 and <=1000
     *      - Handles exception from user object if his credit exceeds 999,999
     *
     *
     */
    @Override
    protected boolean callAddCredit() {

        // Input valid username
        while (true) {
            String username = this.getStringInput("Enter username of the account: ");
            String user = null; //User user = this.session.getUser(username);
            if (user == null){
                System.out.println("Username does not match any users in database!");
            }
            else {break;}
        }

        // Input valid amount
        while (true){
            float amount = this.getFloatInput("Enter an amount to be added: ");
            if (amount < 0.00){
                System.out.println("Amount cannot be less than 0!");
            }
            else if (amount > 1000.00){
                System.out.println("A maximum of $1000.00 can be added to an account in a given day!");
            }
            else {break;}
        }

        // Add amount to User's credit
        try {
            // user.addCredit(amount);
            return true;
        } catch (Exception e) {
            System.out.println("User's balance exceeds 999,999");
            return false;
        }
        //Todo: Shift main functionality to Admin object class
    }

    /**
     * Takes inputs required for creating a user and calls createUser method in Admin class
     */
    private void callCreateUser(){
        String username = this.getStringInput("Enter username: ");
        String userType = this.getStringInput("Enter user type: ");
        Float credit = this.getFloatInput("Enter amount for initial credit: ");

        // Todo: Plug these values into Admin's createUser method
    }

    /**
     * Takes in a String username and removes the account associated with it by calling deleteUser
     */
    private void callDeleteUser(){
        String username = this.getStringInput("Enter username of the user to be deleted: ");

        // Todo: call delete method
    }

    /**
     * Takes in a buyer and a seller's username and an amount to be refunded then call the refund method.
     */
    private void callRefund(){
        String buyerUsername = this.getStringInput("Enter the username of the buyer: ");
        String sellerUsername = this.getStringInput("Enter the username of the seller: ");
        Float refundAmount = this.getFloatInput("Enter the amount to be refunded: ");

        // Todo: call Refund method
    }

    /**
     * if Auction sale is false sets it to true and vice-versa
     */
    private void auctionSale(){
        if (AuctionSale){ AuctionSale = false;}
        else {AuctionSale = true;}
    }

}