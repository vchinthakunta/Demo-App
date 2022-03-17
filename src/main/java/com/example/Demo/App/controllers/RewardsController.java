package com.example.Demo.App.controllers;


import com.example.Demo.App.beans.CustomerRewards;
import com.example.Demo.App.beans.CustomerTransactions;
import com.example.Demo.App.services.RewardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
public class RewardsController {


    private RewardService rewardService;

    @PostMapping(value = "/addTransactions")
    public Object addActiveStockoutForCafe(@RequestBody CustomerTransactions transactions) {
        CustomerRewards customerRewards = rewardService.getTransactionDetails(transactions);
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }

}
