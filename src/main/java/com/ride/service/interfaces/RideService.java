package com.ride.service.interfaces;

import com.ride.domain.ride.Ride;

public interface RideService {

    // Ride requestRide(Ride ride);

    // boolean acceptRide(Long rideId, Long driverId);

    // void beginRide(Long rideId, Long driverId, double lat, double lon);

    // void endRide(Long rideId, Long driverId, double lat, double lon);

    // void riderCancelRide(Long rideId, Long riderId);

    // void driverCancelRide(Long rideId, Long driverId);

    Ride getRideById(Long rideId);
    
}
