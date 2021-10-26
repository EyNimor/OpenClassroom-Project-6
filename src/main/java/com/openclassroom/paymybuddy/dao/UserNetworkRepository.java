package com.openclassroom.paymybuddy.dao;

import com.openclassroom.paymybuddy.model.entity.UserNetwork;
import com.openclassroom.paymybuddy.model.entity.composedKey.UserNetworkComposedKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNetworkRepository extends JpaRepository<UserNetwork, UserNetworkComposedKey> {
    
}
