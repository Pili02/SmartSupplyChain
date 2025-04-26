import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Create sample products
            Product product1 = new Product("Laptop", "Electronics", 1);
            Product product2 = new Product("Phone", "Electronics", 2);
            Product product3 = new Product("Tablet", "Electronics", 3);
            Product product4 = new Product("Headphones", "Electronics", 4);
            Product product5 = new Product("Chocolates", "Food", 5);
            Product product6 = new Product("Foundation", "MakeUp", 6);

            // Create sample quantities
            int[] quantities = {10, 20, 15, 25, 50, 30};
            // Create sample transactions
            Transaction[] transactions = new Transaction[20];

            // Create a retailer
            Retailer retailer = new Retailer("Retailer1", new Product[]{product1, product2, product5}, new int[]{5, 10, 20}, 101, transactions);

            // Create a warehouse manager
            WarehouseManager warehouseManager = new WarehouseManager(
                "Warehouse1", 
                new Product[]{product1, product2, product3, product4, product5, product6}, 
                quantities, 
                201, 
                transactions
            );

            // Add suppliers with high stock quantities
            Supplier[] bigSuppliers = new Supplier[20];
            for (int i = 0; i < 20; i++) {
                bigSuppliers[i] = new Supplier(
                    "Supplier" + (i + 1),
                    new Product[]{product1, product2, product3, product4, product5, product6},
                    new int[]{100, 100, 100, 100, 100, 100},
                    300 + i,
                    transactions
                );
            }
            warehouseManager.setSuppliers(bigSuppliers);

            // Assign the warehouse manager to the retailer
            retailer.warehouseManager = warehouseManager;

            // Create Admin
            Admin admin = new Admin("Admin1", 1);

            // Barcode usage
            String barcode = admin.createBarcodeForProduct("Laptop");
            String barcodeToValidate = barcode.substring(barcode.length() - 12);
            System.out.println("Is barcode valid? " + admin.checkIfBarcodeValid(barcodeToValidate));

            // Sales trend analysis
            Map<String, Integer> sales = new HashMap<>();
            sales.put("Laptop", 120);
            sales.put("Phone", 220);
            sales.put("Tablet", 150);

            System.out.println(admin.findTopProduct(sales));
            System.out.println("Total Units Sold: " + admin.getTotalSales(sales));

            // admin.scanProductBarcode("Phone"); // Feature not implemented
            // admin.analyzeSalesTrends();        // Feature not implemented

            // Display initial stock
            System.out.println("Initial Stock Levels:");
            displayStock(warehouseManager);

            // User input for sending an order request
            while (true) {
                System.out.println("\nEnter Retailers Order product ID to order (1-6) or 0 to exit: ");
                int productId = scanner.nextInt();
                if (productId == 0) break;

                System.out.println("Enter Retailers quantity to order: ");
                int orderQuantity = scanner.nextInt();
                System.out.println("Enter Retailers price per item to order: ");
                double price = scanner.nextInt();

                Product selectedProduct = null;
                switch (productId) {
                    case 1 -> selectedProduct = product1;
                    case 2 -> selectedProduct = product2;
                    case 3 -> selectedProduct = product3;
                    case 4 -> selectedProduct = product4;
                    case 5 -> selectedProduct = product5;
                    case 6 -> selectedProduct = product6;
                    default -> System.out.println("Invalid product ID.");
                }

                if (selectedProduct != null) {
                    Order newOrder = new Order(
                        new ProductP(
                            selectedProduct.getName(),
                            selectedProduct.getCategory(),
                            selectedProduct.getId(),
                            price
                        ),
                        orderQuantity
                    );
                    retailer.sendOrderRequest(newOrder);
                    logOperation("Retailer sent order request for " + orderQuantity + " units of " + selectedProduct.getName() + ".");
                }
            }

            // Display updated stock
            System.out.println("\nUpdated Stock Levels:");
            displayStock(warehouseManager);

            // Display profit of the warehouse
            System.out.println("\nCalculating profit of the warehouse...");
            double profit = warehouseManager.calculateProfitOrLoss();
            if (profit > 0) {
                System.out.println("Warehouse Profit: ₹" + profit);
            } else if (profit < 0) {
                System.out.println("Warehouse Loss: ₹" + Math.abs(profit));
            } else {
                System.out.println("No profit or loss for the warehouse.");
            }
            logOperation("Warehouse profit/loss calculated and displayed.");

        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter numeric values where required.");
        } catch (IOException e) {
            System.err.println("File I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Method to display stock levels
    public static void displayStock(WarehouseManager warehouseManager) {
        Product[] products = warehouseManager.getTargetProducts();
        int[] quantities = warehouseManager.getQuantities();

        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i].getName() + ": " + quantities[i]);
        }
    }

    // Logging system
    public static void logOperation(String message) throws IOException {
        try (FileWriter fw = new FileWriter("operation_log.txt", true)) {
            fw.write(new Date() + ": " + message + "\n");
        }
    }
}