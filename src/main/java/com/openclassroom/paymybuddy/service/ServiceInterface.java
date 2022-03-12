package com.openclassroom.paymybuddy.service;

import java.util.List;

import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.NewFriend;
import com.openclassroom.paymybuddy.model.TransactionToPost;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.model.entity.User;

public interface ServiceInterface {
    public long getNewId(String userEmail);
    public List<String> getFriends(String userEmail);
    public boolean postFriend(NewFriend newFriend);
    public boolean deleteFriend(String userEmail, String friendEmail);
    public List<Transaction> getTransactionHistoric(String userEmail);
    public boolean postTransaction(TransactionToPost transactionToPost);
    public User getUser(String userEmail);
    public boolean verifyIdentifiers(Identifiers identifiers);
    public boolean postUser(User newUser);
    public boolean addMoney(float moneyToAdd, String userEmail);
    public boolean putIban(IbanToUpdate ibanToUpdate);
    public boolean deleteUser(String userEmail, String userPassword);
}
