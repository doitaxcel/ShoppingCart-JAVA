/**
 * 
 * @author Axcel Guevarra
 * 
 */

// PACKAGES THAT WE NEED TO CREATE A FRAME, BUTTONS, FUNCTIONALITY ETC.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;     // LIST || ARRAYLIST
import java.util.List;  // imports specific List so the IDE won't be confused since theres also a java.awt.List;

public class ShopUI extends JFrame{
    
    // GIVEN PRODUCTS   
    private String[] productsArray = {"CPU - $220", "GPU - $650", "RAM - $40", "MOTHERBOARD - $100", "PSU - $40", "CASE - $40", "FANS - $8", "MONITOR - $320", "MOUSE - $110"};
    private int[] priceArray = {220, 650, 40, 100, 40, 40, 8, 320, 110};
    
    // LIST FOR CAR 
    private List<String> cartList = new ArrayList<>();      // STORES THE products ADDED TO CART

    // LIST FOR AVAILABLE PRODUCTS
    private JList<String> availableProductsList;     // STORES products

    private List<String> availableVouchers = Arrays.asList("AXCELPOGI", "AXLLOVERBOY", "AXCELSUAVE");       // ARRAY for Voucher Codes (this will be used to 
                                                                                                                //check if the voucher entered is Valid) // code shows in applyCodeButton Event
                                                                                                                // Arrays.asList is library that is used when creating an array with fixed values

    // TOTAL PRICE INSTANTIATE ( FOR PRICE UPDATE )
    int totalPrice = 0;

    // UI Attributes
    private JButton addButton, removeButton, checkoutButton;    // GIVEN METHODS (addButton & removeButton modified (CHAL: 1 & 2))
    private JButton clearAllProductButton, cartSummaryButton;      // ADDED METHODS (CHAL: 3 & 4)
    private JButton applyVoucherButton;     // CHALLENGE: 5 
    private JTextField voucherTextField;
    private JButton cancelVoucherButton;       // added challenge in Challenge: 5 (kaartehan lang)
    private JLabel totalLabel;  // for Total Price text     
    private JTextArea textArea;     // this is the ProductList AREA (where ITEMS ARE SHOWN)
    private JSpinner productSpinner;    // FOR QUANTITY    (CHALLENGE: 1)

    public ShopUI(){
        setTitle("Shopping Cart");      // TITLE OF FRAME
        setSize(600, 400);      // Size of Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // HIDES THE FRAME IF THE USER CLOSES IT
        setLocationRelativeTo(null);        // WHERE THE FRAME INITIALLY POP UP
        //setResizable(false);

        // Cart Attributes
        ElementsUIStart();

        // Functions
        addListeners();

        // FRAME VISIBILITY
        setVisible(true);

    }

    private void ElementsUIStart(){

        // FOR ELEMENT POSITION || LAYOUT in UI
        setLayout(new BorderLayout());

        // {
        // P A N E L S //
        // PANEL THAT HOLDS THE PRODUCTS, LIKE IN SHOPEE WHAT WE HAVE A POSITION WHERE WE CAN SEE THE PRODUCTS
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout()); 
        productPanel.setBorder(BorderFactory.createTitledBorder("Products"));    // PANEL TITLE
        
        // PANEL FOR RIGHT SIDE BUTTONS
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // sets BoxLayout Y-Axis (Vertical)
        
        // PANEL FOR UPPER RIGHT (HOLD JSpinner and Add to Cart)
        JPanel upSidePanel = new JPanel();  // this just inherits the sidePanel Layout
                                            // because this is just a div inside sidePanel
                                            // (upSidePanel is added to sidePanel) sidePanel.add(upSidePanel)

        JPanel midSidePanel = new JPanel(); // HOLDS REMOVE, CLEAR, SUMMARY BUTTONS
                                            // PLACES JUST RIGHT BELOW THE upSidePanel
        midSidePanel.setLayout(new BoxLayout(midSidePanel, BoxLayout.Y_AXIS));
        
