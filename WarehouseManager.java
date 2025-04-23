public class WarehouseManager extends Person{
    Product[] targetProducts;
    Order[] orders;
    WarehouseManager(String name,Product[] products, int[] quantity,int id,Transaction[] paymentHistory){
        super(name,products,quantity,id,paymentHistory);
    }
    int receiveOrderRequest(Product item, int quantity){
        boolean f=0;
        int productInd=0;
        for(productInd=0;productInd<this.distinctProductCount;productInd++){
            if(p.equals(item)){
                f=1;
                break;
            }
        }
        if(f==0){
            return 0;
        }
        int currQuantity=this.quantity[productInd];
        if(currQuantity<quantity){
            this.quantity[productInd]=0;
            return currQuantity;
        }
        currQuantity-=quantity;
        this.quantity[productInd]=currQuantity;
        //Need to update transaction history
        return quantity;
    }
    void addOrder(Order item){
        int len=this.orders.length;
        ProductP newOrder=new ProductP[len+1];
        for(int i=0;i<len;i++){
            newOrder=this.orders[i];
        }
        newOrder[len]=item;
        this.orders=newOrder;
    }
    void receiveRetailerRequest(Order requests ...){
        for(Order p: requests){
            this.addOrder(p);
        }
    }
}