public class Transaction {
    private String name;
    private String receiver;
    private Product product;
    private int amount;
    private double price;

    // Constructor
    public Transaction(String name, String receiver, Product product, int amount, double price) {
        this.name = name;
        this.receiver = receiver;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    // Getter for name
    public String getName() {
        return this.name;
    }

    // Getter for receiver
    public String getReceiver() {
        return this.receiver;
    }

    // Getter for product
    public Product getProduct() {
        return this.product;
    }

    // Getter for amount
    public int getAmount() {
        return this.amount;
    }

    // Getter for price
    public double getPrice() {
        return this.price;
    }
}