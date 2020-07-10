package br.com.gransistemas.taurus.factory;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisConnectionFactory {
    private static RedisConnectionFactory instance;
    private static RedissonClient redisson;

    public RedisConnectionFactory() {
        Config config = new Config();

        config.setCodec(new JsonJacksonCodec());
        config.useSingleServer()
            .setAddress("redis://127.0.0.1:6379")
            .setConnectionMinimumIdleSize(5)
        .setConnectionPoolSize(64);

        redisson = Redisson.create(config);
    }

    public static RedisConnectionFactory getInstance(){
        if(instance == null) {
            instance = new RedisConnectionFactory();
        }

        return instance;
    }

    public RedissonClient getRedisson(){
        return redisson;
    }

    public void shutdown(){
        if(redisson != null) {
            redisson.shutdown();
        }
    }
}
