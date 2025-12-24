package com.ride.service.impl;

import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.dto.request.DriverCreateRequest;
import com.ride.repository.interfaces.DriverLocationRepository;
import com.ride.repository.interfaces.DriverRepository;
import com.ride.repository.interfaces.RideRepository;
import com.ride.service.interfaces.DriverService;

import java.util.List;

public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;
    private final DriverLocationRepository locationRepository;

    public DriverServiceImpl(DriverRepository driverRepository,
                             RideRepository rideRepository,
                             DriverLocationRepository locationRepository) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void updateDriverLocation(int driverId, Location location) {
        driverRepository.updateLocation(
                driverId,
                location
        );
    }

    @Override
    public List<Ride> getAvailableRides(int driverId) {
        return rideRepository.findAvailableRidesForDriver(driverId);
    }

    @Override
    public boolean acceptRide(int rideId, int driverId) {
        return rideRepository.acceptRide(rideId, driverId);
    }

    @Override
    public void beginRide(int rideId, int driverId, Location location) {
        rideRepository.beginRide(
                rideId,
                driverId
                // location.getLat(),
                // location.getLon()
        );
    }

    @Override
    public void endRide(int rideId, int driverId, Location location) {
        rideRepository.endRide(
                rideId,
                driverId
                // location.getLat(),
                // location.getLon()
        );
    }

    @Override
    public void updateStatus(int driverId, DriverStatus status) {
        driverRepository.updateStatus(driverId, status);
    }

    @Override
    public int registerDriver(DriverCreateRequest req) {
    return driverRepository.save(req.name, req.phone, req.email);
}

}
