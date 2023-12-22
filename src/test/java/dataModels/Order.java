package dataModels;

import java.util.Date;

public class Order {
    public Integer id;
    public Integer petId;
    public Integer quantity;
    public Date shipDate;
    public String status;
    public Boolean complete;

    public Order(Integer id, Integer petId, Integer quantity, Date shipDate, String status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
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
