package dataModels;

public class User {
    public Object id;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phone;
    public Object userStatus;

    public User(Object id, String username, String firstName, String lastName, String email, String password, String phone, Object userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    public User() {
    }

    public static User defaultUser() {
        return new User(1984,
                "ivan",
                "ivanovich",
                "ivanov",
                "i.i.ivanov@gmail.com",
                "123456",
                "88888888888",
                0);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
