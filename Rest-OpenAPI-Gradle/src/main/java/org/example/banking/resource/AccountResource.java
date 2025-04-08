package org.example.banking.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.banking.model.Account;
import org.example.banking.service.AccountService;
import org.example.banking.dto.AccountRequest;
import org.example.banking.dto.AccountResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Account API", description = "Operations related to customer bank accounts")
public class AccountResource {

    @Inject
    AccountService service;

    @GET
    @Path("/{customerId}")
    @Operation(summary = "Get account(s) by customer ID", description = "Returns all accounts for the specified customer ID.")
    @APIResponse(
        responseCode = "200",
        description = "Accounts retrieved",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AccountResponse.class)
        )
    )
    @APIResponse(responseCode = "404", description = "No accounts found for the customer")
    public Response getAccounts(
            @Parameter(description = "Customer ID", example = "cust123", required = true)
            @PathParam("customerId") String customerId) {

        List<Account> accounts = service.getAccounts(customerId);
        if (accounts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<AccountResponse> response = accounts.stream()
            .map(a -> new AccountResponse(a.getCustomerId(), a.getAccountNumber(), a.getAccountType(), a.getBalance()))
            .collect(Collectors.toList());

        return Response.ok(response).build();
    }

    @POST
    @Path("/{customerId}")
    @Operation(summary = "Create an account for a customer", description = "Creates a new account for a given customer ID.")
    @APIResponse(
        responseCode = "201",
        description = "Account created successfully",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AccountResponse.class),
            examples = @ExampleObject(name = "Example", value = "{ \"customerId\": \"cust123\", \"accountNumber\": \"ACC123\", \"accountType\": \"SAVINGS\", \"balance\": 5000.0 }")
        )
    )
    public Response createAccount(
            @Parameter(description = "Customer ID", example = "cust123", required = true)
            @PathParam("customerId") String customerId,

            @Parameter(description = "Account details (without customerId)", required = true)
            AccountRequest request) {

        Account account = new Account(customerId, request.accountNumber, request.accountType, request.balance);
        Account created = service.createAccount(account);

        AccountResponse response = new AccountResponse(
            created.getCustomerId(), created.getAccountNumber(), created.getAccountType(), created.getBalance()
        );

        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
