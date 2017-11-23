package com.videorentalstore.util.database;

import com.codahale.metrics.MetricRegistry;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public abstract class H2DatabaseTest {
    private final MetricRegistry metricRegistry = new MetricRegistry();
    private final DataSourceFactory dataSourceFactory;
    private final DBI dbi;

    public H2DatabaseTest() {
        this.dataSourceFactory = getDataSourceFactory();
        Environment environment = new Environment("test-env", Jackson.newObjectMapper(), null, metricRegistry, null);
        this.dbi = new DBIFactory().build(environment, dataSourceFactory, "");
    }

    @Before
    public void setUpDatabase() throws Exception {
    	createH2Database(getDataSource());
    }

    @After
    public void tearDown() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource(getDataSource());
        flyway.setLocations("classpath:db/migration/h2","filesystem:src/test/resources/dev_sql");
        flyway.clean();
    }

    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl("jdbc:h2:mem:test");
        dataSourceFactory.setUser("sa");
        dataSourceFactory.setPassword("sa");
        return dataSourceFactory;
    }
    
    protected void createH2Database(ManagedDataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:db/migration/h2","filesystem:src/test/resources/dev_sql");
        flyway.migrate();
    }

    protected ManagedDataSource getDataSource() {
        return dataSourceFactory.build(metricRegistry, "bob");
    }

    protected Handle getHandle(){
        return dbi.open();
    }
}
