package br.com.gransistemas.taurus.factory;

import br.com.gransistemas.taurus.ServerTracker;
import br.com.gransistemas.taurus.handler.MainEventHandler;
import br.com.gransistemas.taurus.handler.OpenChannelHandler;
import br.com.gransistemas.taurus.handler.StandardLoggingHandler;
import br.com.gransistemas.taurus.handler.StoreDataHandler;
import br.com.gransistemas.taurus.handler.events.IgnitionEventHandler;
import br.com.gransistemas.taurus.handler.events.MotionEventHandler;
import br.com.gransistemas.taurus.handler.events.OverSpeedEventHandler;
import br.com.gransistemas.taurus.handler.events.ZoneEventHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public abstract class BasePipelineFactory extends ChannelInitializer<Channel> {
    private final ServerTracker server;

    protected BasePipelineFactory(ServerTracker server) {
        this.server = server;
    }

    protected void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new OpenChannelHandler(server));
        pipeline.addLast(new StandardLoggingHandler(server.getName()));

        addProtocolHandler(pipeline);

        pipeline.addLast(new StoreDataHandler());
        pipeline.addLast(new IgnitionEventHandler());
        pipeline.addLast(new MotionEventHandler());
        pipeline.addLast(new OverSpeedEventHandler());
        pipeline.addLast(new ZoneEventHandler());

        pipeline.addLast(new MainEventHandler());
    }

    public abstract void addProtocolHandler(ChannelPipeline pipeline);
}
