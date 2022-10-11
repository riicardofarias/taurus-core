package br.com.gransistemas.taurus.handler;

import br.com.gransistemas.taurus.model.Position;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class BaseDataHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Position){
            Position position = handlePosition(ctx.channel(), (Position) msg);

            if(position != null)
                ctx.fireChannelRead(position);
        }else{
            super.channelRead(ctx, msg);
        }
    }

    protected abstract Position handlePosition(Channel channel, Position position) throws Exception;
}
