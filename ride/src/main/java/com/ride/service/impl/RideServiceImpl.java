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

    @Override
    public int requestRide(Ride ride) {
        ride.setStatus(RideStatus.REQUESTED);
        return rideRepository.save(ride);
    }

  
    @Override
    public void riderCancelRide(int rideId, int riderId) {

        boolean canceled =
                rideRepository.riderCancelRide(rideId, riderId);

        if (!canceled) {
            throw new IllegalStateException(
                "Ride cannot be cancelled by rider");
        }
    }

   
    @Override
    public Ride getRideById(int rideId) {
        return rideRepository.findById(rideId);
    }
}
