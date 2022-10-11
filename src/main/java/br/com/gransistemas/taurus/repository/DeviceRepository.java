package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.model.Status;
import br.com.gransistemas.taurus.repository.mapper.DeviceMapper;
import org.codejargon.fluentjdbc.api.query.Query;

import java.util.Optional;

public class DeviceRepository extends Repository {
    private final String table = "devices";

    /**
     * Busca o dispositivo pelo ID
     * @param id - Código do dispositivo
     * @return Optional
     */
    public Optional<Device> findById(long id) throws Exception {
        final DeviceMapper mapper = new DeviceMapper();

        String sql = queryBuilder().select("*")
            .from(table).where("id = :id")
        .build();

        return getQuery().select(sql)
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

        String sql = queryBuilder().select("*")
            .from(table).where("imei = :imei")
        .build();

        return getQuery().select(sql)
            .namedParam("imei", imei)
        .firstResult(mapper::mapModel);
    }

    /**
     * Atualiza a situação atual do dispositivo
     * @param deviceId - Identifiacdor de socket do dispositivo
     * @param status - Status do dispositivo // Online e offline
     */
    public void updateStatus(long deviceId, Status status) throws Exception {
        Query query = getQuery();

        query.transaction().in(() ->
            query.update("UPDATE " + table + " SET status = :status WHERE id = :deviceId")
                .namedParam("deviceId", deviceId)
                .namedParam("status", status.name())
            .run()
        );
    }

    /**
     * Atualiza a última posição do GPS
     * @param position - Código do dispositivo
     */
    public void updateDevicePosition(Position position) throws Exception {
        Query query = getQuery();

        query.transaction().in(() ->
            query.update("UPDATE " + table + " SET position_id = :positionId WHERE id = :deviceId")
                .namedParam("deviceId", position.getDeviceId())
                .namedParam("positionId", position.getId())
             .run()
        );
    }
}
