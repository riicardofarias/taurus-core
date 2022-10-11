package br.com.gransistemas.taurus.factory;

import br.com.gransistemas.taurus.helpers.AppConfig;
import br.com.gransistemas.taurus.helpers.GetIt;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private static DatabaseConnectionFactory instance;
    private static HikariDataSource dataSource;

    private DatabaseConnectionFactory() throws SQLException {
        AppConfig c = GetIt.get(AppConfig.class);

        String name = c.get("db.name");
        String host = c.get("db.host");
        String port = c.get("db.port");

        dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/%s", host, port, name));
        dataSource.setUsername(c.get("db.user"));
        dataSource.setPassword(c.get("db.password"));
        dataSource.setLogWriter(new PrintWriter(System.out));

        dataSource.setAutoCommit(false);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(1);
    }

    public static DatabaseConnectionFactory getInstance() throws SQLException {
        if(instance == null)
            instance = new DatabaseConnectionFactory();

        return instance;
    }

    public DataSource getConnection() {
        return dataSource;
    }
}
