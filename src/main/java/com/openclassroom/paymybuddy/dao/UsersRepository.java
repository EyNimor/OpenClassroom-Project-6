package com.openclassroom.paymybuddy.dao;

import com.openclassroom.paymybuddy.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
    
}
