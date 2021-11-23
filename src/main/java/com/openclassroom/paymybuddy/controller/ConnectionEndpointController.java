package com.openclassroom.paymybuddy.controller;

import com.openclassroom.paymybuddy.model.Identifiers;
import com.openclassroom.paymybuddy.model.entity.User;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/connection")
public class ConnectionEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    @PostMapping(value = "/goodIdentifiers")
    public ResponseEntity<Void> verifyIdentifiersRequest(@RequestBody Identifiers identifiers) {
        logger.info("Requête GET, Endpoint 'Connection' - Vérification des identifiants : " + identifiers.toString());
        boolean isIdentifiersCorrects = appService.verifyIdentifiers(identifiers);
        ResponseEntity<Void> response;
        if(isIdentifiersCorrects == true) {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @PostMapping(value = "createAccount")
    public ResponseEntity<Void> postUserRequest(@RequestBody User newUser) {
        logger.info("Requête POST, Endpoint 'Connection' - Ajout d'un nouvel utilisateur : " + newUser.getEmail());
        appService.postUser(newUser);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }
    
}
