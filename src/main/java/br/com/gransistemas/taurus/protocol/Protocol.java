package br.com.gransistemas.taurus.protocol;

import io.netty.channel.ChannelPipeline;

/**
 * Created by riicardofarias on 15/12/19.
 */
public abstract class Protocol {
    private String name;
    private int port;

    public Protocol(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public abstract void addHandlers(ChannelPipeline pipeline);
}