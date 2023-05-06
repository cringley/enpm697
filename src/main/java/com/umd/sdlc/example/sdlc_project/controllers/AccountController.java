package com.umd.sdlc.example.sdlc_project.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umd.sdlc.example.sdlc_project.models.Account;
import com.umd.sdlc.example.sdlc_project.query.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    
    private AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Testing GET request using direct unchecked input leading to SQL injection
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/")
    public Account getAccount(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        long accountId = Long.parseLong(parameterMap.get("id")[0]);
        Account account = accountRepository.getAccountById(accountId);
        return account;
    }

    /**
     * Testing PUT request using direct unchecked input leading to potential overflow/underflow
     * @param httpServletRequest
     * @return
     */
    @PutMapping("/transaction")
    public ResponseEntity<String> updateAccountAmount(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        double purchaseAmount = Double.parseDouble(parameterMap.get("ammount")[0]);
        int numPurchase = Integer.parseInt(parameterMap.get("num")[0]);
        long accountId = Long.parseLong(parameterMap.get("id")[0]);
        Account account = accountRepository.getAccountById(accountId);
        
        if(account == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid account");
        }

        accountRepository.updateAccountAmount(account.getId(), purchaseAmount, numPurchase);
        return ResponseEntity.ok("account updated");
    }
}
