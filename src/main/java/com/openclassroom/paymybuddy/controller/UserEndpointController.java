package com.openclassroom.paymybuddy.controller;

import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.ServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEndpointController {

    private static final Logger logger = LogManager.getLogger("UserEndpointController");

    @Autowired
    @Qualifier("userService")
    protected ServiceInterface service;

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUserRequest(@RequestParam(value = "email") String userEmail) {
        logger.info("Requête GET, Endpoint 'User' - Récupération du profil d'un utilisateur : " + userEmail);
        User user = service.getUser(userEmail);
        ResponseEntity<User> response = new ResponseEntity<User>(user, HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Boolean> addMoneyRequest(@RequestParam(value = "moneyToAdd") float moneyToAdd, @RequestParam(value = "email") String email) {
        logger.info("Requête POST, Endpoint 'User' - Ajout d'argent au compte : " + email);
        boolean isModified = service.addMoney(moneyToAdd, email);
        ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(isModified, HttpStatus.OK);
        return response;
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<Void> deleteUserRequest(@RequestParam(value = "email") String userEmail, @RequestParam(value = "password") String userPassword) {
        logger.info("Requête DELETE, Endpoint 'User' - Suppresion d'un utilisateur : " + userEmail);
        boolean isDeleted = service.deleteUser(userEmail, userPassword);
        ResponseEntity<Void> response;
        if(isDeleted == false) {
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } 
        else {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }
    
}
