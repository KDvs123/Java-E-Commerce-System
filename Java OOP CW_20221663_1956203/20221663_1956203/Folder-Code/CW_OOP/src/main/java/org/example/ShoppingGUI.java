package org.example;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/*
*
references-https://youtu.be/Kmgo00avvEw?feature=shared
* java swing j table demo-https://youtu.be/S6evF1T_lrU?feature=shared
* j tables and j labels-https://youtube.com/playlist?list=PLFDH5bKmoNqxtOTzA4tjo-Exck6T2v7cG&feature=shared
* JAVA layout-https://youtube.com/playlist?list=PLjJmj2FyqToa6ktTILKN-nEXsqn2k8F6t&feature=shared


* */

public class ShoppingGUI extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JTable productTable,cartTable;
    private DefaultTableModel tableModel,cartTableModel;
    private WestminsterShoppingManager westminsterShoppingManager;

    private ArrayList<String[]> cartItems = new ArrayList<>();
    private String[] columnNames = {"Product ID", "Name", "Category", "Price($)", "Info", "Available"};
    private JLabel totalLabel, firstPurchaseDiscountLabel,sameCategoryDiscountLabel,finalTotalLabel;

    private JPanel cartPanel,productDetailsPanel;

    private JLabel lblProductId,lblCategory,lblName,lblSize,lblColor,lblAvailable,lblBrand,lblWarranty;

    private User currentUser;
    private ShoppingCart shoppingCart;

    private static  int selectedRowIndex = -1;

    /**
     * Constructor for the ShoppingGUI class.
     * @param westminsterShoppingManager - The WestminsterShoppingManager instance for managing products.
     * @param currentUser - The current user of the shopping interface.
     */
    public ShoppingGUI(WestminsterShoppingManager westminsterShoppingManager,User currentUser){
        this.westminsterShoppingManager=westminsterShoppingManager;
        this.shoppingCart=new ShoppingCart();
        this.currentUser=currentUser;
        setTitle("Webstore Shopping Centre");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        // Label for the category dropdown
        JLabel categoryLabel = new JLabel("Select Product Category");
//        gbc.gridx = 1; // Place it to the left of the dropdown
        gbc.gridy = 1;
        gbc.weightx = 1; // This ensures that the label does not take extra horizontal space
        gbc.insets = new Insets(0, 15, 40, 0); // Top, left, bottom, right padding (smaller right padding for proximity to the dropdown)
        mainPanel.add(categoryLabel, gbc);


        // Dropdown for product category
        // Dropdown for product category
        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        gbc.gridx = 1;
        gbc.gridy = 1; // Place the dropdown in the second row
        gbc.weightx = 0.0; // No extra space horizontally
        gbc.anchor = GridBagConstraints.CENTER; // Align to the start of the line (left side)
        gbc.insets = new Insets(0, 0, 45, 10); // Top, left, bottom, right padding (no left padding)
        Dimension preferredSize = categoryComboBox.getPreferredSize();
        preferredSize.width += 80; // Adjust the width as needed
        categoryComboBox.setPreferredSize(preferredSize);
        mainPanel.add(categoryComboBox, gbc);
        categoryComboBox.addActionListener(e -> updateTableData((String) categoryComboBox.getSelectedItem()));

        // Shopping Cart button
        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.setMargin(new Insets(5, 15, 5, 15));
        gbc.gridx = 2; // Third column
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Takes only one column
        gbc.weightx = 1.0; // Give it all extra horizontal space
        gbc.anchor = GridBagConstraints.LINE_END; // Align to the end of the line (right side)
        gbc.insets = new Insets(10, 10, 10, 10); // Top, left, bottom, right padding
        shoppingCartButton.addActionListener(this::showShoppingCart);
        mainPanel.add(shoppingCartButton, gbc);

        // Add main panel to frame
        add(mainPanel, BorderLayout.NORTH);
        // Table setup
        setupProductTable();

        // Product details setup
        setupProductDetailsPanel();

        // Shopping Cart details setup
        setupShoppingCartPanel();

        // Populate the table with initial data
        updateTableData("All");

    }


    /**
     * Update the product table based on the selected category.
     * @param category - The selected category ("All", "Electronics", "Clothing").
     */


    private void updateTableData(String category) {
        ArrayList<Product> productList=westminsterShoppingManager.getProductList();

        tableModel.setRowCount(0);

        for (Product product : productList) {
            String productCategory = (product instanceof Electronics) ? "Electronics" : "Clothing";
            // Add new data based on category
            if (category.equals("All") || productCategory.equals(category)) {
                Object[] row = {product.getProductID(), product.getProductName(), productCategory, product.getPrice(), product.toString(), product.getNoOfItems()};
                tableModel.addRow(row);
            }

        }
    }

    /**
     * Set up the shopping cart table with its initial settings.
     */

    private void setupCartTable() {
        // Create a new DefaultTableModel with non-editable cells
        cartTableModel = new DefaultTableModel(new Object[]{"Product", "Quantity", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all the cells in the table non-editable
                return false;
            }
        };

        cartTable = new JTable(cartTableModel);
        cartTable.setFillsViewportHeight(true);


        // Adjust row height
        cartTable.setRowHeight(60);

        // Add padding to the JScrollPane containing the cart table
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        cartPanel.add(cartScrollPane, BorderLayout.CENTER); // Adding the JScrollPane with padding to the cart panel
    }

    /**
     * Set up the product table with its initial settings.
     */

    public void setupProductTable() {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        productTable = new JTable(tableModel);
        TableCellRenderer headerRenderer = productTable.getTableHeader().getDefaultRenderer();

        // Create a custom header renderer
        TableCellRenderer wrapper = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = headerRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (component instanceof JLabel) {
                    ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
                    ((JLabel) component).setBorder(BorderFactory.createCompoundBorder(
                            ((JLabel) component).getBorder(),
                            BorderFactory.createEmptyBorder(5, 10, 5, 10) // Top, left, bottom, right padding
                    ));
                }
                return component;
            }
        };
        // Set the custom renderer to the table header
        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setDefaultRenderer(wrapper);
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));

        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRowIndex = productTable.getSelectedRow();
                productTable.repaint(); // To refresh the table display
                displayProductDetails();
            }
        });


        productTable.setFillsViewportHeight(true);
        productTable.setBackground(new Color(255, 255, 255)); // White background
        productTable.setForeground(new Color(50, 50, 50)); // Dark text
        productTable.setGridColor(new Color(200, 200, 200)); // Light gray grid
        productTable.setSelectionBackground(new Color(180, 220, 240)); // Light blue selection
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setRowHeight(50);



        // Custom cell renderer
        productTable.setDefaultRenderer(Object.class, new TableCellRendererWithRedColor());

        // Create a main panel to hold both the table and product details
        JPanel mainPanel = new JPanel(new BorderLayout());

        // After setting the custom renderers and other properties, adjust the column width
        TableColumnModel columnModel = productTable.getColumnModel();
        TableColumn infoColumn = columnModel.getColumn(columnModel.getColumnIndex("Info"));
        infoColumn.setPreferredWidth(400); // Adjust the width as needed

        int availableColumnIndex = productTable.getColumnModel().getColumnIndex("Available");
        TableColumn availableColumn = productTable.getColumnModel().getColumn(availableColumnIndex);
        productTable.getColumnModel().removeColumn(availableColumn);

        // Add the table to a scroll pane and then to the main panel
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(scrollPane, BorderLayout.CENTER);



        // Finally, add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER); // This replaces the previous add(scrollPane, BorderLayout.CENTER);

        productTable.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                int column=productTable.columnAtPoint(e.getPoint());
                if (column == 0) { // Checks if the clicked column is the product ID column
                    sortTableByName(); // Sort the table by product name
                }
            }
        });


    }


    /**
     * Set up the product details panel with labels for displaying product information.
     */
    private void setupProductDetailsPanel() {
        // Main panel for product details with BorderLayout
        productDetailsPanel = new JPanel(new BorderLayout());



        JPanel labelsPanel = new JPanel(new GridLayout(0, 1)); // 0 rows mean any number of rows, 1 column
        JLabel lblTitle=new JLabel("Selected Product - Details");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(new Color(30, 30, 30)); // Dark text color
        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 0
        ));

        productDetailsPanel.add(lblTitle, BorderLayout.NORTH);

        // Initialize the labels for product details
        lblProductId = new JLabel("Product ID: ");
        lblCategory = new JLabel("Category: ");
        lblName = new JLabel("Name: ");
        lblSize = new JLabel("Size: ");
        lblColor = new JLabel("Color: ");
        lblAvailable = new JLabel("Items Available: ");
        lblBrand = new JLabel("Brand: ");
        lblWarranty = new JLabel("Warranty: ");

        Font labelFont = new Font("Arial", Font.PLAIN, 14
        );

        // Style the labels uniformly
        JLabel[] labels = {lblProductId, lblCategory, lblName, lblAvailable, lblBrand, lblWarranty,lblSize,lblColor};
        for (JLabel label : labels) {
            label.setForeground(new Color(30, 30, 30)); // Dark text color
            labelsPanel.add(label); // Add labels to the panel
            label.setFont(labelFont); // Set the new font
            label.setBorder(BorderFactory.createEmptyBorder(5, 12, 15, 0)); // Add padding to increase line spacing
        }

        // Style the panel

        labelsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // Padding around labels panel



        // Add the labels panel to the main details panel at the center
        productDetailsPanel.add(labelsPanel, BorderLayout.CENTER);

        // Panel for the "Add to Shopping Cart" button with FlowLayout for center alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAddToCart = new JButton("Add to Shopping Cart");
        btnAddToCart.setMargin(new Insets(5, 15, 5, 15));
        btnAddToCart.addActionListener(this::addToCart);
        buttonPanel.add(btnAddToCart);


        // Add the button panel to the main details panel at the bottom
        productDetailsPanel.add(buttonPanel, BorderLayout.SOUTH);


        // Add the complete details panel to the main frame
        add(productDetailsPanel, BorderLayout.SOUTH);
    }




    /**
     * Set up the shopping cart panel with the cart table and labels for displaying totals and discounts.
     */

    private void setupShoppingCartPanel() {


        // Initialize the labels for displaying totals and discounts
        totalLabel = new JLabel("Total: 0.00 €");
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): -0.00 €");
        sameCategoryDiscountLabel = new JLabel("Three Items in Same Category Discount (20%): -0.00 €");
        finalTotalLabel = new JLabel("Final Total: 0.00 €");


        Font labelFont = new Font("Arial", Font.PLAIN, 14); // Change font size as needed
        Font boldLabelFont = new Font("Arial", Font.BOLD, 14);

        // Set the font for labels and increase line spacing by adding empty border
        totalLabel.setFont(labelFont);
        firstPurchaseDiscountLabel.setFont(labelFont);
        sameCategoryDiscountLabel.setFont(labelFont);
        finalTotalLabel.setFont(boldLabelFont); // Set final total label to bold

        // Style the labels
        JLabel[] labels = {totalLabel, firstPurchaseDiscountLabel, sameCategoryDiscountLabel, finalTotalLabel};
        for (JLabel label : labels) {
            label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Top, left, bottom, right padding
            label.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        // Set up the shopping cart panel
        cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        cartPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(4, 2,5,5));

        // Create and add empty JLabels for spacing between lines
        JLabel spacingLabel1 = new JLabel(" ");
        JLabel spacingLabel2 = new JLabel(" ");
        JLabel spacingLabel3 = new JLabel(" ");

        // Add the labels and spacing JLabels to the labelsPanel
        labelsPanel.add(totalLabel);
        labelsPanel.add(spacingLabel1); // Add spacing between lines
        labelsPanel.add(firstPurchaseDiscountLabel);
        labelsPanel.add(spacingLabel2);
        labelsPanel.add(sameCategoryDiscountLabel);
        labelsPanel.add(spacingLabel3);
        labelsPanel.add(finalTotalLabel);

        cartPanel.add(labelsPanel, BorderLayout.EAST);

        cartPanel.revalidate();
        cartPanel.repaint();
        setupCartTable();


    }


    /**
     * Handle the "Add to Shopping Cart" button click event.
     * @param event - The ActionEvent object representing the button click event.
     */

    private void addToCart(ActionEvent event) {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String productId = tableModel.getValueAt(selectedRow, 0).toString();
            String productName = tableModel.getValueAt(selectedRow, 1).toString();
            double price = Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString());
            int available = Integer.parseInt(tableModel.getValueAt(selectedRow, 5).toString());
            String info=tableModel.getValueAt(selectedRow, 4).toString();
            String category = tableModel.getValueAt(selectedRow, 2).toString();

            if (available > 0) {
                available--; // Decrease the available quantity
                tableModel.setValueAt(available, selectedRow, 5);
                westminsterShoppingManager.updateProductQuantity(productId, available);


                String cartProductName = "<html>" + productId + "<br/>"+ productName + "<br/>" + info + "</html>";

                // Use the addToCart method of ShoppingCart
                shoppingCart.addToCart(productId, cartProductName, price, 1,category,currentUser);
                // Confirm the purchase to apply any necessary discounts

                updateCartTableModel(productId,1);
                updateCartDisplay();
                displayProductDetails();
                JOptionPane.showMessageDialog(this, "Item added to cart!");
            } else {
                JOptionPane.showMessageDialog(this, "This item is no longer available.", "Stock Issue", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to add to the cart!");
        }

    }

    /**
     * Update the display of the shopping cart with the current cart items and totals.
     */

    private void updateCartDisplay() {
        // Clear existing cart data
        cartTableModel.setRowCount(0);
        double price;
        double totalPrice = 0;
        double quantity=0;

        // Add items from ShoppingCart to the cartTableModel
        for (String[] item : shoppingCart.getCartItems()) {
            String productName = item[1];
            price = Double.parseDouble(item[3]);
            quantity = Double.parseDouble(item[4]);
            double itemTotal = price * quantity;
            totalPrice += itemTotal; // Accumulate the item total into the total price
            cartTableModel.addRow(new Object[]{productName, quantity, String.format("%.2f", itemTotal)});
        }

        // Assuming getPurchaseDiscount returns the total discount amount for the first purchase
        double firstPurchaseDiscount = shoppingCart.getPurchaseDiscount();
        // Assuming getDiscount returns the total discount amount for having three items in the same category
        double categoryDiscount = shoppingCart.getDiscount();
        // Calculate the final total after applying discounts
        double finalTotal = totalPrice - firstPurchaseDiscount - categoryDiscount;

        // Update labels for total, discounts, and final total
        totalLabel.setText(String.format("Total:      %.2f €", totalPrice));
        firstPurchaseDiscountLabel.setText(String.format("First Purchase Discount (10%%):      -%.2f €", firstPurchaseDiscount));
        sameCategoryDiscountLabel.setText(String.format("Three Items in Same Category Discount (20%%):      -%.2f €", categoryDiscount));
        finalTotalLabel.setText(String.format("Final Total:      %.2f €", finalTotal));

        cartPanel.revalidate();
        cartPanel.repaint();
    }


    /**
     * Display product details for the selected product in the product details panel.
     */

    private void displayProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            String productId = tableModel.getValueAt(selectedRow, 0).toString();
            String category = tableModel.getValueAt(selectedRow, 2).toString();
            String name = tableModel.getValueAt(selectedRow, 1).toString();
            String available = tableModel.getValueAt(selectedRow, 5).toString();


            // Update labels with consistent formatting
            lblProductId.setText(String.format("Product ID: %s", productId));
            lblCategory.setText(String.format("Category: %s", category));
            lblName.setText(String.format("Name: %s", name));
            lblAvailable.setText(String.format("Items Available: %s", available));

            // Initialize all details as blank
            lblBrand.setText("Brand: N/A");
            lblWarranty.setText("Warranty: N/A ");
            lblSize.setText("Size: N/A");
            lblColor.setText("Color: N/A");

            // Fetch the product details based on the ID
            Product product = westminsterShoppingManager.getProductById(productId);

            if ("Electronics".equals(category) && product instanceof Electronics) {
                Electronics electronic = (Electronics) product;
                lblBrand.setText(String.format("Brand: %s", electronic.getBrand()));
                lblWarranty.setText(String.format("Warranty: %s years", electronic.getWarrentyPeriod()));
            } else if ("Clothing".equals(category) && product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                lblSize.setText(String.format("Size: %s", clothing.getSize()));
                lblColor.setText(String.format("Color: %s", clothing.getColor()));
            }

            productDetailsPanel.revalidate();
            productDetailsPanel.repaint();
        }
    }



    /**
     * Update the cart table model with the new quantity for a product.
     * @param productId - The ID of the product to update.
     * @param newQuantity - The new quantity of the product.
     */

    private void updateCartTableModel(String productId, int newQuantity) {
        // Iterate through the cartTableModel to find the product
        boolean found = false;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            String cartProductId = (String) cartTableModel.getValueAt(i, 0);
            if (cartProductId.equals(productId)) {
                // Product is found, update the quantity and price
                double price = Double.parseDouble(cartTableModel.getValueAt(i, 2).toString());
                cartTableModel.setValueAt(newQuantity, i, 1); // Update quantity
                cartTableModel.setValueAt(String.format("%.2f", price * newQuantity), i, 2); // Update price
                found = true;
                break;
            }
        }
        if (!found) {
            // If the product is not found in the cartTableModel, add a new row
            for (String[] item : cartItems) {
                if (item[0].equals(productId)) {
                    double price = Double.parseDouble(item[3]);
                    // Add a new row to the cart table with the product details
                    cartTableModel.addRow(new Object[]{item[1], newQuantity, String.format("%.2f", price)});
                    break;
                }
            }
        }


    }



    /**
     * Show the shopping cart dialog with the cart contents and totals.
     * @param event - The ActionEvent object representing the button click event.
     */

    private void showShoppingCart(ActionEvent event) {

        // Calculate the totals and update the cart display
        updateCartDisplay();

        // Create a panel to hold the shopping cart table and totals
        JPanel shoppingCartPanel = new JPanel(new BorderLayout());
        JTable shoppingCartTable = new JTable(cartTableModel);
        shoppingCartTable.setFillsViewportHeight(true);


        // Adjust row height
        shoppingCartTable.setRowHeight(80);

        JScrollPane scrollPane = new JScrollPane(shoppingCartTable);
        shoppingCartPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel to hold the total, discount, and final total
        JPanel totalsPanel = new JPanel(new GridLayout(4, 1));
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 40));

        totalsPanel.add(totalLabel);
        totalsPanel.add(firstPurchaseDiscountLabel);
        totalsPanel.add(sameCategoryDiscountLabel);
        totalsPanel.add(finalTotalLabel);
        shoppingCartPanel.add(totalsPanel, BorderLayout.SOUTH);


        // Create and display the dialog
        JDialog shoppingCartDialog = new JDialog(this, "Shopping Cart", true);
        shoppingCartDialog.setContentPane(shoppingCartPanel);
        shoppingCartDialog.pack();
        shoppingCartDialog.setLocationRelativeTo(this);
        shoppingCartDialog.setVisible(true);
    }

    /**
     * Custom TableCellRenderer to color items with reduced availability.
     */
    private static class TableCellRendererWithRedColor extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check if the row is selected
            if (isSelected) {
                c.setBackground(Color.YELLOW); // Highlight the selected row in yellow
            } else {
                // Get the availability value from the 6th column (index 5)
                int available = Integer.parseInt(table.getModel().getValueAt(row, 5).toString());

                // Check if the availability is less than 3
                if (available < 3) {
                    c.setForeground(Color.WHITE); // Text color white for better visibility
                    c.setBackground(Color.RED); // Entire row background color red
                } else {
                    c.setForeground(Color.BLACK); // Text color black
                    c.setBackground(Color.WHITE); // Background color white
                }
            }
            return c;
        }
    }

    /**
     * Sort the product table by product name.
     */

    private void sortTableByName() {
        // Get the current rows data
        ArrayList<Object[]> rowData = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object[] row = new Object[columnNames.length];
            for (int j = 0; j < columnNames.length; j++) {
                row[j] = tableModel.getValueAt(i, j);
            }
            rowData.add(row);
        }

        // Sort the data based on the product name (column 1)
        Collections.sort(rowData, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] row1, Object[] row2) {
                String productName1 = row1[0].toString();
                String productName2 = row2[0].toString();
                return productName1.compareTo(productName2);
            }
        });

        // Clear the table model
        tableModel.setRowCount(0);

        // Add the sorted data back to the table model
        for (Object[] row : rowData) {
            tableModel.addRow(row);
        }
    }





}

