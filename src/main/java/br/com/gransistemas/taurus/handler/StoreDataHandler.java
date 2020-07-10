package br.com.gransistemas.taurus.handler;

import br.com.gransistemas.taurus.Context;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.repository.DeviceRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class StoreDataHandler extends BaseDataHandler {
    private DeviceRepository repository;

    public StoreDataHandler() {
        repository = Context.getDatabaseManager().getDeviceRepository();
    }

    @Override
    protected Position handlePosition(Channel channel, Position position) throws Exception {
        return repository.saveDevicePosition(position);
    }
}
