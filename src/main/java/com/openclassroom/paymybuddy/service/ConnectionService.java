package com.openclassroom.paymybuddy.service;

import java.util.List;
import java.util.Optional;

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

@Service("connectionService")
public class ConnectionService implements ServiceInterface {

    private static final Logger logger = LogManager.getLogger("ConnectionService");

    @Autowired
    protected UsersRepository usersRepo;

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
    public User getUser(String userEmail) {
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
