package com.example.banking.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Bank Accounts", description = "Manage bank accounts")
public class AccountResource {

    // In-memory "database"
    private static final Map<String, Account> accounts = new HashMap<>();

    @GET
    @Path("/{id}")
    @Operation(summary = "Get account by ID")
    public Response getAccount(@PathParam("id") String id) {
        Account account = accounts.get(id);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Account not found"))
                    .build();
        }
        return Response.ok(account).build();
    }

    @POST
    @Operation(summary = "Create a new account")
    public Response createAccount(AccountRequest request) {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, request.ownerName(), request.balance());
        accounts.put(id, account);
        
        return Response.status(Response.Status.CREATED)
                .entity(account)
                .build();
    }

    // Data classes
    public record AccountRequest(String ownerName, double balance) {}
    public record Account(String id, String ownerName, double balance) {}
}