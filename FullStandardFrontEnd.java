public class FullStandardFrontEnd extends FrontEnd{

    protected void callBuy(){
        String gameName = this.getStringInput("Enter the name of the game: ");
        String sellerName = this.getStringInput("Enter the username of the seller: ");
        //Todo: call Buy method

    }

    protected void callSell(){
        String gameName = this.getStringInput("Enter the name of the game: ");
        Float gamePrice = this.getFloatInput("Enter the price of to sell this game at: ");
        if (AuctionSale){ Float saleDiscount = this.getFloatInput("Enter the sale discount for the auction sale: ");}

        //Todo: call Sell method
    }

    private void callDelete(){
        //Todo: Solve confusion here that how does a user sned an admin the request to delete their account
        
    }
}
