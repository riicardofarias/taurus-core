package br.com.gransistemas.taurus;

import br.com.gransistemas.taurus.factory.BasePipelineFactory;
import br.com.gransistemas.taurus.factory.EventLoopGroupFactory;
import br.com.gransistemas.taurus.protocol.Protocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;

public class ServerTracker {
    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final ServerBootstrap bootstrap;
    private final Protocol protocol;

    public ServerTracker(final Protocol protocol) {
        this.protocol = protocol;

        final BasePipelineFactory pipelineFactory = new BasePipelineFactory(this) {
            public void addProtocolHandler(ChannelPipeline pipeline) {
                protocol.addHandlers(pipeline);
            }
        };

        this.bootstrap = new ServerBootstrap()
            .group(EventLoopGroupFactory.getBossGroup(), EventLoopGroupFactory.getWorkerGroup())
            .channel(NioServerSocketChannel.class)
        .childHandler(pipelineFactory);
    }

    protected void start() throws Exception {
        InetSocketAddress endpoint = new InetSocketAddress(protocol.getPort());
        Channel channel = bootstrap.bind(endpoint).sync().channel();

        if(channel != null){
            getChannelGroup().add(channel);
        }
    }

    protected void stop(){
        channelGroup.close().awaitUninterruptibly();
    }

    public String getName(){
        return protocol.getName();
    }

    public int getPort(){
        return protocol.getPort();
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}
