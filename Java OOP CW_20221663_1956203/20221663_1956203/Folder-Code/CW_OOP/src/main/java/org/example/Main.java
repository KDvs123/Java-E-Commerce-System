package org.example;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    /**
     * The main entry point for the Westminster Shopping application.
     * Allows users to log in as an Admin or User and interact with the shopping system.
     * @param args - The command-line arguments (not used in this application).
     */

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        ShoppingCart shoppingCart = new ShoppingCart();
        ShoppingGUI frame = null;
        boolean a = true;

        while (a) {
            System.out.print("Enter 'A' for Admin, 'U' for User, or 'Q' to Quit: ");
            String choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    do {
                        westminsterShoppingManager.displayMenu();
                    } while (askIfContinue(input, "admin"));
                    break;
                case "U":
                    do {
                        System.out.println("------------------------Opens the Terminal--------------------");
                        String username = westminsterShoppingManager.getInput("Enter username: ", input, "string");
                        boolean validPassword = false;
                        User user = null;

                        while (!validPassword) {
                            try {
                                String password = westminsterShoppingManager.getInput("Enter password with a minimum of 8 characters and include at least one number: ", input, "string");
                                user = new User(username, password);
                                validPassword = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid password format. Please try again.");
                            }
                        }
                        shoppingCart.addUser(username, user.getPassword());

                        // Check if the frame already exists. If so, dispose the old one.
                        if (frame != null) {
                            frame.dispose();
                        }

                        // Create a new instance of ShoppingGUI with the updated user information
                        frame = new ShoppingGUI(westminsterShoppingManager, user);
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


                    } while (askIfContinue(input, "user"));
                    break;
                case "Q":
                    System.out.println("Exiting the system...");
                    a = false;
                    break;
                default:
                    System.out.println("Invalid input. Please enter 'A', 'U', or 'Q'.");
                    break;
            }
        }
    }

    /**
     * Helper method to ask the user if they want to continue as a certain role (admin or user).
     * @param input - Scanner object for user input.
     * @param role  - The role (admin or user) for which the user is asked to continue.
     * @return True if the user wants to continue as the given role, false if they want to redirect to the main menu.
     */
    private static boolean askIfContinue(Scanner input, String role) {
        System.out.print("Do you want to continue as a " + role + "? (Y/N): ");
        String response = input.nextLine().trim().toUpperCase();

        if (response.equals("N")) {
            return false;
        } else if (response.equals("Y")) {
            return true;
        } else {
            System.out.println("Incorrect value. Please enter Y or N.");
            return askIfContinue(input, role);
        }

    }
}

