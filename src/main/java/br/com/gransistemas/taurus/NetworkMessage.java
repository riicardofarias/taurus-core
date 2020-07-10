package br.com.gransistemas.taurus;

import java.net.SocketAddress;

public class NetworkMessage {
    private final Object message;
    private final SocketAddress socketAddress;

    public NetworkMessage(Object message, SocketAddress socketAddress) {
        this.message = message;
        this.socketAddress = socketAddress;
    }

    public Object getMessage() {
        return message;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
