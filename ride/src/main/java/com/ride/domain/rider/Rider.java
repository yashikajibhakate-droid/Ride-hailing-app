package com.ride.domain.rider;

public class Rider {
    private int riderId;
    private String riderName;
    private String phone;

    public Rider(int riderId, String riderName, String phone) {
        this.riderId = riderId;
        this.riderName = riderName;
        this.phone = phone;
    }

    public int getRiderId() { return riderId; }
    public String getRiderName() { return riderName; }
    public String getPhone() { return phone; }
}
