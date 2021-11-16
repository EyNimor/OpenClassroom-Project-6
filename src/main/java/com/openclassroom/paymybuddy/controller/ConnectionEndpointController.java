package com.openclassroom.paymybuddy.controller;

import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class ConnectionEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    public ResponseEntity<Void> verifyIdentifiersRequest(String emailToVerify, String passwordToVerify) {
        logger.info("Requête GET, Endpoint 'Connection' - Vérification des identifiants : " + emailToVerify + ", " + passwordToVerify);
        return null;
    }

    public ResponseEntity<Void> postUserRequest(User newUser) {
        logger.info("Requête POST, Endpoint 'Connection' - Ajout d'un nouvel utilisateur : " + newUser.getEmail());
        return null;
    }
    
}
