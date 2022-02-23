package com.openclassroom.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.openclassroom.paymybuddy.controller.UserEndpointController;
import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.methods.TestsMethods;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.AppService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class UserEndpointControllerTests extends UserEndpointController {

    @Autowired
    protected AppService appService;

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
    void getUserTest() {
        User returnedUser = this.getUserRequest(vars.getUserEmail()).getBody();
        assertEquals(vars.getUser().toString(), returnedUser.toString());
    }

    @Test
    void deleteUserTest() {
        usersRepo.save(vars.getNewUser());
        assertEquals(this.deleteUserRequest(vars.getNewUserEmail(), vars.getNewUserPassword()).getStatusCode(), HttpStatus.OK);

        User emptyUser = new User();
        assertEquals(emptyUser.toString(), this.getUserRequest(vars.getNewUserEmail()).getBody().toString());
    }

    @AfterAll
    static void cleanAfterTests() {
        testsMethods.cleanTransactionTable();
        testsMethods.cleanUserNetworkTable();
        testsMethods.cleanUserTable();
    }
    
}
