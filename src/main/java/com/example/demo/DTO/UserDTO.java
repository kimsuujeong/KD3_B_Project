package com.example.demo.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserDTO {
	
	@Id
	@Column(name = "userID")
	private String userID; // id
	
	@Column(name = "email")
	private String email; // email
	
	@Column(name = "password")
	private String password; // password
	
	@Column(name = "userName")
	private String userName; // user name
	
	@CreationTimestamp
	private LocalDateTime date; // date

}
