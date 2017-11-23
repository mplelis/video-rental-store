package com.videorentalstore.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.model.Video;
import com.videorentalstore.model.VideoOrderCorrelation;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class VideoOrderCorrelationRepositoryTest extends H2DatabaseTest {

	private VideoOrderCorrelationRepository videoOrderCorrelationRepository;
	private VideoRepository videoRepository;

	@Before
	public void setUp() {
		createH2Database(getDataSource());
		videoOrderCorrelationRepository = getHandle().attach(VideoOrderCorrelationRepository.class);
		videoRepository = getHandle().attach(VideoRepository.class);
	}

	@Test
	public void testAddVideoOrderCorrelationToDAO() throws Exception {
		Video video = videoRepository.getVideoByTitle("Spider Man").get();
		// order with ID=1 has already been added to the database using the sql
		// scripts
		long orderId = 1;
		VideoOrderCorrelation videoOrderCorrelation = new VideoOrderCorrelation(orderId, video.getId(), 55);
		videoOrderCorrelationRepository.addNewVideoOrderCorrelation(videoOrderCorrelation);

		// test to get the video-order correlation by orderID
		List<VideoOrderCorrelation> retrievedVideoOrderCorrelationByOrderId = videoOrderCorrelationRepository
				.getVideoOrderCorrelationsByOrderId(orderId);
		assertEquals(retrievedVideoOrderCorrelationByOrderId.size() > 0, true);

		// test to get the video-order correlation by videoID
		List<VideoOrderCorrelation> retrievedVideoOrderCorrelationByVideoId = videoOrderCorrelationRepository
				.getVideoOrderCorrelationsByVideoId(video.getId());
		assertEquals(retrievedVideoOrderCorrelationByVideoId.size() > 0, true);
	}

	@Test
	public void testAddVideoOrderCorrelatioBatchToDAO() throws Exception {
		Video video = videoRepository.getVideoByTitle("Spider Man").get();
		Video video2 = videoRepository.getVideoByTitle("Spider Man 2").get();
		// order with ID=1 has already been added to the database using the sql
		// scripts
		long orderId = 1;
		VideoOrderCorrelation videoOrderCorrelation = new VideoOrderCorrelation(orderId, video.getId(), 55);
		VideoOrderCorrelation videoOrderCorrelation2 = new VideoOrderCorrelation(orderId, video2.getId(), 2);

		List<VideoOrderCorrelation> videoOrderCorrelationList = new ArrayList<>();
		videoOrderCorrelationList.add(videoOrderCorrelation);
		videoOrderCorrelationList.add(videoOrderCorrelation2);
		videoOrderCorrelationRepository.addNewVideoOrderCorrelationBatch(videoOrderCorrelationList);

		List<VideoOrderCorrelation> retrievedVideoOrderCorrelation = videoOrderCorrelationRepository
				.getVideoOrderCorrelationsByOrderId(orderId);
		assertEquals(retrievedVideoOrderCorrelation.size() > 0, true);
	}

}
