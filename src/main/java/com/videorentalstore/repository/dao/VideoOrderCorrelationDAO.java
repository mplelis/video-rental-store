package com.videorentalstore.repository.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.videorentalstore.model.VideoOrderCorrelation;
import com.videorentalstore.objectmappers.VideoOrderCorrelationMapper;

public interface VideoOrderCorrelationDAO {
	
	@SqlQuery("SELECT * from videos_orders where order_id=:order_id")
	@RegisterMapper(VideoOrderCorrelationMapper.class)
	List<VideoOrderCorrelation> getVideoOrderCorrelationsByOrderId(@Bind("order_id") long orderId);
	
	@SqlQuery("SELECT * from videos_orders where video_id=:video_id")
	@RegisterMapper(VideoOrderCorrelationMapper.class)
	List<VideoOrderCorrelation> getVideoOrderCorrelationsByVideoId(@Bind("video_id") long videoId);

	@SqlUpdate("INSERT INTO videos_orders (order_id, video_id, days) VALUES (:orderId, :videoId, :daysToRent)")
	void addNewVideoOrderCorrelation(@BindBean VideoOrderCorrelation videoOrderCorrelation);

	@SqlBatch("INSERT INTO videos_orders (order_id, video_id, days) VALUES (:orderId, :videoId, :daysToRent)")
	int[] addNewVideoOrderCorrelationBatch(@BindBean List<VideoOrderCorrelation> videoOrderCorrelationList);
}
