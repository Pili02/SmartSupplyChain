public class ProductP extends Product {
    double price;

    ProductP(String name, String category, int id, double price) {
        super(name, category, id);
        this.price = price;
    }

    ProductP(String name, int id, double price) {
        super(name, id);
        this.price = price;
    }

    double getPrice() {
        return this.price;
    }

    public String getProductName() {
        return this.getName();
    }
}