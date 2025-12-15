package com.ride.domain.rider;

public class Rider {
    private int riderId;
    private String riderName;
    private String phone;
    private String email;

    public Rider(int riderId, String riderName, String phone, String email) {
        this.riderId = riderId;
        this.riderName = riderName;
        this.phone = phone;
        this.email = email;

    }

    public int getRiderId() { return riderId; }
    public String getRiderName() { return riderName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
