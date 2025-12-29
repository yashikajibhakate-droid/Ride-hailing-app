package com.ride.repository.interfaces;

import java.util.List;

import com.ride.domain.ride.Ride;

public interface RideRepository {

    Ride save(Ride ride);

    boolean acceptRide(Long rideId, Long driverId);

    boolean beginRide(Long rideId, Long driverId);

    boolean endRide(Long rideId, Long driverId);

    boolean riderCancelRide(Long rideId, Long riderId);

    boolean driverCancelRide(Long rideId, Long driverId);

    Ride findById(Long rideId);

    Ride getRideDetails(Long rideId);

    Ride getRideById(Long rideId);

    List<Ride> findAvailableRidesForDriver(Long driverId);

}
