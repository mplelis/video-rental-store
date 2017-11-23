package com.videorentalstore.objectmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.videorentalstore.model.VideoOrderCorrelation;

public class VideoOrderCorrelationMapper implements ResultSetMapper<VideoOrderCorrelation> {
	
	public VideoOrderCorrelation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new VideoOrderCorrelation(r.getLong("order_id"), r.getLong("video_id"), r.getInt("days"));
	}

}