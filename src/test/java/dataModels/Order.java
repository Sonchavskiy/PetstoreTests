package dataModels;

import java.util.Date;

public class Order {
    public Integer id;
    public Integer petId;
    public Integer quantity;
    public Date shipDate;
    public String status;
    public Boolean complete;

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
