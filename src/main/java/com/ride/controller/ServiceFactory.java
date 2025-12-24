package com.ride.controller;

import com.ride.repository.impl.*;
import com.ride.service.impl.*;

public class ServiceFactory {

    public static RiderServiceImpl getRiderService() {
        return new RiderServiceImpl(
                new RideRepositoryImpl(),
                new RiderRepositoryImpl()
        );
    }

    public static DriverServiceImpl getDriverService() {
        return new DriverServiceImpl(
                new DriverRepositoryImpl(),
                new RideRepositoryImpl(),
                new DriverLocationRepositoryImpl()
        );
    }
}
