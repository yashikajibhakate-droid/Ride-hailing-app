package com.ride.repository.impl;

import com.ride.config.DatabaseConnection;
import com.ride.domain.rider.Rider;
import com.ride.repository.interfaces.RiderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RiderRepositoryImpl implements RiderRepository {


    @Override
    public Long saveRider(String name, String phone_number, String email) {
        String sql =
            "INSERT INTO rider (name, phone_number, email) " +
            "VALUES (?, ?, ?) " +
            "RETURNING rider_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone_number);
            ps.setString(3, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getLong("rider_id");
        } catch (Exception e) {
            throw new RuntimeException("Error saving rider", e);
        }
    }

    @Override
    public Rider findById(Long riderId) {
        String sql = "SELECT rider_id, name, phone, email FROM rider WHERE rider_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, riderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Rider(
                        rs.getLong("rider_id"),
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
    public boolean existsById(Long riderId) {
        String sql = "SELECT 1 FROM rider WHERE rider_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, riderId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException("Error checking rider existence", e);
        }
    }
}
