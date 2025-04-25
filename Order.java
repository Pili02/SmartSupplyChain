public class Order{
    int quantity;
    int totalPayout;
    ProductP product;
    Order(ProductP product,int quantity){
        this.product=product;
        this.quantity=quantity;
        this.totalPayout=quantity*price;
    }
    int getQuantity(){
        return this.quantity;
    }
    int getTotalPayout(){
        return this.totalPayout;
    }
    ProductP getProduct(){
        return this.product;
    }
}