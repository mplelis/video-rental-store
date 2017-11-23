package com.videorentalstore.model;

public class Customer {

	private long id;
	private int totalOrders;
	private int bonusPoints;

	public Customer(long id, int totalOrders, int bonusPoints) {
		this.id = id;
		this.totalOrders = totalOrders;
		this.bonusPoints = bonusPoints;
	}

	public Customer(int totalOrders, int bonusPoints) {
		this.totalOrders = totalOrders;
		this.bonusPoints = bonusPoints;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [ID=");
		builder.append(id);
		builder.append(", Total Orders=");
		builder.append(totalOrders);
		builder.append(", Bonus Points=");
		builder.append(bonusPoints);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bonusPoints;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + totalOrders;
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
		Customer other = (Customer) obj;
		if (bonusPoints != other.bonusPoints)
			return false;
		if (id != other.id)
			return false;
		if (totalOrders != other.totalOrders)
			return false;
		return true;
	}

}
