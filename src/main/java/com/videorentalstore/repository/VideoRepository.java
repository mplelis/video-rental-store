package com.videorentalstore.repository;

import java.util.List;
import java.util.Optional;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

import com.videorentalstore.model.Video;
import com.videorentalstore.repository.dao.VideoDAO;

public abstract class VideoRepository {
	
    @CreateSqlObject
    abstract VideoDAO videoDAO();

    @Transaction
    public Optional<Video> getVideoById(long id) {
        Video video = videoDAO().getVideoById(id);
        return Optional.ofNullable(video);
    }
    
    @Transaction
    public Optional<Video> getVideoByTitle(String title) {
        Video video = videoDAO().getVideoByTitle(title);
        return Optional.ofNullable(video);
    }
    
    @Transaction
    public List<Video> getAllVideos() {
        List<Video> videos = videoDAO().getAllVideos();
        return videos;
    }
    
    @Transaction
    public long addNewVideo(Video video) {
        return videoDAO().addNewVideo(video);
    }
    
    @Transaction
    public void updateVideoType(Video video) {
        videoDAO().updateVideoType(video);
    }
    
}
