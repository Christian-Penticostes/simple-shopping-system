package com.jmf.app.java_activity.service;

import com.jmf.app.java_activity.model.LoginModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginServiceImpl implements LoginService {
    Scanner scanner = new Scanner(System.in);
    private List<LoginModel> loginCredentials = new ArrayList<>(); // store login credentials

    public LoginServiceImpl() { // constructor initializes default login accounts
        loginCredentials.add(new LoginModel("admin", "12345"));
        loginCredentials.add(new LoginModel("christian", "12345"));
    }

    @Override
    public String login(LoginModel loginmodel) {
        for (LoginModel account : loginCredentials) { // check entered credentials
            if (account.getUsername().equals(loginmodel.getUsername()) && account.getPassword().equals(loginmodel.getPassword())) {

                // return role based on username
                if (account.getUsername().equals("admin")) {
                    return "admin";
                } else if (account.getUsername().equals("christian")) {
                    return "customer";
                }
                return "customer"; // Default for other users
            }
        }

        // if login fails, it will show an error
        System.out.println("\nERROR: Invalid credentials");
        System.out.print("Press \"ENTER\" to continue...");
        try {
            System.in.read();
            System.out.println("\n");
        } catch (Exception e) {
            // do nothing
        }
        throw new RuntimeException("Invalid credentials");
    }
}
