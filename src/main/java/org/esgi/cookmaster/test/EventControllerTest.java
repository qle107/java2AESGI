package org.esgi.cookmaster.test;

import org.esgi.cookmaster.controller.EventController;
import org.esgi.cookmaster.controller.UserController;
import org.esgi.cookmaster.database.Event;
import org.esgi.cookmaster.database.User;

import java.sql.SQLException;
import java.util.List;

public class EventControllerTest {
    public static void main(String[] args) throws SQLException {
        EventController testEventController = new EventController();
        List<Event> storedEvent = testEventController.getLastEvents(0);
        for (Event event : storedEvent) {
            System.out.println(event);
        }
    }
}
