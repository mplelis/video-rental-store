package com.videorentalstore.model;

public class VideoOrderCorrelation {

	private long orderId;
	private long videoId;
	private int daysToRent;

	public VideoOrderCorrelation(long orderId, long videoId, int daysToRent) {
		this.orderId = orderId;
		this.videoId = videoId;
		this.daysToRent = daysToRent;
	}

	public long getVideoId() {
		return videoId;
	}

	public void setVideoId(long videoId) {
		this.videoId = videoId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getDaysToRent() {
		return daysToRent;
	}

	public void setDaysToRent(int daysToRent) {
		this.daysToRent = daysToRent;
	}
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoOrderCorrelation [orderId=");
        builder.append(orderId);
        builder.append(", videoId=");
        builder.append(videoId);
        builder.append(", daysToRent=");
        builder.append(daysToRent);
        builder.append("]");
        return builder.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + daysToRent;
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + (int) (videoId ^ (videoId >>> 32));
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
		VideoOrderCorrelation other = (VideoOrderCorrelation) obj;
		if (daysToRent != other.daysToRent)
			return false;
		if (orderId != other.orderId)
			return false;
		if (videoId != other.videoId)
			return false;
		return true;
	}

}
