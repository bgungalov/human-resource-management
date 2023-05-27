package com.usm.UserManagmentService;

import com.usm.UserManagmentService.SeedingData.SeedingData;
import com.usm.UserManagmentService.Service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UserManagementServiceApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SeedingData seedingData = new SeedingData(userService);
        seedingData.seedUsersTable();
    }
}
