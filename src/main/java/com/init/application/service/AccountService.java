package com.init.application.service;

import java.util.List;

import com.init.application.controller.TransferBalanceRequest;
import com.init.application.dto.AccountStatement;
import com.init.application.entity.Account;
import com.init.application.entity.Transaction;

public interface AccountService {
    
	List<Account> findAll();
    Account save(Account account);
    Transaction sendMoney(
            TransferBalanceRequest transferBalanceRequest
    );
    AccountStatement getStatement(String accountName);
}