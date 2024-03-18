package com.example.demo.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
//	@Id
//	@Column(name = "userID")
	private String userID; // id
	
//	@Column(name = "email")
	private String email; // email
	
//	@Column(name = "password")
	private String password; // password
	
//	@Column(name = "userName")
	private String userName; // user name
	
//	@CreationTimestamp
	private LocalDateTime date; // date

}
