public class Order {
    private int quantity;
    private double totalPayout;
    private ProductP product;

    Order(ProductP product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPayout = quantity * product.getPrice();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getTotalPayout() {
        return this.totalPayout;
    }

    public ProductP getProduct() {
        return this.product;
    }
}