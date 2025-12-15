package com.ride.repository.interfaces;

import com.ride.domain.vehicle.Vehicle;

public interface VehicleRepository {

    Vehicle findByVehicleNo(String vehicleNo);
}
