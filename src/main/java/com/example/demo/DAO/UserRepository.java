package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.DTO.UserDTO;


public interface UserRepository extends JpaRepository<UserDTO, String> {

}
