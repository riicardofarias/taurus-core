package br.com.gransistemas.taurus.factory;

import br.com.gransistemas.taurus.helpers.AppConfig;
import br.com.gransistemas.taurus.helpers.GetIt;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisConnectionFactory {
    private static RedisConnectionFactory instance;
    private static RedissonClient redisson;

    public RedisConnectionFactory() {
        AppConfig c = GetIt.get(AppConfig.class);

        String host = c.get("redis.host");
        String port = c.get("redis.port");

        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        config.useSingleServer()
            .setAddress(String.format("redis://%s:%s", host, port))
            .setConnectionMinimumIdleSize(1)
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
