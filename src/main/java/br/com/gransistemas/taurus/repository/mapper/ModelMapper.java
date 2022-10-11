package br.com.gransistemas.taurus.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ModelMapper<T> {
    T mapModel(ResultSet rs) throws SQLException;
    Map<String, ?> createMap(T model);
}
