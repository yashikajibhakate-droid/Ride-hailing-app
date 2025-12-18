package com.ride.service.interfaces;

import com.ride.domain.ride.Ride;

public interface RideService {

    int requestRide(Ride ride);

    void riderCancelRide(int rideId, int riderId);


    Ride getRideById(int rideId);
    
}
