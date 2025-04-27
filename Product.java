public class Product {
    private String name;
    private String category;
    private int id;


    public Product(String name, String category, int id) {
        this.name = name;
        this.category = category;
        this.id = id;
    }


    public Product(String name, int id) {
        this.name = name;
        this.category = "Miscellaneous";
        this.id = id;
    }

    public String getName() {
        return this.name;
    }


    public String getCategory() {
        return this.category;
    }


    public int getId() {
        return this.id;
    }


    public boolean equals(Product obj) {
        return (obj.id == this.id);
    }
}