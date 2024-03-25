package org.example;

import java.util.Scanner;

public interface ShoppingManager {
    public void addNewProducts(Scanner input);
    public void deleteNewProduct(Scanner input);
    public  void printProductList();
    public void saveProductList();
    public void loadFromFile();
}
