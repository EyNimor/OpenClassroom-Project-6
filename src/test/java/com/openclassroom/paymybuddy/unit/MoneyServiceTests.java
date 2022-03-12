package com.openclassroom.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.methods.TestsMethods;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.service.MoneyService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest @TestPropertySource(locations="classpath:application-test.properties")
public class MoneyServiceTests extends MoneyService {

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
        assertTrue(this.postTransaction(vars.getNewTransactionToPost()));

        boolean isSaved = transactionsRepo.findById(vars.getNewTransaction().getKey()).isPresent();
        assertTrue(isSaved);
    }

    @Test
    void putNewIbanTest() {
        assertTrue(this.putIban(new IbanToUpdate(vars.getUserEmail(), vars.getNewIban())));

        assertEquals(vars.getNewIban(), usersRepo.findById(vars.getUserEmail()).get().getIban());
    }

    @AfterAll
    static void cleanAfterTests() {
        testsMethods.cleanTransactionTable();
        testsMethods.cleanUserNetworkTable();
        testsMethods.cleanUserTable();
    }
    
}
