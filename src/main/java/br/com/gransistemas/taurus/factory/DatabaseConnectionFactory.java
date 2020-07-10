package br.com.gransistemas.taurus.factory;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private static DatabaseConnectionFactory instance;
    private static HikariDataSource dataSource;

    private DatabaseConnectionFactory() throws SQLException {
        dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/tracker");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setLogWriter(new PrintWriter(System.out));

        dataSource.setAutoCommit(false);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
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
