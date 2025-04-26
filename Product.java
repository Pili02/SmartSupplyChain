public class Product {
    private String name;
    private String category;
    private int id;

    // Constructor
    public Product(String name, String category, int id) {
        this.name = name;
        this.category = category;
        this.id = id;
    }

    // Overloaded Constructor
    public Product(String name, int id) {
        this.name = name;
        this.category = "Miscellaneous";
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return this.name;
    }

    // Getter for category
    public String getCategory() {
        return this.category;
    }

    // Getter for id
    public int getId() {
        return this.id;
    }

    // Equals method
    public boolean equals(Product obj) {
        return (obj.id == this.id);
    }
}