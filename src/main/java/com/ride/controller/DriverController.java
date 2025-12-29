package com.ride.controller;

import java.util.Map;

import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.dto.request.DriverCreateRequest;
import com.ride.service.impl.DriverServiceImpl;
import io.javalin.Javalin;

public class DriverController {

    public static void register(Javalin app) {

        DriverServiceImpl driverService = ServiceFactory.getDriverService();

        app.post("/driver/register", ctx -> {
            DriverCreateRequest req = ctx.bodyAsClass(DriverCreateRequest.class);
            Long driverId = driverService.registerDriver(req);
            ctx.json(Map.of("driverId", driverId));
        });

        app.post("/driver/{id}/status", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            DriverStatus status = DriverStatus.valueOf(ctx.queryParam("status"));
            driverService.updateStatus(driverId, status);
            ctx.result("Status updated");
        });

        app.post("/driver/{id}/location", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            double lat = ctx.queryParamAsClass("lat", Double.class).get();
            double lon = ctx.queryParamAsClass("lon", Double.class).get();
            driverService.updateDriverLocation(driverId, new Location(lat, lon));
            ctx.result("Location updated");
        });

        app.get("/driver/{id}/rides", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            ctx.json(driverService.getAvailableRides(driverId));
        });

        app.post("/driver/{id}/{rideId}/accept", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            Long rideId = Long.parseLong(ctx.pathParam("rideId"));
            ctx.json(driverService.acceptRide(rideId, driverId));
        });

        app.post("/driver/{id}/{rideId}/cancel", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            Long rideId = Long.parseLong(ctx.pathParam("rideId"));
            driverService.driverCancelRide(rideId, driverId);
            ctx.result("Ride cancelled");
        }); 

        app.post("/driver/{id}/{rideId}/begin", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            Long rideId = Long.parseLong(ctx.pathParam("rideId"));
            double lat = ctx.queryParamAsClass("lat", Double.class).get();
            double lon = ctx.queryParamAsClass("lon", Double.class).get();
            driverService.beginRide(rideId, driverId, new Location(lat, lon));
            driverService.updateDriverLocation(driverId, new Location(lat, lon));
            ctx.result("Ride started");
        });

        app.post("/driver/{id}/{rideId}/end", ctx -> {
            Long driverId = Long.parseLong(ctx.pathParam("id"));
            Long rideId = Long.parseLong(ctx.pathParam("rideId"));
            double lat = ctx.queryParamAsClass("lat", Double.class).get();
            double lon = ctx.queryParamAsClass("lon", Double.class).get();
            driverService.endRide(rideId, driverId, new Location(lat, lon));
            ctx.result("Ride completed");
        });
    }
}
