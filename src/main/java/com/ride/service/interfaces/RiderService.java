package com.ride.service.interfaces;

import com.ride.domain.ride.Ride;
import com.ride.dto.request.CreateRideRequestDTO;
import com.ride.dto.request.RiderCreateRequest;

public interface RiderService {



    Ride requestRide(
            CreateRideRequestDTO request
    );

    Ride getRideDetails(Long rideId);

    void cancelRide(Long rideId, Long riderId);

    void validateRider(Long riderId);

    Long registerRider(RiderCreateRequest req);
}
