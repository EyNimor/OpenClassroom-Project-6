package com.openclassroom.paymybuddy.controller;

import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUserRequest(String userEmail) {
        logger.info("Requête GET, Endpoint 'User' - Récupération du profil d'un utilisateur : " + userEmail);
        try {
            User user = appService.getUser(userEmail);
            ResponseEntity<User> response = new ResponseEntity<User>(user, HttpStatus.OK);
            return response;
        } catch(Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<Void> deleteUserRequest(String userEmail, String userPassword) {
        logger.info("Requête DELETE, Endpoint 'User' - Suppresion d'un utilisateur : " + userEmail);
        try {
            boolean isDeleted = appService.deleteUser(userEmail, userPassword);
            ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
            return response;
        } catch(Exception e) {
            throw e;
        }
    }
    
}
