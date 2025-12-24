package com.ride.repository.impl;

import com.ride.config.DatabaseConnection;
import com.ride.domain.rider.Rider;
import com.ride.repository.interfaces.RiderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RiderRepositoryImpl implements RiderRepository {

    @Override
    public Rider findById(int riderId) {
        String sql = "SELECT rider_id, name, phone, email FROM rider WHERE rider_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, riderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Rider(
                        rs.getInt("rider_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching rider", e);
        }
    }

    @Override
    public boolean existsById(int riderId) {
        String sql = "SELECT 1 FROM rider WHERE rider_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, riderId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException("Error checking rider existence", e);
        }
    }
}
