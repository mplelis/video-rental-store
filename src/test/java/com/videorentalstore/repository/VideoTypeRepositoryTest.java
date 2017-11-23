package com.videorentalstore.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.model.VideoType;
import com.videorentalstore.repository.VideoTypeRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class VideoTypeRepositoryTest extends H2DatabaseTest {

	private VideoTypeRepository videoTypeRepository;

	@Before
	public void setUp() {
		createH2Database(getDataSource());
		videoTypeRepository = getHandle().attach(VideoTypeRepository.class);
	}

	@Test
	public void testAddVideoTypeToDAO() throws Exception {
		VideoType videoType = new VideoType("Very old film", new BigDecimal("10.0000"), "GBP");
		videoTypeRepository.addNewType(videoType);

		List<VideoType> videoTypesList = videoTypeRepository.getTypes();
		assertEquals(videoTypesList.contains(videoType), true);
	}

	@Test
	public void testGetVideoTypes() throws Exception {
		List<VideoType> videoTypesList = videoTypeRepository.getTypes();
		assertEquals(videoTypesList.size() > 0, true);
	}
}
