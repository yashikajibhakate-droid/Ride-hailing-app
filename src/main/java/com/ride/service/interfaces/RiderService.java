package com.ride.service.interfaces;

import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.dto.request.RiderCreateRequest;

public interface RiderService {



    Ride requestRide(
            int riderId,
            String pickup,
            String dropoff,
            Location pickupLocation,
            Location dropoffLocation
    );

    Ride getRideStatus(int rideId);

    void cancelRide(int rideId, int riderId);

    void validateRider(int riderId);

    int registerRider(RiderCreateRequest req);
}
