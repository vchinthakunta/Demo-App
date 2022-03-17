package com.example.Demo.App.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerTransactions {

    private List<TransactionDetails> transactionDetails = new ArrayList<>();

    private String customerId;
}
