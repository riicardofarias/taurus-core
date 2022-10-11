package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.repository.mapper.PositionMapper;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.UpdateResultGenKeys;

import java.util.Optional;

public class PositionRepository extends Repository {
    private String table = "positions";

    /**
     * Busca a posição pelo código
     * @param positionId - Código da posição
     * @return Optional
     */
    public Optional<Position> findById(long positionId) throws Exception {
        PositionMapper mapper = new PositionMapper();

        String sql = queryBuilder().select("*")
            .from(table).where("id = :positionId")
        .build();

        return getQuery().select(sql)
            .namedParam("positionId", positionId)
        .firstResult(mapper::mapModel);
    }

    /**
     * Salva a posição do GPS
     * @param position - Posição do GPS
     * @return - Posição do GPS
     */
    public Position create(Position position) throws Exception {
        Query query = getQuery();
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO ");
        sql.append("    positions(latitude, longitude, speed, course, odometer, alarm, key_ignition, power, battery_level, signal_level, date, device_id) ");
        sql.append("VALUES ");
        sql.append("    (:latitude, :longitude, :speed, :course, :odometer, :alarm, :key_ignition, :power, :battery_level, :signal_level, :date, :device_id)");

        long id = query.transaction().in(() -> {
            UpdateResultGenKeys<Long> keys = query.update(sql.toString())
                .namedParams(new PositionMapper().createMap(position))
            .runFetchGenKeys(Mappers.singleLong());

            return keys.generatedKeys().get(0);
        });

        position.setId(id);
        return position;
    }
}
