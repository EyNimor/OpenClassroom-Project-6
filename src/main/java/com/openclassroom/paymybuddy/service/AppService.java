package com.openclassroom.paymybuddy.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.openclassroom.paymybuddy.dao.TransactionsRepository;
import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.NewFriend;
import com.openclassroom.paymybuddy.model.TransactionToPost;
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

    protected long getNewId(String userEmail) {
        logger.info("UserNetwork - Création d'un nouvel ID de transaction");
        List<Transaction> transactionList = getTransactionHistoric(userEmail);
        return transactionList.size() + 1;
    }

    public List<String> getFriends(String userEmail) {
        logger.info("UserNetwork - Récupération de la liste d'amis d'un utilisateur : " + userEmail);
        Optional<User> user = usersRepo.findById(userEmail);
        List<UserNetwork> userNetworks = userNetworkRepo.findUserNetworkByUserEmail(user.get());
        List<String> friendEmailList = userNetworks.stream().map(n -> n.getKey().getFriendEmail().getEmail()).collect(Collectors.toList());
        return friendEmailList;
    }

    public boolean postFriend(NewFriend newFriend) {
        logger.info("UserNetwork - Ajout d'un nouvel ami : " + newFriend.getFriendEmail());
        Optional<User> user = usersRepo.findById(newFriend.getUserEmail());
        Optional<User> friend = usersRepo.findById(newFriend.getFriendEmail());
        UserNetwork newRelation = new UserNetwork(user.get(), friend.get());
        boolean saved = false;
        userNetworkRepo.saveAndFlush(newRelation);
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

    public User getUser(String userEmail) {
        logger.info("User - Récupération du profil d'un utilisateur : " + userEmail);
        User user;
        if(usersRepo.findById(userEmail).isPresent() == true) {
            user = usersRepo.findById(userEmail).get();
        }
        else {
            user = new User();
        }
        return user;
    }

    public boolean verifyIdentifiers(Identifiers identifiers) {
        logger.info("Connection - Vérification des identifiants : " + identifiers.toString());
        Optional<User> hypotheticalUser = usersRepo.findById(identifiers.getEmailToTest());
        if(hypotheticalUser.isPresent() == false) {
            logger.error("Connection - Mauvais email : " + identifiers.getEmailToTest());
            return false;
        }
        else if(hypotheticalUser.get().getPassword().equals(identifiers.getPasswordToTest()) == false) {
            logger.error("Connection - Mauvais mot de passe ");
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

        Optional<User> userToDelete = usersRepo.findById(userEmail);

        List<UserNetwork> friendsToDelete = userNetworkRepo.findUserNetworkByUserEmail(userToDelete.get()).stream().collect(Collectors.toList());
        friendsToDelete.addAll(userNetworkRepo.findUserNetworkByFriendEmail(userToDelete.get()).stream().collect(Collectors.toList()));
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
