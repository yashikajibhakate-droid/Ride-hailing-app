package com.ride.service.impl;

import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.domain.ride.RideStateMachine;
import com.ride.domain.ride.RideStatus;
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
    private final RideStateMachine stateMachine = new RideStateMachine();

    public DriverServiceImpl(DriverRepository driverRepository,
            RideRepository rideRepository,
            DriverLocationRepository locationRepository) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Long registerDriver(DriverCreateRequest req) {
        return driverRepository.save(req.name, req.phone, req.email, DriverStatus.OFFLINE);
    }

    @Override
    public void updateStatus(Long driverId, DriverStatus status) {
        driverRepository.updateStatus(driverId, status);
    }

    @Override
    public void updateDriverLocation(Long driverId, Location location) {
        driverRepository.updateLocation(driverId, location);
    }

    @Override
    public List<Ride> getAvailableRides(Long driverId) {
        return rideRepository.findAvailableRidesForDriver(driverId);
    }

    @Override
    public boolean acceptRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.getRideById(rideId);
        DriverStatus status = driverRepository.getDriverStatus(driverId);

        if (status != DriverStatus.ONLINE) {
            throw new IllegalStateException(
                    "Driver must be ONLINE to accept a ride");
        }

        stateMachine.onDriverAccept(ride);

        ride.setDriverId(driverId);

        return rideRepository.acceptRide(rideId, driverId);
    }

    @Override
    public void beginRide(Long rideId, Long driverId, Location location) {

        Ride ride = rideRepository.getRideById(rideId);

        if (!driverId.equals(ride.getDriverId())) {
            throw new IllegalArgumentException("Unauthorized driver");
        }

        // 🔥 lifecycle enforcement
        stateMachine.onStart(ride);

        rideRepository.beginRide(rideId, driverId);
        driverRepository.updateLocation(driverId, location);
    }

    @Override
    public void endRide(Long rideId, Long driverId, Location location) {

        Ride ride = rideRepository.getRideById(rideId);

        if (!driverId.equals(ride.getDriverId())) {
            throw new IllegalArgumentException("Unauthorized driver");
        }

        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new IllegalStateException("Ride is not in progress");
        }

         if (!stateMachine.isAtDropLocation(location, ride.getDropoffLocation())) {
        throw new IllegalStateException(
            "Cannot end ride before reaching drop-off location"
        );
    }

        // 🔥 lifecycle enforcement
        stateMachine.onComplete(ride);

        rideRepository.endRide(rideId, driverId);
        driverRepository.updateLocation(driverId, location);
    }

    @Override
    public void driverCancelRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.getRideById(rideId);

        if (!driverId.equals(ride.getDriverId())) {
            throw new IllegalArgumentException("Unauthorized driver");
        }

        // 🔥 lifecycle enforcement
        stateMachine.onDriverCancel(ride);

        rideRepository.driverCancelRide(rideId, driverId);
    }
}
