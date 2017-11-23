package com.videorentalstore.model.resources.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.videorentalstore.model.Video;

public class ResponseOfOrderVideos {
	private long orderId;
	private List<Video> videoList;

	public ResponseOfOrderVideos(@JsonProperty("orderId") long orderId,
			@JsonProperty("videoList") List<Video> videoList) {
		this.orderId = orderId;
		this.videoList = videoList;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public List<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}

}
