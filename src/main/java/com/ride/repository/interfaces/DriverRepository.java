package com.ride.repository.interfaces;

import com.ride.domain.driver.Driver;
import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;

import java.util.List;

public interface DriverRepository {

    Driver findById(Long driverId);

    void updateStatus(Long driverId, DriverStatus status);

    void updateLocation(Long driverId, Location location);

    DriverStatus getDriverStatus(Long driverId);

    Location getCurrentLocation(Long driverId);

    List<Driver> findAvailableDriversNear(Location pickupLocation, double radiusMeters);
    Long save(String name, String phone, String email, DriverStatus status);
}
