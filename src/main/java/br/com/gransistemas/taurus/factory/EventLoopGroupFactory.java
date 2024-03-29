package br.com.gransistemas.taurus.factory;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public final class EventLoopGroupFactory {
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private EventLoopGroupFactory() {
    }

    public static EventLoopGroup getBossGroup() {
        return bossGroup;
    }
    public static EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }
}