package br.com.gransistemas.taurus.handler.events;

import br.com.gransistemas.taurus.Context;
import br.com.gransistemas.taurus.handler.BaseDataHandler;
import br.com.gransistemas.taurus.model.Event;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.repository.EventRepository;
import br.com.gransistemas.taurus.repository.ZoneRepository;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseEventHandler extends BaseDataHandler {
    private final Logger logger = LoggerFactory.getLogger(BaseEventHandler.class.getName());

    @Override
    protected Position handlePosition(Channel channel, Position position){
        try {
            Map<Event, Position> events = analyzeEvent(channel, position);

            if (Objects.nonNull(events)) {
                List<Event> deviceEvent = getEventRepository().findByDeviceId(position.getDeviceId()).stream()
                    .filter(e -> events.get(e) != null)
                .collect(Collectors.toList());

                for(Event event: deviceEvent) {
                    getEventRepository().saveEventNotification(
                        position.getDeviceId(), event.getId(), position.getId()
                    );

                    logger.info(String.format("[%s]: event detected [%s]", channel.id().asShortText(), event.getEventType().name()));
                }
            }
        } catch (Exception e){
            logger.error(String.format("[%s]: error", channel.id().asShortText()), e);
        }

        return position;
    }

    EventRepository getEventRepository(){
        return Context.getDatabaseManager().getEventRepository();
    }

    ZoneRepository getZoneRepository(){
        return Context.getDatabaseManager().geoFenceRepository();
    }

    protected abstract Map<Event, Position> analyzeEvent(Channel channel, Position position) throws Exception;
}
