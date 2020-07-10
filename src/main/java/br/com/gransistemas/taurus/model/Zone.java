package br.com.gransistemas.taurus.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Zone {
    @JsonProperty(value = "id")
    private long id;

    @JsonProperty(value = "area")
    private String area;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public List<Coordinate> getArea() {
        try {
            return new ObjectMapper().readValue(area, new TypeReference<List<Coordinate>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public String getRawArea(){
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zone zone = (Zone) o;

        return id == zone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
