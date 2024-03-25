import org.example.Clothing;
import org.example.Electronics;
import org.example.Product;
import org.example.WestminsterShoppingManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager shoppingManager;

    private ByteArrayOutputStream outputStream;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;


    @BeforeEach
    public void setUp() {
        shoppingManager = new WestminsterShoppingManager();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

    }

    @Test
    public void testAddElectronicProduct() {
        // Create a sample input to simulate user input
        String simulatedInput = "1\n" +       // Choose product type (1 for Electronics)
                "ABC123\n" +  // Product ID
                "Laptop\n" +  // Product name
                "Dell\n" +    // Brand
                "10\n" +      // Available items
                "999.99\n" +  // Price
                "12\n";       // Warranty period
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Create a new Scanner with the simulated input
        Scanner input = new Scanner(System.in);

        // Call the addNewProduct method
        shoppingManager.addNewProducts(input);

        // Get the product list from the shopping manager
        ArrayList<Product> productList = shoppingManager.getProductList();

        // Assert that the product has been added
        assertEquals(1, productList.size());

        // Get the added product
        Product addedProduct = productList.get(0);

        // Assert the product details
        assertEquals("ABC123", addedProduct.getProductID());
        assertEquals("Laptop", addedProduct.getProductName());
        assertTrue(addedProduct instanceof Electronics);
        Electronics electronicProduct = (Electronics) addedProduct;
        assertEquals("Dell", electronicProduct.getBrand());
        assertEquals(10, electronicProduct.getNoOfItems());
        assertEquals(999.99, electronicProduct.getPrice(), 0.001);
        assertEquals(12, electronicProduct.getWarrentyPeriod());
    }

    @Test
    public void testAddClothingProduct() {
        // Create a sample input to simulate user input for adding a clothing product
        String simulatedInput = "2\n" +       // Choose product type (2 for Clothing)
                "XYZ456\n" +  // Product ID
                "Shirt\n" +   // Product name
                "Blue\n" +    // Color
                "15\n" +      // Available items
                "29.99\n" +   // Price
                "L\n";       // Size
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Create a new Scanner with the simulated input
        Scanner input = new Scanner(System.in);

        // Call the addNewProduct method
        shoppingManager.addNewProducts(input);

        // Get the product list from the shopping manager
        ArrayList<Product> productList = shoppingManager.getProductList();

        // Assert that the product has been added
        assertEquals(1, productList.size());

        // Get the added product
        Product addedProduct = productList.get(0);

        // Assert the product details
        assertEquals("XYZ456", addedProduct.getProductID());
        assertEquals("Shirt", addedProduct.getProductName());
        assertTrue(addedProduct instanceof Clothing);
        Clothing clothingProduct = (Clothing) addedProduct;
        assertEquals("Blue", clothingProduct.getColor());
        assertEquals(15, clothingProduct.getNoOfItems());
        assertEquals(29.99, clothingProduct.getPrice(), 0.001);
        assertEquals("L", clothingProduct.getSize());
    }


    @Test
    public void testAddMultipleProducts() {
        ArrayList<Product> productList = shoppingManager.getProductList();

        for (int i = 1; i <= 25; i++) {
            // Add 25 electronic products
            String simulatedInput = "1\n" +                 // Choose product type (1 for Electronics)
                    "Elec" + i + "\n" +     // Product ID
                    "Electronic" + i + "\n" + // Product name
                    "Brand" + i + "\n" +    // Brand
                    "10\n" +                // Available items
                    "99.99\n" +             // Price
                    "12\n";                 // Warranty period
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            // Create a new Scanner with the simulated input
            Scanner input = new Scanner(System.in);

            // Call the addNewProduct method
            shoppingManager.addNewProducts(input);
        }

        for (int i = 1; i <= 26; i++) {
            // Add 25 clothing products
            String simulatedInput = "2\n" +                 // Choose product type (2 for Clothing)
                    "Cloth" + i + "\n" +    // Product ID
                    "Clothing" + i + "\n" + // Product name
                    "Color" + i + "\n" +    // Color
                    "15\n" +                // Available items
                    "49.99\n" +             // Price
                    "M\n";                  // Size
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            // Create a new Scanner with the simulated input
            Scanner input = new Scanner(System.in);

            // Call the addNewProduct method
            shoppingManager.addNewProducts(input);
        }

        // Assert that the total number of products is 50
        assertEquals(50, productList.size());
    }


    @Test
    public void testDeleteProduct() {
        ArrayList<Product> productList = shoppingManager.getProductList();

        // Add a sample product
        String simulatedInput = "1\n" +                 // Choose product type (1 for Electronics)
                "Elec1\n" +              // Product ID
                "Electronic1\n" +        // Product name
                "Brand1\n" +             // Brand
                "10\n" +                 // Available items
                "99.99\n" +              // Price
                "12\n";                  // Warranty period
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Scanner input = new Scanner(System.in);
        shoppingManager.addNewProducts(input);

        // Verify that the product has been added
        assertEquals(1, productList.size());

        // Simulate user input to delete the product
        simulatedInput = "Elec1\n";  // Product ID to delete
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        input = new Scanner(System.in);
        shoppingManager.deleteNewProduct(input);

        // Verify that the product has been deleted
        assertEquals(0, productList.size());
    }


    @Test
    public void testDeleteNonExistentProduct() {
        ArrayList<Product> productList = shoppingManager.getProductList();

        // Verify that the product list is initially empty
        assertEquals(0, productList.size());

        // Simulate user input to delete a product that does not exist in the list
        String simulatedInput = "NonExistentProduct\n";  // Product ID that doesn't exist
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Scanner input = new Scanner(System.in);
        shoppingManager.deleteNewProduct(input);

        // Verify that the product list remains empty (no product added)
        assertEquals(0, productList.size());
    }










    @Test
    public void testInvalidMenuChoice() {
        String input = "7\n" +      // Invalid menu choice
                "6\n";       // Exit menu
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        shoppingManager.displayMenu();

        assertTrue(outContent.toString().contains("Invalid Choice. Please enter a valid option."));
    }

    @Test
    public void testExitMenu() {
        String input = "6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        shoppingManager.displayMenu();
        assertTrue(outContent.toString().contains("Exiting from the admin panel"));
    }

    @Test
    public void testPrintEmptyProductList() {
        String input = "3\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        shoppingManager.displayMenu();
        assertTrue(outContent.toString().contains("There aren't any products on the list."));
    }

    @Test
    public void testInvalidProductType() {
        String input = "1\n3\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        shoppingManager.displayMenu();
        assertTrue(outContent.toString().contains("Invalid product type"));
        assertEquals(0, shoppingManager.getProductList().size());
    }























}

