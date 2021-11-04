package com.openclassroom.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.model.entity.UserNetwork;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AppService {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    @Autowired
    protected UserNetworkRepository userNetworkRepo;

    public List<String> getFriends(String userEmail) {
        logger.info("UserNetwork - Récupération de la liste d'amis d'un utilisateur : " + userEmail);
        List<UserNetwork> networkList = userNetworkRepo.findAll();
        List<String> friendEmailList = new ArrayList<>();
        for(int i = 0; i < networkList.size(); i++) {
            if(networkList.get(i).getKey().getUserEmail().getEmail() == userEmail) { friendEmailList.add(networkList.get(i).getKey().getFriendEmail().getEmail()); }
            if(networkList.get(i).getKey().getFriendEmail().getEmail() == userEmail) { friendEmailList.add(networkList.get(i).getKey().getUserEmail().getEmail()); }
        }
        return friendEmailList;
    }

    public boolean postFriend(UserNetwork newFriend) {
        logger.info("UserNetwork - Ajout d'un nouvel ami : " + newFriend.getKey().getFriendEmail().getEmail());
        boolean saved = false;
        try { 
            userNetworkRepo.save(newFriend);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        return saved;
    }

    public boolean deleteFriend(String userEmail, String friendEmail) {
        logger.info("UserNetwork - Suppression d'un ami : " + friendEmail);
        boolean deleted = false;
        Optional<User> user = usersRepo.findById(userEmail);
        Optional<User> friend = usersRepo.findById(friendEmail);
        if(user.isPresent() == false) {
            logger.error("UserNetwork - Erreur : l'utilisateur " + userEmail + " n'existe pas dans la base de donnée");
            return deleted;
        }
        else if(friend.isPresent() == false) {
            logger.error("UserNetwork - Erreur : l'utilisateur " + friendEmail + " n'existe pas dans la base de donnée");
            return deleted;
        }
        UserNetwork friendToDelete = new UserNetwork(user.get(), friend.get());
        try {
            userNetworkRepo.delete(friendToDelete);
            deleted = true;
        }
        catch(IllegalArgumentException e) {
            throw e;
        }
        return deleted;
    }

    public List<Transaction> getTransactionHistoric(String userEmail) {
        logger.info("Transaction - Récupération de l'historique de transaction d'un utilisateur : " + userEmail);
        List<Transaction> transactionList = transactionsRepo.findAll();
        List<Transaction> transactionHistoric = new ArrayList<>();
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getKey().getGiverEmail().getEmail() == userEmail | transactionList.get(i).getKey().getReceiverEmail().getEmail() == userEmail) 
            { transactionHistoric.add(transactionList.get(i)); }
        }
        return transactionHistoric;
    }

    public boolean postTransaction(Transaction newTransaction) {
        logger.info("Transaction - Nouvelle Transaction entre " + newTransaction.getKey().getGiverEmail().getEmail() + " et " + newTransaction.getKey().getReceiverEmail().getEmail());
        boolean saved = false;
        try { 
            transactionsRepo.save(newTransaction);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        return saved;
    }

    public User getUser(String userEmail) {
        logger.info("User - Récupération du profil d'un utilisateur : " + userEmail);
        try {
            User user = usersRepo.findById(userEmail).get();
            return user;
        } catch(NoSuchElementException e) {
            logger.error("Erreur : aucun utilisateur n'est relié à cette adresse mail - " + userEmail);
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifyIdentifiers(String emailToVerify, String passwordToVerify) {
        logger.info("Connection - Vérification des identifiants : " + emailToVerify + ", " + passwordToVerify);
        Optional<User> hypotheticalUser = usersRepo.findById(emailToVerify);
        if(hypotheticalUser.isPresent() == false) {
            return false;
        }
        else if(hypotheticalUser.get().getPassword() != passwordToVerify) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean postUser(User newUser) {
        logger.info("User - Ajout d'un nouvel utilisateur : " + newUser.getEmail());
        boolean saved = false;
        try {
            usersRepo.saveAndFlush(newUser);
            saved = true;
        }
        catch(IllegalArgumentException e) {
            throw e;
        }
        return saved;
    }

    public boolean putIban(String userEmail, String iban) {
        logger.info("User - Ajout/Modification de l'IBAN de l'utilisateur : " + userEmail);
        boolean updated = false;
        try {
            usersRepo.modifyUserIban(userEmail, iban);
            updated = true;
        }
        catch(Exception e) {
            throw e;
        }
        return updated;
    }
    
}
