package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.model.Status;
import br.com.gransistemas.taurus.repository.mapper.DeviceMapper;
import br.com.gransistemas.taurus.repository.mapper.PositionMapper;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

import java.util.Optional;

public class DeviceRepository extends Repository {

    /**
     * Busca o dispositivo pelo ID
     * @param id - Código do dispositivo
     * @return Optional
     */
    public Optional<Device> findById(long id) throws Exception {
        final DeviceMapper mapper = new DeviceMapper();

        return queryBuilder().select("SELECT * FROM device WHERE id = :id")
            .namedParam("id", id)
        .firstResult(mapper::mapModel);
    }

    /**
     * Busca o dispositivo pelo Imei
     * @param imei - Imei do dispositivo
     * @return Optional
     */
    public Optional<Device> findByImei(String imei) throws Exception {
        final DeviceMapper mapper = new DeviceMapper();

        return queryBuilder().select("SELECT * FROM device WHERE imei = :imei")
            .namedParam("imei", imei)
        .firstResult(mapper::mapModel);
    }

    /**
     * Busca a posição pelo código
     * @param positionId - Código da posição
     * @return Optional
     */
    public Optional<Position> findPositionById(long positionId) throws Exception {
        PositionMapper mapper = new PositionMapper();

        return queryBuilder().select("SELECT * FROM position WHERE id = :positionId")
            .namedParam("positionId", positionId)
        .firstResult(mapper::mapModel);
    }

    /**
     * Atualiza a situação atual do dispositivo
     * @param deviceId - Identifiacdor de socket do dispositivo
     * @param status - Status do dispositivo // Online e offline
     */
    public void updateDeviceStatus(long deviceId, Status status) throws Exception {
        Query query = queryBuilder();

        query.transaction().in(() ->
            query.update("UPDATE device SET status = :status WHERE id = :deviceId")
                .namedParam("deviceId", deviceId)
                .namedParam("status", status.name())
            .run()
        );
    }

    /**
     * Salva a posição do GPS
     * @param position - Posição do GPS
     * @return - Posição do GPS
     */
    public Position saveDevicePosition(Position position) throws Exception {
        PositionMapper mapper = new PositionMapper();
        Query query = queryBuilder();

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO ");
        sql.append("    position(latitude, longitude, speed, course, odometer, alarm, key_ignition, power, battery_level, signal_level, date, device_id) ");
        sql.append("VALUES ");
        sql.append("    (:latitude, :longitude, :speed, :course, :odometer, :alarm, :key_ignition, :power, :battery_level, :signal_level, :date, :device_id)");

        long id = query.transaction().in(() -> {
            UpdateResultGenKeys<Long> keys = query.update(sql.toString())
                .namedParams(mapper.createMap(position))
            .runFetchGenKeys(Mappers.singleLong());

            return keys.generatedKeys().get(0);
        });

        position.setId(id);
        return position;
    }

    /**
     * Atualiza a última posição do GPS
     * @param position - Código do dispositivo
     */
    public void updateDevicePosition(Position position) throws Exception {
        Query query = queryBuilder();

        query.transaction().in(() ->
            query.update("UPDATE device SET position_id = :positionId WHERE id = :deviceId")
                .namedParam("deviceId", position.getDeviceId())
                .namedParam("positionId", position.getId())
             .run()
        );
    }
}
