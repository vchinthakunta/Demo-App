package com.example.Demo.App.services;


import com.example.Demo.App.beans.CustomerRewards;
import com.example.Demo.App.beans.CustomerTransactions;
import com.example.Demo.App.beans.TransactionDetails;
import com.example.Demo.App.beans.TransactionRewards;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
public class RewardServiceTest {


    @InjectMocks
    private RewardService classUnderTest;


    @Test
    public void getRewardsTest() throws Exception {

        String customerId = "98989";

        CustomerTransactions customerTransactions = new CustomerTransactions();
        customerTransactions.setCustomerId(customerId);

        TransactionDetails transactionDetails1 = new TransactionDetails();
        transactionDetails1.setSalePrice(100);
        transactionDetails1.setTransactionId(RandomStringUtils.random(4));


        TransactionDetails transactionDetails2 = new TransactionDetails();
        transactionDetails2.setSalePrice(120);
        transactionDetails2.setTransactionId(RandomStringUtils.random(4));

        customerTransactions.getTransactionDetails().add(transactionDetails1);
        customerTransactions.getTransactionDetails().add(transactionDetails2);

        CustomerRewards customerRewards = new CustomerRewards();
        customerRewards.setCustomerId(customerId);

        TransactionRewards transactionRewards1 = new TransactionRewards();
        transactionRewards1.setRewards(new Random().nextInt());
        transactionRewards1.setTransactionId("transactionId");


        TransactionRewards transactionRewards2 = new TransactionRewards();
        transactionRewards2.setRewards(new Random().nextInt());
        transactionRewards2.setTransactionId("transactionId");

        customerRewards.setCustomerId(customerId);
        customerRewards.getTransactionRewards().add(transactionRewards1);
        customerRewards.getTransactionRewards().add(transactionRewards2);
        customerRewards.setRewardsTotal(new Random().nextInt());


        CustomerRewards result = classUnderTest.getTransactionDetails(customerTransactions);
        assertEquals(java.util.Optional.ofNullable(result.getRewardsTotal()), Optional.of(140));

    }
}
