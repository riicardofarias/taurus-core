package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.EventZone;
import br.com.gransistemas.taurus.repository.mapper.EventZoneMapper;

import java.util.List;

public class ZoneRepository extends Repository {
    public List<EventZone> findByDeviceId(long deviceId) throws Exception {
        final EventZoneMapper mapper = new EventZoneMapper();

        final String sql = new StringBuilder().append(
            "SELECT DISTINCT " +
                "f.id fence_id, " +
                "f.area, " +
                "e.id event_id, " +
                "e.event_type " +
            "FROM " +
                "device_event de " +
            "INNER JOIN event_fence ef ON de.event_id = ef.event_id " +
            "INNER JOIN event e ON e.id = de.event_id " +
            "INNER JOIN fence f ON ef.fence_id = f.id " +
            "WHERE " +
                "de.device_id = :deviceId"
        ).toString();

        return queryBuilder().select(sql)
            .namedParam("deviceId", deviceId)
        .listResult(mapper::mapModel);
    }
}