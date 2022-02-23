package com.openclassroom.paymybuddy.model;

import java.sql.Date;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@ExcludeFromJacocoGeneratedReport
public class TransactionToPost {

    String giverEmail;
    String receiverEmail;
    Date transactionDate;
    float amount;
    String description;

    public TransactionToPost(String giverEmail, String receiverEmail, float amount) {
        this.setGiverEmail(giverEmail);
        this.setReceiverEmail(receiverEmail);
        this.setAmount(amount); 
    }

    public TransactionToPost(String giverEmail, String receiverEmail, Date transactionDate, float amount) {
        this.setGiverEmail(giverEmail);
        this.setReceiverEmail(receiverEmail);
        this.setAmount(amount); 
    }

    public TransactionToPost(String giverEmail, String receiverEmail, float amount, String description) {
        this.setGiverEmail(giverEmail);
        this.setReceiverEmail(receiverEmail);
        this.setAmount(amount);
        this.setDescription(description);
    }
    
}
