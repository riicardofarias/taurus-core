package br.com.gransistemas.taurus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.StringJoiner;

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

    public Position() { }

    public Position(long deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getCourse() { return course; }
    public void setCourse(double course) { this.course = course; }

    public int getOdometer() { return odometer; }
    public void setOdometer(int odometer) { this.odometer = odometer; }

    public String getAlarm() { return alarm; }
    public void setAlarm(String alarm) { this.alarm = alarm; }

    public int getKeyIgnition() { return keyIgnition; }
    public void setKeyIgnition(boolean ignition) { this.keyIgnition = ignition ? 1 : 0; }

    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }

    public int getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(int batteryLevel) { this.batteryLevel = batteryLevel; }

    public int getSignalLevel() { return signalLevel; }
    public void setSignalLevel(int signalLevel) { this.signalLevel = signalLevel; }

    public long getDeviceId() { return deviceId; }
    public void setDeviceId(long deviceId) { this.deviceId = deviceId; }

    public int getValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid ? 1: 0;}

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return new StringJoiner(", ", Position.class.getSimpleName() + "[", "]")
            .add("date=" + date)
            .add("latitude=" + latitude)
            .add("longitude=" + longitude)
            .add("speed=" + speed)
            .add("course=" + course)
            .add("odometer=" + odometer)
            .add("alarm=" + alarm)
            .add("keyIgnition=" + keyIgnition)
            .add("power=" + power)
            .add("batteryLevel=" + batteryLevel)
            .add("signalLevel=" + signalLevel)
            .add("deviceId=" + deviceId)
        .toString();
    }
}
