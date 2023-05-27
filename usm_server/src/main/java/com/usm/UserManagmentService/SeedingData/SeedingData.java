package com.usm.UserManagmentService.SeedingData;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * A class that is used to seed data into the database.
 */
public class SeedingData {

    @Autowired
    UserService userService;

    Gson gson = new Gson();

    public SeedingData(UserService userService) {
        this.userService = userService;
    }

    /**
     * It seeds the users table.
     *
     * @param event The event that triggered the listener.
     */
    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException, ParseException {
        seedUsersTable();
    }

    /**
     * It reads the test_users.json file and saves the users to the database.
     */
    public void seedUsersTable() throws IOException, ParseException {

        try {
            InputStream stream = new FileInputStream("src/main/java/com/usm/UserManagmentService/assets/test_users.json");
            JsonReader reader = new JsonReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            // Read file in stream mode
            reader.beginArray();
            while (reader.hasNext()) {
                User userInfo = gson.fromJson(reader, User.class);
                System.out.println(userInfo);
                userInfo.getUserCredentials().setPassword(userInfo.getUserCredentials().getPassword());
                userService.saveUser(userInfo);
            }
            reader.endArray();
            reader.close();

        } catch (UnsupportedEncodingException | ResponseStatusException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
