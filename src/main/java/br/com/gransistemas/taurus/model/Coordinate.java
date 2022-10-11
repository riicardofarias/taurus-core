package br.com.gransistemas.taurus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Coordinate {
    @JsonProperty("lat")
    public double latitude;

    @JsonProperty("lng")
    public double longitude;
}
