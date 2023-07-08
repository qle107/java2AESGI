package org.esgi.cookmaster.database;

public class Room {
    private String id;
    private String name;
    private String address;
    private String max_capacity;
    private String price;
    private String availability;

    public void defineRoom(String id) {
        ConnectDatabase testDb = new ConnectDatabase();
        testDb.importConfig("src/config.cfg");
        testDb.connect();
        createRoom(testDb.getRoom(id));
    }
    public Room() {
    }

    @Override
    public String toString() {
        return name + " - " + address ;
    }

    public Room(String id, String name, String address, String max_capacity, String price, String availability) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.max_capacity = max_capacity;
        this.price = price;
        this.availability = availability;
    }

    protected void createRoom(Room insertRoom) {
        this.id = insertRoom.id;
        this.name = insertRoom.name;
        this.address = insertRoom.address;
        this.max_capacity = insertRoom.max_capacity;
        this.price = insertRoom.price;
        this.availability = insertRoom.availability;
    }
}
