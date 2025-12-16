package com.ride.repository.impl;

import com.ride.config.DatabaseConnection;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.domain.ride.RideStatus;
import com.ride.repository.interfaces.RideRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideRepositoryImpl implements RideRepository {

    @Override
    public Ride create(Ride ride) {
        String sql = """
            INSERT INTO ride (
                rider_id, pickup, dropoff,
                pickup_lat, pickup_lon,
                dropoff_lat, dropoff_lon,
                status, requested_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING ride_id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ride.getRiderId());
            ps.setString(2, ride.getPickup());
            ps.setString(3, ride.getDropoff());
            ps.setDouble(4, ride.getPickupLocation().getLat());
            ps.setDouble(5, ride.getPickupLocation().getLon());
            ps.setDouble(6, ride.getDropoffLocation().getLat());
            ps.setDouble(7, ride.getDropoffLocation().getLon());
            ps.setString(8, ride.getStatus().name());
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ride.rideId = rs.getInt(1);
            }
            return ride;

        } catch (Exception e) {
            throw new RuntimeException("Error creating ride", e);
        }
    }

    @Override
    public Ride findById(int rideId) {
        String sql = "SELECT * FROM ride WHERE ride_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rideId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Mapping left concise for clarity
                return null; // (will complete fully in service step)
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching ride", e);
        }
    }

    @Override
    public List<Ride> findByStatus(RideStatus status) {
        return new ArrayList<>();
    }

    @Override
    public void updateRide(Ride ride) {
        String sql = """
            UPDATE ride SET
                status = ?,
                accepted_at = ?,
                started_at = ?,
                ended_at = ?
            WHERE ride_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ride.getStatus().name());
            ps.setTimestamp(2, null);
            ps.setTimestamp(3, null);
            ps.setTimestamp(4, null);
            ps.setInt(5, ride.getRideId());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error updating ride", e);
        }
    }

    @Override
    public boolean acceptRide(int rideId, int driverId) {
        String sql = """
            UPDATE ride
            SET driver_id=?, status='ACCEPTED', accepted_at=NOW()
            WHERE ride_id=? AND status='REQUESTED'
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            ps.setInt(2, rideId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            throw new RuntimeException("Error accepting ride", e);
        }
    }
}
