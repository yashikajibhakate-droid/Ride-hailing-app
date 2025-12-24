package com.ride.repository.interfaces;

import java.util.List;

import com.ride.domain.ride.Ride;

public interface RideRepository {

    int save(Ride ride);

    boolean acceptRide(int rideId, int driverId);

    boolean beginRide(int rideId, int driverId);

    boolean endRide(int rideId, int driverId);

    boolean riderCancelRide(int rideId, int riderId);

    boolean driverCancelRide(int rideId, int driverId);

    Ride findById(int rideId);

    Ride getRideStatus(int rideId);
Ride getRideById(int rideId);
    List<Ride> findAvailableRidesForDriver(int driverId); 



}
