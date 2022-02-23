package com.openclassroom.paymybuddy.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.model.entity.UserNetwork;

import lombok.Getter;

@Getter
public class TestsVariables {

    //Emails addresses for Test Users
    private String userEmail,
                    friend1Email,
                    friend2Email,
                    badEmail,
                    newUserEmail,
                    newFriendEmail;

    //First Names for Test Users
    private String userFirstName,
                    friend1FirstName,
                    friend2FirstName,
                    newUserFirstName,
                    newFriendFirstName;

    //Last Names for Test Users
    private String userLastName,
                    friend1LastName,
                    friend2LastName,
                    newUserLastName,
                    newFriendLastName;
    
    //Passwords for Test Users
    private String userPassword,
                    friend1Password,
                    friend2Password,
                    badPassword,
                    newUserPassword,
                    newFriendPassword;

    //IBAN
    private String newIban;

    //Test Users
    private User user,
                friend1,
                friend2,
                newUser,
                newFriend;

    private NewFriend newFriend1,
                    newFriend2,
                    newNewFriend;

    //Test UserNetwork
    private UserNetwork userNetwork1,
                        userNetwork2,
                        newUserNetwork;

    //Date for Test Transactions
    private Date transactionDate1,
                    transactionDate2,
                    transactionDate3,
                    transactionDate4,
                    newTransactionDate;
    
    //Amount for Test Transactions
    private float wallet,
                amount1,
                amount2,
                amount3,
                amount4,
                newAmount;
    
    //Description for two Test Transactions
    private String description1,
                    description2,
                    newDescription;

    private TransactionToPost transactionToPost1,
                                transactionToPost2,
                                transactionToPost3,
                                transactionToPost4,
                                newTransactionToPost;


    //Test Transactions
    private Transaction transaction1,
                        transaction2,
                        transaction3,
                        transaction4,
                        newTransaction;

    private List<User> usersList;
    private List<UserNetwork> networkList;
    private List<Transaction> transactionList;

    public TestsVariables() {
        userEmail = "user-email@testmail.com";
        friend1Email = "first-friend-email@testmail.com";
        friend2Email = "second-friend-email@testmail.com";
        badEmail = "uncorrect-email@testmail.com";
        newUserEmail = "new-user-email@testmail.com";
        newFriendEmail = "new-friend-email@testmail.com";

        userFirstName = "Yves";
        friend1FirstName = "Valentin";
        friend2FirstName = "Adrien";
        newUserFirstName = "Jean";
        newFriendFirstName = "Jean";

        userLastName = "TestUser";
        friend1LastName = "TestFirstFriend";
        friend2LastName = "TestSecondFriend";
        newUserLastName = "NewTestUser";
        newFriendLastName = "NewTestFriend";

        userPassword = "Password1";
        friend1Password = "Password2";
        friend2Password = "Password3";
        badPassword = "UncorrectPassword";
        newUserPassword = "NewUserPassword";
        newFriendPassword = "NewFriendPassword";

        newIban = "FR11 1111 1111 1111 1111 1111 111";

        wallet = 999999999;

        user = new User(userEmail, userFirstName, userLastName, userPassword, wallet);
        friend1 = new User(friend1Email, friend1FirstName, friend1LastName, friend1Password, wallet);
        friend2 = new User(friend2Email, friend2FirstName, friend2LastName, friend2Password, wallet);
        newUser = new User(newUserEmail, newUserFirstName, newUserLastName, newUserPassword, wallet);
        newFriend = new User(newFriendEmail, newFriendFirstName, newFriendLastName, newFriendPassword, wallet);

        newFriend1 = new NewFriend(userEmail, friend1Email);
        newFriend2 = new NewFriend(userEmail, friend2Email);
        newNewFriend = new NewFriend(newUserEmail, newFriendEmail);

        userNetwork1 = new UserNetwork(user, friend1);
        userNetwork2 = new UserNetwork(user, friend2);
        newUserNetwork = new UserNetwork(newUser, newFriend);

        transactionDate1 = new Date(0);
        transactionDate2 = new Date(0 + (86400000*1));
        transactionDate3 = new Date(0 + (86400000*2));
        transactionDate4 = new Date(0 + (86400000*3));
        newTransactionDate = new Date(0 + (86400000*4));

        amount1 = 15;
        amount2 = 125;
        amount3 = 32;
        amount4 = 600;
        newAmount = 500;

        description1 = "Une première description Test";
        description2 = "Une seconde description Test";
        newDescription = "La description de la transaction de test de POST";

        transactionToPost1 = new TransactionToPost(userEmail, friend1Email, transactionDate1, amount1, description1);
        transactionToPost2 = new TransactionToPost(userEmail, friend1Email, transactionDate2, amount2);
        transactionToPost3 = new TransactionToPost(userEmail, friend2Email, transactionDate3, amount3, description2);
        transactionToPost4 = new TransactionToPost(userEmail, friend2Email, transactionDate4, amount4);
        newTransactionToPost = new TransactionToPost(newUserEmail, newFriendEmail, newTransactionDate, newAmount, newDescription);

        transaction1 = new Transaction(1, user, friend1, transactionDate1, amount1, description1);
        transaction2 = new Transaction(2, user, friend1, transactionDate2, amount2);
        transaction3 = new Transaction(3, user, friend2, transactionDate3, amount3, description2);
        transaction4 = new Transaction(4, user, friend2, transactionDate4, amount4);
        newTransaction = new Transaction(1, newUser, newFriend, newTransactionDate, newAmount, newDescription);

        usersList = new ArrayList<>();
        usersList.add(user);
        usersList.add(friend1);
        usersList.add(friend2);

        networkList = new ArrayList<>();
        networkList.add(userNetwork1);
        networkList.add(userNetwork2);

        transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.add(transaction4);
    }
    
}
