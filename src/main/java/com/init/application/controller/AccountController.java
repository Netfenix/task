package com.init.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.init.application.dao.AccountDAO;
import com.init.application.entity.Account;
import com.sun.el.stream.Optional;

@RestController
@RequestMapping("/task")
public class AccountController {
	
	
		@Autowired
		private AccountDAO accountDAO;

		@GetMapping
		public ResponseEntity<List<Account>> getAccount(){
			List<Account> accounts = accountDAO.findAll();
			
			return ResponseEntity.ok(accounts);
			
		}
		
		@GetMapping("/task/name")
		public ResponseEntity<Account> findByName(String name) {

			List<Account> list =  accountDAO.findByName(name);
			
			Account account = list.get(0);
			
			return ResponseEntity.ok(account);
}

			
		@PostMapping
		public ResponseEntity<Account> createAccount(@RequestBody Account account){
			
			Account newAccount= accountDAO.save(account);
			return ResponseEntity.ok(newAccount);
			
		}
			
		}
		


