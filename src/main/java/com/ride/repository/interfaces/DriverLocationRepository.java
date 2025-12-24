package com.ride.repository.interfaces;

public interface DriverLocationRepository {

    void updateLocation(int driverId, double lat, double lon);

    double[] getLocation(int driverId);
}
