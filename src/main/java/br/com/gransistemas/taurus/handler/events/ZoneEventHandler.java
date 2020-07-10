package br.com.gransistemas.taurus.handler.events;

import br.com.gransistemas.taurus.Context;
import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.helpers.GeoPoint;
import br.com.gransistemas.taurus.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ChannelHandler.Sharable
public class ZoneEventHandler extends BaseEventHandler {
    @Override
    protected Map<Event, Position> analyzeEvent(Channel channel,  Position position) throws Exception {
        Device device = Context.getDeviceManager()
            .getDeviceSession(position.getDeviceId())
        .orElseThrow(DeviceNotFoundException::new);

        List<EventZone> zones = getZoneRepository().findByDeviceId(device.getId());
        if(zones.isEmpty())
            return null;

        Position lastPosition = Context.getDeviceManager()
            .getDevicePosition(device.getLastPositionId())
        .orElse(null);

        if(lastPosition == null)
            return null;

        GeoPoint polygon = new GeoPoint();
        Coordinate point = new Coordinate(position.getLatitude(), position.getLongitude());
        Coordinate lastPoint = new Coordinate(lastPosition.getLatitude(), lastPosition.getLongitude());

        Map<Event, Position> events = new HashMap<>();

        for(EventZone eventZone: zones){
            final Zone zone = eventZone.getZone();
            final Event event = eventZone.getEvent();

            if(event.getEventType() == EventType.event_geofence_enter){
                if(polygon.isInPolygon(zone.getArea(), point) && !polygon.isInPolygon(zone.getArea(), lastPoint)){
                    events.put(event, position);
                }
            }

            if(event.getEventType() == EventType.event_geofence_exit){
                if (!polygon.isInPolygon(zone.getArea(), point) && polygon.isInPolygon(zone.getArea(), lastPoint)) {
                    events.put(event, position);
                }
            }
        }

        return events;
    }
}