        // BUTTOM PANEL THAT HOLDS TOTAL PRICE AND CHECKOUT
        JPanel buttomPanel = new JPanel();
        buttomPanel.setLayout(new BorderLayout());
        
        JLabel blank1 = new JLabel(" ");
        JLabel blank2 = new JLabel(" ");
        JLabel blank3 = new JLabel(" ");
        
        // Product List (MIDDLE)
        availableProductsList = new JList<>(productsArray);  // CONNECTS availableProducts to products array using JList
        JScrollPane availableProductScroll = new JScrollPane(availableProductsList);  // MAKES THE availableProduct section         scrollable
        productPanel.add(availableProductScroll, BorderLayout.CENTER); // set Location to Center

        // Text Area (THIS IS THE CART DIV) (LEFT)
        textArea = new JTextArea();     // THIS IS THE CART (LEFT POSITION)
        JScrollPane textAreaScroll = new JScrollPane(textArea); // MAKES THE textArea section scrollable (this is the cart)
        textArea.setBorder(BorderFactory.createTitledBorder("Cart"));    // PANEL TITLE
        textArea.setEditable(false);        // MAKES the Cart read-only so we can't edit or input text to this area
        textArea.setVisible(false);

        // Value of Buttons
        // Default
        addButton = new JButton("Add to Cart");
        removeButton = new JButton("Remove From Cart");
        checkoutButton = new JButton("Checkout");
        // Addition Method
        cartSummaryButton = new JButton("View Cart Summary");
        clearAllProductButton = new JButton("Clear Cart");
        

        // JSpinner DECLARATION
        productSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        JPanel spinnerPanel = new JPanel();
        spinnerPanel.add(new JLabel("Quantity:"));
        spinnerPanel.add(productSpinner);

        // TOTAL PRICE
        totalLabel = new JLabel("Total Price is: $0");  // default price label
        totalLabel.setHorizontalAlignment(JLabel.CENTER);

        // DISCOUNT SECTION
        JPanel discountPanel = new JPanel();
        discountPanel.setLayout(new BoxLayout(discountPanel, BoxLayout.Y_AXIS));
        
        JPanel voucherInputPanel = new JPanel();
        voucherInputPanel.setPreferredSize(new Dimension(100,100));
        
        JPanel discountButtons = new JPanel();
        discountButtons.setLayout(new BoxLayout(discountButtons, BoxLayout.X_AXIS));

        //JLABEL
        JLabel codLabel = new JLabel("Voucher Codes:");
        // codLabel.setLayout(new Dimension(codLabel)); /DRAFT - SETS THE SIZE OF VOUCHER INPUT AREA
        JLabel code1 = new JLabel("·AXCELPOGI");
        JLabel code2 = new JLabel("·AXLLOVERBOY");
        JLabel code3 = new JLabel("·AXCELSUAVE");

        cancelVoucherButton = new JButton("CANCEL VOUCHER");
        cancelVoucherButton.setVisible(false);

        // VOUCHER CODE INPUT SECTION USING JTEXTFIELD
        voucherTextField = new JTextField("Input Voucher");
        //voucherTextField.setPreferredSize(new Dimension(50,30));
        voucherTextField.setMaximumSize(new Dimension(200,200));

        // VOUCHER BUTTON
        applyVoucherButton = new JButton("Apply Discount");

        // LABELS ADD TO sidePanel
        discountPanel.add(codLabel);
        discountPanel.add(code1);
        discountPanel.add(code2);
        discountPanel.add(code3);

        // TEXT FIELD AND BUTTON TO buttomPanel
        //voucherInputPanel.add(voucherTextField);
        discountButtons.add(applyVoucherButton);
        discountButtons.add(cancelVoucherButton);
        //discountPanel.add(voucherInputPanel);
        discountPanel.add(voucherTextField);

