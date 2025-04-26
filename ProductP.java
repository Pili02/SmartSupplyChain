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

    ProductP(Product item, double price) {
        super(item.getName(), item.getId());
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getProductName() {
        return this.getName();
    }
}