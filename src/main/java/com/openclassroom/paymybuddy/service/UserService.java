package com.openclassroom.paymybuddy.service;

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

import org.apache.commons.lang.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements ServiceInterface {

    private static final Logger logger = LogManager.getLogger("UserService");

    @Autowired
    protected UsersRepository usersRepo;

    @Autowired
    protected TransactionsRepository transactionsRepo;

    @Autowired
    protected UserNetworkRepository userNetworkRepo;

    @Autowired
    protected ConnectionService connectionService;

    @Autowired
    protected MoneyService moneyService;

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

    public boolean addMoney(float moneyToAdd, String userEmail) {
        logger.info("User - Ajout d'argent sur le compte : " + userEmail);
        Optional<User> hypotheticalUser = usersRepo.findById(userEmail);
        if(hypotheticalUser.isPresent() == false) {
            logger.error("User - L'utilisateur n'existe pas : " + userEmail);
            return false;
        }
        else {
            User user = hypotheticalUser.get();
            user.setWallet(user.getWallet() + moneyToAdd);
            usersRepo.saveAndFlush(user);
            return true;
        }
    }

    public boolean deleteUser(String userEmail, String userPassword) {
        logger.info("User - Suppresion d'un utilisateur : " + userEmail);
        boolean deleted = false;
        if(connectionService.verifyIdentifiers(new Identifiers(userEmail, userPassword)) == false) {
            return deleted;
        }

        Optional<User> userToDelete = usersRepo.findById(userEmail);

        List<UserNetwork> friendsToDelete = userNetworkRepo.findUserNetworkByUserEmail(userToDelete.get()).stream().collect(Collectors.toList());
        friendsToDelete.addAll(userNetworkRepo.findUserNetworkByFriendEmail(userToDelete.get()).stream().collect(Collectors.toList()));
        if(friendsToDelete.isEmpty() == false) {
            userNetworkRepo.deleteAll(friendsToDelete);
        }

        List<Transaction> transactionsToDelete = moneyService.getTransactionHistoric(userEmail);
        if(transactionsToDelete.isEmpty() == false) {
            transactionsRepo.deleteAll(transactionsToDelete);
        }
        
        usersRepo.deleteById(userEmail);
        deleted = true;
        return deleted;
    }

    @Override
    public long getNewId(String userEmail) {
        throw new NotImplementedException();
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
    public List<Transaction> getTransactionHistoric(String userEmail) {
        throw new NotImplementedException();
    }

    @Override
    public boolean postTransaction(TransactionToPost transactionToPost) {
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
    public boolean putIban(IbanToUpdate ibanToUpdate) {
        throw new NotImplementedException();
    }
    
}
