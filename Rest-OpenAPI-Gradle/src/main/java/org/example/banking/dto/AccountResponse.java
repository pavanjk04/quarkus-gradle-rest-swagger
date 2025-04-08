package org.example.banking.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Account response with customer and account details")
public class AccountResponse {
    public String customerId;
    public String accountNumber;
    public String accountType;
    public double balance;

    public AccountResponse() {}

    public AccountResponse(String customerId, String accountNumber, String accountType, double balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }
}
