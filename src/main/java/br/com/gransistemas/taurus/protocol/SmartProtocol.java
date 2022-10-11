package br.com.gransistemas.taurus.protocol;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class SmartProtocol extends Protocol {
    public SmartProtocol() {
        super("Smart", 5050);
    }

    @Override
    public void addHandlers(ChannelPipeline pipeline) {
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(16384));
        pipeline.addLast(new SmartProtocolDecoder());
    }
}
