package br.com.gransistemas.taurus.handler.events;

import br.com.gransistemas.taurus.Context;
import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import java.util.Collections;
import java.util.Map;

@ChannelHandler.Sharable
public class OverSpeedEventHandler extends BaseEventHandler {
    @Override
    protected Map<Event, Position> analyzeEvent(Channel channel, Position position) throws Exception {
        Device device = Context.getDeviceManager().getDeviceSession(position.getDeviceId()).orElseThrow(
            DeviceNotFoundException::new
        );

        Event event = getEventRepository().findByDeviceId(device.getId()).stream()
            .filter(e -> e.getEventType() == EventType.event_overspeed)
        .findFirst().orElse(null);

        if(event == null || event.getSpeedLimit() == 0)
            return null;

        Position lastPosition = Context.getDeviceManager()
            .getDevicePosition(device.getLastPositionId())
        .orElse(null);

        if(lastPosition == null) {
            return null;
        }

        if(position.getSpeed() > event.getSpeedLimit() && lastPosition.getSpeed() <= event.getSpeedLimit()){
            return Collections.singletonMap(new Event(EventType.event_overspeed), position);
        }

        return null;
    }
}
