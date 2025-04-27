public class Transaction {
    private String name;
    private String receiver;
    private Product product;
    private int amount;
    private double price;


    public Transaction(String name, String receiver, Product product, int amount, double price) {
        this.name = name;
        this.receiver = receiver;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }


    public String getName() {
        return this.name;
    }


    public String getReceiver() {
        return this.receiver;
    }


    public Product getProduct() {
        return this.product;
    }


    public int getAmount() {
        return this.amount;
    }

    public double getPrice() {
        return this.price;
    }
}