package org.example;

public class Electronics extends  Product{
    private String brand;
    private int warrentyPeriod;

    /**
     * Constructor for the Electronics class.
     * @param productID - The unique ID of the electronic product.
     * @param productName - The name of the electronic product.
     * @param noOfItems - The number of items available.
     * @param price - The price of the electronic product.
     * @param brand - The brand of the electronic product.
     * @param warrentyPeriod - The warranty period (in years) of the electronic product.
     */

    public Electronics(String productID,String productName,int noOfItems,double price,String brand,int warrentyPeriod){
        super(productID,productName,noOfItems,price);
        this.brand=brand;
        this.warrentyPeriod=warrentyPeriod;
    }


    /**
     * Get the brand of the electronic product.
     * @return The brand of the electronic product.
     */


    public String getBrand() {
        return brand;
    }

    /**
     * Set the brand of the electronic product.
     * @param brand - The brand to set for the electronic product.
     */

    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get the warranty period (in years) of the electronic product.
     * @return The warranty period of the electronic product.
     */

    public int getWarrentyPeriod() {
        return warrentyPeriod;
    }


    /**
     * Set the warranty period (in years) of the electronic product.
     * @param warrentyPeriod - The warranty period to set for the electronic product.
     */

    public void setWarrentyPeriod(int warrentyPeriod) {
        this.warrentyPeriod = warrentyPeriod;
    }


    /**
     * Generate a string representation of the electronic product, including details.
     * @return A string representation of the electronic product.
     */

    @Override
    public String toString() {
        return

                "\nbrand: " + brand +" "+
                "\nWarrenty Period: " + warrentyPeriod+" weeks"
                ;
    }
}
