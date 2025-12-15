package com.ride.service.interfaces;

import com.ride.domain.ride.Ride;

public interface RideService {

    Ride getRideById(int rideId);

    void riderCancelRide(int rideId, int riderId);

    void driverCancelRide(int rideId, int driverId);
}
