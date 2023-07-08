package org.esgi.cookmaster.database;

public class User {
    private String id;
    private String email;
    private String phone;
    private String lastName;
    private String firstName;
    private String address;
    private String role;
    private Subscription subscription = new Subscription();

    public void defineUser(String id) {
        ConnectDatabase testDb = new ConnectDatabase();
        testDb.importConfig("src/config.cfg");
        testDb.connect();
        createUser(testDb.getUser(id));
    }
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", subscription=" + subscription +
                '}';
    }

    protected User(String id, String email, String phone, String lastName, String firstName, String address, String role, String subscription_id) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.role = role;
        this.subscription.defineSubscription(subscription_id);
    }

    protected void createUser(User insertUser) {
        this.id = insertUser.id;
        this.email = insertUser.email;
        this.phone = insertUser.phone;
        this.lastName = insertUser.lastName;
        this.firstName = insertUser.firstName;
        this.address = insertUser.address;
        this.role = insertUser.role;
        this.subscription = insertUser.subscription;
    }
}
