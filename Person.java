public abstract Person{
    String name;
    int id;
    Product[] products;
    int[] quantity;
    Transaction[] paymentHistory;
    int distinctProductCount;
    Person(String name,Product[] products, int[] quantity,int id,Transaction[] paymentHistory){
        this.name=name;
        this.id=id;
        this.products=products;
        this.quantity=quantity;
        this.paymentHistory=paymentHistory;
        this.distinctProductCount=products.length;
    }
    String stockUpdate(){
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