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
public class IgnitionEventHandler extends BaseEventHandler {
    @Override
    protected Map<Event, Position> analyzeEvent(Channel channel, Position position) {
        Device device = getDeviceManager()
            .getById(position.getDeviceId())
        .orElseThrow(DeviceNotFoundException::new);

        Position lastPosition = getDeviceManager()
            .getPosition(device.getLastPositionId())
        .orElse(null);

        boolean isIgnitionOn = position.getKeyIgnition() == 1;

        if(lastPosition == null){
            return Collections.singletonMap(new Event(isIgnitionOn ? EventType.KEY_IGNITION_ON : EventType.KEY_IGNITION_OFF), position);
        }

        boolean beforeIgnition = lastPosition.getKeyIgnition() == 1;

        // Se o status anterior for desligado e o status atual for ligado a chave está ligada
        if(!beforeIgnition && isIgnitionOn){
            return Collections.singletonMap(new Event(EventType.KEY_IGNITION_ON), position);
        // Se o status atual for desligado e o status anterior for ligado a chave está desligada
        }else if(!isIgnitionOn && beforeIgnition){
            return Collections.singletonMap(new Event(EventType.KEY_IGNITION_OFF), position);
        }

        return null;
    }
}