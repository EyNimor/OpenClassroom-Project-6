package com.openclassroom.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassroom.paymybuddy.controller.ConnectionEndpointController;
import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.methods.TestsMethods;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.TestsVariables;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class ConnectionEndpointControllerTests extends ConnectionEndpointController {
    
    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    @Autowired
    protected UserNetworkRepository userNetworkRepo;

    protected static TestsVariables vars;

    protected static TestsMethods testsMethods;

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
        assertTrue(this.verifyIdentifiersRequest(new Identifiers(vars.getUserEmail(), vars.getUserPassword())).getBody());
    }

    @Test
    void verifyIdentifiersTestIfEmailIsIncorrect() {
        assertFalse(this.verifyIdentifiersRequest(new Identifiers(vars.getBadEmail(), vars.getUserPassword())).getBody());
    }

    @Test
    void verifyIdentifiersTestIfPasswordIsIncorrect() {
        assertFalse(this.verifyIdentifiersRequest(new Identifiers(vars.getUserEmail(), vars.getBadPassword())).getBody());
    }
    
    @Test
    void createAccountTest() {
        assertEquals(this.postUserRequest(vars.getNewUser()).getStatusCode(), HttpStatus.CREATED);

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
