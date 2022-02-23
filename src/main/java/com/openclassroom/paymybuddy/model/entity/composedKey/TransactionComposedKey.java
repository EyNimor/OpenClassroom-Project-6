package com.openclassroom.paymybuddy.model.entity.composedKey;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.paymybuddy.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable @AllArgsConstructor @NoArgsConstructor
@ExcludeFromJacocoGeneratedReport
public class TransactionComposedKey implements Serializable {

    @JoinColumn(name = "number_id", columnDefinition = "bigint(20)")
    long numberId;

    @ManyToOne
    @JoinColumn(name = "giver_email")
    User giverEmail;

    @ManyToOne
    @JoinColumn(name = "receiver_email")
    User receiverEmail;

    @Override
    public String toString() {
        return "TransactionComposedKey [giverEmail=" + giverEmail.getEmail() + ", receiverEmail=" + receiverEmail.getEmail()
                + "]";
    }
    
}
