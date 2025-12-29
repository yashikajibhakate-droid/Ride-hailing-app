package com.ride.domain.ride;

import java.time.Instant;
import java.time.LocalDateTime;

public class Ride {

    /* =======================
       Core Identifiers
       ======================= */

    private Long rideId;
    private Long riderId;
    private Long driverId; // nullable until accepted

    /* =======================
       Locations & Route
       ======================= */

    private String pickup;
    private String dropoff;

    private Location pickupLocation;
    private Location dropoffLocation;

    /* =======================
       State Machine
       ======================= */

    private RideStatus status;

    /* =======================
       Timestamps
       ======================= */

    private Instant requestedAt;
    private Instant acceptedAt;
    private Instant startedAt;
    private Instant endedAt;

    /* =========================================================
       Constructor 1: Create NEW ride (used by RiderService)
       ========================================================= */
    public Ride(
            String pickup,
            String dropoff,
            Location pickupLocation,
            Location dropoffLocation,
            Long riderId
    ) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.riderId = riderId;

        this.status = RideStatus.REQUESTED;
        this.requestedAt = Instant.now();
    }

    /* =========================================================
       Constructor 2: Load ride FROM DB (used by Repository)
       ========================================================= */
   public Ride(
        Long rideId,
        Long riderId,
        Long driverId,
        String pickup,
        String dropoff,
        Location pickupLocation,
        Location dropoffLocation,
        RideStatus status,
        Instant requestedAt,
        Instant acceptedAt,
        Instant endedAt
) {
    this.rideId = rideId;
    this.riderId = riderId;
    this.driverId = driverId;

    this.pickup = pickup;
    this.dropoff = dropoff;

    this.pickupLocation = pickupLocation;
    this.dropoffLocation = dropoffLocation;

    this.status = status;

    this.requestedAt = requestedAt;
    this.acceptedAt = acceptedAt;
    this.endedAt = endedAt;
}


   
    public Ride() {}

    /* =======================
       Getters (Jackson needs these)
       ======================= */

    // public Ride(int int1, int int2, Integer object, String string, String string2, Location location,
    //         Location location2, RideStatus valueOf, Instant localDateTime, Object object2, Object object3) {
    //     //TODO Auto-generated constructor stub
    // }

    public Long getRideId() {
        return rideId;
    }

    public Long getRiderId() {
        return riderId;
    }

    public Long getDriverId() {
        return driverId;
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

    public RideStatus getStatus() {
        return status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

    /* =======================
       State Transition Helpers
       ======================= */

    public void assignDriver(Long driverId) {
        this.driverId = driverId;
        this.status = RideStatus.ACCEPTED;
        this.acceptedAt = Instant.now();
    }

    public void beginRide() {
        this.status = RideStatus.IN_PROGRESS;
        this.startedAt = Instant.now();
    }

    public void endRide() {
        this.status = RideStatus.COMPLETED;
        this.endedAt = Instant.now();
    }

    public void cancelByRider() {
        this.status = RideStatus.CANCELED;
    }

    public void cancelByDriver() {
        this.status = RideStatus.DRIVER_CANCELED;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }   
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
    public void setAcceptedAt(Instant acceptedAt) {
        this.acceptedAt = acceptedAt;
    }
    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }
    public void setEndedAt(Instant endedAt) {
        this.endedAt = endedAt;
    }
    public void setStatus(RideStatus status) {
        this.status = status;
    }
    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public void setDropoffLocation(Location dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }
    public void setPickup(String pickup) {
        this.pickup = pickup;
    }
    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }
    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }
}
