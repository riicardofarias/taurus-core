package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.Event;
import br.com.gransistemas.taurus.repository.mapper.EventMapper;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

import java.util.List;

public class EventRepository extends Repository {
    public List<Event> findByDeviceId(long deviceId) throws Exception {
        final EventMapper mapper = new EventMapper();

        return queryBuilder().select("SELECT e.* FROM event e WHERE exists(SELECT 1 FROM device_event de WHERE de.event_id = e.id AND de.device_id = :deviceId)")
            .namedParam("deviceId", deviceId)
        .listResult(mapper::mapModel);
    }

    public long saveEventNotification(long deviceId, long eventId, long positionId) throws Exception {
        Query query = queryBuilder();

        String sql = new StringBuilder()
            .append("INSERT INTO ")
            .append("    device_event_notification(device_id, event_id, position_id) ")
            .append("VALUES ")
            .append("    (:device_id, :event_id, :position_id)")
        .toString();

        return query.transaction().in(() -> {
            UpdateResultGenKeys<Long> keys = query.update(sql)
                .namedParam("device_id", deviceId)
                .namedParam("event_id", eventId)
                .namedParam("position_id", positionId)
            .runFetchGenKeys(Mappers.singleLong());

            return keys.generatedKeys().get(0);
        });
    }
}
