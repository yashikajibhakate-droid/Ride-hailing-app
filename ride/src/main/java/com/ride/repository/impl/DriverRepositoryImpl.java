package com.ride.repository.impl;

import com.ride.config.DatabaseConnection;
import com.ride.domain.driver.Driver;
import com.ride.domain.driver.DriverStatus;
import com.ride.domain.ride.Location;
import com.ride.repository.interfaces.DriverRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

public class DriverRepositoryImpl implements DriverRepository {

    @Override
   public int save(String name, String phone, String email) {
    String sql =
        "INSERT INTO driver (name, phone, email) " +
        "VALUES (?, ?, ?) " +
        "RETURNING driver_id";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, phone);
        ps.setString(3, email);

        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("driver_id");

    } catch (SQLException e) {
        throw new RuntimeException("Error saving driver", e);
    }
}



    @Override
    public Driver findById(int driverId) {
        String sql = "SELECT * FROM driver WHERE driver_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("license_no"),
                        rs.getString("current_vechicle_no"),
                        rs.getNString("email")
                        
                );
                driver.goOffline();
                if ("ONLINE".equals(rs.getString("status"))) {
                    driver.goOnline();
                }
                return driver;
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching driver", e);
        }
    }

    @Override
    public void updateStatus(int driverId, DriverStatus status) {
        String sql = "UPDATE driver SET status = ? WHERE driver_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, driverId);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error updating driver status", e);
        }
    }

    @Override
    public void updateLocation(int driverId, Location location) {
        String sql = """
            INSERT INTO driver_location (driver_id, lat, lon)
            VALUES (?, ?, ?)
            ON CONFLICT (driver_id)
            DO UPDATE SET lat=?, lon=?, updated_at=NOW()
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            ps.setDouble(2, location.getLat());
            ps.setDouble(3, location.getLon());
            ps.setDouble(4, location.getLat());
            ps.setDouble(5, location.getLon());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error updating driver location", e);
        }
    }

    @Override
    public Location getCurrentLocation(int driverId) {
        String sql = "SELECT lat, lon FROM driver_location WHERE driver_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Location(rs.getDouble("lat"), rs.getDouble("lon"));
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching driver location", e);
        }
    }

    @Override
    public List<Driver> findAvailableDriversNear(Location pickupLocation, double radiusMeters) {
        String sql = "SELECT * FROM driver WHERE status='ONLINE'";

        List<Driver> drivers = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                drivers.add(new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("license_no"),
                        rs.getString("current_vechicle_no"),
                        rs.getNString("email")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return drivers;
    }
}
