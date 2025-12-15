package com.ride.repository.interfaces;

import com.ride.domain.driver.Driver;
import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;

import java.util.List;

public interface DriverRepository {

    Driver findById(int driverId);

    void updateStatus(int driverId, DriverStatus status);

    void updateLocation(int driverId, Location location);

    Location getCurrentLocation(int driverId);

    List<Driver> findAvailableDriversNear(Location pickupLocation, double radiusMeters);
}
