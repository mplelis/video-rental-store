package com.videorentalstore.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.model.Video;
import com.videorentalstore.repository.VideoRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class VideoRepositoryTest extends H2DatabaseTest {

	private VideoRepository videoRepository;

	@Before
	public void setUp() {
		createH2Database(getDataSource());
		videoRepository = getHandle().attach(VideoRepository.class);
	}

	@Test
	public void testAddVideoToDAO() throws Exception {
		Video video = new Video("test video", "New release");
		long videoId = videoRepository.addNewVideo(video);
		video.setId(videoId);
		Optional<Video> retrievedVideo = videoRepository.getVideoById(videoId);
		assertEquals(retrievedVideo.get(), video);
	}

	@Test
	public void testGetVideoByTitle() throws Exception {
		Video video = new Video("test video", "New release");
		long videoId = videoRepository.addNewVideo(video);
		video.setId(videoId);
		Optional<Video> retrievedVideo = videoRepository.getVideoByTitle("test video");
		assertEquals(retrievedVideo.get().getTitle(), video.getTitle());
	}

	@Test
	public void testGetNotExistingVideo() throws Exception {
		Optional<Video> retrievedVideo = videoRepository.getVideoByTitle("test video");
		assertThat(retrievedVideo, is(Optional.empty()));
	}
	
	@Test
	public void testGetAllVideos() throws Exception {
		List<Video> videos = videoRepository.getAllVideos();
		assertEquals(true, videos.size()>0);
		
		Video video = new Video(1, "Matrix 11", "New release");
		assertEquals(videos.contains(video),true);
	}
}
