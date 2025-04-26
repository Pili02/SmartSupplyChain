package barcodescanner;

import java.util.Random;

public class BarcodeScanner {

    private Random random;

    public BarcodeScanner() {
        this.random = new Random();
    }

    // Generate a fake barcode for a product (12-digit random number)
    public String generateBarcode(String productName) {
        StringBuilder barcode = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            barcode.append(random.nextInt(10)); // Append random digits [0-9]
        }
        return "Product: " + productName + " | Barcode: " + barcode.toString();
    }

    // Validate if a barcode is 12 digits long (simple validation)
    public boolean validateBarcode(String barcode) {
        return barcode != null && barcode.matches("\\d{12}");
    }
}
