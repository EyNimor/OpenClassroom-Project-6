package com.openclassroom.paymybuddy.dao;

import com.openclassroom.paymybuddy.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends JpaRepository<User, String> {

    @Transactional
    @Modifying
    @Query("update User u set u.iban = :iban where u.email = :email")
    void modifyUserIban(@Param("email") String userEmail, @Param("iban") String iban);
    
}
