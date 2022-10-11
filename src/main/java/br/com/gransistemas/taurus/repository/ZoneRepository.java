package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.EventZone;
import br.com.gransistemas.taurus.repository.mapper.EventZoneMapper;

import java.util.List;

public class ZoneRepository extends Repository {
    public List<EventZone> findByDeviceId(long deviceId) throws Exception {
        final EventZoneMapper mapper = new EventZoneMapper();

        String sql = queryBuilder().select("ef.zone_id", "z.area", "ef.event_type").distinct()
            .from("events_zones ef")
            .innerJoin("zones z", "z.id", "ef.zone_id")
            .where("ef.device_id = :deviceId")
        .build();

        return getQuery().select(sql)
            .namedParam("deviceId", deviceId)
        .listResult(mapper::mapModel);
    }
}