package com.revature.chronicle.models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user of Chronicle
 * -NOTE- may change depending on progress
 */
//@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "user_id")
    private String uid;
	
	@Transient
    private String role;
    
    @Transient
    private String email;
    
    @Transient
    private String displayName;
    
    public User(String userID) {
    	this.uid = userID;
    }
}
