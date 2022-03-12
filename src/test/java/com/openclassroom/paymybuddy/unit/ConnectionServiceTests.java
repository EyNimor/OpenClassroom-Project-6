package com.openclassroom.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.methods.TestsMethods;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.service.ConnectionService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class ConnectionServiceTests extends ConnectionService {

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

    @AfterAll
    static void cleanAfterTests() {
        testsMethods.cleanTransactionTable();
        testsMethods.cleanUserNetworkTable();
        testsMethods.cleanUserTable();
    }
    
}
