package br.com.gransistemas.taurus.handler;

import br.com.gransistemas.taurus.ServerTracker;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

public class OpenChannelHandler extends ChannelDuplexHandler {
    private final ServerTracker server;

    public OpenChannelHandler(ServerTracker server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        server.getChannelGroup().add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        server.getChannelGroup().remove(ctx.channel());
    }
}
