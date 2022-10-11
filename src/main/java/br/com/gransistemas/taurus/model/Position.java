package br.com.gransistemas.taurus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position extends BaseModel {
    public static final String ALARM_SOS = "sos";

    private Date date;
    private double latitude;
    private double longitude;
    private double speed;
    private double course;
    private int odometer;
    private String alarm;
    private int keyIgnition;
    private double power;
    private int batteryLevel;
    private int signalLevel;
    private int valid;
    private long deviceId;
    private Date createdAt;

    public Position(long deviceId) {
        this.deviceId = deviceId;
    }

    public void setKeyIgnition(boolean ignition) { this.keyIgnition = ignition ? 1 : 0; }

    public void setValid(boolean valid) { this.valid = valid ? 1: 0;}
}
