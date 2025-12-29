package com.ride.repository.impl;

import com.ride.config.DatabaseConnection;
import com.ride.domain.ride.Location;
import com.ride.domain.ride.Ride;
import com.ride.domain.ride.RideStatus;
import com.ride.repository.interfaces.RideRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RideRepositoryImpl implements RideRepository {

    @Override
    public Ride save(Ride ride) {

        String sql = """
                    INSERT INTO ride (
                        rider_id, pickup, dropoff,
                        pickup_lat, pickup_lon,
                        dropoff_lat, dropoff_lon,
                        status, ride_requested_at
                    )
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())
                    RETURNING ride_id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, ride.getRiderId());
            ps.setString(2, ride.getPickup());
            ps.setString(3, ride.getDropoff());
            ps.setDouble(4, ride.getPickupLocation().getLat());
            ps.setDouble(5, ride.getPickupLocation().getLon());
            ps.setDouble(6, ride.getDropoffLocation().getLat());
            ps.setDouble(7, ride.getDropoffLocation().getLon());
            ps.setString(8, RideStatus.REQUESTED.name());

            ResultSet rs = ps.executeQuery();
            rs.next();

            Long generatedRideId = rs.getLong("ride_id");
            ride.setRideId(generatedRideId);

            return ride;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create ride", e);
        }
    }

    @Override
    public boolean acceptRide(Long rideId, Long driverId) {

        String sql = """
                    UPDATE ride
                    SET driver_id = ?, status = 'ACCEPTED', ride_accepted_at = NOW()
                    WHERE ride_id = ? AND status = 'REQUESTED'
                """;

        return executeUpdate(sql, driverId, rideId);
    }

    @Override
    public boolean beginRide(Long rideId, Long driverId) {

        String sql = """
                    UPDATE ride
                    SET status = 'IN_PROGRESS'
                    WHERE ride_id = ?
                      AND driver_id = ?
                      AND status = 'ACCEPTED'
                """;

        return executeUpdate(sql, rideId, driverId);
    }

    @Override
    public boolean endRide(Long rideId, Long driverId) {

        String sql = """
                    UPDATE ride
                    SET status = 'COMPLETED', ride_ended_at = NOW()
                    WHERE ride_id = ?
                      AND driver_id = ?
                      AND status = 'IN_PROGRESS'
                """;

        return executeUpdate(sql, rideId, driverId);
    }

    @Override
    public boolean riderCancelRide(Long rideId, Long riderId) {

        String sql = """
                    UPDATE ride
                    SET status = 'CANCELED'
                    WHERE ride_id = ?
                      AND rider_id = ?
                      AND status IN ('REQUESTED', 'ACCEPTED')
                """;

        return executeUpdate(sql, rideId, riderId);
    }

    @Override
    public boolean driverCancelRide(Long rideId, Long driverId) {

        String sql = """
                    UPDATE ride
                    SET status = 'DRIVER_CANCELED'
                    WHERE ride_id = ?
                      AND driver_id = ?
                      AND status = 'ACCEPTED'
                """;

        return executeUpdate(sql, rideId, driverId);
    }

    @Override
    public Ride findById(Long rideId) {

        String sql = "SELECT * FROM ride WHERE ride_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, rideId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                return null;

            Ride ride = new Ride();
            ride.setRideId(rs.getLong("ride_id"));
            ride.setRiderId(rs.getLong("rider_id"));
            ride.setDriverId(rs.getLong("driver_id"));
            ride.setPickup(rs.getString("pickup"));
            ride.setDropoff(rs.getString("dropoff"));
            ride.setStatus(RideStatus.valueOf(rs.getString("status")));

            return ride;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch ride", e);
        }
    }

    private boolean executeUpdate(String sql, Object... params) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ride> findAvailableRidesForDriver(Long driverId) {

        List<Ride> rides = new ArrayList<>();

        String sql = """
                SELECT
                    ride_id,
                    rider_id,
                    driver_id,
                    pickup,
                    dropoff,
                    pickup_lat,
                    pickup_lon,
                    dropoff_lat,
                    dropoff_lon,
                    status,
                    ride_requested_at,
                    ride_accepted_at,
                    ride_ended_at
                FROM ride
                WHERE status = 'REQUESTED'
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Ride ride = new Ride(
                        rs.getLong("ride_id"),
                        rs.getLong("rider_id"),
                        (Long) rs.getObject("driver_id"),
                        rs.getString("pickup"),
                        rs.getString("dropoff"),
                        new Location(
                                rs.getDouble("pickup_lat"),
                                rs.getDouble("pickup_lon")),
                        new Location(
                                rs.getDouble("dropoff_lat"),
                                rs.getDouble("dropoff_lon")),
                        RideStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("ride_requested_at").toInstant(),
                        rs.getTimestamp("ride_accepted_at") != null
                                ? rs.getTimestamp("ride_accepted_at").toInstant()
                                : null,

                        rs.getTimestamp("ride_ended_at") != null
                                ? rs.getTimestamp("ride_ended_at").toInstant()
                                : null);

                System.out.println(
                        ride.getRideId() + " " +
                                ride.getPickup() + " " +
                                ride.getStatus());

                rides.add(ride);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch available rides", e);
        }

        return rides;
    }

    @Override
    public Ride getRideById(Long rideId) {

        String sql = """
                SELECT
                    ride_id,
                    rider_id,
                    driver_id,
                    pickup,
                    dropoff,
                    pickup_lat,
                    pickup_lon,
                    dropoff_lat,
                    dropoff_lon,
                    status,
                    ride_requested_at,
                    ride_accepted_at,
                    ride_ended_at
                FROM ride
                WHERE ride_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, rideId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null; // or throw RideNotFoundException
                }

                Ride ride = new Ride();

                ride.setRideId(rs.getLong("ride_id"));
                ride.setRiderId(rs.getLong("rider_id"));

                Long driverId = rs.getLong("driver_id");
                if (!rs.wasNull()) {
                    ride.setDriverId(driverId);
                }

                ride.setPickup(rs.getString("pickup"));
                ride.setDropoff(rs.getString("dropoff"));

                ride.setPickupLocation(
                        new Location(
                                rs.getDouble("pickup_lat"),
                                rs.getDouble("pickup_lon")));

                ride.setDropoffLocation(
                        new Location(
                                rs.getDouble("dropoff_lat"),
                                rs.getDouble("dropoff_lon")));

                ride.setStatus(RideStatus.valueOf(rs.getString("status")));

                ride.setRequestedAt(
                        rs.getTimestamp("ride_requested_at").toInstant());

                Timestamp acceptedTs = rs.getTimestamp("ride_accepted_at");
                if (acceptedTs != null) {
                    ride.setAcceptedAt(acceptedTs.toInstant());
                }

                Timestamp endedTs = rs.getTimestamp("ride_ended_at");
                if (endedTs != null) {
                    ride.setEndedAt(endedTs.toInstant());
                }

                return ride;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching ride " + rideId, e);
        }
    }

    @Override
    public Ride getRideDetails(Long rideId) {
        return getRideById(rideId);
    }

}
