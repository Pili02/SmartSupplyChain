public interface WarehouseOperations {
    Product[] getTargetProducts();
    Order[] getOrders();
    Supplier[] getSuppliers();
    void setSuppliers(Supplier[] suppliers);
    int[] getQuantities();
    void addOrder(Order item);
    void receiveRetailerRequest(Order... requests);
    void addStock(Product item, int quantity);
    void addStock(Product item, int... quantities);
    void fillOrders();
    double calculateProfitOrLoss();
    void displayProfitOrLoss();
    String getRole();
}
