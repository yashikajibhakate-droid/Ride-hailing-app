package com.ride.service.interfaces;

import com.ride.domain.driver.Driver;
import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;

import java.util.List;

public interface DriverService {

    void updateDriverStatus(int driverId, DriverStatus status);

    void updateDriverLocation(int driverId, Location location);

    List<Ride> getAvailableRides(int driverId);

    Ride acceptRide(int rideId, int driverId);

    Ride beginRide(int rideId, int driverId, Location currentLocation);

    Ride endRide(int rideId, int driverId, Location currentLocation);
}
