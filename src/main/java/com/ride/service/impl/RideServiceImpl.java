package com.ride.service.impl;

import com.ride.domain.ride.Ride;
import com.ride.domain.ride.RideStatus;
import com.ride.repository.interfaces.RideRepository;
import com.ride.service.interfaces.RideService;

public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // @Override
    // public Ride requestRide(Ride ride) {
    //     ride.setStatus(RideStatus.REQUESTED);
    //     return rideRepository.save(ride);
    // }

    // @Override
    // public boolean acceptRide(Long rideId, Long driverId) {
    //     return rideRepository.acceptRide(rideId, driverId);
    // }

    // @Override
    // public void beginRide(Long rideId, Long driverId,
    //                       double lat, double lon) {

    //     boolean started =
    //             rideRepository.beginRide(rideId, driverId);

    //     if (!started) {
    //         throw new IllegalStateException(
    //             "Ride cannot be started");
    //     }
    // }

    // @Override
    // public void endRide(Long rideId, Long driverId,
    //                     double lat, double lon) {

    //     boolean ended =
    //             rideRepository.endRide(rideId, driverId);

    //     if (!ended) {
    //         throw new IllegalStateException(
    //             "Ride cannot be completed");
    //     }
    // }

    // @Override
    // public void riderCancelRide(Long rideId, Long riderId) {

    //     boolean canceled =
    //             rideRepository.riderCancelRide(rideId, riderId);

    //     if (!canceled) {
    //         throw new IllegalStateException(
    //             "Ride cannot be cancelled by rider");
    //     }
    // }

    // @Override
    // public void driverCancelRide(Long rideId, Long driverId) {

    //     boolean canceled =
    //             rideRepository.driverCancelRide(rideId, driverId);

    //     if (!canceled) {
    //         throw new IllegalStateException(
    //             "Ride cannot be cancelled by driver");
    //     }
    // }

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId);
    }
}
