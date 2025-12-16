package com.ride;

import java.sql.Connection;

import com.ride.config.DatabaseConnection;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ Database connection successful!");
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.err.println("❌ Database connection failed");
            e.printStackTrace();
        }
    }
}