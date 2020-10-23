package com.init.application.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.init.application.dao.AccountDAO;
import com.init.application.entity.Account;
import jdk.internal.org.jline.utils.Log;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class AccountControllerTest {
	
	@Autowired
	private AccountController controller ;
	
	
	@Autowired
	private AccountDAO accountDAO;
	


	@Test
	public void testGetAccount() throws Exception {
		
				
		try {			
			
		ResponseEntity<List<Account>> list = controller.getAccount();
		assertNotNull(list);
		}catch(final Exception e) {
			Log.info(e);
		}
		
	}
	
	@Test
	public void findByName() throws Exception {
		
		String name = "Jesus";
		long id = 1;
		
		try {			
			
			ResponseEntity<Account> account = controller.findByName(name);
			assertEquals(account.getBody().getId(),id);
			}catch(final Exception e) {
				Log.info(e);
			}
		
	}

	@Test
	public void testCreateAccount() throws Exception {
		
		Account account = new Account();
		account.setBalance(120000);
		account.setCurrency("eu");
		account.setName("Fernando");
		account.setTreasury(true);
		
		try {			
			
			ResponseEntity<Account> newAccount = controller.createAccount(account);
			assertEquals(account.getName(), newAccount.getBody().getName());
			}catch(final Exception e) {
				Log.info(e);
			}
			
		
	}

}
