package com.ride.controller;

import com.ride.domain.ride.Location;
import com.ride.service.impl.RiderServiceImpl;
import io.javalin.Javalin;

public class RiderController {

    public static void register(Javalin app) {

        RiderServiceImpl riderService = ServiceFactory.getRiderService();

        app.post("/rider/ride", ctx -> {

            int riderId = ctx.queryParamAsClass("riderId", Integer.class).get();
            String pickup = ctx.queryParam("pickup");
            String dropoff = ctx.queryParam("dropoff");

            double pLat = ctx.queryParamAsClass("pickupLat", Double.class).get();
            double pLon = ctx.queryParamAsClass("pickupLon", Double.class).get();

            double dLat = ctx.queryParamAsClass("dropLat", Double.class).get();
            double dLon = ctx.queryParamAsClass("dropLon", Double.class).get();

            ctx.json(
                riderService.requestRide(
                    riderId,
                    pickup,
                    dropoff,
                    new Location(pLat, pLon),
                    new Location(dLat, dLon)
                )
            );
        });

        app.get("/rider/ride/{rideId}", ctx -> {
            int rideId = Integer.parseInt(ctx.pathParam("rideId"));
            ctx.json(riderService.getRideStatus(rideId));
        });

        app.post("/rider/ride/{rideId}/cancel", ctx -> {
            int rideId = Integer.parseInt(ctx.pathParam("rideId"));
            int riderId = ctx.queryParamAsClass("riderId", Integer.class).get();
            riderService.cancelRide(rideId, riderId);
            ctx.result("Ride cancelled");
        });
    }
}
