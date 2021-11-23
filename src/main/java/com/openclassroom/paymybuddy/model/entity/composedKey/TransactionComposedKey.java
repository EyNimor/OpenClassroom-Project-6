package com.openclassroom.paymybuddy.model.entity.composedKey;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.paymybuddy.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable @AllArgsConstructor @NoArgsConstructor
@ExcludeFromJacocoGeneratedReport
public class TransactionComposedKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "giver_email")
    User giverEmail;

    @ManyToOne
    @JoinColumn(name = "receiver_email")
    User receiverEmail;

    @Column(name = "transaction_date")
    Date transactionDate;

    @Override
    public String toString() {
        return "TransactionComposedKey [giverEmail=" + giverEmail.getEmail() + ", receiverEmail=" + receiverEmail.getEmail()
                + ", transactionDate=" + transactionDate + "]";
    }
    
}
