package com.videorentalstore.model.resources;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.videorentalstore.model.Video;

public class VideoRentalReturnItem {

	@NotEmpty
	private final String title;
	
	private Video video;
	
	@NotEmpty
	private final int daysDelayed;
	
	private BigDecimal surcharges;
	private String currencyCode;

	public VideoRentalReturnItem(@JsonProperty(value = "videoTitle", required = true) String title,
			@JsonProperty(value = "daysDelayed", required = true) int daysDelayed) {
		this.title = title;
		this.daysDelayed = daysDelayed;
		surcharges = new BigDecimal(0);
	}
	
	public String getTitle() {
		return title;
	}

	public int getDaysDelayed() {
		return daysDelayed;
	}

	public BigDecimal getSurcharges() {
		return surcharges;
	}

	public void setSurcharges(BigDecimal surcharges) {
		this.surcharges = surcharges;
	}
	
    public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
        builder.append("VideoRentalReturnItem [title=");
        builder.append(title);
        builder.append(", video=");
        builder.append(video);
        builder.append(", daysToRent=");
        builder.append(daysDelayed);
        builder.append(", surcharges=");
        builder.append(surcharges);
        builder.append("]");
        return builder.toString();
    }
	
}
