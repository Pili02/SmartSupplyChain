public abstract class Person {
    protected String name;
    protected int id;
    protected Product[] products;
    protected int[] quantity;
    protected Transaction[] paymentHistory;
    protected int distinctProductCount;

    Person(String name, Product[] products, int[] quantity, int id, Transaction[] paymentHistory) {
        this.name = name;
        this.id = id;
        this.products = products;
        this.quantity = quantity;
        this.paymentHistory = paymentHistory;
        this.distinctProductCount = products.length;
    }
    public abstract String getRole();

    public String getName() {
        return this.name;
    }

    

    String stockUpdate() {
        StringBuilder stockDetails = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            stockDetails.append(products[i].getName())
                        .append(": ")
                        .append(quantity[i])
                        .append("\n");
        }
        return stockDetails.toString();
    }
}