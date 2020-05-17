package de.staticred.server.objects;

import org.bukkit.entity.Player;

public class Event {

    private Player executer;
    private long timeStamp;
    private EventType eventType;

    public Event(Player executer, long timeStamp, EventType eventType) {
        this.executer = executer;
        this.timeStamp = timeStamp;
        this.eventType = eventType;
    }

    public Player getExecuter() {
        return executer;
    }

    public void setExecuter(Player executer) {
        this.executer = executer;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
