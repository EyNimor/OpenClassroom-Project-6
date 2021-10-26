package com.openclassroom.paymybuddy.model.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.paymybuddy.model.entity.composedKey.UserNetworkComposedKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@Entity @Table(name = "usernetwork") @ExcludeFromJacocoGeneratedReport
public class UserNetwork implements Serializable {

    @EmbeddedId
    private UserNetworkComposedKey key;
    
    public UserNetwork(User user, User friend) {
        this.setKey(new UserNetworkComposedKey(user, friend));
    }
    
}
