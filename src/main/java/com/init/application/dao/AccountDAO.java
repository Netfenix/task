package com.init.application.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.init.application.entity.Account;


public interface AccountDAO extends JpaRepository<Account, Long>{
	
	
	
	List<Account> findByName(@Param("name") String name);
               
	}
	
