package com.example.Demo.App.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerRewards {

    private String customerId;

    private List<TransactionRewards> transactionRewards = new ArrayList<>();

    private Integer rewardsTotal = 0;
}
