import java.util.Random;

public class Supplier extends Person implements OrderHandler{

    private Random rand = new Random();
    private ProductP[] productsOnMarket;

    Supplier(String name, Product[] products, int[] quantity, int id, Transaction[] paymentHistory) {
        super(name, products, quantity, id, paymentHistory);
        this.productsOnMarket = new ProductP[this.distinctProductCount];
        createProductsOnMarket(); 
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

            switch (product.getCategory()) {
                case "Food":
                    price = 10 + rand.nextInt(21); 
                    break;
                case "Electronics":
                    price = 800 + rand.nextInt(201);
                    break;
                case "MakeUp":
                    price = 20 + rand.nextInt(51); 
                    break;
                case "Miscellaneous":
                    price = 5 + rand.nextInt(16); 
                    break;
                case "MiscellaneousExpensive":
                    price = 200 + rand.nextInt(301);
                    break;
                default:
                    price = 50; 
                    break;
            }

            this.productsOnMarket[i] = new ProductP(
                product.getName(),
                product.getCategory(),
                product.getId(),
                price
            );
        }
    }

    int receiveOrderRequest(Product item, int quantity) {
        boolean found = false;
        int productInd = 0;
        for (productInd = 0; productInd < this.distinctProductCount; productInd++) {
            if (this.products[productInd].equals(item)) { 
                found = true;
                break;
            }
        }
        if (!found) {
            return 0;
        }
        int currQuantity = this.quantity[productInd];
        if (currQuantity < quantity) {
            this.quantity[productInd] = 0;
            return currQuantity;
        }
        currQuantity -= quantity;
        this.quantity[productInd] = currQuantity;


        Transaction transaction = new Transaction(
            "Order from Supplier",
            "Warehouse",
            item,
            quantity,
            currQuantity * getPrice(item)
        );

        return quantity;
    }

    @Override
    public int receiveOrderRequest(Order order) {
        return receiveOrderRequest(order.getProduct(), order.getQuantity());
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
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) {
                return (int) productsOnMarket[i].getPrice(); 
            }
        }
        return -1;
    }

    int getQuantity(Product item) {
        int id = item.getId();
        for (int i = 0; i < this.distinctProductCount; i++) {
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) { 
                return this.quantity[i];
            }
        }
        return 0;
    }

    int getQuantity(ProductP item) {
        int id = item.getId();
        for (int i = 0; i < this.distinctProductCount; i++) {
            if (productsOnMarket[i] != null && productsOnMarket[i].getId() == id) { 
                return this.quantity[i];
            }
        }
        return 0;
    }

    void reduceStock(Product item, int quantity) {
        int productInd = 0;
        for (productInd = 0; productInd < this.distinctProductCount; productInd++) {
            if (this.products[productInd].equals(item)) { 
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