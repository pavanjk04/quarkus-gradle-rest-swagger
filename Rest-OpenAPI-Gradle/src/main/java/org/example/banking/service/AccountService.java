package org.example.banking.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.banking.model.Account;

import java.util.*;

@ApplicationScoped
public class AccountService {

    private final Map<String, List<Account>> store = new HashMap<>();

    public List<Account> getAccounts(String customerId) {
        return store.getOrDefault(customerId, Collections.emptyList());
    }

    public Account createAccount(Account account) {
        store.computeIfAbsent(account.getCustomerId(), k -> new ArrayList<>()).add(account);
        return account;
    }
}
