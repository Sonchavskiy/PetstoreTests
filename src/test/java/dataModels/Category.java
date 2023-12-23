package dataModels;

public class Category {
    public Object id;
    public String name;

    public Category(Object id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
