package com.openclassroom.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.methods.TestsMethods;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.service.FriendService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class FriendServiceTests extends FriendService {

    protected static TestsVariables vars;
    protected static TestsMethods testsMethods;

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
        testsMethods = new TestsMethods(usersRepo, transactionsRepo, userNetworkRepo);

        testsMethods.cleanTransactionTable();
        testsMethods.cleanUserNetworkTable();
        testsMethods.cleanUserTable();

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
        assertTrue(this.postFriend(vars.getNewNewFriend()));

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

    @AfterAll
    static void cleanAfterTests() {
        testsMethods.cleanTransactionTable();
        testsMethods.cleanUserNetworkTable();
        testsMethods.cleanUserTable();
    }
    
}
