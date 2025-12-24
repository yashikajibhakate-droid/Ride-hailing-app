package com.ride.service.interfaces;

import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.dto.request.DriverCreateRequest;

import java.util.List;

public interface DriverService {

    void updateDriverLocation(int driverId, Location location);

    List<Ride> getAvailableRides(int driverId);

    boolean acceptRide(int rideId, int driverId);

    void driverCancelRide(int rideId, int driverId);

    void beginRide(int rideId, int driverId, Location location);

    void endRide(int rideId, int driverId, Location location);

    void updateStatus(int driverId, DriverStatus status);
    int registerDriver(DriverCreateRequest req);
}
