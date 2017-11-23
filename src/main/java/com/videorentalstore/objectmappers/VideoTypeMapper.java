package com.videorentalstore.objectmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.videorentalstore.model.VideoType;

public class VideoTypeMapper implements ResultSetMapper<VideoType> {

	public VideoType map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new VideoType(r.getString("type_value"), r.getBigDecimal("price"), r.getString("currency_code"));
	}
	
}