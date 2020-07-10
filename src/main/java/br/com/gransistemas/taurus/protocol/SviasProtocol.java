package br.com.gransistemas.taurus.protocol;

import br.com.gransistemas.taurus.helpers.CharacterDelimiterFrameDecoder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by riicardofarias on 15/12/19.
 */
public class SviasProtocol extends Protocol {
    public SviasProtocol() {
        super("SVias",5000);
    }

    public void addHandlers(ChannelPipeline pipeline) {
        pipeline.addLast(new CharacterDelimiterFrameDecoder(1024, "]"));
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new SViasProtocolDecoder());
    }
}
