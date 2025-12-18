package com.ride.domain.ride;

import java.time.LocalDateTime;

public class Ride {

    public int rideId;

    private String pickup;
    private String dropoff;
    Location pickupLocation;
    Location dropoffLocation;

    int driverId;
    private int riderId;

    RideStatus status;

    private LocalDateTime requestedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public Ride(int rideId, String pickup, String dropoff,
            Location pickupLocation, Location dropoffLocation,
            int riderId) {

        this.rideId = rideId;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.riderId = riderId;

        this.status = RideStatus.REQUESTED;
        this.requestedAt = LocalDateTime.now();
    }

    public Ride() {
    }

    public Ride(int int1, int int2, String string, String string2, Location location, Location location2,
            RideStatus valueOf) {
    }

    public RideStatus getStatus() {
        return status;
    }

    public int getRideId() {
        return rideId;
    }

    public int getDriverId() {
        return driverId;
    }

    public int getRiderId() {
        return riderId;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropoffLocation() {
        return dropoffLocation;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {

        this.acceptedAt = acceptedAt;
    }

    public void pickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;

    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }
    public void setRideId(int rideId) {
        this.rideId = rideId;
    }
    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    public void setPickup(String pickup) {
        this.pickup = pickup;
    }
    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public void setDropoffLocation(Location dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }
}
