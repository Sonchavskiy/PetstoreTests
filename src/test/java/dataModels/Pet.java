package dataModels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pet {
    public Object id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;

    public Pet(Integer id, Category category, String name, List<String> photoUrls, List<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Pet() {
    }

    public static Pet defaultPet() {
        return new Pet(1984,
                new Category(1, "dog"),
                "puppy",
                Arrays.asList("some/url",
                        "someother/url"),
                Arrays.asList(new Tag(1, "white dog"),
                        new Tag(2, "labrador")),
                "available");
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }
}
