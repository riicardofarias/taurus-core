package br.com.gransistemas.taurus.handler.events;

import br.com.gransistemas.taurus.database.DeviceManager;
import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.handler.BaseDataHandler;
import br.com.gransistemas.taurus.helpers.GeoPoint;
import br.com.gransistemas.taurus.helpers.GetIt;
import br.com.gransistemas.taurus.model.*;
import br.com.gransistemas.taurus.repository.EventRepository;
import br.com.gransistemas.taurus.repository.ZoneRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ChannelHandler.Sharable
public class ZoneEventHandler extends BaseDataHandler {
    private final Logger logger = LoggerFactory.getLogger(ZoneEventHandler.class.getName());

    @Override
    protected Position handlePosition(Channel channel, Position position) throws Exception {
        Device device = getDeviceManager()
            .getById(position.getDeviceId())
        .orElseThrow(DeviceNotFoundException::new);

        List<EventZone> zones = getZoneRepository().findByDeviceId(device.getId());
        if(zones.isEmpty())
            return null;

        Position lastPosition = getDeviceManager()
            .getPosition(device.getLastPositionId())
        .orElse(null);

        if(lastPosition == null)
            return position;

        GeoPoint polygon = new GeoPoint();
        Coordinate point = new Coordinate(position.getLatitude(), position.getLongitude());
        Coordinate lastPoint = new Coordinate(lastPosition.getLatitude(), lastPosition.getLongitude());

        Map<EventType, Position> events = new HashMap<>();

        for(EventZone eventZone: zones){
            final Zone zone = eventZone.getZone();
            final EventType eventType = eventZone.getEventType();

            if(eventType == EventType.GEOFENCE_ENTER){
                if(polygon.isInPolygon(zone.getArea(), point) && !polygon.isInPolygon(zone.getArea(), lastPoint)){
                    events.put(eventType, position);
                }
            }

            if(eventType == EventType.GEOFENCE_EXIT){
                if (!polygon.isInPolygon(zone.getArea(), point) && polygon.isInPolygon(zone.getArea(), lastPoint)) {
                    events.put(eventType, position);
                }
            }
        }

        for(Map.Entry<EventType, Position> entry: events.entrySet()) {
            getEventRepository().saveEventNotification(
                position.getDeviceId(), entry.getKey(), position.getId()
            );

            logger.info(String.format("[%s]: event detected [%s]", channel.id().asShortText(), entry.getKey()));
        }

        return position;
    }

    DeviceManager getDeviceManager() {
        return GetIt.get(DeviceManager.class);
    }
    ZoneRepository getZoneRepository(){
        return GetIt.get(ZoneRepository.class);
    }
    EventRepository getEventRepository(){
        return GetIt.get(EventRepository.class);
    }
}
