package com.videorentalstore.repository.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.videorentalstore.model.Video;
import com.videorentalstore.objectmappers.VideoMapper;

public interface VideoDAO {
	
	@SqlQuery("SELECT * from videos where id=:id")
	@RegisterMapper(VideoMapper.class)
	Video getVideoById(@Bind("id") long id);
	
	@SqlQuery("SELECT * from videos where title=:title")
	@RegisterMapper(VideoMapper.class)
	Video getVideoByTitle(@Bind("title") String title);

	@SqlUpdate("INSERT INTO videos(title,video_type_value) VALUES (:title, :videoTypeStr)")
	@GetGeneratedKeys
	long addNewVideo(@BindBean Video video);
	
	@SqlUpdate("UPDATE videos set video_type_value=:videoTypeStr where id=:id")
	void updateVideoType(@BindBean Video video);

	@SqlQuery("SELECT * from videos")
	@RegisterMapper(VideoMapper.class)
	List<Video> getAllVideos();
}
