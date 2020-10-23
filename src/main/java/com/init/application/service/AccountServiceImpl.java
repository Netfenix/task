package com.init.application.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.application.controller.TransferBalanceRequest;
import com.init.application.dto.AccountStatement;
import com.init.application.entity.Account;
import com.init.application.entity.Transaction;
import com.init.application.repository.AccountRepository;
import com.init.application.repository.TransactionRepository;

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

    public Account findByAccountNumber(String accountName){
        Account account = accountRepository.findByAccountNameEquals(accountName);
        return account;
    }


    @Override
    public Transaction sendMoney(
            TransferBalanceRequest transferBalanceRequest
    ) {
        String fromAccountName = transferBalanceRequest.getFromAccountName();
        String toAccountNumber = transferBalanceRequest.getToAccountName();
        BigDecimal amount = transferBalanceRequest.getAmount();
        Account fromAccount = accountRepository.findByAccountNameEquals(
                fromAccountName
        );
        Account toAccount = accountRepository.findByAccountNameEquals(toAccountNumber);
        if(fromAccount.getBalance().compareTo(BigDecimal.ONE) == 1
                && fromAccount.getBalance().compareTo(amount) == 1
        ){
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            accountRepository.save(fromAccount);
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountRepository.save(toAccount);
            Transaction transaction = transactionRepository.save(new Transaction(0L,fromAccountName,amount,new Timestamp(System.currentTimeMillis())));
            return transaction;
        }
        return null;
    }

    @Override
    public AccountStatement getStatement(String accountName) {
        Account account = accountRepository.findByAccountNameEquals(accountName);
        return new AccountStatement(account.getBalance(),transactionRepository.findByAccountNameEquals(accountName));
    }

}
