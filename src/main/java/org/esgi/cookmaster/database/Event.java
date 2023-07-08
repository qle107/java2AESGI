package org.esgi.cookmaster.database;

public class Event {
    private String id;
    private String name;
    private String max_capacity;
    private String description;
    private String type;
    private String start_time;
    private String end_time;
    private Room room = new Room();

    public void defineEvent(String id) {
        ConnectDatabase testDb = new ConnectDatabase();
        testDb.importConfig("src/config.cfg");
        testDb.connect();
        createEvent(testDb.getEvent(id));
    }
    public Event() {
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", max_capacity='" + max_capacity + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", room='" + room + '\''+
                '}';
    }

    protected Event(String id, String name, String max_capacity, String description, String type, String start_time, String end_time, String room_id) {
        this.id = id;
        this.name = name;
        this.max_capacity = max_capacity;
        this.description = description;
        this.type = type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.room.defineRoom(room_id);
    }

    protected void createEvent(Event insertEvent) {
        this.id = insertEvent.id;
        this.name = insertEvent.name;
        this.max_capacity = insertEvent.max_capacity;
        this.description = insertEvent.description;
        this.type = insertEvent.type;
        this.start_time = insertEvent.start_time;
        this.end_time = insertEvent.end_time;
        this.room= insertEvent.room;
    }
}
