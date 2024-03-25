package org.example;

public abstract class Product {

    private String productID;
    private String productName;
    private int noOfItems;
    private double price;
    private String category;

    /**
     * Constructor for creating a Product object.
     * @param productID - The unique identifier for the product.
     * @param productName - The name of the product.
     * @param noOfItems - The quantity of items available.
     * @param price - The price of the product.
     */
    public Product( String productID,String productName,int noOfItems,double price){
        this.productID=productID;
        this.productName=productName;
        this.noOfItems=noOfItems;
        this.price=price;


    }

    /**
     * Get the product's unique identifier.
     * @return The product's unique identifier.
     */

    public String getProductID() {
        return productID;
    }




    /**
     * Get the name of the product.
     * @return The name of the product.
     */
    public String getProductName() {
        return productName;
    }


    /**
     * Get the quantity of items available for this product.
     * @return The quantity of items available.
     */
    public int getNoOfItems() {
        return noOfItems;
    }


    /**
     * Get the price of the product.
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the unique identifier for the product.
     * @param productID - The new unique identifier for the product.
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * Set the name of the product.
     * @param productName - The new name for the product.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Set the quantity of items available for this product.
     * @param noOfItems - The new quantity of items available.
     */
    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    /**
     * Set the price of the product.
     * @param price - The new price for the product.
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the category of the product.
     * @return The category of the product.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category of the product.
     * @param category - The new category for the product.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
