package com.openclassroom.paymybuddy.controller;

import java.util.List;

import com.openclassroom.paymybuddy.model.NewFriend;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    @GetMapping(value = "/friend")
    public ResponseEntity<List<String>> getFriendsRequest(@RequestParam(value = "email") String userEmail) {
        logger.info("Requête GET, Endpoint 'UserNetwork' - Récupération de la liste d'amis d'un utilisateur : " + userEmail);
        List<String> friendsEmailsList = appService.getFriends(userEmail);
        ResponseEntity<List<String>> response = new ResponseEntity<List<String>>(friendsEmailsList, HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/friend")
    public ResponseEntity<Void> postFriendRequest(@RequestBody NewFriend newFriend) {
        logger.info("Requête POST, Endpoint 'UserNetwork' - Ajout d'un nouvel ami : " + newFriend.getFriendEmail());
        appService.postFriend(newFriend);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }

    @DeleteMapping(value = "/friend")
    public ResponseEntity<Void> deleteFriendRequest(@RequestParam(value = "userEmail") String userEmail, @RequestParam(value = "friendEmail") String friendEmail) {
        logger.info("Requête DELETE, Endpoint 'UserNetwork' - Suppression d'un ami : " + friendEmail + ", pour l'utilisateur : " + userEmail);
        appService.deleteFriend(userEmail, friendEmail);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }
    
}
