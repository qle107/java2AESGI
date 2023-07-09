package org.esgi.cookmaster.database;

import com.github.javafaker.Faker;

import java.util.Random;

public class EventStat {
    public enum Type{
        CONFERENCES,
        WORKSHOPS,
        EXHIBITIONS
    }
    public enum Time{
        DAILY,
        WEEKLY,
        MONTHLY
    }
    private String name;
    private EventStat.Type eventType;
    private EventStat.Time eventTime;
    private int participation;

    public EventStat() {
        Type[] eventTypes = EventStat.Type.values();
        int randomIndex = new Random().nextInt(eventTypes.length);
        this.eventType = eventTypes[randomIndex];

        Time[] eventTime = EventStat.Time.values();
        randomIndex = new Random().nextInt(eventTime.length);
        this.eventTime = eventTime[randomIndex];

        randomIndex = new Random().nextInt(90);
        this.participation = randomIndex+10;

        this.name = new Faker().funnyName().name();
    }

    public Type getEventType() {
        return eventType;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public int getParticipation() {
        return participation;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "EventStat{" +
                "eventType=" + eventType +
                ", eventTime=" + eventTime +
                ", participation=" + participation +
                '}';
    }
}
