package br.com.gransistemas.taurus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
    @JsonProperty("lat")
    public double latitude;

    @JsonProperty("lng")
    public double longitude;

    public Coordinate() { }

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