        // ADD TO CART BUTTON and SPINNER
        upSidePanel.add(addButton);
        upSidePanel.add(spinnerPanel);

        // MIDDLE SIDE PANEL
        midSidePanel.add(removeButton);
        midSidePanel.add(blank1);
        midSidePanel.add(clearAllProductButton);
        midSidePanel.add(blank2);
        midSidePanel.add(cartSummaryButton);
        midSidePanel.add(blank3);

        // ADDING BUTTONS TO RIGHT
        sidePanel.add(upSidePanel, BorderLayout.CENTER);     
        sidePanel.add(midSidePanel, BorderLayout.CENTER);
        sidePanel.add(discountPanel, BorderLayout.CENTER);
        sidePanel.add(discountButtons);
 
        // ADDING BUTTONS TO BUTTOM
        buttomPanel.add(totalLabel, BorderLayout.CENTER);
        buttomPanel.add(checkoutButton, BorderLayout.SOUTH);

        // MAIN PANEL OR CONTAINER FOR LAYOUT
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // PANELS ARRANGEMENT
        mainPanel.add(sidePanel, BorderLayout.EAST);    // Buttons || FUNCTIONS
        mainPanel.add(productPanel, BorderLayout.CENTER);    // SHOP || PRODUCTS
        mainPanel.add(textAreaScroll, BorderLayout.WEST);   // CART

