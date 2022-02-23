package com.openclassroom.paymybuddy.methods;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.TestsVariables;
import com.openclassroom.paymybuddy.model.entity.composedKey.TransactionComposedKey;
import com.openclassroom.paymybuddy.model.entity.composedKey.UserNetworkComposedKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestsMethods {

    private static final Logger logger = LogManager.getLogger("Tests");

    protected static TestsVariables vars;

    protected UsersRepository usersRepo;

    protected TransactionsRepository transactionsRepo;

    protected UserNetworkRepository userNetworkRepo;

    public TestsMethods(UsersRepository usersRepo, TransactionsRepository transactionsRepo, UserNetworkRepository userNetworkRepo) {
        vars = new TestsVariables();
        this.usersRepo = usersRepo;
        this.transactionsRepo = transactionsRepo;
        this.userNetworkRepo = userNetworkRepo;
    }

    public void cleanTransactionTable() {
        List<TransactionComposedKey> transactionIdsList = new ArrayList<>();
        transactionIdsList.add(vars.getTransaction1().getKey());
        transactionIdsList.add(vars.getTransaction2().getKey());
        transactionIdsList.add(vars.getTransaction3().getKey());
        transactionIdsList.add(vars.getTransaction4().getKey());
        for(int i = 0; i < transactionIdsList.size(); i++) {
            try {
                transactionsRepo.deleteById(transactionIdsList.get(i));
            } catch(Exception e) {
                logger.warn("Tests SetUp - Échec de la suppression d'une Transaction : " + transactionIdsList.get(i));
                e.fillInStackTrace();
            }
        }
    }

    public void cleanUserNetworkTable() {
        List<UserNetworkComposedKey> userNetworkIdsList = new ArrayList<>();
        userNetworkIdsList.add(vars.getUserNetwork1().getKey());
        userNetworkIdsList.add(vars.getUserNetwork2().getKey());
        for(int i = 0; i < userNetworkIdsList.size(); i++) {
            try {
                userNetworkRepo.deleteById(userNetworkIdsList.get(i));
            } catch(Exception e) {
                logger.warn("Tests SetUp - Échec de la suppression d'un UserNetwork : " + userNetworkIdsList.get(i));
                e.fillInStackTrace();
            }
        }
    }

    public void cleanUserTable() {
        List<String> usersIdsList = new ArrayList<>();
        usersIdsList.add(vars.getUserEmail());
        usersIdsList.add(vars.getFriend1Email());
        usersIdsList.add(vars.getFriend2Email());
        for(int i = 0; i < usersIdsList.size(); i++) {
            try {
                usersRepo.deleteById(usersIdsList.get(i));
            } catch(Exception e) {
                logger.warn("Tests SetUp - Échec de la suppression d'un User : " + usersIdsList.get(i));
                e.fillInStackTrace();
            }
        }
    }
    
}
