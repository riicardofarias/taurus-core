package br.com.gransistemas.taurus.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Device extends BaseModel {
    private String imei;
    private Status status;
    private long lastPositionId;

    public boolean isOnline(){
        return status == Status.online;
    }
    public boolean isOffline(){
        return status == Status.offline;
    }
}
