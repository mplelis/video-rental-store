package com.videorentalstore.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class VideoApplicationConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty
	private DataSourceFactory databaseH2 = new DataSourceFactory();

	public DataSourceFactory getH2DataSourceFactory() {
		return databaseH2;
	}

}
