package dataModels;

public class ApiResponse {
    public Integer code;
    public String type;
    public String message;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
