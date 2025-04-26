import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create sample products
        Product product1 = new Product("Laptop", "Electronics", 1);
        Product product2 = new Product("Phone", "Electronics", 2);
        Product product3 = new Product("Tablet", "Electronics", 3);

        // Create sample quantities
        int[] quantities = {10, 20, 15};

        // Create sample transactions
        Transaction[] transactions = new Transaction[10];

        // Create a retailer
        Retailer retailer = new Retailer("Retailer1", new Product[]{product1, product2}, new int[]{5, 10}, 101, transactions);

        // Create a warehouse manager
        WarehouseManager warehouseManager = new WarehouseManager("Warehouse1", new Product[]{product1, product2, product3}, quantities, 201, transactions);

        // Assign the warehouse manager to the retailer
        retailer.warehouseManager = warehouseManager;

        // Test sending an order request
        retailer.sendOrderRequest(product1, 5);

        // Test adding a transaction
        retailer.addTransaction("Order1", "Warehouse1", product1, 5, 500.0);

        // Create a supplier
        Supplier supplier = new Supplier("Supplier1", new Product[]{product1, product2, product3}, quantities, 301, transactions);

        // Assign suppliers to the warehouse manager
        warehouseManager.setSuppliers(new Supplier[]{supplier}); // Use the setter method

        // Test supplier stock reduction
        supplier.reduceStock(product1, 5);

        // Test warehouse manager filling orders
        Order order1 = new Order(new ProductP("Laptop", "Electronics", 1, 1000.0), 5);
        Order order2 = new Order(new ProductP("Phone", "Electronics", 2, 800.0), 10);
        warehouseManager.addOrder(order1);
        warehouseManager.addOrder(order2);

        System.out.println("Warehouse manager starts filling orders...");
        warehouseManager.fillOrders();

        // Display profit or loss
        System.out.println("\nCalculating profit or loss...");
        warehouseManager.displayProfitOrLoss();

        // Test admin functionality
        Admin admin = new Admin("Admin1", 401);
        admin.addUser("Retailer", retailer);
        admin.addUser("WarehouseManager", warehouseManager);
        admin.addUser("Supplier", supplier);

        admin.viewAllStockLevels();
        admin.generateReport();

        // Test bar graph generation (placeholder)
        System.out.println("Generating bar graph for stock levels...");
        generateBarGraph(warehouseManager);
    }

    // Placeholder for bar graph generation
    public static void generateBarGraph(WarehouseManager warehouseManager) {
        // Example: Print stock levels as a bar graph in the console
        Product[] products = warehouseManager.getTargetProducts();
        int[] quantities = warehouseManager.getQuantities(); // Assuming getQuantities() exists

        System.out.println("Stock Levels:");
        for (int i = 0; i < products.length; i++) {
            System.out.print(products[i].getName() + ": ");
            for (int j = 0; j < quantities[i]; j++) {
                System.out.print("|");
            }
            System.out.println(" (" + quantities[i] + ")");
        }
    }
}