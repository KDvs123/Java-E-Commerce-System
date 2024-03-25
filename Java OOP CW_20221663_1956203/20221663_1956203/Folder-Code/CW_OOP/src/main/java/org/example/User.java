package org.example;

import java.util.regex.*;
/**
 * Class representing a user with username, password, and purchase status.
 */

public class User {
    private String username;
    private String password;


    /**
     * Constructor for User. Validates password format before initializing.
     * @param username - The username of the user.
     * @param password - The password of the user, which must meet specific format requirements.
     * @throws IllegalArgumentException if the password format is invalid.
     */

    public User(String username, String password) {

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password format");
        }
        this.username = username;
        this.password = password;


    }


    /**
     * Gets the username of the user.
     * @return String - The username.
     */

    public String getUsername() {
        return username;
    }


    /**
     * Validates the format of the given password.
     * @param password - The password to be validated.
     * @return boolean - True if the password is valid, false otherwise.
     */

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

    /**
     * Sets a new password for the user, validating the password format.
     * @param newPassword - The new password to set.
     * @throws IllegalArgumentException if the new password format is invalid.
     */
    public void setPassword(String newPassword) {
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Invalid password format");
        }
        this.password = newPassword;
    }

    /**
     * Gets the password of the user.
     * @return String - The password.
     */
    public String getPassword() {
        return password;

    }
}
