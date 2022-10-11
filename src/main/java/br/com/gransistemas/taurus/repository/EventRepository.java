package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.Event;
import br.com.gransistemas.taurus.model.EventType;
import br.com.gransistemas.taurus.repository.mapper.EventMapper;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

import java.util.List;

public class EventRepository extends Repository {
    public List<Event> findByDeviceId(long deviceId) throws Exception {
        final EventMapper mapper = new EventMapper();

        String sql = queryBuilder().select("e.*")
            .from("events e").where("e.device_id = :deviceId")
        .build();

        return getQuery().select(sql)
            .namedParam("deviceId", deviceId)
        .listResult(mapper::mapModel);
    }

    public long saveEventNotification(long deviceId, EventType eventType, long positionId) throws Exception {
        Query query = getQuery();

        String sql = new StringBuilder()
            .append("INSERT INTO ")
            .append("    notifications(device_id, event_type, position_id) ")
            .append("VALUES ")
            .append("    (:device_id, :event_type, :position_id)")
        .toString();

        return query.transaction().in(() -> {
            UpdateResultGenKeys<Long> keys = query.update(sql)
                .namedParam("device_id", deviceId)
                .namedParam("event_type", eventType)
                .namedParam("position_id", positionId)
            .runFetchGenKeys(Mappers.singleLong());

            return keys.generatedKeys().get(0);
        });
    }
}
