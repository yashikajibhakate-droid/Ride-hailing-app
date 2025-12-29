package com.ride.service.impl;

import com.ride.domain.ride.Ride;
import com.ride.domain.ride.RideStateMachine;
import com.ride.dto.request.CreateRideRequestDTO;
import com.ride.dto.request.RiderCreateRequest;
import com.ride.repository.interfaces.RideRepository;
import com.ride.repository.interfaces.RiderRepository;
import com.ride.service.interfaces.RiderService;

public class RiderServiceImpl implements RiderService {

    private final RideRepository rideRepository;
    private final RiderRepository riderRepository;
    private final RideStateMachine stateMachine = new RideStateMachine();

    public RiderServiceImpl(RideRepository rideRepository,
                            RiderRepository riderRepository) {
        this.rideRepository = rideRepository;
        this.riderRepository = riderRepository;
    }

    @Override
    public Long registerRider(RiderCreateRequest req) {
        return riderRepository.saveRider(
                req.name,
                req.phone_number,
                req.email
        );
    }

    @Override
    public Ride requestRide(CreateRideRequestDTO request) {

        validateRider(request.riderId);

        Ride ride = new Ride(
                request.pickup,
                request.dropoff,
                request.pickupLocation,
                request.dropoffLocation,
                request.riderId
        );

        // 🔥 lifecycle enforced HERE
        stateMachine.onRequest(ride);

        Ride savedRide = rideRepository.save(ride);
        return savedRide;
    }

    @Override
    public Ride getRideDetails(Long rideId) {
        return rideRepository.getRideById(rideId);
    }

    @Override
    public void cancelRide(Long rideId, Long riderId) {

        Ride ride = rideRepository.getRideById(rideId);

        if (!ride.getRiderId().equals(riderId)) {
            throw new IllegalArgumentException("Unauthorized cancel");
        }

        stateMachine.onRiderCancel(ride);

        rideRepository.riderCancelRide(rideId, riderId);
    }

    @Override
    public void validateRider(Long riderId) {
        if (!riderRepository.existsById(riderId)) {
            throw new IllegalArgumentException("Invalid rider ID");
        }
    }
}
