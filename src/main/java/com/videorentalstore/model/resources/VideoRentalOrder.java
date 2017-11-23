package com.videorentalstore.model.resources;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoRentalOrder {

	@NotEmpty
	private final List<VideoRentalOrderItem> listOfVideos;

	private int customerId;

	@NotEmpty
	private final String currencyCode;

	private int bonusPoints;
	private BigDecimal totalPrice;

	public VideoRentalOrder(
			@JsonProperty(value = "listOfVideos", required = true) List<VideoRentalOrderItem> listOfVideos,
			@JsonProperty(value = "customerId", required = false) int customerId,
			@JsonProperty(value = "currencyCode", required = true) String currencyCode) {
		this.listOfVideos = listOfVideos;
		this.customerId = customerId;
		this.currencyCode = currencyCode;
		totalPrice = new BigDecimal(0);
	}

	public List<VideoRentalOrderItem> getListOfVideos() {
		return listOfVideos;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	@Override
	public String toString() {
		return "VideoRentalOrder [listOfVideos=" + listOfVideos + ", customerId=" + customerId + ", currencyCode="
				+ currencyCode + ", bonusPoints=" + bonusPoints + ", totalPrice=" + totalPrice + "]";
	}
}
