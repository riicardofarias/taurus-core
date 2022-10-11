package br.com.gransistemas.taurus.repository.mapper;

import br.com.gransistemas.taurus.model.EventType;
import br.com.gransistemas.taurus.model.EventZone;
import br.com.gransistemas.taurus.model.Zone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EventZoneMapper implements ModelMapper<EventZone> {
    @Override
    public EventZone mapModel(ResultSet rs) throws SQLException {
        Zone zone = new Zone();
        zone.setId(rs.getLong("zone_id"));
        zone.setArea(rs.getString("area"));

        EventZone eventZone = new EventZone();
        eventZone.setEventType(EventType.valueOf(rs.getString("event_type")));
        eventZone.setFence(zone);

        return eventZone;
    }

    @Override
    public Map<String, ?> createMap(EventZone model) {
        return null;
    }
}
