package aitrendanalyzer;

import java.util.Map;
import java.util.HashMap;

public class TrendAnalyzer {

    public TrendAnalyzer() {
        
    }

    public String findBestSellingProduct(Map<String, Integer> salesData) {
        if (salesData == null || salesData.isEmpty()) {
            return "No sales data available.";
        }

        String bestProduct = null;
        int maxSales = -1;

        for (Map.Entry<String, Integer> entry : salesData.entrySet()) {
            if (entry.getValue() > maxSales) {
                bestProduct = entry.getKey();
                maxSales = entry.getValue();
            }
        }
        return "Best-selling product: " + bestProduct + " (Sold: " + maxSales + " units)";
    }


    public int calculateTotalSales(Map<String, Integer> salesData) {
        int total = 0;
        if (salesData != null) {
            for (int quantity : salesData.values()) {
                total += quantity;
            }
        }
        return total;
    }
}
