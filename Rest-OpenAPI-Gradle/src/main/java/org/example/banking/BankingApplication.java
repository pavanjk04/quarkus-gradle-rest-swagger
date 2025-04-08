package org.example.banking;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class BankingApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}