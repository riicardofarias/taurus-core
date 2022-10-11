package br.com.gransistemas.taurus.repository.mapper;

import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DeviceMapper implements ModelMapper<Device> {

    @Override
    public Device mapModel(ResultSet rs) throws SQLException {
        Device device = new Device();

        device.setId(rs.getLong("id"));
        device.setImei(rs.getString("imei"));
        device.setStatus(Status.valueOf(rs.getString("status")));
        device.setLastPositionId(rs.getLong("position_id"));

        return device;
    }

    @Override
    public Map<String, ?> createMap(Device model) {
        throw new RuntimeException();
    }
}
