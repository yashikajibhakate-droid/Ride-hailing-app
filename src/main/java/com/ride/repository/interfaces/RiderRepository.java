package com.ride.repository.interfaces;

import com.ride.domain.rider.Rider;

public interface RiderRepository {

    Rider findById(int riderId);

    boolean existsById(int riderId);

    int saveRider(String name, String phone, String email);
}
