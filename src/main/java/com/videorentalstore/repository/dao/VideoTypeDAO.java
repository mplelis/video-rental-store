package com.videorentalstore.repository.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.videorentalstore.model.VideoType;
import com.videorentalstore.objectmappers.VideoTypeMapper;

public interface VideoTypeDAO {
	
	@SqlQuery("Select * from video_types")
	@RegisterMapper(VideoTypeMapper.class)
	List<VideoType> getTypes();

	@SqlUpdate("INSERT INTO video_types(type_value,price,currency_code) VALUES (:type, :price, :currencyCode)")
	void addNewType(@BindBean VideoType videoType);
}
