public class Product{
    String name;
    String Category;
    int id;
    Product(String name,String Category,int id){
        this.name=name;
        this.Category=Category;
        this.id=id;
    }
    Product(String name,int id){
        this.name=name;
        this.Category="Miscellaneous";
        this.id=id;
    }
    boolean equals(Product obj){
        if(obj.id==this.id){
            return true;
        }
        return false;
    }
    int getId(){
        return this.id;
    }
}