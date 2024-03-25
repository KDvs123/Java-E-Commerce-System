package org.example;

public class Clothing extends Product{
    private String size;
    private String color;

    /**
     * Constructor for the Clothing class.
     * @param productID - The unique ID of the clothing product.
     * @param productName - The name of the clothing product.
     * @param noOfItems - The number of items available.
     * @param price - The price of the clothing product.
     * @param size - The size of the clothing product.
     * @param color - The color of the clothing product.
     */

    public Clothing(String productID,String productName,int noOfItems,double price,String size,String color){
        super(productID,productName,noOfItems,price);
        this.size=size;
        this.color=color;
    }

    /**
     * Get the size of the clothing product.
     * @return The size of the clothing product.
     */
    public String getSize() {
        return size;
    }

    /**
     * Set the size of the clothing product.
     * @param size - The size to set for the clothing product.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Get the color of the clothing product.
     * @return The color of the clothing product.
     */

    public String getColor() {
        return color;
    }

    /**
     * Set the color of the clothing product.
     * @param color - The color to set for the clothing product.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Generate a string representation of the clothing product, including details.
     * @return A string representation of the clothing product.
     */

    @Override
    public String toString() {
        return

                "\nsize: " + size +" "+
                "\ncolor: " + color ;
    }

}
