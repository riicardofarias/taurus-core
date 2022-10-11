package br.com.gransistemas.taurus.repository.mapper;

import br.com.gransistemas.taurus.model.Position;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PositionMapper implements ModelMapper<Position> {
    @Override
    public Position mapModel(ResultSet rs) throws SQLException {
        Position position = new Position();

        position.setId(rs.getLong("id"));
        position.setLatitude(rs.getDouble("latitude"));
        position.setLongitude(rs.getDouble("longitude"));
        position.setSpeed(rs.getDouble("speed"));
        position.setCourse(rs.getDouble("course"));
        position.setOdometer(rs.getInt("odometer"));
        position.setAlarm(rs.getString("alarm"));
        position.setKeyIgnition(rs.getInt("key_ignition") == 1);
        position.setPower(rs.getDouble("power"));
        position.setBatteryLevel(rs.getInt("battery_level"));
        position.setSpeed(rs.getDouble("signal_level"));
        position.setDate(rs.getDate("date"));
        position.setCreatedAt(rs.getDate("created_at"));
        position.setDeviceId(rs.getLong("device_id"));

        return position;
    }

    @Override
    public Map<String, ?> createMap(Position model){
        return new HashMap<String, Object>() {{
            put("latitude", model.getLatitude());
            put("longitude", model.getLongitude());
            put("speed", model.getSpeed());
            put("course", model.getCourse());
            put("odometer", model.getOdometer());
            put("alarm", model.getAlarm());
            put("key_ignition", model.getKeyIgnition());
            put("power", model.getPower());
            put("battery_level", model.getBatteryLevel());
            put("signal_level", model.getSignalLevel());
            put("date", model.getDate());
            put("created_at", new Date());
            put("device_id", model.getDeviceId());
        }};
    }
}
