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

@Getter
@Embeddable @AllArgsConstructor @NoArgsConstructor
@ExcludeFromJacocoGeneratedReport
public class UserNetworkComposedKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_email")
    User userEmail;

    @ManyToOne
    @JoinColumn(name = "friend_email")
    User friendEmail;

    @Override
    public String toString() {
        return "UserNetworkComposedKey [friendEmail=" + friendEmail.getEmail() + ", userEmail=" + userEmail.getEmail() + "]";
    }
    
}
