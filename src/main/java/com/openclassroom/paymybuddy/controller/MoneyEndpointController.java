package com.openclassroom.paymybuddy.controller;

import java.util.List;

import com.openclassroom.paymybuddy.model.IbanToUpdate;
import com.openclassroom.paymybuddy.model.TransactionToPost;
import com.openclassroom.paymybuddy.model.entity.Transaction;
import com.openclassroom.paymybuddy.service.ServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/money")
public class MoneyEndpointController {

    private static final Logger logger = LogManager.getLogger("MoneyEndpointController");

    @Autowired
    @Qualifier("moneyService")
    protected ServiceInterface service;

    @GetMapping(value = "/getTransactionHistoric")
    public ResponseEntity<List<Transaction>> getTransactionHistoricRequest(@RequestParam(value = "email") String userEmail) {
        logger.info("Requête GET, Endpoint 'Money' - Récupération de l'historique de transaction de l'utilisateur : " + userEmail);
        List<Transaction> transactionHistoric = service.getTransactionHistoric(userEmail);
        ResponseEntity<List<Transaction>> response = new ResponseEntity<List<Transaction>>(transactionHistoric, HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<Void> postTransactionRequest(@RequestBody TransactionToPost newTransaction) {
        logger.info("Requête POST, Endpoint 'Money' - Nouvelle Transaction entre " + newTransaction.getGiverEmail() + " et " + newTransaction.getReceiverEmail());
        ResponseEntity<Void> response;
        if(newTransaction.getAmount() <= 0.0) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            boolean saved = service.postTransaction(newTransaction);
            if(saved) {
                response = new ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    @PostMapping(value = "/userIban")
    public ResponseEntity<Void> putIbanRequest(@RequestBody IbanToUpdate ibanToUpdate) {
        logger.info("Requête PUT, Endpoint 'Money' - Ajout/Modification de l'IBAN de l'utilisateur : " + ibanToUpdate.toString());
        service.putIban(ibanToUpdate);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }
    
}
