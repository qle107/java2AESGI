package org.esgi.cookmaster.controller;

import org.esgi.cookmaster.database.ConnectDatabase;
import org.esgi.cookmaster.database.Event;
import org.esgi.cookmaster.database.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventController {
    protected ConnectDatabase connectDb = new ConnectDatabase();

    public Event getEvent(String id) {
        connectDb.importConfig("src/config.cfg");
        connectDb.connect();
        return connectDb.getEvent(id);
    }

    public List<Event> getLastEvents(int limited) throws SQLException {
        List<Event> storedEvent = new ArrayList<>();
        connectDb.importConfig("src/config.cfg");
        connectDb.connect();
        ResultSet resultSet = connectDb.executeQuery("SELECT COUNT(*) AS total_events FROM "+connectDb.getEventTable());
        int totalEvents = 0;
        if (resultSet.next()) {
            totalEvents = resultSet.getInt("total_events");

        }
        int totalQuery = 0;

        if (limited < totalEvents && limited > 0) {
            totalQuery = totalEvents - limited;
        }

        for (int countEvent = totalEvents; countEvent > totalQuery; countEvent--) {
            Event storedEventToArray = connectDb.getEvent(String.valueOf(countEvent));
            storedEvent.add(storedEventToArray);
        }
        return storedEvent;
    }
}
