package br.com.gransistemas.taurus.model;

import io.netty.channel.Channel;

public class ActiveDevice {
    private Device device;
    private Channel channel;

    public ActiveDevice(Device device, Channel channel) {
        this.device = device;
        this.channel = channel;
    }

    public Device getDevice() {
        return device;
    }

    public Channel getChannel() {
        return channel;
    }
}
