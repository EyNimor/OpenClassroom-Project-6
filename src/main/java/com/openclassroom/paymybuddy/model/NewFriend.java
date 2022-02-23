package com.openclassroom.paymybuddy.model;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@ExcludeFromJacocoGeneratedReport
public class NewFriend {

    String userEmail;
    String friendEmail;
    
}
