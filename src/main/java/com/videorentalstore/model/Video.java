package com.videorentalstore.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Video {
	
	private long id;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String videoTypeStr;
	private VideoType videoType;
	
	public Video(long id, String title, String videoTypeStr) {
		this.id = id;
		this.title = title;
		this.videoTypeStr = videoTypeStr;
	}
	
	public Video(@JsonProperty("title") String title, @JsonProperty("videoType") String videoTypeStr) {
		this.title = title;
		this.videoTypeStr = videoTypeStr;
	}
	
	public Video(long id, String title, String videoTypeStr, VideoType videoType) {
		this.id = id;
		this.title = title;
		this.videoTypeStr = videoTypeStr;
		this.videoType = videoType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public VideoType getVideoType() {
		return videoType;
	}

	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonIgnore
	public String getVideoTypeStr() {
		return videoTypeStr;
	}

	public void setVideoTypeStr(String videoTypeStr) {
		this.videoTypeStr = videoTypeStr;
	}
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Video [ID=");
        builder.append(id);
        builder.append(", title=");
        builder.append(title);
        builder.append(", video type=");
        builder.append(videoTypeStr);
        builder.append("]");
        return builder.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((videoType == null) ? 0 : videoType.hashCode());
		result = prime * result + ((videoTypeStr == null) ? 0 : videoTypeStr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (videoType == null) {
			if (other.videoType != null)
				return false;
		} else if (!videoType.equals(other.videoType))
			return false;
		if (videoTypeStr == null) {
			if (other.videoTypeStr != null)
				return false;
		} else if (!videoTypeStr.equals(other.videoTypeStr))
			return false;
		return true;
	}

}
