public class ProductP extends Product{
    double price;
    ProductP(String name,String Category,int id,double price){
        super(name,Category,id);
        this.price=price;
    }
    ProductP(String name,int id,double price){
        super(name,id);
        this.price=price;
    }
    int getPrice(){
        return this.price;
    }
}