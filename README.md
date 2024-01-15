

## Overview

In this project, you will be required to design a working software application based on specifications provided (mimicking the sort of specifications you may receive from a real-life client).

## Part 1: Project specifications below

You will be creating the back-end of a digital distribution system for video games (similar to Valve Corporation's Steam) that allows users to buy or sell access to games.

Your program should work alongside a theoretical front-end interface that provides the back-end with data in the form of daily transaction text files, which contain a day's worth of transactions and user commands.

Your system will be used by four types of users: buyers, sellers, full-standard users (who can buy and sell), and system staff (admin users). Each user, including administators, will have a username, account balance, and inventory of games that they own or have put up for purchase.

Like most customer requirements in real-life, the ones listed in this document are **notoriously unreliable.** That is, everything stated below is a correct requirement of the system, but there may be other considerations that were omitted (for example, there is no mention as to whether users can have a negative account balance [they cannot]). If there are other edge cases or unclear requirements not addressed in this document, it is your responsibility to seek clarification by asking questions on Piazza before the assignment due date.

The customers of this system are the CSC207 teaching team, who reserve the right to make adjustments to the requirements when they deem it necessary. Because of this, it is a good idea not to hard code values into your program in case the requirements change partway through the project.

### Transactions:

The Front End is capable of handling the following transactions, which will then be processed by your Back End:

**login** - start a Front End session

**logout** - end a Front End session

**create** - add a user with the ability to buy/sell games (privileged transaction)

**delete** - remove a user (privileged transaction)

**sell** - add a game to the user's inventory and to the list of games for sale

**buy** - purchase a game being sold by another user and add it to the user's inventory

**refund** - issue a credit to a buyer’s account from a seller’s account (privileged transaction)

**addcredit** - add credit directly into the system

**auctionsale** - change the prices of all games for sale to incorporate a seasonal discount (privileged transaction)

### src.Transaction Code Details:

**login** - start a Front End session

-   The front end will handle all of the login functionality, including passwords and security. You will not need to implement anything in your system to support this.

**logout** - end a Front End session

-   The front end will handle all of the logout functionality. You will not need to implement anything in your system to support this.

**create** – creates a new user with buying and/or selling privileges.

-   The front end will ask for the new username
-   The front end will ask for the type of user (admin or full-standard, buy-standard, sell-standard)
-   The front end will ask for the initial account balance of the new user
-   This information is saved to the daily transaction file
-   Constraints:
    -   privileged transaction - only accepted when logged in as admin user
    -   new user name is limited to at most 15 characters
    -   new user names must be different from all other current users
    -   maximum credit can be 999,999

**delete** - cancel any games for sale by the user and remove the user account.

-   The front end will ask for the username
-   This information is saved to the daily transaction file
-   Constraints:
    -   privileged transaction - only accepted when logged in as admin user
    -   username must be the name of an existing user but not the name of the current user
    -   no further transactions should be accepted on a deleted user’s behalf, nor should other users be able to purchase their games for sale

**sell** – put up a game for sale

-   The front end will ask for the game name
-   The front end will ask for the price of the game in dollars (e.g. 15.00)
-   The front end will ask for the sale discount when an auctionsale is taking place (e.g. 25.00 percent deducted)
-   This information is saved to the daily transaction file
-   Constraints:
    -   Semi-privileged transaction - only accepted when logged in any type of account except standard-buy.
    -   the maximum price for an game is 999.99
    -   the maximum length of an game name is 25 characters
    -   the maximum sale discount is 90 percent
    -   a game that was just put up for sale cannot be purchased until the following day.

**buy** – purchase an available game for sale

-   The front end will ask for the game name and the seller’s username
-   The price of the game should be deducted from the buyer's account balance and added to the seller's account balance
-   The game should be added to the buyer's inventory
-   This information is saved to the daily transaction file
-   Constraints:
    -   Semi-privileged transaction - only accepted when logged in any type of account except standard-sell.
    -   game name must be an existing game in the seller's inventory that is available for sale
    -   cannot purchase a game already in the user's inventory
    -   user must have enough available funds to purchase the game

**refund** - issue a credit to a buyer’s account from a seller’s account (privileged transaction)

-   The front end will ask for the buyer’s username, the seller’s username and the amount of credit to transfer.
-   The specified amount of credit should be transferred from the seller’s credit balance to the buyer’s credit balance.
-   This information is saved to the daily transaction file
-   Constraints:
    -   src.Buyer and seller both must be current users

**addcredit** - add credit into the system for the purchase of accounts

-   In admin mode, should ask for the amount of credit to add and the username of the account to which credit is being added.
-   In a standard account, should ask for the amount of credit to add to the user's own account.
-   This information is saved to the daily transaction file
-   Constraints:
    -   In admin mode, the username has to be an existing username in the system.
    -   A maximum of $1000.00 can be added to an account in a given day.

**auctionsale** - change the prices of all games for sale to incorporate a seasonal discount (privileged transaction)

-   Activate the discounts on all games for sale, changing the amount transferred during buy transactions
-   If an auctionsale is already on, this transaction should conclude the auctionsale and disable the discounts
-   Constraints:
    -   privileged transaction - only accepted when logged in as admin user

### Daily src.Transaction File:

At the end of each day, the front end provides a daily transaction file called daily.txt, listing every transaction made in the day.
Contains variable-length text lines of the following formats:

XX UUUUUUUUUUUUUUU TT CCCCCCCCC

Where:

-   XX
    -   is a two-digit transaction code: 00-login, 01-create, 02-delete, 06-addcredit, 10-logout
-   UUUUUUUUUUUUUUU
    -   is the username
-   TT
    -   is the user type (AA=admin, FS=full-standard, BS=buy-standard, SS=sell-standard)
-   CCCCCCCCC
    -   is the available credit

XX UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS CCCCCCCCC

Where:

-   XX
    -   is a two-digit transaction code: 05-refund
-   UUUUUUUUUUUUUUU
    -   is the buyer’s username
-   SSSSSSSSSSSSSSS
    -   is the seller’s username
-   CCCCCCCCC
    -   is the refund credit

XX IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSS DDDDD PPPPPP

Where:

-   XX
    -   is a two-digit transaction code: 03-sell.
-   IIIIIIIIIIIIIIIIIII
    -   is the game name
-   SSSSSSSSSSSSSS
    -   is the seller’s username
-   DDDDD
    -   Is the discount percentage
-   PPPPPP
    -   is the sale price

XX IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSSSS UUUUUUUUUUUUUU

Where:

-   XX
    -   is a two-digit transaction code: 04-buy.
-   IIIIIIIIIIIIIIIIIII
    -   is the game name
-   SSSSSSSSSSSSSSS
    -   is the seller’s username
-   UUUUUUUUUUUUUUU
    -   is the buyer's username

Constraints:

-   numeric fields are right justified, filled with zeroes
    (e.g., 005.00 for a 5$ game)
-   alphabetic fields are left justified, filled with spaces
    (e.g. John Doe for account holder John Doe)
-   unused numeric fields are filled with zeros
    (e.g., 0000)
-   In a numeric field that is used to represent a monetary value or percentage, “.00” is appended to the end of the value (e.g. 00110.00 for 110)
-   unused alphabetic fields are filled with spaces (blanks)
    (e.g., Mike M         )
-   all sequences of transactions begin with a login (00) transaction code and end with a logout (10) transaction code

### Back End Error Recording:

All recorded errors should be of the form: ERROR: \<msg\>

-   For failed constraint errors, \<msg\> should contain the type and description of
    the error and the transaction that caused it to occur.
-   For fatal errors, \<msg\> should contain the type and description and the file that
    caused the error.

### Data output structure:

All output should be written to the screen using text. For example, your program can have println statements saying things like "$20.00 has been added to the balance of user Mike Miljanovic".

## Part 2: Create a product backlog

See `scrum_data.md` for a link to the spreadsheet template you should use for this.

Your product backlog must list all the tasks you need to do for this project, based on the specifications above. Part of software design in a real world setting is identifying development tasks based on specifications that might be imprecise, like some of the information provided to you in Part 1. In your copy of the spreadsheet we provided, write down each "user story" (requirement from the specifications that you need to complete), with an "estimation" and a "priority" ranking for each story. In the included file `"scrum_data.md"` in this repository, copy/paste the link to your product backlog (follow the instructions in the `"scrum_data.md"` file to do this), then add and commit the scrum_data file. Do this before doing anything further in this assignment.

Note: Product backlogs change over time. Don't expect to get everything right the first time.

More information about each entry in the backlog:

1. src.User story: The user stories should specify the user which the task is for (in this case, buyer, seller, both, or admin), and what it is that the user wants to do (follow the example format in the given spreadsheet)
2. Estimation: The estimation should be how much effort you estimate a task will take (for particularly large tasks, break it up into smaller 'sub-tasks' -- you can make their IDs be in the format 1.1, 1.2, ...). Within the Scrum Framework the estimation is usually not actual time estimates - a more abstracted metric to quantify effort is used. For this project, we want you to use sizes like XS (really tiny task), S (small task), M (medium task), L (large task), or XL (very large task).
3. Priority: Rank each user story to be either "low", "medium" or "high" priority

## Part 3: Test Suite

A small portion of this assignment's marks will be dedicated to
ensuring you have created a proper test suite. Specifically,
you will be required to conduct **White Box testing** by using junit
tests.

We have covered a little bit of white box testing with code reviews
and paraphrasing but not to a large extent.

White box testing should test every possible input which leads to a
different output. This means, you should be checking that loops, conditionals (ifs, else ifs, else, switch + case, ...) and other statements relying on input, function as intended.

Here are some resources you might find helpful:

-   White Box: https://www.guru99.com/white-box-testing.html
-   JUnit 5: https://junit.org/junit5/docs/current/user-guide/

In addition to these, a TA has made a video demonstrating how to
setup JUnit 5 in IntelliJ, as well as a guide on making basic test cases for a simple program.

Video: [How to make JUnit5.4 Test Cases!](https://youtu.be/_sVVBGHISnE)

You should be testing all of the important methods + constructors in your program to get full marks for this part.
By doing this, you will also ensure that your program works as you
want it to work! If you find any test cases which should be passing,
but are failing, you will have found yourself a bug that you can fix!

Making test cases is something you will spend a lot of time doing in
the workfield to ensure software is robust and can't be broken. This is
something we want to introduce you to early on for your own benefit :).







Here's a [great video](https://www.youtube.com/watch?v=dQw4w9WgXcQ) I watch to celebrate completing assignments!

And here's **another** [great video](https://streamable.com/t9587) I like to watch, trust me, all UTM CS kids will relate to this, credits to TheRaghavSharma for making this masterpiece.
