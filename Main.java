package com.jmf.app.java_activity;

import com.jmf.app.java_activity.model.LoginModel;
import com.jmf.app.java_activity.model.OrdersModel;
import com.jmf.app.java_activity.model.ProductsModel;
import com.jmf.app.java_activity.service.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // scanner for user input
    private static final ProductsService productsService = new ProductsServiceImpl(); // services for managing products
    private static final OrdersService ordersService = new OrdersServiceImpl(); // services for managing orders

    public static void main(String[] args) {

        while (true) {
            // display welcome screen
            System.out.println("***********************");
            System.out.println("* Welcome to My Shop! *");
            System.out.println("***********************");
            System.out.println("1 - Login");
            System.out.println(".......................");
            System.out.println("0 - Exit");
            System.out.print("\nWhat do you want to do? : ");

            String userInput = scanner.nextLine().trim(); // get user input
            if (userInput.isEmpty()) { // handle empty or null input
                System.out.println("\nERROR: Invalid input.");
                System.out.print("Press \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                    System.out.println("\n");
                } catch (Exception e) {
                    // do nothing
                }
                continue;
            }

            try {
                int input = Integer.parseInt(userInput); // convert input to integer

                if (input == 1) { // go to login screen
                    handleLogin();
                } else if (input == 0) { // exit the program
                    System.out.println("\nThank you for using our services!");
                    System.exit(0);
                } else { // handle invalid number
                    System.out.println("\nERROR: Invalid Number");
                    System.out.print("Press \"ENTER\" to continue...");
                    try {
                        int ignored = System.in.read();
                        System.out.println("\n");
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            } catch (NumberFormatException e) { // handle non-numeric input
                System.out.println("\nERROR: Invalid number.");
                System.out.print("Press \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                    System.out.println("\n");
                } catch (Exception ex) {
                    // do nothing
                }
            }
        }
    }

    private static void handleLogin() {
        // display login screen
        System.out.println("\n\n***********************");
        System.out.println("*        LOGIN        *");
        System.out.println("***********************");
        System.out.print("\nEnter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        LoginModel loginModel = new LoginModel(username, password); // create login model with user input
        LoginService loginService = new LoginServiceImpl(); // initialize login service
        try {
            String userType = loginService.login(loginModel); // validate login and get user type
            if ("admin".equals(userType)) { // go to administrator home screen
                adminHomeScreen();
            } else if ("customer".equals(userType)) { // go to customer home screen
                customerHomeScreen();
            }
        } catch (RuntimeException e) {
            // for invalid credentials
        }
    }

    private static void adminHomeScreen() {
        while (true) {
            // display administrator home screen
            System.out.println("\n\n***********************");
            System.out.println("*    ADMINISTRATOR    *");
            System.out.println("***********************");
            System.out.println("1 - Manage Products");
            System.out.println("2 - Manage Orders");
            System.out.println(".......................");
            System.out.println("0 - Logout");
            System.out.print("\nWhat do you want to do? : ");

            String userInput = scanner.nextLine().trim(); // get user input
            if (userInput.isEmpty()) { // handle empty or null input
                System.out.println("\nERROR: Invalid input.");
                System.out.print("Press \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                } catch (Exception e) {
                    // do nothing
                }
                continue;
            }

            try {
                int input = Integer.parseInt(userInput); // convert input to integer

                if (input == 1) { // go to manage products screen
                    manageProduct();
                    break;
                } else if (input == 2) { // go to manage orders screen
                    manageOrders();
                    break;
                } else if (input == 0) { // logout the admin account
                    System.out.println("\nLogging out...\n");
                    main(null);
                    break;
                } else { // handle invalid number
                    System.out.println("\nERROR: Invalid Number");
                    System.out.print("Press \"ENTER\" to continue...");
                    try {
                        int ignored = System.in.read();
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            } catch (NumberFormatException e) { // handle non-numeric input
                System.out.println("\nERROR: Invalid number.");
                System.out.print("Press \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                } catch (Exception ex) {
                    // do nothing
                }
            }
        }
    }

    private static void manageProduct() {
        while (true) {
            // display administrator manage product screen
            System.out.println("\n\n***********************");
            System.out.println("*       PRODUCTS      *");
            System.out.println("***********************");
            List<ProductsModel> products = productsService.getProducts(); // fetch product list from service
            if (products.isEmpty()) { // if the products is empty
                System.out.println("No Products Found.");
            } else { // display product list
                System.out.printf("%-3s %-12s %-10s%n", "ID", "Name", "Price");
                for (ProductsModel productsModel : products) {
                    System.out.printf("%-3s %-12s %-10s%n", productsModel.getId(), productsModel.getName(), productsModel.getPrice());
                }
            }
            System.out.println(".......................");
            System.out.println("1 - Add New Product");
            System.out.println("2 - Remove Products");
            System.out.println("0 - Back");
            System.out.print("\nWhat do you want to do? : ");

            String userInput = scanner.nextLine().trim(); // get user input
            if (userInput.isEmpty()) { // handle empty or null input
                System.out.println("\nERROR: Invalid input.");
                System.out.print("Press \"ENTER\" to continue...");
                scanner.nextLine();
                continue;
            }

            try {
                int input = Integer.parseInt(userInput); // convert input to integer

                if (input == 1) { // go to add product screen
                    addProduct();
                } else if (input == 2) {
                    if (products.isEmpty()) { // if the products is empty
                        System.out.println("\nNo products found. Can't proceed to remove products.");
                        System.out.print("Press \"ENTER\" to continue...");
                        scanner.nextLine();
                    } else { // if there is products, it will proceed to remove product screen
                        removeProduct();
                    }
                } else if (input == 0) { // go back to administrator home screen
                    adminHomeScreen();
                    break;
                } else { // handle invalid number
                    System.out.println("\nERROR: Invalid number");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                }
            } catch (NumberFormatException e) { // handle non-numeric input
                System.out.println("\nERROR: Invalid number.");
                System.out.print("Press \"ENTER\" to continue...");
                scanner.nextLine();
            }
        }
    }

    private static void addProduct() {
        // display add product screen
        System.out.println("\n\n***********************");
        System.out.println("*     ADD PRODUCT     *");
        System.out.println("***********************");
        // Name validation
        String name;
        while (true) {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Invalid input");
                continue;
            }
            if (productsService.isProductNameExists(name)) { // Check if product name already exists
                System.out.println("Invalid product name. Product name already exist.");
                continue;
            }
            break;
        }

        // Price validation
        BigDecimal price;
        while (true) {
            System.out.print("Price: ");
            try {
                price = new BigDecimal(scanner.nextLine().trim());
                if (price.compareTo(BigDecimal.ZERO) > 0) break;
            } catch (Exception e) {
                // do nothing, just fall through
            }
            System.out.println("Invalid Number");
        }

        ProductsModel newProduct = new ProductsModel(0, name, price); // create new product object

        while (true) {
            System.out.print("Are you sure you want add this product (Y/N) : ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) { // proceed to adding the product
                productsService.addProduct(newProduct); // add the new product to the product list
                System.out.println("\nProduct added successfully!");
                System.out.print("\nPress \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                } catch (Exception e) {
                    // do nothing
                }
                manageProduct();
                break;
            } else if (confirm.equalsIgnoreCase("N")) { // cancel the adding of product
                System.out.println("\nAction canceled.");
                System.out.print("\nPress \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();

                } catch (Exception e) {
                    // do nothing
                }
                manageProduct();
                break;
            } else { // handle invalid input
                System.out.println("\nERROR: Invalid character\n");
            }
        }
    }

    private static void removeProduct() {
        // display remove product
        System.out.println("\n\n***********************");
        System.out.println("*   REMOVE PRODUCT    *");
        System.out.println("***********************");
        Integer id;
        while (true) { // ID validation
            System.out.print("Product ID: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid Input");
                continue;
            }
            try {
                id = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number");
            }
        }

        ProductsModel removeProduct = new ProductsModel(id); // product to be remove based on ID

        while (true) {
            System.out.print("Are you sure you want remove this product (Y/N) : ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Y")) { // proceed to remove the product
                boolean found = productsService.removeProduct(removeProduct); // remove the product to the product list
                if (found) { // if the ID is found, it will proceed to remove the product
                    System.out.println("\nProduct removed successfully!");
                    System.out.print("\nPress \"ENTER\" to continue...");
                    try {
                        int ignored = System.in.read();
                    } catch (Exception e) {
                        // do nothing
                    }
                    manageProduct();
                    break;
                } else { // if the ID is not found, it will not proceed to remove the product
                    System.out.println("\nNo Products Found.");
                    System.out.print("\nPress \"ENTER\" to continue...");
                    try {
                        int ignored = System.in.read();
                    } catch (Exception e) {
                        // do nothing
                    }
                    removeProduct();
                    return;
                }
            } else if (confirm.equalsIgnoreCase("N")) { // cancel the removing of product
                System.out.println("\nAction canceled.");
                System.out.print("\nPress \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();

                } catch (Exception e) {
                    // do nothing
                }
                manageProduct();
                break;
            } else { // handle invalid input
                System.out.println("\nERROR: Invalid character\n");
            }
        }
    }

    private static void manageOrders() {
        while (true) {
            // display administrator manage order screen
            System.out.println("\n\n***********************");
            System.out.println("*        ORDERS       *");
            System.out.println("***********************");
            List<OrdersModel> orders = ordersService.getOrders(); // fetch order list from service
            if (orders.isEmpty()) { // if the order is empty
                System.out.println("No Orders Found.");
            } else { // display order list
                System.out.printf("%-20s %-10s %-10s %-7s %-5s %-8s %-10s%n", "Date", "Reference", "Name", "Price", "Qty", "Total", "Status");
                for (OrdersModel ordersModel : orders) {
                    System.out.printf("%-20s %-10s %-10s %-7s %-5d %-8s %-10s%n", ordersModel.getDate(), ordersModel.getReference(), ordersModel.getName(), ordersModel.getPrice(), ordersModel.getQuantity(), ordersModel.getTotal(), ordersModel.getStatus());
                }
            }
            System.out.println(".......................");
            System.out.println("1 - Mark Order as Delivered");
            System.out.println("0 - Back");
            System.out.print("\nWhat do you want to do? : ");

            String userInput = scanner.nextLine().trim(); // get user input
            if (userInput.isEmpty()) { // handle empty or null input
                System.out.println("\nERROR: Invalid input.");
                System.out.print("Press \"ENTER\" to continue...");
                scanner.nextLine();
                continue;
            }

            try {
                int input = Integer.parseInt(userInput); // convert input to integer

                if (input == 1) { // go to update order status
                    if (orders.isEmpty()) { // can't proceed if there is no order
                        System.out.println("\nERROR: Cannot proceed with action. No orders found.");
                        System.out.print("Press \"ENTER\" to continue...");
                        scanner.nextLine();
                    } else { // proceed to update order status screen
                        updateOrderStatus();
                    }
                } else if (input == 0) { // go back to administrator home screen
                    adminHomeScreen();
                    break;
                } else { // handle invalid number
                    System.out.println("\nERROR: Invalid number");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                }
            } catch (NumberFormatException e) { // handle non-numeric input
                System.out.println("\nERROR: Invalid number");
                System.out.print("Press \"ENTER\" to continue...");
                scanner.nextLine();
            }
        }
    }

    private static void updateOrderStatus() {
        while (true) {
            // display update order status screen
            System.out.println("\n\n***********************");
            System.out.println("* UPDATE ORDER STATUS *");
            System.out.println("***********************");
            List<OrdersModel> orders = ordersService.getOrders(); // fetch the list of orders from service
            String reference;
            while (true) { // reference validation
                System.out.print("Order Reference : ");
                reference = scanner.nextLine().trim();
                if (!reference.isEmpty()) break;
                System.out.println("ERROR: Invalid input");
            }

            OrdersModel foundOrder = null; // Search for the order by reference
            for (OrdersModel order : orders) {
                if (reference.equals(order.getReference())) {
                    foundOrder = order;
                    break;
                }
            }

            if (foundOrder == null) { // if the order is not found, it can't proceed to update status
                System.out.println("\nERROR: Order does not exist.");
                System.out.print("Press \"ENTER\" to continue...");
                scanner.nextLine();
            } else {
                while (true) {
                    System.out.print("Are you sure you want to mark this order as delivered (Y/N) : ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) { // proceed to update status
                        ordersService.markOrder(foundOrder); // update the status of the order
                        System.out.println("\nOrder update successfully!");
                        System.out.print("\nPress \"ENTER\" to continue...");
                        scanner.nextLine();
                        manageOrders();
                        break;
                    } else if (confirm.equalsIgnoreCase("N")) { // cancel the updating of status
                        System.out.println("\nAction canceled.");
                        System.out.print("\nPress \"ENTER\" to continue...");
                        try {
                            int ignored = System.in.read();

                        } catch (Exception e) {
                            // do nothing
                        }
                        manageOrders();
                        break;
                    } else { // handle invalid input
                        System.out.println("\nERROR: Invalid character\n");
                    }
                }
            }
        }
    }

    private static void customerHomeScreen() {
        while (true) {
            // display customer home screen
            System.out.println("\n\n***********************");
            System.out.println("*       CUSTOMER      *");
            System.out.println("***********************");
            System.out.println("1 - Shop");
            System.out.println("2 - My Orders");
            System.out.println(".......................");
            System.out.println("0 - Logout");
            System.out.print("\nWhat do you want to do? : ");

            String userInput = scanner.nextLine().trim(); // get user input
            if (userInput.isEmpty()) { // handle empty or null input
                System.out.println("\nERROR: Invalid input.");
                System.out.print("Press \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                } catch (Exception e) {
                    // do nothing
                }
                continue;
            }

            try {
                int input = Integer.parseInt(userInput); // convert input to integer

                if (input == 1) { // go to customer shop screen
                    customerShop();
                    break;
                } else if (input == 2) { // go to customer order screen
                    customerOrder();
                    break;
                } else if (input == 0) { // logout the customer account
                    System.out.println("\nLogging out...\n");
                    main(null);
                    break;
                } else { // handle invalid number
                    System.out.println("\nERROR: Invalid number");
                    System.out.print("Press \"ENTER\" to continue...");
                    try {
                        int ignored = System.in.read();
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            } catch (NumberFormatException e) { // handle non-numeric input
                System.out.println("\nERROR: Invalid number");
                System.out.print("Press \"ENTER\" to continue...");
                try {
                    int ignored = System.in.read();
                } catch (Exception ex) {
                    // do nothing
                }
            }
        }
    }

    private static void customerShop() {
        while (true) {
            // display customer shop screen
            System.out.println("\n\n***********************");
            System.out.println("*         SHOP        *");
            System.out.println("***********************");
            List<ProductsModel> products = productsService.getProducts(); // fetch product list from the service
            if (products.isEmpty()) { // if no products available
                System.out.println("No Products Found.");
                System.out.println(".......................");
                System.out.println("0 - Back");
                System.out.print("\nWhat do you want to order? : ");

                String userInput = scanner.nextLine().trim(); // get user input
                if (userInput.isEmpty()) { // handle empty or null input
                    System.out.println("\nERROR: Invalid input.");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                    continue;
                }

                try {
                    int order = Integer.parseInt(userInput); // convert input to integer

                    if (order == 0) { // go back to customer home screen
                        customerHomeScreen();
                        break;
                    } else { // can't proceed to order when there is no products
                        System.out.println("\nERROR: Cannot proceed with action. No products Found");
                        System.out.print("Press \"ENTER\" to continue...");
                        scanner.nextLine();
                        customerHomeScreen();
                    }
                } catch (NumberFormatException e) { // handle non-numeric input
                    System.out.println("\nERROR: Invalid number");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                }
            } else {
                // display product list
                System.out.printf("%-3s %-12s %-10s%n", "ID", "Name", "Price");
                for (ProductsModel productsModel : products) {
                    System.out.printf("%-3s %-12s %-10s%n", productsModel.getId(), productsModel.getName(), productsModel.getPrice());
                }
                System.out.println(".......................");
                System.out.println("0 - Back");

                System.out.print("\nWhat do you want to order? : ");

                String userInput = scanner.nextLine().trim(); // get user input
                if (userInput.isEmpty()) { // handle empty or null input
                    System.out.println("\nERROR: Invalid input.");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                    continue;
                }

                try {
                    int order = Integer.parseInt(userInput); // convert input to integer

                    if (order == 0) { // go back to customer home screen
                        customerHomeScreen();
                        break;
                    } else {
                        // check if product exist
                        boolean productFound = false;
                        for (ProductsModel productsModel : products) {
                            if (order == productsModel.getId()) {
                                productFound = true;
                                placeOrder(order); // place order for selected product based on ID
                                break;
                            }
                        }
                        if (!productFound) { // if there is no product ID, it can't proceed
                            System.out.println("\nERROR: Invalid Input. Product does not exist.\n");
                            System.out.print("Press \"ENTER\" to continue...");
                            scanner.nextLine();
                        } else {
                            break;
                        }
                    }
                } catch (NumberFormatException e) { // handle non-numeric input
                    System.out.println("\nERROR: Invalid number");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                }
            }
        }
    }

    private static void placeOrder(int productId) {
        while (true) {
            // display customer place order screen
            System.out.println("\n\n***********************");
            System.out.println("*     PLACE ORDER     *");
            System.out.println("***********************");
            List<ProductsModel> products = productsService.getProducts(); // fetch product list from service
            // find the product based on product ID
            ProductsModel selectedProduct = null;
            for (ProductsModel product : products) {
                if (product.getId() == productId) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct != null) { // if product exist, it will proceed to place the order
                System.out.println("Name : " + selectedProduct.getName());
                System.out.println("Price : " + selectedProduct.getPrice());
                System.out.println(".......................");
                System.out.print("How many do you want? : ");
                try {
                    String input = scanner.nextLine().trim();

                    if (input.isEmpty()) { // handle empty or null input
                        System.out.println("ERROR: Invalid input.");
                        System.out.print("Press \"ENTER\" to continue...");
                        scanner.nextLine();
                    } else {
                        int quantity = Integer.parseInt(input);

                        if (quantity >= 1) {
                            while (true) {
                                System.out.println("That would be Php " + selectedProduct.getPrice().multiply(BigDecimal.valueOf(quantity))); // display total amount of order
                                System.out.print("Proceed with you order (Y/N) : ");
                                String proceed = scanner.nextLine();

                                // Get current date and time
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String date = LocalDateTime.now().format(formatter);

                                // Generate 5 random alphanumeric characters
                                String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                                StringBuilder sb = new StringBuilder();
                                Random random = new Random();
                                for (int i = 0; i < 5; i++) {
                                    sb.append(chars.charAt(random.nextInt(chars.length())));
                                }
                                String reference = sb.toString();

                                String name = selectedProduct.getName();
                                BigDecimal price = selectedProduct.getPrice();
                                BigDecimal total = selectedProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
                                String status = "FOR_DELIVERY";
                                if (proceed.equalsIgnoreCase("Y")) { // proceed to place the order
                                    OrdersModel newOrder = new OrdersModel(date, reference, name, price, quantity, total, status); // create a new order
                                    ordersService.placeOrder(newOrder); // save the new order and will put on list
                                    System.out.print("\nOrder Placed Successfully");
                                    System.out.print("\nPress \"ENTER\" to continue...");
                                    scanner.nextLine();
                                    customerShop();
                                } else if (proceed.equalsIgnoreCase("N")) { // cancel the placing order
                                    System.out.println("\nAction canceled.");
                                    System.out.print("\nPress \"ENTER\" to continue...");
                                    try {
                                        int ignored = System.in.read();

                                    } catch (Exception e) {
                                        // do nothing
                                    }
                                    customerShop();
                                    break;
                                } else { // handle invalid input
                                    System.out.println("\nPlease enter a valid input\n");
                                }
                            }
                            break;
                        } else { // handle invalid number
                            System.out.println("\nERROR: Invalid number");
                            System.out.print("Press \"ENTER\" to continue...");
                            scanner.nextLine();
                        }
                    }
                } catch (Exception e) { // handle non-numeric input
                    System.out.println("\nERROR: Invalid number.");
                    System.out.print("Press \"ENTER\" to continue...");
                    scanner.nextLine();
                }
            } else { // if product is not found
                System.out.println("ERROR: Product not found.");
                break;
            }
        }
    }

    private static void customerOrder() {
        // display my order screen
        System.out.println("\n\n***********************");
        System.out.println("*      MY ORDERS      *");
        System.out.println("***********************");
        List<OrdersModel> orders = ordersService.getOrders(); // fetch order list from service
        if (orders.isEmpty()) { // if no orders available
            System.out.println("No Orders Found.");
        } else { // display order list
            System.out.printf("%-20s %-10s %-10s %-7s %-5s %-8s %-10s%n", "Date", "Reference", "Name", "Price", "Qty", "Total", "Status");
            for (OrdersModel ordersModel : orders) {
                System.out.printf("%-20s %-10s %-10s %-7s %-5d %-8s %-10s%n", ordersModel.getDate(), ordersModel.getReference(), ordersModel.getName(), ordersModel.getPrice(), ordersModel.getQuantity(), ordersModel.getTotal(), ordersModel.getStatus());
            }
        }
        System.out.println(".......................");
        System.out.print("\nPress \"ENTER\" to continue...");
        scanner.nextLine();
        customerHomeScreen();
    }
}

