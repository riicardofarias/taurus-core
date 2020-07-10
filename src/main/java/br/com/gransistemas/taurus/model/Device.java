package br.com.gransistemas.taurus.model;

import java.util.StringJoiner;

public class Device extends BaseModel {
    private String imei;
    private Status status;
    private long lastPositionId;

    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public long getLastPositionId() { return lastPositionId; }
    public void setLastPositionId(long lastPositionId) { this.lastPositionId = lastPositionId; }

    public boolean isOnline(){
        return status == Status.online;
    }
    public boolean isOffline(){
        return status == Status.offline;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Device.class.getSimpleName() + "[", "]")
            .add("id=" + getId()).add("imei='" + imei + "'")
        .toString();
    }
}
