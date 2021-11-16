package com.openclassroom.paymybuddy.controller;

import java.util.List;

import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class MoneyEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    public ResponseEntity<List<Transaction>> getTransactionHistoricRequest(String userEmail) {
        logger.info("Requête GET, Endpoint 'Money' - Récupération de l'historique de transaction de l'utilisateur : " + userEmail);
        return null;
    }

    public ResponseEntity<Void> postTransactionRequest(Transaction newTransaction) {
        logger.info("Requête POST, Endpoint 'Money' - Nouvelle Transaction entre " + newTransaction.getKey().getGiverEmail().getEmail() + " et " + newTransaction.getKey().getReceiverEmail().getEmail());
        return null;
    }

    public ResponseEntity<Void> putIbanRequest(String userEmail, String iban) {
        logger.info("Requête PUT, Endpoint 'Money' - Ajout/Modification de l'IBAN de l'utilisateur : " + userEmail);
        return null;
    }
    
}
