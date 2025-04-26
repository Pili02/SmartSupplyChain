public class Retailer extends Person {
    WarehouseManager warehouseManager;

    Retailer(String name, Product[] products, int[] quantity, int id, Transaction[] paymentHistory) {
        super(name, products, quantity, id, paymentHistory);
    }

    void sendOrderRequest(Order order) {
        int fulfilledQuantity = warehouseManager.receiveOrderRequest(order);

        if (fulfilledQuantity > 0) {
            System.out.println("Order sent: " + fulfilledQuantity
                + " units of " + order.getProduct().getName());
            updateInventory(order.getProduct(), fulfilledQuantity);
        } else {
            System.out.println("Order could not be fulfilled for: "
                + order.getProduct().getName());
        }
    }

    void sendOrderRequest(ProductP item, int orderQuantity) {
        Order newOrder = new Order(item, orderQuantity);
        sendOrderRequest(newOrder);
    }

    void updateInventory(ProductP item, int quantityToAdd) {
        for (int i = 0; i < this.products.length; i++) {
            if (this.products[i].getId()==(item.getId())) {
                this.quantity[i] += quantityToAdd;
                return;
            }
        }
    }

    void addTransaction(String name, String receiver, Product product, int amount, double price) {
        Transaction transaction = new Transaction(name, receiver, product, amount, price); // Use parameterized constructor
        // Add the transaction to the retailer's transaction history
        for (int i = 0; i < this.paymentHistory.length; i++) {
            if (this.paymentHistory[i] == null) {
                this.paymentHistory[i] = transaction;
                break;
            }
        }
    }
}