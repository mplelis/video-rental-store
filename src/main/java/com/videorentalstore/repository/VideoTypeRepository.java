package com.videorentalstore.repository;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

import com.videorentalstore.model.VideoType;
import com.videorentalstore.repository.dao.VideoTypeDAO;

public abstract class VideoTypeRepository {
	
    @CreateSqlObject
    abstract VideoTypeDAO typeRepoDAO();

    @Transaction
    public List<VideoType> getTypes() {
        final List<VideoType> types = typeRepoDAO().getTypes();
        return types;
    }
    
    @Transaction
    public void addNewType(VideoType videoType) {
        typeRepoDAO().addNewType(videoType);
    }
    
}
