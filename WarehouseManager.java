public class WarehouseManager extends Person implements OrderHandler, WarehouseOperations {
    private Product[] targetProducts;
    private Order[] orders;
    private Supplier[] suppliers;
    private double totalCost = 0.0;
    private double totalRevenue = 0.0; 

    WarehouseManager(String name, Product[] products, int[] quantity, int id, Transaction[] paymentHistory) {
        super(name, products, quantity, id, paymentHistory);
        this.targetProducts = products; 
        this.suppliers = new Supplier[0]; 
    }

    public Product[] getTargetProducts() {
        return this.targetProducts;
    }

    public Order[] getOrders() {
        return this.orders;
    }

    public Supplier[] getSuppliers() {
        return this.suppliers;
    }


    public void setSuppliers(Supplier[] suppliers) {
        this.suppliers = suppliers;
    }

    public int[] getQuantities() {
        return this.quantity;
    }

    @Override
    public int receiveOrderRequest(Order order) {
        Product targetProduct = order.getProduct();
        int targetQuantity = order.getQuantity();
        boolean found = false;
        int productInd = 0;

        for (productInd = 0; productInd < this.distinctProductCount; productInd++) {
            if (this.products[productInd].equals(targetProduct)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return 0;
        }

        int currQuantity = this.quantity[productInd];
        int fulfilledQuantity = 0;
        if (currQuantity < targetQuantity) {
            int remainingQuantity = targetQuantity - currQuantity;
            this.quantity[productInd] = 0;
            fulfilledQuantity += currQuantity;


            while (remainingQuantity > 0) {
                Supplier potentialSupplier = null;
                int minPrice = Integer.MAX_VALUE;
                for (Supplier s : this.suppliers) {
                    int supplierPrice = s.getPrice(targetProduct);
                    if (supplierPrice == -1 || s.getQuantity(targetProduct) <= 0) {
                        continue;
                    }
                    if (supplierPrice < minPrice) {
                        potentialSupplier = s;
                        minPrice = supplierPrice;
                    }
                }
                if (potentialSupplier == null) {
                    break;
                }
                int supplierQuantity = potentialSupplier.getQuantity(targetProduct);
                int quantityToAcquire = Math.min(supplierQuantity, remainingQuantity);
                potentialSupplier.reduceStock(targetProduct, quantityToAcquire);
                remainingQuantity -= quantityToAcquire;
                fulfilledQuantity += quantityToAcquire;
                this.totalCost += quantityToAcquire * minPrice;
            }
        } else {
            this.quantity[productInd] -= targetQuantity;
            fulfilledQuantity = targetQuantity;
        }

        this.totalRevenue += fulfilledQuantity * ((ProductP) targetProduct).getPrice();
        return fulfilledQuantity;
    }

    @Override
    public void addOrder(Order item) {
        int len = (this.orders == null) ? 0 : this.orders.length; 
        Order[] newOrder = new Order[len + 1]; 
        for (int i = 0; i < len; i++) {
            newOrder[i] = this.orders[i];
        }
        newOrder[len] = item;
        this.orders = newOrder;
    }

    @Override
    public void receiveRetailerRequest(Order... requests) { 
        for (Order p : requests) {
            this.addOrder(p);
        }
    }

    @Override
    public void addStock(Product item, int quantity) {
        int prodind = 0;
        for (prodind = 0; prodind < this.distinctProductCount; prodind++) {
            if (this.products[prodind].equals(item)) {
                break;
            }
        }
        int len = this.products.length;
        if (prodind == len) {
            return;
        }
        this.quantity[prodind] += quantity;
        return;
    }

    @Override
    public void addStock(Product item, int... quantities) {
        for (int q : quantities) {
            addStock(item, q);
        }
    }

    @Override
    public void fillOrders() {
        if (this.suppliers == null || this.suppliers.length == 0) {
            System.out.println("No suppliers available to fill orders.");
            return;
        }
        for (Order o : this.orders) {
            ProductP targetProduct = o.getProduct();
            int targetQuantity = o.getQuantity();

            while (targetQuantity > 0) {
                Supplier potentialSupplier = null;
                int minPrice = Integer.MAX_VALUE;
                for (Supplier s : this.suppliers) {
                    int supplierPrice = s.getPrice(targetProduct);
                    if (supplierPrice == -1 || s.getQuantity(targetProduct) <= 0) {
                        continue;
                    }
                    if (supplierPrice < minPrice) {
                        potentialSupplier = s;
                        minPrice = supplierPrice;
                    }
                }
                if (potentialSupplier == null) {
                    System.out.println("Unable to fulfill order for " + targetProduct.getProductName());
                    break;
                }
                int currQuantity = potentialSupplier.getQuantity(targetProduct);
                int quantityToReduce = Math.min(currQuantity, targetQuantity);
                potentialSupplier.reduceStock(targetProduct, quantityToReduce);
                targetQuantity -= quantityToReduce;


                this.totalCost += quantityToReduce * minPrice;
            }


            this.totalRevenue += o.getQuantity() * targetProduct.getPrice();
        }
    }


    @Override
    public double calculateProfitOrLoss() {

        return totalRevenue - totalCost;
    }

    @Override
    public void displayProfitOrLoss() {
        double profitOrLoss = calculateProfitOrLoss();
        if (profitOrLoss > 0) {
            System.out.println("Profit: $" + profitOrLoss);
        } else if (profitOrLoss < 0) {
            System.out.println("Loss: $" + Math.abs(profitOrLoss));
        } else {
            System.out.println("No profit or loss.");
        }
    }
    
    @Override
    public String getRole() {
        return "Warehouse Manager";
    }
}