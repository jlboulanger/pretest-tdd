package com.priceminister.account;


import static org.junit.Assert.*;

import org.junit.*;

import com.priceminister.account.implementation.*;


/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass.
 * Then focus on the second test, and so on.
 * 
 * We want to see how you "think code", and how you organize and structure a simple application.
 * 
 * When you are done, please zip the whole project (incl. source-code) and send it to recrutement-dev@priceminister.com
 * 
 */
public class CustomerAccountTest {
    
    Account customerAccount;
    AccountRule rule;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        customerAccount = new CustomerAccount();
    }
    
    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        Double balance = customerAccount.getBalance();
        assertNotNull(balance);
        assertEquals(Double.valueOf(0.0), balance);
    }
    
    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        Double balance = customerAccount.getBalance();
        double ten = 10.5;
        Double expected = balance + ten;

        customerAccount.add(ten);
        Double newBalance = customerAccount.getBalance();
        assertNotNull(newBalance);
        assertEquals(expected, newBalance);
    }
    
    /**
     * Tests that an illegal withdrawal throws the expected exception.
     * Use the logic contained in CustomerAccountRule; feel free to refactor the existing code.
     */
    @Test(expected = IllegalBalanceException.class)
    public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalBalanceException {
        rule = new CustomerAccountRule();
        customerAccount.withdrawAndReportBalance(100.0, rule);
    }
    
    // Also implement missing unit tests for the above functionalities.
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullParameter() {
        customerAccount.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNegativeParameter() {
        customerAccount.add(-1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNullAmount() {
        rule = new CustomerAccountRule();
        try {
            customerAccount.withdrawAndReportBalance(null, rule);
        } catch (IllegalBalanceException e) {
            fail("should have failed before");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegativeAmount() {
        rule = new CustomerAccountRule();
        try {
            customerAccount.withdrawAndReportBalance(-100.0, rule);
        } catch (IllegalBalanceException e) {
            fail("should have failed before");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNoRule() {
        try {
            customerAccount.withdrawAndReportBalance(100.0, null);
        } catch (IllegalBalanceException e) {
            fail("should have failed before");
        }
    }

    @Test
    public void testWithdrawRuleNoNegativeBalance() {
        try {
            rule = new CustomerAccountRule();
            customerAccount.add(250.0);
            Double expectedBalance = customerAccount.getBalance() - 100.0;
            Double newBalance =  customerAccount.withdrawAndReportBalance(100.0, rule);
            assertTrue(newBalance >= 0.0);
            assertEquals(expectedBalance, newBalance);
        } catch (IllegalBalanceException e) {
            fail("should not have failed");
        }
    }
}
