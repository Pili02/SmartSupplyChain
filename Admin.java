import java.io.*;
import java.util.*;

// Interface for system-wide actions
interface SystemMonitor {
    void generateReport();
    void alertLowStock();
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
                    System.out.printf("%-15s | %s%n", p.products[i].name, "*".repeat(p.quantity[i]));
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
                        System.out.println("Low stock alert: " + p.products[i].name + " for " + p.name);
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
    public void predictDemand() {
        System.out.println("Predicting future demand using sales history...");
        // Placeholder for AI algorithm integration
    }
}
