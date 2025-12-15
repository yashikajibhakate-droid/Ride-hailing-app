package com.ride.repository.interfaces;

import com.ride.domain.ride.Ride;
import com.ride.domain.ride.RideStatus;

import java.util.List;

public interface RideRepository {

    Ride create(Ride ride);

    Ride findById(int rideId);

    List<Ride> findByStatus(RideStatus status);

    void update(Ride ride);

    boolean acceptRide(int rideId, int driverId);

}
