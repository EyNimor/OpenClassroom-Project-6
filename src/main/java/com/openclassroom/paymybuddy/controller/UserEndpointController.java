package com.openclassroom.paymybuddy.controller;

import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UserEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    public ResponseEntity<User> getUserRequest(String userEmail) {
        logger.info("Requête GET, Endpoint 'User' - Récupération du profil d'un utilisateur : " + userEmail);
        return null;
    }

    public ResponseEntity<Void> deleteUserRequest(String userEmail, String userPassword) {
        logger.info("Requête DELETE, Endpoint 'User' - Suppresion d'un utilisateur : " + userEmail);
        return null;
    }
    
}
