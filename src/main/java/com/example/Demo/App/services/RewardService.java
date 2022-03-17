package com.example.Demo.App.services;

import com.example.Demo.App.beans.CustomerRewards;
import com.example.Demo.App.beans.CustomerTransactions;
import com.example.Demo.App.beans.TransactionDetails;
import com.example.Demo.App.beans.TransactionRewards;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RewardService {

    public CustomerRewards getTransactionDetails(CustomerTransactions customerTransactions) {
        int rewardsTotal = 0;
        CustomerRewards customerRewards = new CustomerRewards();
        customerRewards.setCustomerId(customerTransactions.getCustomerId());
        try {
            for (TransactionDetails transactionDetail : customerTransactions.getTransactionDetails()) {
                TransactionRewards transactionReward = new TransactionRewards();
                Integer salePrice = transactionDetail.getSalePrice();
                int reward = 0;
                if(salePrice > 50) {
                    if(salePrice < 100) {
                        reward += salePrice - 50;
                    } else {
                        int doubleRewards = salePrice - 100;
                        reward += (doubleRewards)*2 + (salePrice - (doubleRewards + 50));
                    }
                    rewardsTotal += reward;
                    transactionReward.setRewards(reward);
                    transactionReward.setTransactionId(transactionDetail.getTransactionId());
                    customerRewards.getTransactionRewards().add(transactionReward);
                }
            }
            customerRewards.setRewardsTotal(rewardsTotal);
            return customerRewards;
        } catch (Exception e) {
            log.error("Error calculating rewards for customer {}", customerTransactions.getCustomerId(), e);
        }
        return customerRewards;
    }
}
