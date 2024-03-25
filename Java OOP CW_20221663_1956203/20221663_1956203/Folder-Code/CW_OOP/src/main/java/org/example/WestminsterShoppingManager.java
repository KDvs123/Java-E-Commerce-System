package org.example;

import java.io.*;

import java.sql.SQLOutput;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    private static final int MAX_PRODUCTS = 50;
    protected ArrayList<Product> productList;
    private HashMap<String,User> users;


    /**
     * Constructor for WestminsterShoppingManager class.
     * Initializes the user HashMap, FileManager, and product list.
     */

    public WestminsterShoppingManager() {
        users=new HashMap<>();
        this.productList = new ArrayList<>();

    }

    /**
     * Get the list of products in the shopping manager.
     * @return ArrayList<Product> - The list of products.
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }

    /**
     * Display the main menu for the shopping manager.
     */

    public void displayMenu() {
        Scanner input = new Scanner(System.in);
        boolean continueMenu = true;

        while (continueMenu) {
            try {
                System.out.print("""
                    \n=======================================================================================
                    ||                                     MENU                                          ||
                    ||                                         
                    =======================================================================================
                    ||                                                                                   ||
                    ||             [1] add a product               [4] save all to a file                ||
                    ||             [2] delete a product            [5] reads and load the file           ||
                    ||             [3] print the list of products  [6] Exit From the Program             ||
                    ||                                                                                   ||
                    ||                                                                                   ||
                    =======================================================================================
                   
                    What is your choice ?\s""");

                int choice = input.nextInt();
                input.nextLine(); // Consume the newline left-over
                switch (choice) {
                    case 1:
                        addNewProducts(input);
                        break;
                    case 2:
                        deleteNewProduct(input);
                        break;
                    case 3:
                        printProductList();
                        break;
                    case 4:
                        saveProductList();
                        break;
                    case 5:
                        loadFromFile();
                        break;
                    case 6:
                        System.out.println("Exiting from the admin panel......");
                        continueMenu = false; // Set flag to false to exit the while loop
                        break;
                    default:
                        System.out.println("Invalid Choice. Please enter a valid option.");
                        // The while loop will continue, prompting the user again.
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid integer value.");
                input.nextLine(); // Consume the invalid input and clear the buffer
            }
        }


        }



    /**
     * Add a new product to the shopping manager.
     * @param input - Scanner object for user input.
     */

    @Override
    public void addNewProducts(Scanner input) {
        if (productList.size() >= MAX_PRODUCTS) {
            System.out.println("Maximum product limit reached");
            return;
        }
        System.out.println("Select product type to add: ");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        int productType = Integer.parseInt(getInput("Enter product type (1 for Electronics or 2 for Clothing): ", input,"int"));


        switch (productType) {
            case 1:
                addProduct(input,productType);
                break;
            case 2:
                addProduct(input,productType);
                break;
            default:
                System.out.println("Invalid product type");

        }


    }


    /**
     * Print the list of products in the shopping manager.
     */

    @Override
    public void printProductList() {
        if (productList.isEmpty()) {
            System.out.println("There aren't any products on the list.\n");
        } else {
            // Sort products by name in ascending order
            productList.sort(Comparator.comparing(Product::getProductID));

            System.out.println("\n\n==========================List of Products===============================================");
            for (Product product : productList) {
                System.out.println("ID: " + product.getProductID());
                String productType = (product instanceof Electronics) ? "Electronics" : "Clothing";
                System.out.println("Name: " + product.getProductName()); // Ensure getProductName exists in Product class
                System.out.println("Product Type: " + productType);
                System.out.println("Items Available: " +product.getNoOfItems());
                System.out.println("Product Price: " +product.getPrice());

                // Check product type and print additional details
                if (product instanceof Electronics) {
                    Electronics electronicProduct = (Electronics) product;
                    System.out.println("Brand: " + electronicProduct.getBrand());
                    System.out.println("Warranty Period: " + electronicProduct.getWarrentyPeriod() + " weeks");
                } else if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    System.out.println("Color: " + clothingProduct.getColor());
                    System.out.println("Size: " + clothingProduct.getSize());
                }

                System.out.println("=======================================================================================");
            }

            System.out.println();
        }
    }


    /**
     * Delete a product from the shopping manager.
     * @param input - Scanner object for user input.
     */
    @Override
    public void deleteNewProduct(Scanner input) {
        if (productList.size() == 0) {
            System.out.println("product list is empty");
            return;
        }

        System.out.println("Removing a product from the shopping cart");

        while (true) {
            String productID = getInput("Enter the product ID To Delete an Item: ", input, "string");
            Product productToRemove = getProductById(productID);

            if (productToRemove != null) {
                String productType = (productToRemove instanceof Electronics) ? "Electronics" : "Clothing";
                productList.remove(productToRemove);
                System.out.println("Deleted " + productType + " product");
                System.out.println("Product ID: " + productToRemove.getProductID());
                System.out.println("Name: " + productToRemove.getProductName());
                System.out.println("Price: "+productToRemove.getPrice());
                System.out.println("Available Items: "+productToRemove.getNoOfItems());

                if (productToRemove instanceof Electronics) {
                    Electronics electronicProduct = (Electronics) productToRemove;
                    System.out.println("Brand: " + electronicProduct.getBrand());
                    System.out.println("Warranty Period: " + electronicProduct.getWarrentyPeriod() + " weeks");
                } else if (productToRemove instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) productToRemove;
                    System.out.println("Color: " + clothingProduct.getColor());
                    System.out.println("Size: " + clothingProduct.getSize());
                }
                System.out.println("Product deleted successfully.\n");

                int totalProductsLeft = productList.size();
                System.out.println("Total number of products left: " + totalProductsLeft+"\n");
                break; // Exit the loop when a valid product is deleted
            } else {
                System.out.println("Product not found. Please enter a valid product ID.");
            }
        }


    }

    /**
     * Get user input with appropriate validation for different data types.
     * @param message - The message to display when prompting for input.
     * @param scanner - Scanner object for user input.
     * @param type    - Data type for input validation (e.g., "string", "double", "int").
     * @return The user input as a String.
     */

    public String getInput(String message, Scanner scanner, String type) {
        while (true) {
            try {
                System.out.print(message);

                switch (type.toLowerCase()) {
                    case "string":
                        String inputLine = scanner.nextLine();
                        if (inputLine.trim().isEmpty()) {
                            System.out.println("Input cannot be empty. Please try again.");
                            continue; // Skip the rest of the loop and prompt again
                        }
                        return inputLine;

                    case "double":
                        double doubleValue = scanner.nextDouble();
                        scanner.nextLine(); // Clear the buffer
                        if (doubleValue < 0) {
                            System.out.println("Invalid input. Please enter a non-negative double value.");
                            continue;
                        }
                        return String.valueOf(doubleValue);

                    case "int":
                        int intValue = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                        if (intValue < 0) {
                            System.out.println("Invalid input. Please enter a non-negative integer value.");
                            continue;
                        }
                        return String.valueOf(intValue);
                    default:
                        throw new IllegalArgumentException("Invalid type specified.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the scanner buffer
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        return null;
    }

    /**
     * Get a product by its unique product ID.
     * @param productID - The product ID to search for.
     * @return The product if found, or null if not found.
     */

    public Product getProductById(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;

    }

    /**
     * Add a product to the shopping manager's product list.
     * @param input       - Scanner object for user input.
     * @param productType - The type of product to add (1 for Electronics, 2 for Clothing).
     */
    private void addProduct(Scanner input, int productType){
        System.out.println(productType==1? "Adding a new Electronic Product":"Adding a new Clothing Product");

        String productId;
        while (true) {
            productId = getInput("Enter the product ID: ", input, "string");
            if (isProductIDExists(productId)) {
                System.out.println("Error: A product with this ID already exists. Please enter a unique ID.");
            } else {
                break; // Break the loop if the product ID is unique
            }
        }
        String productName = getInput("Enter the product name: ", input,"string");
        String brandOrColor = productType == 1 ? getInput("Enter the brand: ", input, "string")
                : getInput("Enter the color: ", input, "string");

        int availableItems =Integer.parseInt(getInput("Enter the available item values: ", input,"int"));
        double price = Double.parseDouble(getInput("Enter the price(in €): ", input,"double"));

        if (productType==1) {
            int warrantyPeriod = Integer.parseInt(getInput("Enter the warranty period: ", input,"int"));
            Product electronics = new Electronics(productId, productName, availableItems, price, brandOrColor, warrantyPeriod);
            productList.add(electronics);
            System.out.println("Electronics product added successfully");
        } else if (productType==2) {
            String size =getInput("Enter the size(s,m,l,xl): ", input,"String");
            Product clothing = new Clothing(productId, productName, availableItems, price, size, brandOrColor);
            productList.add(clothing);
            System.out.println("Clothing product added successfully");
        } else {
            System.out.println("Invalid product type.");
        }

    }

    /**
     * Check if a product with a given product ID already exists in the product list.
     * @param productId - The product ID to check.
     * @return True if the product ID exists, false otherwise.
     */

    private boolean isProductIDExists(String productId) {
        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Save the list of products to a file.
     */
    @Override
    public void saveProductList() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productList.txt",true))) {

            for (Product product : productList) {
                writer.write("Product  Data\n");
                writer.write("ID: " + product.getProductID() + "\n");
                writer.write("Name: " + product.getProductName() + "\n");
                writer.write("Type: " + (product instanceof Electronics ? "Electronics" : "Clothing") + "\n");
                writer.write("No of Items: " + product.getNoOfItems() + "\n");
                writer.write("Price: " + product.getPrice() + "\n");
                writer.write("Brand/Color: " + (product instanceof Electronics ? ((Electronics)product).getBrand() : ((Clothing)product).getColor()) + "\n");
                writer.write("Warranty/Size: " + (product instanceof Electronics ? ((Electronics)product).getWarrentyPeriod() : ((Clothing)product).getSize()) + "\n");
                writer.write("\n");

            }
            System.out.println("Product list saved to the file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Update the quantity of a product by its product ID.
     * @param productId   - The product ID to update.
     * @param newQuantity - The new quantity for the product.
     */
    public void updateProductQuantity(String productId, int newQuantity) {
        Product product = getProductById(productId);
        if (product != null) {
            product.setNoOfItems(newQuantity); // Assuming there's a setter method in your Product class
        }
    }


    /**
     * Load product data from a file.
     */
    @Override
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productList.txt"))) {
            String line;
            System.out.println("Loading data from the file...");

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Product  Data")) {
                    String id = reader.readLine().split(": ")[1].trim();
                    String name = reader.readLine().split(": ")[1].trim();
                    String type = reader.readLine().split(": ")[1].trim();
                    int noOfItems = Integer.parseInt(reader.readLine().split(": ")[1].trim());
                    double price = Double.parseDouble(reader.readLine().split(": ")[1].trim());
                    String brandOrColor = reader.readLine().split(": ")[1].trim();


                    // Check if a product with the same ID already exists
                    if (isProductIDExists(id)) {
                        System.out.println("Duplicate product ID found: " + id + ". Skipping...");
                        continue; // Skip adding this product
                    }

                    Product product = null;
                    if ("Electronics".equals(type)) {
                        int warrantyPeriod = Integer.parseInt(reader.readLine().split(": ")[1].trim());
                        product = new Electronics(id, name, noOfItems, price, brandOrColor, warrantyPeriod);
                    } else if ("Clothing".equals(type)) {
                        String size =reader.readLine().split(": ")[1].trim();;
                        product = new Clothing(id, name, noOfItems, price, brandOrColor, size);
                    }

                    if (product != null) {
                        productList.add(product);
                    }
                }
            }

            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }





}
