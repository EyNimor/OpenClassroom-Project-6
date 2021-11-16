package com.openclassroom.paymybuddy.controller;

import java.util.List;

import com.openclassroom.paymybuddy.model.entity.UserNetwork;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class FriendEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    public ResponseEntity<List<String>> getFriendsRequest(String userEmail) {
        logger.info("Requête GET, Endpoint 'UserNetwork' - Récupération de la liste d'amis d'un utilisateur : " + userEmail);
        return null;
    }

    public ResponseEntity<Void> postFriendRequest(UserNetwork newFriend) {
        logger.info("Requête POST, Endpoint 'UserNetwork' - Ajout d'un nouvel ami : " + newFriend.getKey().getFriendEmail().getEmail());
        return null;
    }

    public ResponseEntity<Void> deleteFriendRequest(String userEmail, String friendEmail) {
        logger.info("Requête DELETE, Endpoint 'UserNetwork' - Suppression d'un ami : " + friendEmail + ", pour l'utilisateur : " + userEmail);
        return null;
    }
    
}
