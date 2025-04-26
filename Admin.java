import java.io.*;
import java.util.*;

// Interface for system-wide actions
interface SystemMonitor {
    void generateReport();
    void alertLowStock();
    void predictDemand(); // Add this method to the interface
}

// Admin class with nested helper class
public class Admin implements SystemMonitor {
    String name;
    int id;

    // Map to track all people by role
    Map<String, List<Person>> usersByRole = new HashMap<>();

    // Constructor
    public Admin(String name, int id) {
        this.name = name;
        this.id = id;
        usersByRole.put("Retailer", new ArrayList<>());
        usersByRole.put("WarehouseManager", new ArrayList<>());
        usersByRole.put("Supplier", new ArrayList<>());
    }

    // Overloaded method: add user
    public void addUser(String role, Person user) {
        usersByRole.get(role).add(user);
    }

    // Overloaded method: add multiple users (varargs)
    public void addUser(String role, Person... users) {
        usersByRole.get(role).addAll(Arrays.asList(users));
    }

    // Check all inventories
    public void viewAllStockLevels() {
        for (String role : usersByRole.keySet()) {
            System.out.println("-- " + role + " --");
            for (Person p : usersByRole.get(role)) {
                System.out.println(p.name + "'s Stock:\n" + p.stockUpdate());
            }
        }
    }

    // Method to display stock levels as a text-based bar chart
    public void viewStockGraph() {
        for (String role : usersByRole.keySet()) {
            System.out.println("-- " + role + " --");
            for (Person p : usersByRole.get(role)) {
                System.out.println(p.name + "'s Stock:");
                for (int i = 0; i < p.products.length; i++) {
                    System.out.printf("%-15s | %s%n", p.products[i].getName(), "*".repeat(p.quantity[i])); // Use getName()
                }
            }
        }
    }

    // Interface Implementation
    @Override
    public void generateReport() {
        try (FileWriter fw = new FileWriter("inventory_report.txt")) {
            for (String role : usersByRole.keySet()) {
                fw.write("Role: " + role + "\n");
                for (Person p : usersByRole.get(role)) {
                    fw.write("User: " + p.name + "\n" + p.stockUpdate() + "\n");
                }
            }
            System.out.println("Inventory report generated.");
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }

    @Override
    public void alertLowStock() {
        for (String role : usersByRole.keySet()) {
            for (Person p : usersByRole.get(role)) {
                for (int i = 0; i < p.products.length; i++) {
                    if (p.quantity[i] < 5) { // Example threshold
                        System.out.println("Low stock alert: " + p.products[i].getName() + " for " + p.name); // Use getName()
                    }
                }
            }
        }
    }

    // Static nested class for Admin utilities
    static class AdminUtils {
        public static void log(String message) {
            try (FileWriter fw = new FileWriter("admin_log.txt", true)) {
                fw.write(new Date() + ": " + message + "\n");
            } catch (IOException e) {
                System.err.println("Logging failed: " + e.getMessage());
            }
        }
    }

    // Method to simulate demand prediction (basic demo)
    @Override
    public void predictDemand() {
        System.out.println("\n=== Demand Prediction Report ===");

        // Step 1: Calculate total sales and transaction counts
        Map<Integer, Integer> totalSales = calculateTotalSales();
        Map<Integer, Integer> salesCount = calculateSalesCount();

        // Step 2: Generate predictions
        generateDemandPredictions(totalSales, salesCount);
    }

    // Helper method to calculate total sales
    private Map<Integer, Integer> calculateTotalSales() {
        Map<Integer, Integer> totalSales = new HashMap<>();
        for (String role : usersByRole.keySet()) {
            for (Person p : usersByRole.get(role)) {
                if (p.paymentHistory == null) continue;

                for (Transaction t : p.paymentHistory) {
                    if (t != null && t.getProduct() != null) {
                        int productId = t.getProduct().getId();
                        totalSales.put(productId, totalSales.getOrDefault(productId, 0) + t.getAmount());
                    }
                }
            }
        }
        return totalSales;
    }

    // Helper method to calculate sales count
    private Map<Integer, Integer> calculateSalesCount() {
        Map<Integer, Integer> salesCount = new HashMap<>();
        for (String role : usersByRole.keySet()) {
            for (Person p : usersByRole.get(role)) {
                if (p.paymentHistory == null) continue;

                for (Transaction t : p.paymentHistory) {
                    if (t != null && t.getProduct() != null) {
                        int productId = t.getProduct().getId();
                        salesCount.put(productId, salesCount.getOrDefault(productId, 0) + 1);
                    }
                }
            }
        }
        return salesCount;
    }

    // Helper method to generate demand predictions
    private void generateDemandPredictions(Map<Integer, Integer> totalSales, Map<Integer, Integer> salesCount) {
        for (int productId : totalSales.keySet()) {
            int total = totalSales.get(productId);
            int count = salesCount.getOrDefault(productId, 0);

            // Avoid division by zero
            if (count == 0) {
                System.out.println("Product ID: " + productId + " | No transactions available for prediction.");
                continue;
            }

            double predictedDemand = (double) total / count;
            System.out.println("Product ID: " + productId + " | Predicted Average Demand per Order: " + String.format("%.2f", predictedDemand));
        }
    }
}
