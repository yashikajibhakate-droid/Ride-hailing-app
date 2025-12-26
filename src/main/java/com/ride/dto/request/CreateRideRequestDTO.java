package com.ride.dto.request;

import com.ride.domain.ride.Location;

public class CreateRideRequestDTO {
    public int riderId;
    public String pickup;
    public String dropoff;
    public Location pickupLocation;
    public Location dropoffLocation;
}
