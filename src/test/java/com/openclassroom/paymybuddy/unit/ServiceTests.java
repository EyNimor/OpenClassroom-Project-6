package com.openclassroom.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.model.entity.UserNetwork;
import com.openclassroom.paymybuddy.service.AppService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class ServiceTests extends AppService {

    //Emails addresses for Test Users
    private static String userEmail,
                            friend1Email,
                            friend2Email,
                            badEmail,
                            newUserEmail,
                            newFriendEmail;

    //First Names for Test Users
    private static String userFirstName,
                            friend1FirstName,
                            friend2FirstName,
                            newUserFirstName,
                            newFriendFirstName;

    //Last Names for Test Users
    private static String userLastName,
                            friend1LastName,
                            friend2LastName,
                            newUserLastName,
                            newFriendLastName;
    
    //Passwords for Test Users
    private static String userPassword,
                            friend1Password,
                            friend2Password,
                            badPassword,
                            newUserPassword,
                            newFriendPassword;

    //IBAN
    private static String newIban;

    //Test Users
    private static User user,
                        friend1,
                        friend2,
                        newUser,
                        newFriend;

    //Test UserNetwork
    private static UserNetwork userNetwork1,
                                userNetwork2,
                                newUserNetwork;

    //Date for Test Transactions
    private static Date transactionDate1,
                        transactionDate2,
                        transactionDate3,
                        transactionDate4,
                        newTransactionDate;
    
    //Amount for Test Transactions
    private static float amount1,
                        amount2,
                        amount3,
                        amount4,
                        newAmount;
    
    //Description for two Test Transactions
    private static String description1,
                            description2,
                            newDescription;

    //Test Transactions
    private static Transaction transaction1,
                                transaction2,
                                transaction3,
                                transaction4,
                                newTransaction;

    private static List<User> usersList;
    private static List<UserNetwork> networkList;
    private static List<Transaction> transactionList;

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    @Autowired
    protected UserNetworkRepository userNetworkRepo;

    @BeforeAll
    static void setUp() {
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

        user = new User(userEmail, userFirstName, userLastName, userPassword);
        friend1 = new User(friend1Email, friend1FirstName, friend1LastName, friend1Password);
        friend2 = new User(friend2Email, friend2FirstName, friend2LastName, friend2Password);
        newUser = new User(newUserEmail, newUserFirstName, newUserLastName, newUserPassword);
        newFriend = new User(newFriendEmail, newFriendFirstName, newFriendLastName, newFriendPassword);

        userNetwork1 = new UserNetwork(user, friend1);
        userNetwork2 = new UserNetwork(user, friend2);
        newUserNetwork = new UserNetwork(newUser, newFriend);

        transactionDate1 = new Date(System.currentTimeMillis());
        transactionDate2 = new Date(System.currentTimeMillis() + (86400000*1));
        transactionDate3 = new Date(System.currentTimeMillis() + (86400000*2));
        transactionDate4 = new Date(System.currentTimeMillis() + (86400000*3));
        newTransactionDate = new Date(System.currentTimeMillis() + (86400000*4));

        amount1 = 15;
        amount2 = 125;
        amount3 = 32;
        amount4 = 600;
        newAmount = 500;

        description1 = "Une première description Test";
        description2 = "Une seconde description Test";
        newDescription = "La description de la transaction de test de POST";

        transaction1 = new Transaction(user, friend1, transactionDate1, amount1, description1);
        transaction2 = new Transaction(user, friend1, transactionDate2, amount2);
        transaction3 = new Transaction(user, friend2, transactionDate3, amount3, description2);
        transaction4 = new Transaction(user, friend2, transactionDate4, amount4);
        newTransaction = new Transaction(newUser, newFriend, newTransactionDate, newAmount, newDescription);

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

    @BeforeEach
    void setUpPerTest() {
        transactionsRepo.deleteAll();
        userNetworkRepo.deleteAll();
        usersRepo.deleteAll();

        usersRepo.saveAll(usersList);
        userNetworkRepo.saveAll(networkList);
        transactionsRepo.saveAll(transactionList);
    }

    @Test
    void getFriendsTest() {
        List<String> expectedFriendList = new ArrayList<>();
        expectedFriendList.add(friend1Email);
        expectedFriendList.add(friend2Email);

        List<String> returnedFriendsList = this.getFriends(userEmail);
        assertEquals(expectedFriendList.toString(), returnedFriendsList.toString());
    }

    @Test
    void postNewFriendTest() {
        usersRepo.save(newUser);
        usersRepo.save(newFriend);
        assertTrue(this.postFriend(newUserNetwork));

        boolean isSaved = userNetworkRepo.findById(newUserNetwork.getKey()).isPresent();
        assertTrue(isSaved);
    }

    @Test
    void deleteFriendTest() {
        postNewFriendTest();
        assertTrue(this.deleteFriend(newUserEmail, newFriendEmail));

        boolean isSaved = userNetworkRepo.findById(newUserNetwork.getKey()).isPresent();
        assertFalse(isSaved);
    }

    @Test
    void getTransactionHistoricTest() {
        List<Transaction> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(transaction1);
        expectedTransactionList.add(transaction2);

        List<Transaction> returnedTransactionList = this.getTransactionHistoric(friend1Email);
        assertEquals(expectedTransactionList.toString(), returnedTransactionList.toString());
    }

    @Test
    void postNewTransaction() {
        usersRepo.save(newUser);
        usersRepo.save(newFriend);
        assertTrue(this.postTransaction(newTransaction));

        boolean isSaved = transactionsRepo.findById(newTransaction.getKey()).isPresent();
        assertTrue(isSaved);
    }
    
    @Test
    void getUserTest() {
        User returnedUser = this.getUser(userEmail);
        assertEquals(user.toString(), returnedUser.toString());
    }
    
    @Test
    void verifyIdentifiersTestIfIdentifiersAreCorrect() {
        assertTrue(this.verifyIdentifiers(userEmail, userPassword));
    }

    @Test
    void verifyIdentifiersTestIfEmailIsIncorrect() {
        assertFalse(this.verifyIdentifiers(badEmail, userPassword));
    }

    @Test
    void verifyIdentifiersTestIfPasswordIsIncorrect() {
        assertFalse(this.verifyIdentifiers(userEmail, badPassword));
    }
    
    @Test
    void createAccountTest() {
        assertTrue(this.postUser(newUser));

        boolean isSaved = usersRepo.findById(newUserEmail).isPresent();
        assertTrue(isSaved);
    }
    
    @Test
    void putNewIban() {
        assertTrue(this.putIban(userEmail, newIban));

        assertEquals(newIban, usersRepo.findById(userEmail).get().getIban());
    }
    
}
