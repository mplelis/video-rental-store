package com.videorentalstore.repository;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

import com.videorentalstore.model.VideoOrderCorrelation;
import com.videorentalstore.repository.dao.VideoOrderCorrelationDAO;

public abstract class VideoOrderCorrelationRepository {
	
    @CreateSqlObject
    abstract VideoOrderCorrelationDAO videoOrderCorrelationDAO();
    
    @Transaction
    public List<VideoOrderCorrelation> getVideoOrderCorrelationsByOrderId(long orderId) {
    	List<VideoOrderCorrelation> videoOrderCorrelations = videoOrderCorrelationDAO().getVideoOrderCorrelationsByOrderId(orderId);
        return videoOrderCorrelations;
    }
    
    @Transaction
    public List<VideoOrderCorrelation> getVideoOrderCorrelationsByVideoId(long videoId) {
    	List<VideoOrderCorrelation> videoOrderCorrelations = videoOrderCorrelationDAO().getVideoOrderCorrelationsByVideoId(videoId);
        return videoOrderCorrelations;
    }
    
    @Transaction
    public void addNewVideoOrderCorrelation(VideoOrderCorrelation videoOrderCorrelation) {
        videoOrderCorrelationDAO().addNewVideoOrderCorrelation(videoOrderCorrelation);
    }

    @Transaction
	public void addNewVideoOrderCorrelationBatch(List<VideoOrderCorrelation> videoOrderCorrelationList) {
        videoOrderCorrelationDAO().addNewVideoOrderCorrelationBatch(videoOrderCorrelationList);
	}
    
}
