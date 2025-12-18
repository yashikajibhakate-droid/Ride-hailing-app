package com.ride.repository.interfaces;

import java.util.List;

import com.ride.domain.ride.Ride;

public interface RideRepository {

    int save(Ride ride);

    boolean riderCancelRide(int rideId, int riderId);

    Ride findById(int rideId);

    Ride getRideStatus(int rideId);
Ride getRideById(int rideId);
    List<Ride> findAvailableRidesForDriver(int driverId);



}
