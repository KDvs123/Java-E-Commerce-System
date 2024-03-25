# Java-E-Commerce-System

**Project Overview**
This repository contains the source code for a comprehensive e-commerce management system, specifically designed to cater to both electronics and clothing products. It follows a structured approach to object-oriented programming, utilizing Java for backend development and providing both console-based and graphical user interfaces for system interaction.

**Key Features**
Abstract Product Class: Serves as a superclass with subclasses for Electronics and Clothing, incorporating basic product information like ID, name, available quantity, and price.
User and ShoppingCart Classes: Manage user accounts and shopping cart operations, including product addition, removal, and total cost calculation.
WestminsterShoppingManager Class: Implements the ShoppingManager interface to maintain product listings and handle system management tasks through a console menu.
Graphical User Interface: Offers a user-friendly GUI for clients to view, sort, and purchase products. Includes features like dynamic product listing, shopping cart management, and application of discounts.
Persistence: Product data is saved to a file for retrieval in subsequent sessions, ensuring continuity of system state.
System Management via Console
The WestminsterShoppingManager class provides a console menu with options to add or delete products, display product listings, and save the current state to a file. It supports a maximum of 50 products, allowing for detailed management of electronics and clothing items.

**GUI Interaction**
Clients interact with the system through a GUI, where they can choose to view all products or filter by category (Electronics or Clothes), sort listings alphabetically, and manage their shopping cart. The GUI emphasizes user engagement with features such as highlighting low-stock items, displaying detailed product information, and enabling easy product addition to the shopping cart.

**Discounts and Special Offers**
The system encourages purchases through discount incentives, applying a 20% discount for buying at least three products of the same category and a 10% discount on the first purchase. The shopping cart displays the final price after discounts, enhancing the shopping experience.

**Testing and Validation**
Comprehensive automated testing ensures the reliability and robustness of the system, with a focus on error handling and input validation. The test plan and implementation aim to cover all main use cases and functionalities provided in the console menu.
