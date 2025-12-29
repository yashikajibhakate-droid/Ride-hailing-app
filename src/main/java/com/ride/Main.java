package com.ride;

import java.sql.Connection;
import java.util.NoSuchElementException;

import com.ride.config.DatabaseConnection;
import com.ride.controller.RiderController;
import com.ride.dto.response.ApiResponse;
import com.ride.controller.DriverController;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(5000);

        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(
                    new ApiResponse(e.getMessage(), 400));
        });

        app.exception(IllegalStateException.class, (e, ctx) -> {
            ctx.status(409).json(
                    new ApiResponse(e.getMessage(), 409));
        });

        app.exception(NoSuchElementException.class, (e, ctx) -> {
            ctx.status(404).json(
                    new ApiResponse(e.getMessage(), 404));
        });

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace(); // still log in terminal
            ctx.status(500).json(
                    new ApiResponse("Internal server error", 500));
        });

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ Database connection successful!");
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.err.println("❌ Database connection failed");
            e.printStackTrace();
        }

        RiderController.register(app);
        DriverController.register(app);

        System.out.println("🚖 Ride Hailing API running on port 5000");
    }
}