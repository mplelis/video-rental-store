package com.videorentalstore.model.resources;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.videorentalstore.model.Video;

public class VideoRentalOrderItem {

	@NotEmpty
	private final String videoTitle;

	private Video video;
	
	@NotEmpty
	private final int daysToRent;
	
	private BigDecimal price;

	public VideoRentalOrderItem(@JsonProperty(value = "videoTitle", required = true) String videoTitle,
			@JsonProperty(value = "daysToRent", required = true) int daysToRent) {
		this.videoTitle = videoTitle;
		this.daysToRent = daysToRent;
		price = new BigDecimal(0);
	}
	
	public String getVideoTitle() {
		return videoTitle;
	}

	public int getDaysToRent() {
		return daysToRent;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoRentalOrderItem [videoTitle=");
        builder.append(videoTitle);
        builder.append(", video=");
        builder.append(video);
        builder.append(", daysToRent=");
        builder.append(daysToRent);
        builder.append(", price=");
        builder.append(price);
        builder.append("]");
        return builder.toString();
    }
	
}
