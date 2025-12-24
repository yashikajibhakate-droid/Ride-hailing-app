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
            int driverId = driverService.registerDriver(req);
            ctx.json(Map.of("driverId", driverId));
        });

        app.post("/driver/{id}/status", ctx -> {
            int driverId = Integer.parseInt(ctx.pathParam("id"));
            DriverStatus status = DriverStatus.valueOf(ctx.queryParam("status"));
            driverService.updateStatus(driverId, status);
            ctx.result("Status updated");
        });

        app.post("/driver/{id}/location", ctx -> {
            int driverId = Integer.parseInt(ctx.pathParam("id"));
            double lat = ctx.queryParamAsClass("lat", Double.class).get();
            double lon = ctx.queryParamAsClass("lon", Double.class).get();
            driverService.updateDriverLocation(driverId, new Location(lat, lon));
            ctx.result("Location updated");
        });

        app.get("/driver/{id}/rides", ctx -> {
            int driverId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(driverService.getAvailableRides(driverId));
        });

        app.post("/driver/{id}/ride/{rideId}/accept", ctx -> {
            int driverId = Integer.parseInt(ctx.pathParam("id"));
            int rideId = Integer.parseInt(ctx.pathParam("rideId"));
            ctx.json(driverService.acceptRide(rideId, driverId));
        });

        app.post("/driver/{id}/ride/{rideId}/begin", ctx -> {
            int driverId = Integer.parseInt(ctx.pathParam("id"));
            int rideId = Integer.parseInt(ctx.pathParam("rideId"));
            double lat = ctx.queryParamAsClass("lat", Double.class).get();
            double lon = ctx.queryParamAsClass("lon", Double.class).get();
            driverService.beginRide(rideId, driverId, new Location(lat, lon));
            ctx.result("Ride started");
        });

        app.post("/driver/{id}/ride/{rideId}/end", ctx -> {
            int driverId = Integer.parseInt(ctx.pathParam("id"));
            int rideId = Integer.parseInt(ctx.pathParam("rideId"));
            double lat = ctx.queryParamAsClass("lat", Double.class).get();
            double lon = ctx.queryParamAsClass("lon", Double.class).get();
            driverService.endRide(rideId, driverId, new Location(lat, lon));
            ctx.result("Ride completed");
        });
    }
}
