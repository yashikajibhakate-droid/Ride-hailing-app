package com.ride.service.interfaces;

import com.ride.domain.ride.Ride;
import com.ride.dto.request.CreateRideRequestDTO;
import com.ride.dto.request.RiderCreateRequest;

public interface RiderService {



    Ride requestRide(
            CreateRideRequestDTO request
    );

    Ride getRideDetails(int rideId);

    void cancelRide(int rideId, int riderId);

    void validateRider(int riderId);

    int registerRider(RiderCreateRequest req);
}
