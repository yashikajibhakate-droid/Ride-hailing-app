package com.ride.repository.impl;

import com.ride.repository.interfaces.DriverLocationRepository;

public class DriverLocationRepositoryImpl
        implements DriverLocationRepository {

    @Override
    public void updateLocation(int driverId, double lat, double lon) {
        // JDBC UPDATE driver_location
    }

    @Override
    public double[] getLocation(int driverId) {
        // JDBC SELECT lat, lon
        return null;
    }
}
