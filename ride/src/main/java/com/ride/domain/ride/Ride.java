package com.ride.domain.ride;

import java.time.LocalDateTime;

public class Ride {

    /* =======================
       Core Identifiers
       ======================= */

    private int rideId;
    private int riderId;
    private Integer driverId; // nullable until accepted

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

    private LocalDateTime requestedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    /* =========================================================
       Constructor 1: Create NEW ride (used by RiderService)
       ========================================================= */
    public Ride(
            String pickup,
            String dropoff,
            Location pickupLocation,
            Location dropoffLocation,
            int riderId
    ) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.riderId = riderId;

        this.status = RideStatus.REQUESTED;
        this.requestedAt = LocalDateTime.now();
    }

    /* =========================================================
       Constructor 2: Load ride FROM DB (used by Repository)
       ========================================================= */
   public Ride(
        int rideId,
        int riderId,
        Integer driverId,
        String pickup,
        String dropoff,
        Location pickupLocation,
        Location dropoffLocation,
        RideStatus status,
        LocalDateTime requestedAt,
        LocalDateTime acceptedAt,
        LocalDateTime endedAt
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


    /* =======================
       Required No-Args Constructor
       (Jackson / frameworks)
       ======================= */
    public Ride() {}

    /* =======================
       Getters (Jackson needs these)
       ======================= */

    public Ride(int int1, int int2, Integer object, String string, String string2, Location location,
            Location location2, RideStatus valueOf, LocalDateTime localDateTime, Object object2, Object object3) {
        //TODO Auto-generated constructor stub
    }

    public int getRideId() {
        return rideId;
    }

    public int getRiderId() {
        return riderId;
    }

    public Integer getDriverId() {
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

    /* =======================
       State Transition Helpers
       ======================= */

    public void assignDriver(int driverId) {
        this.driverId = driverId;
        this.status = RideStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
    }

    public void beginRide() {
        this.status = RideStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
    }

    public void endRide() {
        this.status = RideStatus.COMPLETED;
        this.endedAt = LocalDateTime.now();
    }

    public void cancelByRider() {
        this.status = RideStatus.CANCELED;
    }

    public void cancelByDriver() {
        this.status = RideStatus.DRIVER_CANCELED;
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
    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
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
    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }
}
