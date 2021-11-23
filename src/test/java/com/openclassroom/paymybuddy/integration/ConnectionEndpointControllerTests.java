package com.openclassroom.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassroom.paymybuddy.controller.ConnectionEndpointController;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.TestsVariables;

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

    protected static TestsVariables vars;

    @BeforeAll
    static void setUp() {
        vars = new TestsVariables();
    }

    @BeforeEach
    void setUpPerTest() {
        usersRepo.deleteAll();

        usersRepo.saveAll(vars.getUsersList());
    }

    @Test
    void verifyIdentifiersTestIfIdentifiersAreCorrect() {
        assertEquals(this.verifyIdentifiersRequest(new Identifiers(vars.getUserEmail(), vars.getUserPassword())).getStatusCode(), HttpStatus.OK);
    }

    @Test
    void verifyIdentifiersTestIfEmailIsIncorrect() {
        assertEquals(this.verifyIdentifiersRequest(new Identifiers(vars.getBadEmail(), vars.getUserPassword())).getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    void verifyIdentifiersTestIfPasswordIsIncorrect() {
        assertEquals(this.verifyIdentifiersRequest(new Identifiers(vars.getUserEmail(), vars.getBadPassword())).getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    void createAccountTest() {
        assertEquals(this.postUserRequest(vars.getNewUser()).getStatusCode(), HttpStatus.CREATED);

        boolean isSaved = usersRepo.findById(vars.getNewUserEmail()).isPresent();
        assertTrue(isSaved);
    }

}
