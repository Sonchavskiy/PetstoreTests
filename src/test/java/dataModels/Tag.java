package dataModels;

public class Tag {
    public Object id;
    public String name;

    public Tag(Object id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
