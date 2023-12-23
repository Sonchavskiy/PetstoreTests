package dataModels;

import java.util.Date;

public class Order {
    public Object id;
    public Object petId;
    public Object quantity;
    public Date shipDate;
    public String status;
    public Boolean complete;

    public Order(Object id, Object petId, Object quantity, Date shipDate, String status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public Order() {
    }

    public static Order defaultOrder() {
        return new Order(1984,
                20,
                2,
                new Date(121212L),
                "placed",
                true);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate=" + shipDate +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }
}
