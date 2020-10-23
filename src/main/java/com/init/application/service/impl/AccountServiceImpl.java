package com.init.application.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.application.controller.request.TransferBalanceRequest;
import com.init.application.dto.model.AccountStatement;
import com.init.application.entity.Account;
import com.init.application.entity.Transaction;
import com.init.application.repository.AccountRepository;
import com.init.application.repository.TransactionRepository;
import com.init.application.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Account save(Account account){
        accountRepository.save(account);
        return accountRepository.findByAccountNameEquals(account.getName());
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public Account findByAccountName(String accountName){
        Account account = accountRepository.findByAccountNameEquals(accountName);
        return account;
    }


    @Override
    public Transaction sendMoney(
            TransferBalanceRequest transferBalanceRequest
    ) {
    	Transaction transaction = new Transaction();
        String fromAccountName = transferBalanceRequest.getFromAccountName();
        String toAccountName = transferBalanceRequest.getToAccountName();
        BigDecimal amount = transferBalanceRequest.getAmount();
        Account fromAccount = accountRepository.findByAccountNameEquals(
                fromAccountName
        );
        Account toAccount = accountRepository.findByAccountNameEquals(toAccountName);
        if(fromAccount.getBalance().compareTo(BigDecimal.ONE) == 1
                && fromAccount.getBalance().compareTo(amount) == 1
        ){
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            accountRepository.save(fromAccount);
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountRepository.save(toAccount);
            Transaction finalTransaction = transactionRepository.saveAndFlush(transaction);
            return finalTransaction;
        }
        return null;
    }

    @Override
    public AccountStatement getStatement(String accountName) {
        Account account = accountRepository.findByAccountNameEquals(accountName);
        AccountStatement statement = new  AccountStatement();
        List<Transaction> list = statement.getTransactionHistory();
        AccountStatement newstatement = new  AccountStatement();
        newstatement.setCurrentBalance(account.getBalance());
        newstatement.setTransactionHistory(list);
        
        return  newstatement;
        		
        		
    }

}
