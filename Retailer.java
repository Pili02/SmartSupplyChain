public class Retailer extends Person{
    WarehouseManager warehouseManager;
    Retailer(String name,Product[] products, int[] quantity,int id,Transaction[] paymentHistory){
        super(name,products,quantity,id,paymentHistory);
    }
    void sendOrderRequest(Product item, int quantity) {
        int fulfilledQuantity = warehouseManager.receiveOrderRequest(item, quantity);

        if (fulfilledQuantity > 0) {
            System.out.println("Order sent: " + fulfilledQuantity + " units of " + item.getName());
            updateInventory(item, fulfilledQuantity); // updating retailer's stock
        } else {
            System.out.println("Order could not be fulfilled for: " + item.getName());
        }
    }

    void updateInventory(Product item, int quantityToAdd) {
        for (int i = 0; i < this.product.length; i++) {
            if (this.products[i].equals(item)) {
                this.quantity[i] += quantityToAdd;
                return;
            }
        }
    }
    void addTransaction(String name, String receiver, Product product, int amount, double price) {
        Transaction newTransaction = new Transaction();
        newTransaction.name = name;
        newTransaction.receiver = receiver;
        newTransaction.product = product;
        newTransaction.amount = amount;
        newTransaction.price = price;

        for (int i = 0; i < paymentHistory.length; i++) {
            if (paymentHistory[i] == null) {
                paymentHistory[i] = newTransaction;
                return;
            }
        } // should paymentHistory array be limited in size?
    // System.out.println("Transaction history is full. Cannot add more.");
    }
}