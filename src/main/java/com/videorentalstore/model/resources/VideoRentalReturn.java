package com.videorentalstore.model.resources;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoRentalReturn {

	@NotEmpty
	private final long orderId;

	@NotEmpty
	private final List<VideoRentalReturnItem> listOfVideos;

	@NotEmpty
	private final String currencyCode;

	private BigDecimal totalSurcharges;

	public VideoRentalReturn(@JsonProperty(value = "orderId", required = true) long orderId,
			@JsonProperty(value = "listOfVideos", required = true) List<VideoRentalReturnItem> listOfVideos,
			@JsonProperty(value = "currencyCode", required = true) String currencyCode) {
		this.orderId = orderId;
		this.listOfVideos = listOfVideos;
		this.currencyCode = currencyCode;
		totalSurcharges = new BigDecimal(0);
	}

	public List<VideoRentalReturnItem> getListOfVideos() {
		return listOfVideos;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getTotalSurcharges() {
		return totalSurcharges;
	}

	public void setTotalSurcharges(BigDecimal totalSurcharges) {
		this.totalSurcharges = totalSurcharges;
	}

	public long getOrderId() {
		return orderId;
	}

}
