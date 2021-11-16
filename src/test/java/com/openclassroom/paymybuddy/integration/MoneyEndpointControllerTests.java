package com.openclassroom.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.controller.MoneyEndpointController;
import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.Transaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class MoneyEndpointControllerTests extends MoneyEndpointController {

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    protected static TestsVariables vars;

    @BeforeAll
    static void setUp() {
        vars = new TestsVariables();
    }

    @BeforeEach
    void setUpPerTest() {
        transactionsRepo.deleteAll();
        usersRepo.deleteAll();

        usersRepo.saveAll(vars.getUsersList());
        transactionsRepo.saveAll(vars.getTransactionList());
    }

    @Test
    void getTransactionHistoricTest() {
        List<Transaction> expectedTransactionList = new ArrayList<>();
        expectedTransactionList.add(vars.getTransaction1());
        expectedTransactionList.add(vars.getTransaction2());

        List<Transaction> returnedTransactionList = this.getTransactionHistoricRequest(vars.getFriend1Email()).getBody();
        assertEquals(expectedTransactionList.toString(), returnedTransactionList.toString());
    }

    @Test
    void postNewTransaction() {
        usersRepo.save(vars.getNewUser());
        usersRepo.save(vars.getNewFriend());
        assertEquals(this.postTransactionRequest(vars.getNewTransaction()).getStatusCode(), HttpStatus.CREATED);

        boolean isSaved = transactionsRepo.findById(vars.getNewTransaction().getKey()).isPresent();
        assertTrue(isSaved);
    }

    @Test
    void putNewIbanTest() {
        assertEquals(this.putIbanRequest(vars.getUserEmail(), vars.getNewIban()).getStatusCode(), HttpStatus.CREATED);

        assertEquals(vars.getNewIban(), usersRepo.findById(vars.getUserEmail()).get().getIban());
    }
    
}
