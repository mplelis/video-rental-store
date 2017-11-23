package com.videorentalstore.model;

import java.math.BigDecimal;

public class Order {

	private long id;
	private BigDecimal price;
	private BigDecimal surcharges;
	private long customerId;
	private String currencyCode;
	private boolean isOrderReturned;

	public Order(long id, BigDecimal price, BigDecimal surcharges, long customerId, String currencyCode,
			boolean isOrderReturned) {
		this.id = id;
		this.price = price;
		this.surcharges = surcharges;
		this.customerId = customerId;
		this.currencyCode = currencyCode;
		this.isOrderReturned = isOrderReturned;
	}

	public Order(BigDecimal price, BigDecimal surcharges, long customerId, String currencyCode,
			boolean isOrderReturned) {
		this.price = price;
		this.surcharges = surcharges;
		this.customerId = customerId;
		this.currencyCode = currencyCode;
		this.isOrderReturned = isOrderReturned;
	}

	public Order(BigDecimal price, BigDecimal surcharges, long customerId, String currencyCode) {
		this.price = price;
		this.surcharges = surcharges;
		this.customerId = customerId;
		this.currencyCode = currencyCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSurcharges() {
		return surcharges;
	}

	public void setSurcharges(BigDecimal surcharges) {
		this.surcharges = surcharges;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public boolean isOrderReturned() {
		return isOrderReturned;
	}

	public void setOrderReturned(boolean isOrderReturned) {
		this.isOrderReturned = isOrderReturned;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", price=");
		builder.append(price);
		builder.append(", surcharges=");
		builder.append(surcharges);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", isOrderReturned=");
		builder.append(isOrderReturned);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isOrderReturned ? 1231 : 1237);
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((surcharges == null) ? 0 : surcharges.hashCode());
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
		Order other = (Order) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (customerId != other.customerId)
			return false;
		if (id != other.id)
			return false;
		if (isOrderReturned != other.isOrderReturned)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (surcharges == null) {
			if (other.surcharges != null)
				return false;
		} else if (!surcharges.equals(other.surcharges))
			return false;
		return true;
	}

}
