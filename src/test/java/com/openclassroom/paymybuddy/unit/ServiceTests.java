package com.openclassroom.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.AppService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class ServiceTests extends AppService {

    protected static TestsVariables vars;

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    @Autowired
    protected UserNetworkRepository userNetworkRepo;

    @BeforeAll
    static void setUp() {
        vars = new TestsVariables();
    }

    @BeforeEach
    void setUpPerTest() {
        transactionsRepo.deleteAll();
        userNetworkRepo.deleteAll();
        usersRepo.deleteAll();

        usersRepo.saveAll(vars.getUsersList());
        userNetworkRepo.saveAll(vars.getNetworkList());
        transactionsRepo.saveAll(vars.getTransactionList());
    }

    @Test
    void getFriendsTest() {
        List<String> expectedFriendList = new ArrayList<>();
        expectedFriendList.add(vars.getFriend1Email());
        expectedFriendList.add(vars.getFriend2Email());

        List<String> returnedFriendsList = this.getFriends(vars.getUserEmail());
        assertEquals(expectedFriendList.toString(), returnedFriendsList.toString());
    }

    @Test
    void postNewFriendTest() {
        usersRepo.save(vars.getNewUser());
        usersRepo.save(vars.getNewFriend());
        assertTrue(this.postFriend(vars.getNewUserNetwork()));

        boolean isSaved = userNetworkRepo.findById(vars.getNewUserNetwork().getKey()).isPresent();
        assertTrue(isSaved);
    }

    @Test
    void deleteFriendTest() {
        postNewFriendTest();
        assertTrue(this.deleteFriend(vars.getNewUserEmail(), vars.getNewFriendEmail()));

        boolean isSaved = userNetworkRepo.findById(vars.getNewUserNetwork().getKey()).isPresent();
        assertFalse(isSaved);
    }

    @Test
    void getTransactionHistoricTest() {
        List<Transaction> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(vars.getTransaction1());
        expectedTransactionList.add(vars.getTransaction2());

        List<Transaction> returnedTransactionList = this.getTransactionHistoric(vars.getFriend1Email());
        assertEquals(expectedTransactionList.toString(), returnedTransactionList.toString());
    }

    @Test
    void postNewTransaction() {
        usersRepo.save(vars.getNewUser());
        usersRepo.save(vars.getNewFriend());
        assertTrue(this.postTransaction(vars.getNewTransaction()));

        boolean isSaved = transactionsRepo.findById(vars.getNewTransaction().getKey()).isPresent();
        assertTrue(isSaved);
    }
    
    @Test
    void getUserTest() {
        User returnedUser = this.getUser(vars.getUserEmail());
        assertEquals(vars.getUser().toString(), returnedUser.toString());
    }
    
    @Test
    void verifyIdentifiersTestIfIdentifiersAreCorrect() {
        assertTrue(this.verifyIdentifiers(new Identifiers(vars.getUserEmail(), vars.getUserPassword())));
    }

    @Test
    void verifyIdentifiersTestIfEmailIsIncorrect() {
        assertFalse(this.verifyIdentifiers(new Identifiers(vars.getBadEmail(), vars.getUserPassword())));
    }

    @Test
    void verifyIdentifiersTestIfPasswordIsIncorrect() {
        assertFalse(this.verifyIdentifiers(new Identifiers(vars.getUserEmail(), vars.getBadPassword())));
    }
    
    @Test
    void createAccountTest() {
        assertTrue(this.postUser(vars.getNewUser()));

        boolean isSaved = usersRepo.findById(vars.getNewUserEmail()).isPresent();
        assertTrue(isSaved);
    }
    
    @Test
    void putNewIbanTest() {
        assertTrue(this.putIban(new IbanToUpdate(vars.getUserEmail(), vars.getNewIban())));

        assertEquals(vars.getNewIban(), usersRepo.findById(vars.getUserEmail()).get().getIban());
    }

    @Test
    void deleteUserTest() {
        createAccountTest();
        assertTrue(this.deleteUser(vars.getNewUserEmail(), vars.getNewUserPassword()));

        assertThrows(NoSuchElementException.class, () -> { this.getUser(vars.getNewUserEmail()); });
    }
    
}