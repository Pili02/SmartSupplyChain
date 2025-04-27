package barcodescanner;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class BarcodeScanner {

    private Random random;
    private Map<Integer, String> store;

    public BarcodeScanner() {
        this.random = new Random();
        this.store = new HashMap<>();
    }

    public void includeProduct(String name, int id) {
        store.put(id, name);
    }

    public String getProduct(int id) {
        return store.get(id);
    }
}
