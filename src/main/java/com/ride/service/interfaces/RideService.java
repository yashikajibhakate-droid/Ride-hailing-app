package com.ride.service.interfaces;

import com.ride.domain.ride.Ride;

public interface RideService {

    Ride requestRide(Ride ride);

    boolean acceptRide(int rideId, int driverId);

    void beginRide(int rideId, int driverId, double lat, double lon);

    void endRide(int rideId, int driverId, double lat, double lon);

    void riderCancelRide(int rideId, int riderId);

    void driverCancelRide(int rideId, int driverId);

    Ride getRideById(int rideId);
    
}
