import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {

            Product product1 = new Product("Laptop", "Electronics", 1);
            Product product2 = new Product("Phone", "Electronics", 2);
            Product product3 = new Product("Tablet", "Electronics", 3);
            Product product4 = new Product("Headphones", "Electronics", 4);
            Product product5 = new Product("Chocolates", "Food", 5);
            Product product6 = new Product("Foundation", "MakeUp", 6);


            int[] quantities = {10, 20, 15, 25, 50, 30};

            Transaction[] transactions = new Transaction[100];


            Retailer retailer = new Retailer("Retailer1", new Product[]{product1, product2, product5}, new int[]{5, 10, 20}, 101, transactions);


            WarehouseManager warehouseManager = new WarehouseManager(
                "Warehouse1", 
                new Product[]{product1, product2, product3, product4, product5, product6}, 
                quantities, 
                201, 
                transactions
            );


            Supplier[] bigSuppliers = new Supplier[10];
            for (int i = 0; i < 10; i++) {
                bigSuppliers[i] = new Supplier(
                    "Supplier" + (i + 1),
                    new Product[]{product1, product2, product3, product4, product5, product6},
                    new int[]{100, 100, 100, 100, 100, 100},
                    300 + i,
                    transactions
                );
            }
            warehouseManager.setSuppliers(bigSuppliers);


            retailer.warehouseManager = warehouseManager;


            Admin admin = new Admin("Admin1", 1);


            admin.addUser("Retailer", retailer);
            admin.addUser("WarehouseManager", warehouseManager);
            for (Supplier supplier : bigSuppliers) {
                admin.addUser("Supplier", supplier);
            }


            admin.scanProductBarcode();

            System.out.println(admin.getProductUsingId(3));


            Map<String, Integer> sales = new HashMap<>();
            sales.put("Laptop", 120);
            sales.put("Phone", 220);
            sales.put("Tablet", 150);

            System.out.println(admin.findTopProduct(sales));
            System.out.println("Total Units Sold: " + admin.getTotalSales(sales));


            System.out.println("Initial Stock Levels:");
            displayStock(warehouseManager);

            System.out.println("\nStock Bar Graph:");
            admin.viewStockGraph();

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
                    Admin.AdminUtils.log("Retailer sent order request for " + orderQuantity + " units of " + selectedProduct.getName() + ".");
                }
            }


            System.out.println("\nUpdated Stock Levels:");
            displayStock(warehouseManager);

            // Show updated bar graph of stock levels
            System.out.println("\nUpdated Stock Bar Graph:");
            admin.viewStockGraph();

            System.out.println("\nCalculating profit of the warehouse...");
            double profit = warehouseManager.calculateProfitOrLoss();
            if (profit > 0) {
                System.out.println("Warehouse Profit: ₹" + profit);
            } else if (profit < 0) {
                System.out.println("Warehouse Loss: ₹" + Math.abs(profit));
            } else {
                System.out.println("No profit or loss for the warehouse.");
            }
            Admin.AdminUtils.log("Warehouse profit/loss calculated and displayed.");

        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter numeric values where required.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void displayStock(WarehouseManager warehouseManager) {
        Product[] products = warehouseManager.getTargetProducts();
        int[] quantities = warehouseManager.getQuantities();

        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i].getName() + ": " + quantities[i]);
        }
    }
}