        add(mainPanel, BorderLayout.CENTER);        // MAIN CONTAINER
        add(buttomPanel,BorderLayout.SOUTH);        // SOUTH CONTAINER (BUTTOM)
        
    }

    private void addListeners(){
        // add items in cart
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int selectIndex = availableProductsList.getSelectedIndex();     // selectIndex is equals   
                                                                                // to the available 
                                                                                // product index chosen
                if (selectIndex != 1){
                    textArea.setVisible(true);
                    int quantity = (int) productSpinner.getValue();     // stores the value of spinner // spinner is casted to int to that its return value is a number not an object
                    String selectProduct = productsArray[selectIndex];
                    cartList.add(selectProduct + " x " + quantity);     // for car printing (product x quantity)
                    totalPrice += priceArray[selectIndex] * quantity;      // multiplies the selected item to quantity value then store to totalPrice
                    updateCartDisplay();

                    productSpinner.setValue(1);
                } 
                else{
                    JOptionPane.showMessageDialog(null, "Please select an Item", "INFO", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //remove items from the cart
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String selectedItem = textArea.getSelectedText();
                if (selectedItem != null){           // if there's a chosen product, the will continue if non there will be a message that will pop (else..statement)
                    int select = cartList.indexOf(selectedItem);  // gets the index of chosen product from the cart
                    if (select != -1){
                        
                        // NEED TO SEPARATE THE QUANTITY FROM PRODUCT NAME
                        String[] splitArray =  selectedItem.split(" x ");        // SPLITS NAME and QUANTITY (e.g: Monitor x 1) then stores it in the var(split)
                        // it will create an array the looks splitArray = {"Monitor", "1"}   /1st element is (Monitor)[0] and 2nd (1)[1]
                        String parts = splitArray[0];           // Access the splitArray index
                        
                        int value = Integer.parseInt(splitArray[1]);    // accesses the price Index in splitArray
                                                                        // All things from the cart is an integer
                                                                        // so Integer class is used to deal with numbers not string
                                                                        // parseInt is used to get String representing a number and then make it an integer. 
                        int quantity = (int) productSpinner.getValue();

                        if (quantity == value){
                            
                            int selected = Arrays.asList(productsArray).indexOf(parts);     
                            int currentPrice = totalPrice;

                            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this product?", "REMOVE", JOptionPane.YES_NO_OPTION);

                            if (confirmation == JOptionPane.YES_OPTION){
                                if (selected != -1){   
                                cartList.remove(select);        
                                totalPrice -= priceArray[selected] * (quantity);   // substracts the selected pproduct price removed in the cart
                                productSpinner.setValue(1);  // resets the quantity to 1 after removing 1 selected item
                                if (cartList.isEmpty()){
                                    textArea.setVisible(false);
                                }
                            }
                            System.out.println(currentPrice + " - (" + priceArray[selected] + " * " + quantity +") = " + totalPrice);
                            updateCartDisplay();

                            }
                        }

                        else {
                            JOptionPane.showMessageDialog(null, "Please set the quantity counter depending on the cart", "REMOVE", JOptionPane.WARNING_MESSAGE);
                        }    
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please select an item to remove", "INFO", JOptionPane.ERROR_MESSAGE);
                }
            }

        });


        // Empty's the Cart
        clearAllProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!cartList.isEmpty()){
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the Cart? ", "CONFIRM", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION){
                        cartList.clear();   // .clear() is a method that clears all stored elements in the ArrayList<>();   
                        totalPrice = 0;        // resets the total price to 0 after clearing the cart
                        updateCartDisplay(); 
                        productSpinner.setValue(1); // resets the value of JSpinner to its min value   
                        textArea.setVisible(false);
                    } 
                }
                else{
                    JOptionPane.showMessageDialog(null, "Cart is Empty", "INFO", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });

        // RETURNS THE CART SUMMARY BY SHOWING ALL ADDED TO CART PRODUCTS
        cartSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cartList.isEmpty()) {
                    // Create a new JTextArea to avoid affecting the original
                    JTextArea summaryTextArea = new JTextArea();        // JTextArea to hold that holds all info for Summary
                    summaryTextArea.setText(textArea.getText());        // gets the values of textArea
                    summaryTextArea.setEditable(false);             // Makes the section read-only
                    JScrollPane summaryScroll = new JScrollPane(summaryTextArea);   // makes the textArea scrollable (if needed)
        
                    JPanel summaryPanel = new JPanel();         // a div or place that will hold all created elements (like the TextArea ⬆ and Label ⬇)
                    summaryPanel.setLayout(new BorderLayout()); 
        
                    JLabel summaryLabel = new JLabel("Cart Summary:");  // LABEL / TEXT
                    
                    // ELEMENTS ADDED TO PANEL AND ITS POSITION
                    summaryPanel.add(summaryLabel, BorderLayout.NORTH);     
                    summaryPanel.add(summaryScroll, BorderLayout.CENTER);   // LINE 307

                    JPanel pricePanel = new JPanel();
                    //pricePanel.setLayout(new BorderLayout());

                    JLabel priceLabel = new JLabel("Total Price is: $"+ totalPrice);
                    pricePanel.add(priceLabel);
                    
                    summaryPanel.add(pricePanel, BorderLayout.SOUTH);
                    updateCartDisplay();
        
                    JOptionPane.showMessageDialog(null, summaryPanel, "SUMMARY", JOptionPane.INFORMATION_MESSAGE);

                } 
                else {
                    JOptionPane.showMessageDialog(null, "CART IS EMPTY", "INFO", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // RETURNS THE CART SUMMARY AND PROCEEDING to CHECKOUT, ONCE CONFIRM, APP WILL TERMINATE
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!cartList.isEmpty()){
                    int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to proceed checkout?", "CHECKOUT", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION){

                        JTextArea checkoutTextArea = new JTextArea();
                        checkoutTextArea.setText(textArea.getText());
                        checkoutTextArea.setEditable(false);
                        JScrollPane checkoutScroll = new JScrollPane(checkoutTextArea);

                        JPanel checkoutPanel = new JPanel();
                        checkoutPanel.setLayout(new BorderLayout());

                        JLabel checkoutLabel = new JLabel("Checkout: ");

                        checkoutPanel.add(checkoutLabel, BorderLayout.NORTH);
                        checkoutPanel.add(checkoutScroll, BorderLayout.CENTER);

                        JOptionPane.showMessageDialog(null, checkoutPanel, "CHECKOUT", JOptionPane.INFORMATION_MESSAGE);
                        
                        JPanel checkoutDone = new JPanel();
                        checkoutDone.setLayout(new BorderLayout());

                        JLabel done = new JLabel("CHECKOUT:");
                        checkoutDone.add(done);
                        totalLabel.setText("AMOUNT TO PAY IS: $" + totalPrice);
                        checkoutDone.add(totalLabel);

                        JOptionPane.showMessageDialog(null, checkoutDone, "CHECKOUT", JOptionPane.INFORMATION_MESSAGE);

                        System.exit(0);     // CLOSES THE WINDOW
                    } 
                } 
                else {
                    JOptionPane.showMessageDialog(null, "CART IS EMPTY", "INFO", JOptionPane.ERROR_MESSAGE);
                }
                    
            }
        });

        applyVoucherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String inputVoucher = voucherTextField.getText().trim();    // remove unecessary spaces if ever using .trim()

                if (!cartList.isEmpty()){
                    if (availableVouchers.contains(inputVoucher)){
                        //  DISCOUNT GETTING using MAth.random() Library
                        //  DISCOUNT PERCENT GENERATOR from 5% to 50%
                        double minDiscount = 0.05; // 5%
                        double maxDiscount = 0.50; // 40%
                        
                        // Generate a random discount percentage
                        double generatedDiscount = (minDiscount + ((maxDiscount - minDiscount) * Math.random()));
                        // COMPUTATION
                        double discountAmount = totalPrice * generatedDiscount + 1;     
                        double priceHolder = generatedDiscount * 100 + 1;
                        // priceHolder = discountAmount;
                        int originalPrice = totalPrice;
                        totalPrice -= discountAmount;
    
                        System.out.println(originalPrice +"-"+ discountAmount +"="+ totalPrice);     // for debugging
                        System.out.printf("AMOUNT SUBTRACTED FROM ORIGINAL PRICE: " + "%.0f", discountAmount);
    
                        JOptionPane.showMessageDialog(null, String.format("PRICE DISCOUNTED BY : %.0f", priceHolder)+"%", "DISCOUNT", JOptionPane.DEFAULT_OPTION);
                        JOptionPane.showMessageDialog(null, "PRICE NOW IS: $"+ totalPrice, "DISCOUNT", JOptionPane.DEFAULT_OPTION);
                        updateCartDisplay();
    
                        applyVoucherButton.setVisible(false);   // hides apply after clicked
                        cancelVoucherButton.setVisible(true);       // show cancel button
                        voucherTextField.setEditable(false);    // makes the field read-only

                        addButton.setVisible(false);
                        removeButton.setVisible(false);
                        clearAllProductButton.setVisible(false);

    
                        cancelVoucherButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e){
                                voucherTextField.setText("");   //  clears the TextField
                                totalPrice = originalPrice;      // originalPrice is the variable that holds totalPrice that isn't computed yet
                                updateCartDisplay();        
                                
                                cancelVoucherButton.setVisible(false);  // if clicked, cancel button now hides
                                applyVoucherButton.setVisible(true);    // apply now appears, and then just loops
                                voucherTextField.setEditable(true);     // makes the field editable
                                
                                addButton.setVisible(true);
                                removeButton.setVisible(true);
                                clearAllProductButton.setVisible(true);

                            }   
                        });
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "INVALID CODE", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "CART IS EMPTY, CANT APPLY VOUCHER", "ERROR VOUCHER APPLY", JOptionPane.WARNING_MESSAGE);
                }
                
            }
        });

    }

    private void updateCartDisplay() {      // updates the cart in left side
        textArea.setText(""); // Clear the text area
        for (String item : cartList) {  // loops to add each price of added products
            textArea.append(item + "\n");   // .append() is a keyword like .add() but specifically in Array
        }
        totalLabel.setText("Total Price is: $" + totalPrice);   // updates the totalprice
    }

    public static void main(String[] args) { 
        new ShopUI();
    }

}