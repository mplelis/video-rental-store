package com.videorentalstore.configuration;

import javax.validation.constraints.NotNull;

import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class VideoApplicationConfiguration extends Configuration {

	@JsonProperty
	@NotNull
	private DataSourceFactory databasePostgres = new DataSourceFactory();

	public DataSourceFactory getPostgresDataSourceFactory() {
		return databasePostgres;
	}

	@NotNull
	private String flywayLocations;

	@JsonProperty
	public String[] getFlywayLocations() {
		return flywayLocations.split(",");
	}

}
