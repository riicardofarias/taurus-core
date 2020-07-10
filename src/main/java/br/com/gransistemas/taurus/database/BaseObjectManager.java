package br.com.gransistemas.taurus.database;

import br.com.gransistemas.taurus.factory.RedisConnectionFactory;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;

public abstract class BaseObjectManager<T>{
    private DatabaseManager databaseManager;

    public BaseObjectManager() {
        databaseManager = new DatabaseManager();
    }

    protected RedissonClient getRedisson() {
        return RedisConnectionFactory.getInstance().getRedisson();
    }

    protected RLiveObjectService getLiveObjectService() {
        return getRedisson().getLiveObjectService();
    }

    public void shutdown(){
        RedisConnectionFactory.getInstance().shutdown();
    }

    protected DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
}
