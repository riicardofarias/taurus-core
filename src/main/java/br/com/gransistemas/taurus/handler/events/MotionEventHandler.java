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
public class MotionEventHandler extends BaseEventHandler {
    @Override
    protected Map<Event, Position> analyzeEvent(Channel channel, Position position) {
        Device device = getDeviceManager()
            .getById(position.getDeviceId())
        .orElseThrow(DeviceNotFoundException::new);

        Position lastPosition = getDeviceManager()
            .getPosition(device.getLastPositionId())
        .orElse(null);

        int accuracy = 5;

        if(lastPosition == null){
            return Collections.singletonMap(new Event(position.getSpeed() > accuracy ? EventType.MOTION_ON : EventType.MOTION_STOP), position);
        }

        if(position.getSpeed() > accuracy && lastPosition.getSpeed() <= accuracy){
            return Collections.singletonMap(new Event(EventType.MOTION_ON), position);
        }else if(position.getSpeed() <= accuracy && lastPosition.getSpeed() > accuracy){
            return Collections.singletonMap(new Event(EventType.MOTION_STOP), position);
        }

        return null;
    }
}
