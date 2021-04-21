### src.User Constraints:
-   Username: At most 15 characters, can not have the same username twice
-   Credit: min 0, max 999,999
-   Usernames can have spaces !!!

### src.Game Constraints:
-   src.Game Title: At most 25/19? characters, can not have the same game name twice
-   src.Game Price: At most 999.99
-   src.Game Discount: At most 90%
-   not unique to sellers

### Txt File Clarifications
-   usernames.txt is the database that contains all users
-   daily.txt is read by our system and each transaction is executed (if it is valid).
-   alphabetical values are padded with " "
-   numeric values are padded with "0"

### src.Transaction Code Details and Constraints:

**login** - start a Front End session
-   Only one user can be logged in at a time.

**logout** - end a Front End session
-   Set logged in to false

**create** – creates a new user with buying and/or selling privileges.
-   privileged transaction - only accepted when logged in as admin user
-   New user should not already exist in data base

**delete** - cancel any games for sale by the user and remove the user account.
-   privileged transaction - only accepted when logged in as admin user
-   username must be the name of an existing user but not the name of the current user
-   user should be removed from data base

**sell** – put up a game for sale

-   sell transaction introduces a new game in the system
-   Sellers have unlimited copies of a game to sell
-   Semi-privileged transaction - only accepted when logged in any type of account except standard-buy
-   a game that was just put up for sale cannot be purchased until the following day.

**buy** – purchase an available game for sale

-   src.Admin can't buy a game from a user
-   The game should be added to the buyer's inventory
-   Semi-privileged transaction - only accepted when logged in any type of account except standard-sell.
-   game name must be an existing game in the seller's inventory that is available for sale
-   cannot purchase a game already in the user's inventory
-   user’s funds should be more than the game’s price


**refund** - issue a credit to a buyer’s account from a seller’s account (privileged transaction)

-   seller's balance > refund amount
-   The specified amount of credit should be transferred from the seller’s credit balance to the buyer’s credit balance.
-   src.Buyer and seller both must be current users

**addcredit** - add credit into the system for the purchase of accounts

-   add credit till the balance of the user maxes out, give warning if the addedcredit > max_user credit
-   Sell only account can add credit
-   The username has to be an existing username (src.Admin).
-   A maximum of $1000.00 can be added to an account in a given day.

**auctionsale** - change the prices of all games for sale to incorporate a seasonal discount (privileged transaction)

-   the code is 07
-   the discount is percent off
-   privileged transaction - only accepted when logged in as admin user
-   can be switched on and off. Affects buy and sell.

**removegame** - remove a game from a user's inventory or from being sold

-  semi-privileged transaction - Non-admins can only remove their own games.
-  src.Admin can remove any user's game.
-  src.Game can be in the owner's inventory/forSale (same thing)
-  cannot remove a game that was purchased or put up to sale on the same day


**gift** - give a user a game from another user

-   src.Admin can gift any game to a user
- semi-privileged transaction - Non-admins can only gift their own games to another.
- Admins can gift from one user to another.
- game must be in the owner's inventory/forSale
- remove game if it already exists in inventory
- cannot gift a game that was purchased or put up to sale on the same day
- cannot gift a game to a user who already owns a game with the same name
