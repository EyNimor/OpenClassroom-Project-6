package com.openclassroom.paymybuddy.controller;

import java.util.List;

import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.service.AppService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/money")
public class MoneyEndpointController {

    private static final Logger logger = LogManager.getLogger("AppService");

    @Autowired
    protected AppService appService;

    @GetMapping(value = "/getTransactionHistoric")
    public ResponseEntity<List<Transaction>> getTransactionHistoricRequest(@RequestParam(value = "email") String userEmail) {
        logger.info("Requête GET, Endpoint 'Money' - Récupération de l'historique de transaction de l'utilisateur : " + userEmail);
        List<Transaction> transactionHistoric = appService.getTransactionHistoric(userEmail);
        ResponseEntity<List<Transaction>> response = new ResponseEntity<List<Transaction>>(transactionHistoric, HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<Void> postTransactionRequest(@RequestBody Transaction newTransaction) {
        logger.info("Requête POST, Endpoint 'Money' - Nouvelle Transaction entre " + newTransaction.getKey().getGiverEmail().getEmail() + " et " + newTransaction.getKey().getReceiverEmail().getEmail());
        appService.postTransaction(newTransaction);
        ResponseEntity<Void> response = new ResponseEntity<>( HttpStatus.CREATED);
        return response;
    }

    @PutMapping(value = "/userIban")
    public ResponseEntity<Void> putIbanRequest(@RequestParam IbanToUpdate ibanToUpdate) {
        logger.info("Requête PUT, Endpoint 'Money' - Ajout/Modification de l'IBAN de l'utilisateur : " + ibanToUpdate.toString());
        appService.putIban(ibanToUpdate);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }
    
}
