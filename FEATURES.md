# Features

**TODO: Add features of the program**

# Features

###  An Overview of the Program -
The program is the backend implementation of a game store web service where users can
perform transactions.

### Description of Classes -
- src.User (abstract class): There are 4 user subclasses that extend this class in our program.
    1. src.Admin-type user
    2. Full-standard user
    3. src.Buyer user
    4. src.Seller user

- src.Transaction (abstract class): There are 5 transaction subclasses that are used to store transactions


read from daily.txt

- src.data_base: The class that parses through both usernames.txt and daily.txt.


- src.Session: A singleton class that holds all users and transaction executes from daily.txt
  There will always only be one session class.

- src.SessionClient: Creates a src.Session object and executes the backend.

- src.TestUserTransactions: Since all the code relies on the session class, the testing
  is done on the src.Transaction.execute methods and verifies that they all run correctly.

### Design Patterns and Strategies -
We designed the session class as a singleton to allow only a single user to be logged
in at one time.

The transaction classes rely on the command design pattern, where they all have an
execute function which calls the corresponding method from user.

### How to run?
Assuming our data base is not empty
src.SessionClient.java executes the backend. It will read command from the text files daily.txt and execute it if it’s valid. To check if it’s valid, the backend will check with userName.txt, a file that stored all our existing user in
src.data_base.java where these file paths are fixed. They can be changed manually.
Add the daily.txt to a2-a-g-a-n-g and then run the main method for src.SessionClient.java 



### Configure userName.txt (database) -
Users are stored in userName.txt in the form:
UUUUUUUUUUUUUUU TT CCCCCCCCC,IIIIIIIIIIIIIIIIIII PPPPPP BBBB#IIIIIIIIIIIIIIIIIII PPPPPP BBBB
Where:
-   UUUUUUUUUUUUUUU
-   is the user’s username
-  TT
-   is the user’s type
-   CCCCCCCCC
-   is the user’s credit balance
    Everything after the comma is the user’s owned games where:
-  IIIIIIIIIIIIIIIIIII
- is the game title
-  PPPPPP
- is the price of the game
-  BBBB
- Is a boolean value that represents whether the game is for sale.
  A # is used to separate games from each other.
  -IIIIIIIIIIIIIIIIIII
- Is a 









