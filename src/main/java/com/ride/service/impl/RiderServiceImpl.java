package com.ride.service.impl;

import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.dto.request.CreateRideRequestDTO;
import com.ride.dto.request.RiderCreateRequest;
import com.ride.repository.interfaces.RideRepository;
import com.ride.repository.interfaces.RiderRepository;
import com.ride.service.interfaces.RiderService;

public class RiderServiceImpl implements RiderService {

    private final RideRepository rideRepository;
    private final RiderRepository riderRepository;

    public RiderServiceImpl(RideRepository rideRepository,
            RiderRepository riderRepository) {
        this.rideRepository = rideRepository;
        this.riderRepository = riderRepository;
    }


    @Override
    public int registerRider(RiderCreateRequest req) {
        return riderRepository.saveRider(req.name, req.phone_number, req.email);
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

        rideRepository.save(ride);
        return ride;
    }

    @Override
    public Ride getRideDetails(int rideId) {
        return rideRepository.getRideById(rideId);
    }

    @Override
    public void cancelRide(int rideId, int riderId) {
        rideRepository.riderCancelRide(rideId, riderId);
    }

    @Override
    public void validateRider(int riderId) {
        if (!riderRepository.existsById(riderId)) {
            throw new IllegalArgumentException("Invalid rider ID");
        }
    }
}
