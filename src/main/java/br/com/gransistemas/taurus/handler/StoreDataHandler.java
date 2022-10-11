package br.com.gransistemas.taurus.handler;

import br.com.gransistemas.taurus.helpers.GetIt;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.repository.PositionRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class StoreDataHandler extends BaseDataHandler {
    private PositionRepository repository;

    public StoreDataHandler() {
        repository = GetIt.get(PositionRepository.class);
    }

    @Override
    protected Position handlePosition(Channel channel, Position position) throws Exception {
        return repository.create(position);
    }
}
