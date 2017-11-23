package com.videorentalstore.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class VideoType {
	
	private String type;
	private BigDecimal price;
	private String currencyCode;
	
	// Map which holds the video types with their price tag and currency code.
	// The key of the map, is generated using the video type value followed
	// by " - " and the currency code since this combination is unique.
	// The value of the map, is the video type object itself.
	// This data structure is populated during application start-up.
	// It is used for fast access to the video type details without the 
	// expensive operation of database queries.
	public static Map<String,VideoType> videoTypes = new HashMap<>();
	
	public VideoType(String type, BigDecimal price, String currencyCode) {
		this.type = type;
		this.price = price;
		this.currencyCode = currencyCode;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getPrice() {
		return price;
	}

    public String getCurrencyCode() {
		return currencyCode;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoType [type=");
        builder.append(type);
        builder.append(", price=");
        builder.append(price);
        builder.append(", currency code=");
        builder.append(currencyCode);
        builder.append("]");
        return builder.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		VideoType other = (VideoType) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
