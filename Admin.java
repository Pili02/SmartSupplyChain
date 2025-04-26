import barcodescanner.BarcodeScanner;
import aitrendanalyzer.TrendAnalyzer;

import java.io.*;
import java.util.*;

// Interface for system-wide actions
interface SystemMonitor {
    void generateReport();
    void alertLowStock();
    void predictDemand();
}

// Admin class with nested helper class
public class Admin implements SystemMonitor {
    String name;
    int id;
    Map<String, List<Person>> usersByRole = new HashMap<>();

    // New fields for barcode scanner and trend analyzer
    private BarcodeScanner barcodeScanner;
    private TrendAnalyzer trendAnalyzer;

    // Constructor
    public Admin(String name, int id) {
        this.name = name;
        this.id = id;
        usersByRole.put("Retailer", new ArrayList<>());
        usersByRole.put("WarehouseManager", new ArrayList<>());
        usersByRole.put("Supplier", new ArrayList<>());

        // Initialize barcode scanner and trend analyzer
        barcodeScanner = new BarcodeScanner();
        trendAnalyzer = new TrendAnalyzer();
    }

    // ===== New methods to use barcode scanner and AI trend analyzer =====

    public void scanProductBarcode(String productName) {
        // barcodeScanner.scanProduct(productName); // <-- Remove or implement this method in BarcodeScanner
        System.out.println("Barcode scan feature not implemented.");
    }

    public void analyzeSalesTrends() {
        // trendAnalyzer.analyzeTrends(); // <-- Remove or implement this method in TrendAnalyzer
        System.out.println("Sales trend analysis feature not implemented.");
    }

    public String createBarcodeForProduct(String productName) {
        String barcodeInfo = barcodeScanner.generateBarcode(productName);
        System.out.println(barcodeInfo); // display generated barcode
        return barcodeInfo;
    }

    // Admin method to validate barcode
    public boolean checkIfBarcodeValid(String barcode) {
        return barcodeScanner.validateBarcode(barcode);
    }

    // Admin method to find best-selling product
    public String findTopProduct(Map<String, Integer> salesData) {
        return trendAnalyzer.findBestSellingProduct(salesData);
    }

    // Admin method to find total sales
    public int getTotalSales(Map<String, Integer> salesData) {
        return trendAnalyzer.calculateTotalSales(salesData);
    }

    public void addUser(String role, Person user) {
        usersByRole.get(role).add(user);
    }

    public void addUser(String role, Person... users) {
        usersByRole.get(role).addAll(Arrays.asList(users));
    }

    public void viewAllStockLevels() {
        for (String role : usersByRole.keySet()) {
            System.out.println("-- " + role + " --");
            for (Person p : usersByRole.get(role)) {
                System.out.println(p.name + "'s Stock:\n" + p.stockUpdate());
            }
        }
    }

    public void viewStockGraph() {
        for (String role : usersByRole.keySet()) {
            System.out.println("-- " + role + " --");
            for (Person p : usersByRole.get(role)) {
                System.out.println(p.name + "'s Stock:");
                for (int i = 0; i < p.products.length; i++) {
                    System.out.printf("%-15s | %s%n", p.products[i].getName(), "*".repeat(p.quantity[i]));
                }
            }
        }
    }

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
                    if (p.quantity[i] < 5) {
                        System.out.println("Low stock alert: " + p.products[i].getName() + " for " + p.name);
                    }
                }
            }
        }
    }

    @Override
    public void predictDemand() {
        System.out.println("\n=== Demand Prediction Report ===");

        Map<Integer, Integer> totalSales = calculateTotalSales();
        Map<Integer, Integer> salesCount = calculateSalesCount();

        generateDemandPredictions(totalSales, salesCount);
    }

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

    private void generateDemandPredictions(Map<Integer, Integer> totalSales, Map<Integer, Integer> salesCount) {
        for (int productId : totalSales.keySet()) {
            int total = totalSales.get(productId);
            int count = salesCount.getOrDefault(productId, 0);
            if (count == 0) {
                System.out.println("Product ID: " + productId + " | No transactions available for prediction.");
                continue;
            }
            double predictedDemand = (double) total / count;
            System.out.println("Product ID: " + productId + " | Predicted Average Demand per Order: " + String.format("%.2f", predictedDemand));
        }
    }

    // Static nested AdminUtils
    static class AdminUtils {
        public static void log(String message) {
            try (FileWriter fw = new FileWriter("admin_log.txt", true)) {
                fw.write(new Date() + ": " + message + "\n");
            } catch (IOException e) {
                System.err.println("Logging failed: " + e.getMessage());
            }
        }
    }
}
