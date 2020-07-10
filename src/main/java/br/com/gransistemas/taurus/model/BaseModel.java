package br.com.gransistemas.taurus.model;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

import java.io.Serializable;

@REntity
public class BaseModel implements Serializable  {
    @RId
    private long id;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
