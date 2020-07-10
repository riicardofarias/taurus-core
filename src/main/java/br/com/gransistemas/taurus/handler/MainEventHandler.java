package br.com.gransistemas.taurus.handler;

import br.com.gransistemas.taurus.Context;
import br.com.gransistemas.taurus.database.DeviceManager;
import br.com.gransistemas.taurus.model.Position;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainEventHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainEventHandler.class);
    private final DeviceManager deviceManager;

    public MainEventHandler() {
        this.deviceManager = Context.getDeviceManager();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Position){
            Position position = (Position) msg;

            deviceManager.saveLastPosition(position);

            LOGGER.info(position.toString());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info(formatChannel(ctx.channel()) + " connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        LOGGER.info(formatChannel(ctx.channel()) + " disconnected");

        if(ctx.channel() != null){
            Context.getDeviceSession().removeActiveDevice(ctx.channel());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.warn(formatChannel(ctx.channel()) + " error", cause);
        closeChannel(ctx.channel());
    }

    private String formatChannel(Channel channel){
        return String.format("[%s]: %s", channel.id().asShortText(), channel.remoteAddress());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if(evt instanceof IdleStateEvent){
            LOGGER.info(formatChannel(ctx.channel()) + " timed out");
            closeChannel(ctx.channel());
        }
    }

    private void closeChannel(Channel channel){
        channel.close();
    }
}
