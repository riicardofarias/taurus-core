package br.com.gransistemas.taurus.repository.mapper;

import br.com.gransistemas.taurus.model.Event;
import br.com.gransistemas.taurus.model.EventType;
import br.com.gransistemas.taurus.model.EventZone;
import br.com.gransistemas.taurus.model.Zone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EventZoneMapper implements ModelMapper<EventZone> {
    @Override
    public EventZone mapModel(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getLong("event_id"));
        event.setEventType(EventType.valueOf(rs.getString("event_type")));

        Zone fence = new Zone();
        fence.setId(rs.getLong("fence_id"));
        fence.setArea(rs.getString("area"));

        EventZone eventFence = new EventZone();
        eventFence.setEvent(event);
        eventFence.setFence(fence);

        return eventFence;
    }

    @Override
    public Map<String, ?> createMap(EventZone model) {
        return null;
    }
}
