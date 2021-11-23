package com.openclassroom.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.model.entity.UserNetwork;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        List<String> friendEmailList = userNetworkRepo.findUserNetworkByUserEmail(userEmail).stream().map(n -> n.getKey().getUserEmail().getEmail()).collect(Collectors.toList());
        friendEmailList.addAll(userNetworkRepo.findUserNetworkByFriendEmail(userEmail).stream().map(n -> n.getKey().getFriendEmail().getEmail()).collect(Collectors.toList()));
        return friendEmailList;
    }

    public boolean postFriend(UserNetwork newFriend) {
        logger.info("UserNetwork - Ajout d'un nouvel ami : " + newFriend.getKey().getFriendEmail().getEmail());
        boolean saved = false;
        userNetworkRepo.saveAndFlush(newFriend);
        saved = true;
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
        userNetworkRepo.delete(friendToDelete);
        deleted = true;
        return deleted;
    }

    public List<Transaction> getTransactionHistoric(String userEmail) {
        logger.info("Transaction - Récupération de l'historique de transaction de l'utilisateur : " + userEmail);
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
        transactionsRepo.saveAndFlush(newTransaction);
        saved = true;
        return saved;
    }

    public User getUser(String userEmail) {
        logger.info("User - Récupération du profil d'un utilisateur : " + userEmail);
        User user = usersRepo.findById(userEmail).get();
        return user;
    }

    public boolean verifyIdentifiers(Identifiers identifiers) {
        logger.info("Connection - Vérification des identifiants : " + identifiers.toString());
        Optional<User> hypotheticalUser = usersRepo.findById(identifiers.getEmailToTest());
        if(hypotheticalUser.isPresent() == false) {
            logger.error("Connection - Mauvais email : " + identifiers.getEmailToTest());
            return false;
        }
        else if(hypotheticalUser.get().getPassword() != identifiers.getPasswordToTest()) {
            logger.error("Connection - Mauvais mot de passe : " + identifiers.getPasswordToTest());
            return false;
        }
        else {
            return true;
        }
    }

    public boolean postUser(User newUser) {
        logger.info("User - Ajout d'un nouvel utilisateur : " + newUser.getEmail());
        boolean saved = false;
        usersRepo.saveAndFlush(newUser);
        saved = true;
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

    public boolean deleteUser(String userEmail, String userPassword) {
        logger.info("User - Suppresion d'un utilisateur : " + userEmail);
        boolean deleted = false;
        if(verifyIdentifiers(new Identifiers(userEmail, userPassword)) == false) {
            return deleted;
        }

        List<UserNetwork> friendsToDelete = userNetworkRepo.findUserNetworkByUserEmail(userEmail).stream().collect(Collectors.toList());
        friendsToDelete.addAll(userNetworkRepo.findUserNetworkByFriendEmail(userEmail).stream().collect(Collectors.toList()));
        if(friendsToDelete.isEmpty() == false) {
            userNetworkRepo.deleteAll(friendsToDelete);
        }

        List<Transaction> transactionsToDelete = this.getTransactionHistoric(userEmail);
        if(transactionsToDelete.isEmpty() == false) {
            transactionsRepo.deleteAll(transactionsToDelete);
        }
        
        usersRepo.deleteById(userEmail);
        deleted = true;
        return deleted;
    }
    
}
