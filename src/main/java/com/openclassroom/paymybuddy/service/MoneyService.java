package com.openclassroom.paymybuddy.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.NewFriend;
import com.openclassroom.paymybuddy.model.TransactionToPost;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;

import org.apache.commons.lang.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("moneyService")
public class MoneyService implements ServiceInterface {

    private static final Logger logger = LogManager.getLogger("MoneyService");

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    public long getNewId(String userEmail) {
        logger.info("UserNetwork - Création d'un nouvel ID de transaction");
        List<Transaction> transactionList = getTransactionHistoric(userEmail);
        return transactionList.size() + 1;
    }

    public List<Transaction> getTransactionHistoric(String userEmail) {
        logger.info("Transaction - Récupération de l'historique de transaction de l'utilisateur : " + userEmail);
        List<Transaction> transactionList = transactionsRepo.findAll();
        List<Transaction> transactionHistoric = new ArrayList<>();
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getKey().getGiverEmail().getEmail().equals(userEmail) || transactionList.get(i).getKey().getReceiverEmail().getEmail().equals(userEmail)) {
                transactionHistoric.add(transactionList.get(i));
            }
        }
        return transactionHistoric;
    }

    public boolean postTransaction(TransactionToPost transactionToPost) {
        logger.info("Transaction - Nouvelle Transaction entre " + transactionToPost.getGiverEmail() + " et " + transactionToPost.getReceiverEmail());
        boolean saved = false;
        Optional<User> giver = usersRepo.findById(transactionToPost.getGiverEmail());
        Optional<User> receiver = usersRepo.findById(transactionToPost.getReceiverEmail());

        Date transactionDate;
        if(transactionToPost.getTransactionDate() != null) {
            transactionDate = transactionToPost.getTransactionDate();
            logger.info("Transaction - Date et heure : " + transactionDate);
        }
        else {
            transactionDate = new Date(System.currentTimeMillis());
            logger.info("Transaction - Date et heure : " + transactionDate);
        }
        float amountToGive = transactionToPost.getAmount();
        float amountToSubstract = amountToGive + (amountToGive / 200);

        float giverWalletUpdated = giver.get().getWallet() - amountToSubstract;
        float receiverWalletUpdated = receiver.get().getWallet() + amountToGive;
        if(giverWalletUpdated >= 0.00) {
            Transaction newTransaction;
            long id = getNewId(transactionToPost.getGiverEmail());
            if(transactionToPost.getDescription() == null) {
                newTransaction = new Transaction(id, giver.get(), receiver.get(), transactionDate, transactionToPost.getAmount());
            }
            else {
                newTransaction = new Transaction(id, giver.get(), receiver.get(), transactionDate, transactionToPost.getAmount(), transactionToPost.getDescription());
            }
            transactionsRepo.saveAndFlush(newTransaction);

            giver.get().setWallet(giverWalletUpdated);
            usersRepo.saveAndFlush(giver.get());
            receiver.get().setWallet(receiverWalletUpdated);
            usersRepo.saveAndFlush(receiver.get());

            saved = true;
        }
        else {
            logger.error("Transaction - Le compte Giver n'a pas assez sur son compte");
        }
        return saved;
    }

    public boolean putIban(IbanToUpdate ibanToUpdate) {
        logger.info("User - Ajout/Modification de l'IBAN de l'utilisateur : " + ibanToUpdate.getUserEmail());
        boolean updated = false;
        Optional<User> userToModify = usersRepo.findById(ibanToUpdate.getUserEmail());
        if(userToModify.isPresent() == false) {
            logger.error("User - Erreur : l'utilisateur " + ibanToUpdate.getUserEmail() + " n'existe pas dans la base de donnée");
            return updated;
        }
        else {
            User user = userToModify.get();
            user.setIban(ibanToUpdate.getIbanToUpdate());
            usersRepo.saveAndFlush(user);
            updated = true;
        }
        return updated;
    }

    @Override
    public List<String> getFriends(String userEmail) {
        throw new NotImplementedException();
    }

    @Override
    public boolean postFriend(NewFriend newFriend) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deleteFriend(String userEmail, String friendEmail) {
        throw new NotImplementedException();
    }

    @Override
    public User getUser(String userEmail) {
        throw new NotImplementedException();
    }

    @Override
    public boolean verifyIdentifiers(Identifiers identifiers) {
        throw new NotImplementedException();
    }

    @Override
    public boolean postUser(User newUser) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addMoney(float moneyToAdd, String userEmail) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deleteUser(String userEmail, String userPassword) {
        throw new NotImplementedException();
    }
    
}
