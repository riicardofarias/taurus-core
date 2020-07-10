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
public class IgnitionEventHandler extends BaseEventHandler {
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

        boolean beforeIgnition = lastPosition.getKeyIgnition() == 1;
        boolean isIgnitionOn = position.getKeyIgnition() == 1;

        // Se o status anterior for desligado e o status atual for ligado a chave está ligada
        if(!beforeIgnition && isIgnitionOn){
            return Collections.singletonMap(new Event(EventType.event_key_ignition_on), position);
        // Se o status atual for desligado e o status anterior for ligado a chave está desligada
        }else if(!isIgnitionOn && beforeIgnition){
            return Collections.singletonMap(new Event(EventType.event_key_ignition_off), position);
        }

        return null;
    }
}