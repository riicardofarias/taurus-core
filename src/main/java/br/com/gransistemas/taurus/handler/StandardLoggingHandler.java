package br.com.gransistemas.taurus.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardLoggingHandler extends ChannelDuplexHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(StandardLoggingHandler.class);
    private final String protocol;

    public StandardLoggingHandler(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            log(ctx, false, (ByteBuf) msg);
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof ByteBuf){
            log(ctx, true, (ByteBuf) msg);
        }

        super.write(ctx, msg, promise);
    }

    private void log(ChannelHandlerContext ctx, boolean downstream, ByteBuf msg){
        StringBuilder message = new StringBuilder();

        message.append("[").append(ctx.channel().id().asShortText()).append("] ");
        message.append(protocol).append(":");
        message.append(downstream ? " > " : " < ");
        message.append("HEX: ");
        message.append(ByteBufUtil.hexDump(msg));

        LOGGER.info(message.toString());
    }
}
