package br.com.gransistemas.taurus.handler.events;

import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Event;
import br.com.gransistemas.taurus.model.EventType;
import br.com.gransistemas.taurus.model.Position;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import java.util.Collections;
import java.util.Map;

@ChannelHandler.Sharable
public class OverSpeedEventHandler extends BaseEventHandler {
    @Override
    protected Map<Event, Position> analyzeEvent(Channel channel, Position position) throws Exception {
        Device device = getDeviceManager().getById(position.getDeviceId()).orElseThrow(
            DeviceNotFoundException::new
        );

        Event event = getEventRepository().findByDeviceId(device.getId()).stream()
            .filter(e -> e.getEventType() == EventType.OVER_SPEED)
        .findFirst().orElse(null);

        if(event == null || event.getSpeedLimit() == 0)
            return null;

        /*
        Position lastPosition = getDeviceManager()
            .getPosition(device.getLastPositionId())
        .orElse(null);

        if(lastPosition == null) {
            if(position.getSpeed() > event.getSpeedLimit()) {
                return Collections.singletonMap(new Event(EventType.OVER_SPEED), position);
            }

            return null;
        }
         */

        if(position.getSpeed() > event.getSpeedLimit()/* && lastPosition.getSpeed() <= event.getSpeedLimit()*/){
            return Collections.singletonMap(new Event(EventType.OVER_SPEED), position);
        }

        return null;
    }
}
