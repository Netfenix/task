package com.init.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.init.application.entity.Account;
import com.init.application.service.AccountServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class AccountServiceImplTest {

    @TestConfiguration
    static class AccountServiceTestContextConfiguration {
        @Bean
        public AccountServiceImpl accountServiceImplTest() {
            return new AccountServiceImpl();

        }
    }

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    public void sendMoneyTest() {
        Account account1 = new Account();
        Account account2 = new Account();
        account1.setBalance(new BigDecimal(50000));
        account1.setCurrency("eu");
        account1.setName("3456");
        account2.setBalance(new BigDecimal(40000));
        account2.setCurrency("eu");
        account2.setName("223456");
        accountService.save(account1);
        accountService.save(account2);

        TransferBalanceRequest transferBalanceRequest = new TransferBalanceRequest();
        transferBalanceRequest.setFromAccountName(account1.getName());
        transferBalanceRequest.setToAccountName(account2.getName());
        transferBalanceRequest.setAmount(new BigDecimal(3000));
                ;
        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.findByAccountName(account1.getName())
                .getBalance())
                .isEqualTo(new BigDecimal(47000));
        assertThat(accountService.findByAccountName(account2.getName())
                .getBalance())
                .isEqualTo(new BigDecimal(5000));

    }

    @Test
    public void getStatement() {
    	 Account account1 = new Account();
         Account account2 = new Account();
        account1.setBalance(new BigDecimal(50000));
        account1.setCurrency("eu");
        account1.setName("3456");
        account2.setBalance(new BigDecimal(40000));
        account2.setCurrency("eu");
        account2.setName("223456");
        accountService.save(account1);
        accountService.save(account2);
        TransferBalanceRequest transferBalanceRequest = new TransferBalanceRequest();
        transferBalanceRequest.setFromAccountName(account1.getName());
        transferBalanceRequest.setToAccountName(account2.getName());
        transferBalanceRequest.setAmount(new BigDecimal(3000));

        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.getStatement(account1.getName())
                .getCurrentBalance())
                .isEqualTo(new BigDecimal(47000));
        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.getStatement(account1.getName())
                .getCurrentBalance()).isEqualTo(new BigDecimal(44000));
        assertThat(accountService.getStatement(account1.getName())
                .getTransactionHistory().size()).isEqualTo(2);
    }

}
