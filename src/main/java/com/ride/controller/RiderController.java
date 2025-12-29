package com.ride.controller;

import java.util.Map;

import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.dto.request.CreateRideRequestDTO;
import com.ride.dto.request.RiderCreateRequest;
import com.ride.service.impl.RiderServiceImpl;
import io.javalin.Javalin;

public class RiderController {

    public static void register(Javalin app) {

        RiderServiceImpl riderService = ServiceFactory.getRiderService();

        app.post("/rider/register", ctx -> {
            RiderCreateRequest req = ctx.bodyAsClass(RiderCreateRequest.class);
            Long riderId = riderService.registerRider(req);
            ctx.json(Map.of("riderId", riderId));
        });

        app.post("/ride", ctx -> {

            CreateRideRequestDTO request = ctx.bodyAsClass(CreateRideRequestDTO.class);

            Ride ride = riderService.requestRide(request);

            ctx.status(201).json(ride);
        });

        app.get("/rider/ride/{rideId}", ctx -> {
            Long rideId = Long.parseLong(ctx.pathParam("rideId"));
            ctx.json(riderService.getRideDetails(rideId));
        });

        app.patch("/rides/{rideId}", ctx -> {
            Long rideId = Long.parseLong(ctx.pathParam("rideId"));
            Long riderId = ctx.queryParamAsClass("riderId", Long.class).get();
            riderService.cancelRide(rideId, riderId);
            ctx.result("Ride cancelled");
        });
    }
}
