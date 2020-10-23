package com.init.application.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.init.application.dto.Response;
import com.init.application.entity.Account;
import com.init.application.service.AccountService;
import com.sun.el.stream.Optional;

@RestController
@RequestMapping("/task")
public class AccountController {
	
	  @Autowired private AccountService accountService;
	    @RequestMapping("/create")
	    public List<Account> create(@RequestBody Account account) {
	        accountService.save(account);
	        return accountService.findAll();
	    }

	    @RequestMapping("/all")
	    public List<Account> all() {
	        return accountService.findAll();
	    }

	    @RequestMapping("/sendmoney")
	    public void  sendMoney(
	            @RequestBody TransferBalanceRequest transferBalanceRequest
	            ) {

	        Response.ok().setPayload(accountService.sendMoney(transferBalanceRequest));
	    }
	    @RequestMapping("/statement")
	    public void getStatement(
	            @RequestBody AccountStatementRequest accountStatementRequest

	    ){
	        Response.ok().setPayload(accountService.getStatement(accountStatementRequest.getAccountName()));

	    }

		
}
		


