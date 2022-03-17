package com.example.Demo.App.controllers;

import com.example.Demo.App.beans.CustomerRewards;
import com.example.Demo.App.beans.CustomerTransactions;
import com.example.Demo.App.beans.TransactionDetails;
import com.example.Demo.App.beans.TransactionRewards;
import com.example.Demo.App.services.RewardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RewardsControllerTest {

    @InjectMocks
    private RewardsController controllerUnderTest;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    RewardService rewardService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(controllerUnderTest, "rewardService", rewardService);
    }

    @Test
    public void getRewardsTest() throws Exception {

        String url = "/addTransactions";
        String customerId = "98989";

        CustomerTransactions customerTransactions = new CustomerTransactions();
        customerTransactions.setCustomerId(customerId);

        TransactionDetails transactionDetails1 = new TransactionDetails();
        transactionDetails1.setSalePrice(new Random().nextInt());
        transactionDetails1.setTransactionId(RandomStringUtils.random(4));


        TransactionDetails transactionDetails2 = new TransactionDetails();
        transactionDetails2.setSalePrice(new Random().nextInt());
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


        when(rewardService.getTransactionDetails(any(CustomerTransactions.class))).thenReturn(customerRewards);

        String requestPayload = objectMapper.writeValueAsString(customerTransactions);
        String expectedResult = objectMapper.writeValueAsString(customerRewards);
        MvcResult result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isOk())
                .andReturn();

        verify(rewardService, times(1)).getTransactionDetails(eq(customerTransactions));
        assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
}
