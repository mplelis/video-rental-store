package com.videorentalstore;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.h2.tools.Server;
import org.skife.jdbi.v2.DBI;

import com.videorentalstore.configuration.VideoApplicationConfiguration;
import com.videorentalstore.exception.VideoApplicationExceptionMapper;
import com.videorentalstore.model.VideoType;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;
import com.videorentalstore.repository.VideoTypeRepository;
import com.videorentalstore.resources.CustomerResources;
import com.videorentalstore.resources.OrderResources;
import com.videorentalstore.resources.VideoResources;
import com.videorentalstore.util.VideoLogging;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

/**
 * @author Mihalis Plelis
 * @version 1.1
 */
public class VideoApplication extends Application<VideoApplicationConfiguration> {

	private DBI jdbi;
	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;
	private VideoTypeRepository videoTypeRepository;
	private VideoOrderCorrelationRepository videoOrderCorrelationRepository;
	private VideoRepository videoRepository;

	public static void main(String[] args) throws Exception {
		new VideoApplication().run(args);
	}

	@Override
	public void run(VideoApplicationConfiguration configuration, Environment environment) throws Exception {
		VideoLogging.logMessage("Application is starting...");

		final DBIFactory factory = new DBIFactory();
		DataSourceFactory dataSourceFactory = configuration.getH2DataSourceFactory();

		jdbi = factory.build(environment, dataSourceFactory, "h2");
		VideoLogging.logMessage("Database connection has been established.");

		orderRepository = jdbi.onDemand(OrderRepository.class);
		customerRepository = jdbi.onDemand(CustomerRepository.class);
		videoTypeRepository = jdbi.onDemand(VideoTypeRepository.class);
		videoOrderCorrelationRepository = jdbi.onDemand(VideoOrderCorrelationRepository.class);
		videoRepository = jdbi.onDemand(VideoRepository.class);

		final OrderResources orderResources = new OrderResources(orderRepository, customerRepository,
				videoOrderCorrelationRepository, videoRepository);
		final CustomerResources customerResources = new CustomerResources(orderRepository, customerRepository);
		final VideoResources videoResources = new VideoResources(videoRepository);
		environment.jersey().register(orderResources);
		environment.jersey().register(customerResources);
		environment.jersey().register(videoResources);
		environment.jersey().register(new VideoApplicationExceptionMapper());

		// H2 web server is started to give us the ability to connect to the
		// database externally.
		Server server = Server.createTcpServer().start();
		VideoLogging.logMessage("The database server has started and the connection is open.");
		String databaseUrl = getDatabaseURL(configuration, server);
		VideoLogging.logMessage(databaseUrl);

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSourceFactory.build(environment.metrics(), "h2"));
		flyway.setLocations("classpath:db/migration/h2", "classpath:dev_sql");
		flyway.migrate();
		VideoLogging.logMessage("Database Migration has been successful.");

		// Load the video types from the database and put them in the static Map.
		List<VideoType> videoTypesPersisted = videoTypeRepository.getTypes();
		for (VideoType videoType : videoTypesPersisted) {
			String key = videoType.getType() + " - " + videoType.getCurrencyCode();
			VideoType.videoTypes.put(key, videoType);
		}
		VideoLogging.logMessage("Video Types have been successfully loaded into the Map.");
	}

	private String getDatabaseURL(VideoApplicationConfiguration configuration, Server server) {
		String[] databaseUrlArray = configuration.getH2DataSourceFactory().getUrl().split(":");
		List<String> databaseUrlList = Arrays.asList(databaseUrlArray);
		StringBuilder databaseUrl = new StringBuilder();
		databaseUrl.append(
				"Database URL: " + databaseUrlList.get(0) + ":" + databaseUrlList.get(1) + ":" + server.getURL() + "/");
		
		Iterator<String> iterator = databaseUrlList.subList(2, databaseUrlList.size()).iterator();
		while (iterator.hasNext()) {
			databaseUrl.append(iterator.next());
			if (iterator.hasNext()) {
				databaseUrl.append(":");
			}
		}
		
		return databaseUrl.toString();
	}
}
