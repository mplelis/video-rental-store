package com.videorentalstore.objectmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.videorentalstore.model.Video;

public class VideoMapper implements ResultSetMapper<Video> {

	public Video map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Video(r.getLong("id"), r.getString("title"), r.getString("video_type_value"));
	}
}