import java.util.Random;
public class Supplier extends Person{

    Random rand = new Random();
    ProductP[] productsOnMarket;
    Supplier(String name,Product[] products, int[] quantity,int id,Transaction[] paymentHistory){
        super(name,products,quantity,id,paymentHistory);
        int len=products.length;
        this.productsOnMarket= new ProductP[this.distinctProductCount];
    }
    void createProductsOnMarket(){
        int boundedInt = 30 + rand.nextInt(70);
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
    int getPrice(Product item){
        int id=item.getId();
        for(int i=0;i<this.distinctProductCount;i++){
            if(productsOnMarket[i].getId()==id){
                return productsOnMarket[i].getPrice();
            }
        }
        return -1;
    }
}