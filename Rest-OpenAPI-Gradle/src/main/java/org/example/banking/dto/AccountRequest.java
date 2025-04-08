package org.example.banking.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Account request body for creating a new account")
public class AccountRequest {
    @Schema(description = "Account number", example = "ACC3002")
    public String accountNumber;

    @Schema(description = "Account type", example = "SAVINGS")
    public String accountType;

    @Schema(description = "Account balance", example = "5000.0")
    public double balance;
}
