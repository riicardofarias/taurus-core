package br.com.gransistemas.taurus.repository;

import br.com.gransistemas.taurus.factory.DatabaseConnectionFactory;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;
import org.codejargon.fluentjdbc.api.query.listen.AfterQueryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

abstract class Repository {
    private Logger logger = LoggerFactory.getLogger(Repository.class.getName());

    private DataSource getDatasource() throws Exception {
        return DatabaseConnectionFactory.getInstance().getConnection();
    }

    Query queryBuilder() throws Exception {
        FluentJdbc driver = new FluentJdbcBuilder().connectionProvider(getDatasource()).afterQueryListener(listener).build();
        return driver.query();
    }

    private AfterQueryListener listener = execution -> {
        if(execution.success()) {
            logger.info(String.format("Query took %s ms to execute: %s", execution.executionTimeMs(), execution.sql()));
        }
    };
}
