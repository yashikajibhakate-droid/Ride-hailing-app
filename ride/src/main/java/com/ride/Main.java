package com.ride;

import java.sql.Connection;

import com.ride.config.DatabaseConnection;
import com.ride.controller.RiderController;

import io.javalin.Javalin;


public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(5000);


        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connection successful!");
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.err.println("Database connection failed");
            e.printStackTrace();
        }


        RiderController.register(app);
        System.out.println("Ride Hailing API running on port 5000");
    }
}