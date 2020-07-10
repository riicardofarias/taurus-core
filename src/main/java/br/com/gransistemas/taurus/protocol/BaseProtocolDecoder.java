package br.com.gransistemas.taurus.protocol;

import br.com.gransistemas.taurus.Context;
import br.com.gransistemas.taurus.database.DeviceManager;
import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Status;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.Optional;

public abstract class BaseProtocolDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
       try {
           Object decodedMessage = decode(ctx.channel(), msg);
           if (decodedMessage != null) {
                ctx.fireChannelRead(decodedMessage);
           }
       }finally {
           ReferenceCountUtil.release(msg);
       }
    }

    protected Optional<Device> getDeviceByImei(String imei) {
        try {
            // Busca o dispositivo no banco de dados
            Device device = getDeviceManager()
                .getDeviceByImei(imei)
            .orElseThrow(DeviceNotFoundException::new);

            // Atualiza o status somente se o status atual for diferente de online
            if(device.getStatus() == Status.offline){
                device.setStatus(Status.online);
                getDeviceManager().updateDeviceStatus(device.getId(), Status.online);
            }

            // Adiciona o dispositivo no cache
            getDeviceManager().saveDeviceSession(device);

            return Optional.of(device);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public DeviceManager getDeviceManager(){
        return Context.getDeviceManager();
    }

    public abstract Object decode(Channel channel, Object msg);
}
