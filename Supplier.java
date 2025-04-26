import java.util.Random;

public class Supplier extends Person {

    private Random rand = new Random();
    private ProductP[] productsOnMarket;

    Supplier(String name, Product[] products, int[] quantity, int id, Transaction[] paymentHistory) {
        super(name, products, quantity, id, paymentHistory);
        this.productsOnMarket = new ProductP[this.distinctProductCount];
        createProductsOnMarket(); // Initialize productsOnMarket with category-based prices
    }

    public ProductP[] getProductsOnMarket() {
        return this.productsOnMarket;
    }

    public Random getRand() {
        return this.rand;
    }

    void createProductsOnMarket() {
        for (int i = 0; i < this.distinctProductCount; i++) {
            Product product = this.products[i];
            double price;

            // Set price based on category
            switch (product.getCategory()) {
                case "Food":
                    price = 10 + rand.nextInt(21); // Random price between 10 and 30
                    break;
                case "Electronics":
                    price = 800 + rand.nextInt(201); // Random price between 100 and 300
                    break;
                case "MakeUp":
                    price = 20 + rand.nextInt(51); // Random price between 20 and 70
                    break;
                case "Miscellaneous":
                    price = 5 + rand.nextInt(16); // Random price between 5 and 20
                    break;
                case "MiscellaneousExpensive":
                    price = 200 + rand.nextInt(301); // Random price between 200 and 500
                    break;
                default:
                    price = 50; // Default price
                    break;
            }

            // Create ProductP object with the calculated price
            this.productsOnMarket[i] = new ProductP(
                product.getName(),
                product.getCategory(),
                product.getId(),
                price
            );
        }
    }

    int receiveOrderRequest(Product item, int quantity) {
        boolean found = false; // Fix boolean initialization
        int productInd = 0;
        for (productInd = 0; productInd < this.distinctProductCount; productInd++) {
            if (this.products[productInd].equals(item)) { // Fix variable reference
                found = true;
                break;
            }
        }
        if (!found) { // Fix boolean comparison
            return 0;
        }
        int currQuantity = this.quantity[productInd];
        if (currQuantity < quantity) {
            this.quantity[productInd] = 0;
            return currQuantity;
        }
        currQuantity -= quantity;
        this.quantity[productInd] = currQuantity;

        // Example of creating a new Transaction
        Transaction transaction = new Transaction(
            "Order from Supplier",
            "Warehouse",
            item,
            quantity,
            currQuantity * getPrice(item)
        );

        return quantity;
    }

    int getPrice(Product item) {
        int id = item.getId();
        for (int i = 0; i < this.distinctProductCount; i++) {
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) { // Add null check
                return (int) productsOnMarket[i].getPrice(); // Fix type casting
            }
        }
        return -1;
    }

    int getPrice(Product item, int discountPercentage) {
        int basePrice = getPrice(item);
        if (basePrice <= 0) return basePrice;
        return (int) (basePrice - (basePrice * discountPercentage / 100.0));
    }

    int getPrice(ProductP item) {
        int id = item.getId();
        for (int i = 0; i < this.distinctProductCount; i++) {
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) { // Add null check
                return (int) productsOnMarket[i].getPrice(); // Fix type casting
            }
        }
        return -1;
    }

    int getQuantity(Product item) {
        int id = item.getId();
        for (int i = 0; i < this.distinctProductCount; i++) {
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) { // Add null check
                return this.quantity[i];
            }
        }
        return 0;
    }

    int getQuantity(ProductP item) {
        int id = item.getId();
        for (int i = 0; i < this.distinctProductCount; i++) {
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) { // Add null check
                return this.quantity[i];
            }
        }
        return 0;
    }

    void reduceStock(Product item, int quantity) {
        int productInd = 0;
        for (productInd = 0; productInd < this.distinctProductCount; productInd++) {
            if (this.products[productInd].equals(item)) { // Fix variable reference
                break;
            }
        }
        if (productInd == this.distinctProductCount) {
            return;
        }
        if (this.quantity[productInd] < quantity) {
            return;
        }
        this.quantity[productInd] -= quantity; // Fix variable name
    }

    void reduceStock(Product item, int... quantities) {
        for (int q : quantities) {
            reduceStock(item, q);
        }
    }

    void reduceStock(ProductP item, int quantity) {
        int productInd = 0;
        for (productInd = 0; productInd < this.distinctProductCount; productInd++) {
            if (productsOnMarket[productInd] != null && productsOnMarket[productInd].getId() == item.getId()) {
                break;
            }
        }
        if (productInd == this.distinctProductCount) {
            return;
        }
        if (this.quantity[productInd] < quantity) {
            return;
        }
        this.quantity[productInd] -= quantity;
    }
    public String getRole() {
        return "Supplier";
    }
}