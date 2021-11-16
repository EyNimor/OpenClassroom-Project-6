package com.openclassroom.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.openclassroom.paymybuddy.controller.UserEndpointController;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class UserEndpointControllerTests extends UserEndpointController {

    @Autowired
    protected UsersRepository usersRepo;

    protected static TestsVariables vars;

    private ConnectionEndpointControllerTests connectionTests;

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
    void getUserTest() {
        User returnedUser = this.getUserRequest(vars.getUserEmail()).getBody();
        assertEquals(vars.getUser().toString(), returnedUser.toString());
    }

    @Test
    void deleteUserTest() {
        connectionTests.createAccountTest();
        assertEquals(this.deleteUserRequest(vars.getNewUserEmail(), vars.getNewUserPassword()).getStatusCode(), HttpStatus.CREATED);

        assertNull(this.getUserRequest(vars.getNewUserEmail()).getBody());
    }
    
}
