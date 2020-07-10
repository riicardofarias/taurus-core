package br.com.gransistemas.taurus.handler.events;

import br.com.gransistemas.taurus.Context;
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
        Device device = Context.getDeviceManager()
            .getDeviceSession(position.getDeviceId())
        .orElseThrow(DeviceNotFoundException::new);

        Position lastPosition = Context.getDeviceManager()
            .getDevicePosition(device.getLastPositionId())
        .orElse(null);

        if(lastPosition == null)
            return null;

        int accuracy = 5;

        if(position.getSpeed() > accuracy && lastPosition.getSpeed() <= accuracy){
            return Collections.singletonMap(new Event(EventType.event_motion_on), position);
        }else if(position.getSpeed() <= accuracy && lastPosition.getSpeed() > accuracy){
            return Collections.singletonMap(new Event(EventType.event_motion_stop), position);
        }

        return null;
    }
}
