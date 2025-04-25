public class WarehouseManager extends Person{
    Product[] targetProducts;
    Order[] orders;
    Supplier[] suppliers;
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
        ProductP[] newOrder=new ProductP[len+1];
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
    void addStock(Product item,int quantity){
        int prodind=0;
        for(prodind=0;prodind<this.distinctProductCount;prodind++){
            if(this.products[prodind].equals(item)){
                break;
            }
        }
        int len=this.products.length;
        if(prodind==len){
            return;
        }
        this.quantity[prodind]+=quantity;
        return;
    }
    void fillOrders(){
        for(Order o:this.orders){
            ProductP targetProduct=o.getProduct();
            int targetQuantity=o.getQuantity();
            
            while(targetQuantity>0){
                Supplier potentialSupplier=null;
                int minPrice=1e9;
                for(Supplier s:this.suppliers){
                    int supplierPrice=s.getPrice(targetProduct);
                    if(supplierPrice==-1 || s.getQuantity(targetProduct)<=0) {
                        continue;
                    }
                    if(supplierPrice<minPrice){
                        potentialSupplier=s;
                        minPrice=supplierPrice;
                    }
                }
                if(potentialSupplier==null){// we should look at the case where we dont have supplier
                    break;
                }
                int currQuantity=potentialSupplier.getQuantity(targetProduct);
                potentialSupplier.reduceStock(targetProduct,min(currQuantity,targetQuantity));
                targetQuantity-=min(currQuantity,targetQuantity);
            }
            
        }
    }
}