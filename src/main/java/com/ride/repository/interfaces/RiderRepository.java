package com.ride.repository.interfaces;

import com.ride.domain.rider.Rider;

public interface RiderRepository {

    Rider findById(Long riderId);

    boolean existsById(Long riderId);

    Long saveRider(String name, String phone, String email);
}
