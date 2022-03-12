package com.openclassroom.paymybuddy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.openclassroom.paymybuddy.dao.UserNetworkRepository;
import com.openclassroom.paymybuddy.dao.UsersRepository;
import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.NewFriend;
import com.openclassroom.paymybuddy.model.TransactionToPost;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.model.entity.UserNetwork;

import org.apache.commons.lang.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("friendService")
public class FriendService implements ServiceInterface {

    private static final Logger logger = LogManager.getLogger("FriendService");

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected UserNetworkRepository userNetworkRepo;

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

    @Override
    public long getNewId(String userEmail) {
        throw new NotImplementedException();
    }

    @Override
    public List<Transaction> getTransactionHistoric(String userEmail) {
        throw new NotImplementedException();
    }

    @Override
    public boolean postTransaction(TransactionToPost transactionToPost) {
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
    public boolean putIban(IbanToUpdate ibanToUpdate) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deleteUser(String userEmail, String userPassword) {
        throw new NotImplementedException();
    }
    
}
