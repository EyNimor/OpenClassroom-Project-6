package com.openclassroom.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.controller.MoneyEndpointController;
import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.methods.TestsMethods;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.Transaction;

import org.junit.jupiter.api.AfterAll;
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
        assertEquals(this.postTransactionRequest(vars.getNewTransactionToPost()).getStatusCode(), HttpStatus.CREATED);

        boolean isSaved = transactionsRepo.findById(vars.getNewTransaction().getKey()).isPresent();
        assertTrue(isSaved);
    }

    @Test
    void putNewIbanTest() {
        assertEquals(this.putIbanRequest(new IbanToUpdate(vars.getUserEmail(), vars.getNewIban())).getStatusCode(), HttpStatus.CREATED);

        assertEquals(vars.getNewIban(), usersRepo.findById(vars.getUserEmail()).get().getIban());
    }

    @AfterAll
    static void cleanAfterTests() {
        testsMethods.cleanTransactionTable();
        testsMethods.cleanUserNetworkTable();
        testsMethods.cleanUserTable();
    }
    
}
