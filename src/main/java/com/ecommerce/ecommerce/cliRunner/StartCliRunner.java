package com.ecommerce.ecommerce.cliRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartCliRunner implements CommandLineRunner {

    private final CliService cliService;

    public StartCliRunner(CliService cliService) {
        this.cliService = cliService;
    }

    @Override
    public void run(String... args) throws Exception {
        cliService.start();   // THIS starts your actual CLI menu
    }
}
