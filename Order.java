public class Order extends ProductP{
    int quantity;
    int totalPayout;
    Order(String name,String Category,int id,double price,int quantity){
        super(name,Category,id,price);
        this.quantity=quantity;
        this.totalPayout=quantity*price;
    }
    int getQuantity(){
        return this.quantity;
    }
    int getTotalPayout(){
        return this.totalPayout;
    }
}