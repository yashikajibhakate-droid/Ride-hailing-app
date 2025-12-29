package com.ride.service.interfaces;

import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.dto.request.DriverCreateRequest;

import java.util.List;

public interface DriverService {

    void updateDriverLocation(Long driverId, Location location);

    List<Ride> getAvailableRides(Long driverId);

    boolean acceptRide(Long rideId, Long driverId);

    void driverCancelRide(Long rideId, Long driverId);

    void beginRide(Long rideId, Long driverId, Location location);

    void endRide(Long rideId, Long driverId, Location location);

    void updateStatus(Long driverId, DriverStatus status);
    Long registerDriver(DriverCreateRequest req);
}
