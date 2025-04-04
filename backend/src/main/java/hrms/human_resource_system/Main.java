package main.java.hrms.human_resource_system;

import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.service.LoginService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== HRMS Login ====");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = loginService.authenticate(username, password);

        if (user != null) {
            System.out.println("\nWelcome " + user.getUsername() + "! You are logged in as " + user.getRole().getName());
            switch (user.getRole().getName()) {
                case "Admin":
                    adminMenu();
                    break;
                case "HR":
                    hrMenu();
                    break;
                case "Employee":
                    employeeMenu();
                    break;
                default:
                    System.out.println("Unknown role.");
            }
        } else {
            System.out.println("❌ Invalid username or password.");
        }
    }

    private static void adminMenu() {
        System.out.println("\n[ADMIN MENU]");
        System.out.println("1. View all users");
        System.out.println("2. Add user");
        System.out.println("3. Delete user");
        // Add real methods later
    }

    private static void hrMenu() {
        System.out.println("\n[HR MENU]");
        System.out.println("1. View employees");
        System.out.println("2. Manage leave requests");
    }

    private static void employeeMenu() {
        System.out.println("\n[EMPLOYEE MENU]");
        System.out.println("1. View personal info");
        System.out.println("2. Request vacation");
    }
}

