package com.videorentalstore.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Video;
import com.videorentalstore.repository.VideoRepository;

@Path(VideoResources.PATH)
public class VideoResources {

	public static final String PATH = "/video";
	public static final String ADD_NEW_VIDEO = "/addNewVideo";
	public static final String GET_ALL_VIDEOS = "/getAllVideos";

	private VideoRepository videoRepository;

	public VideoResources(VideoRepository videoRepository) {
		this.videoRepository = videoRepository;
	}

	@POST
	@Path(ADD_NEW_VIDEO)
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewVideo(Video video) throws VideoApplicationException {
		try {
			videoRepository.addNewVideo(video);
		} catch (Exception e) {
			throw new VideoApplicationException("There was an error while importing the new video. " 
												+ "Probably duplicate video title?");
		}
		return Response.ok("The new video with title " + video.getTitle() + " was imported successfully.").build();
	}
	
	@GET
	@Path(GET_ALL_VIDEOS)
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public List<Video> getAllVideos() throws VideoApplicationException {
		List<Video> listOfVideos = videoRepository.getAllVideos();
		return listOfVideos;
	}
}
