package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    private ArrayList<String[]> cartItems;
    private double total;
    private double categorydiscount;
    private double purchaseDiscount;
    private double finalTotal;

    private ArrayList<User> users;


    /**
     * Constructor to initialize a new shopping cart.
     */
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.total = 0.0;
        this.categorydiscount = 0.0;
        this.purchaseDiscount=0.0;
        this.finalTotal = 0.0;
        this.users=new ArrayList<>(0);
    }

    /**
     * Adds a product to the shopping cart.
     * @param productId   - The unique identifier of the product.
     * @param productName - The name of the product.
     * @param price       - The price of the product.
     * @param quantity    - The quantity of the product.
     * @param category    - The category of the product.
     * @param currentUser - The current user adding the product.
     */
    public void addToCart(String productId, String productName, double price, int quantity, String category, User currentUser) {
        boolean found = false;
        for (String[] item : cartItems) {
            if (item[0].equals(productId)) {
                // Product found in cart, update the quantity
                int currentQuantity = Integer.parseInt(item[4]) + quantity;
                item[4] = Integer.toString(currentQuantity);
                found = true;
                break;
            }
        }

        if (!found) {
            // Product not found in cart, add as a new entry
            String[] newItem = new String[]{productId, productName, category, Double.toString(price), Integer.toString(quantity)};
            cartItems.add(newItem);
        }

        updateCartTotal(currentUser);
    }

    /**
     * Updates the cart's total price and applies discounts.
     * @param user - The user associated with the cart.
     */
    private void updateCartTotal(User user) {
        total=0.0;

        for (String[] item : cartItems) {
            double price = Double.parseDouble(item[3]);
            int quantity = Integer.parseInt(item[4]);
            total += price * quantity;
        }


        // Calculate first purchase discount only if it's user's first purchase
        if (isFirstTimePurchase(user.getUsername())) {
            FirstPurchaseDiscount(user.getUsername());
        } else {
            purchaseDiscount = 0.0; // No discount if not the first purchase
        }


        // Calculate discount
        categorydiscount = categoryDiscount();

        // Calculate final total
        finalTotal = total - categorydiscount-purchaseDiscount;
    }

    /**
     * Sets a category-based discount.
     * @param discount - The category-based discount value.
     */
    public void setDiscount(double discount) {
        this.categorydiscount = discount;
    }

    /**
     * Sets the final total after applying discounts.
     * @param finalTotal - The final total price.
     */
    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }

    /**
     * Calculates the category-based discount for the cart.
     * @return The category-based discount amount.
     */
    public double categoryDiscount() {
        HashMap<String, Integer> categoryCounts = new HashMap<>();

        // Count the number of items and total price per category
        for (String[] item : cartItems) {
            String category = item[2];
            int quantity = Integer.parseInt(item[4]);
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + quantity);
        }

        // Check if any category has three or more items
        for (int itemCount : categoryCounts.values()) {
            if (itemCount >= 3) {
                // Apply a 20% discount to the total if any category has 3 or more items
                return total * 0.20;
            }
        }
        return 0.0;


    }



    /**
     * Checks if it's the user's first-time purchase.
     * @param username - The username of the user.
     * @return True if it's the first-time purchase, false otherwise.
     */
    public boolean isFirstTimePurchase(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates and applies the first-time purchase discount.
     * @param username - The username of the user.
     */
    public void FirstPurchaseDiscount(String username) {
        if (isFirstTimePurchase(username)) {
            purchaseDiscount = total * 0.10;

        }
    }

    /**
     * Gets the purchase discount.
     * @return The purchase discount amount.
     */
    public double getPurchaseDiscount() {
        return purchaseDiscount;
    }


    /**
     * Adds a new user to the user list.
     * @param username - The username of the new user.
     * @param password - The password of the new user.
     */
    public void addUser(String username, String password) {
        // Check if the user already exists in the list before adding
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return;
            }
        }
        users.add(new User(username, password));

    }


    /**
     * Gets the total price of items in the cart.
     * @return The total price of items in the cart.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Gets the category-based discount amount.
     * @return The category-based discount amount.
     */
    public double getDiscount() {
        return categorydiscount;
    }

    /**
     * Gets the final total after applying discounts.
     * @return The final total price.
     */
    public double getFinalTotal() {
        return finalTotal;
    }

    /**
     * Gets the list of items in the cart.
     * @return The list of items in the cart.
     */
    public ArrayList<String[]> getCartItems() {
        return cartItems;
    }

    /**
     * Removes an item from the cart based on its product ID.
     * @param productId - The unique identifier of the product to be removed.
     * @param currentUser - The current user who is performing the action.
     */
    public void removeFromCart(String productId, User currentUser) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i)[0].equals(productId)) {
                cartItems.remove(i);
                updateCartTotal(currentUser);
                return;
            }
        }
        System.out.println("Product with ID " + productId + " not found in the cart.");
    }



}