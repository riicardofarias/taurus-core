package br.com.gransistemas.taurus.repository.mapper;

import br.com.gransistemas.taurus.model.Event;
import br.com.gransistemas.taurus.model.EventType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EventMapper implements ModelMapper<Event> {
    @Override
    public Event mapModel(ResultSet rs) throws SQLException {
        Event event = new Event();

        event.setId(rs.getLong("id"));
        event.setSpeedLimit(rs.getDouble("speed_limit"));
        event.setEventType(EventType.valueOf(rs.getString("event_type")));

        return event;
    }

    @Override
    public Map<String, ?> createMap(Event model) {
        return null;
    }
}
