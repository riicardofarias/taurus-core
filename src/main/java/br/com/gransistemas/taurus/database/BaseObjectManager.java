package br.com.gransistemas.taurus.database;

import br.com.gransistemas.taurus.factory.RedisConnectionFactory;
import org.redisson.api.RLiveObjectService;

public abstract class BaseObjectManager {
    protected RLiveObjectService getLiveObjectService() {
        return RedisConnectionFactory.getInstance().getRedisson().getLiveObjectService();
    }
}
