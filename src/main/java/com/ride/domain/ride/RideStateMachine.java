package com.ride.domain.ride;

import java.time.Instant;

public class RideStateMachine {

    public void onRequest(Ride ride) {
        ride.setStatus(RideStatus.REQUESTED);
        ride.setRequestedAt(Instant.now());
    }

    public void onRiderCancel(Ride ride) {
        if (ride.getStatus() != RideStatus.REQUESTED &&
                ride.getStatus() != RideStatus.ACCEPTED) {
            throw new IllegalStateException(
                    "Rider cannot cancel ride in state: " + ride.getStatus());
        }
        ride.setStatus(RideStatus.CANCELED);
    }

    public void onDriverCancel(Ride ride) {
        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new IllegalStateException(
                    "Driver cannot cancel ride in state: " + ride.getStatus());
        }
        ride.setStatus(RideStatus.REQUESTED);
        ride.setDriverId(null);
    }

    public void onDriverAccept(Ride ride) {
        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride must be REQUESTED");
        }
        ride.setStatus(RideStatus.ACCEPTED);
        ride.setAcceptedAt(Instant.now());
    }

    public void onStart(Ride ride) {
        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new IllegalStateException("Ride must be ACCEPTED");
        }
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartedAt(Instant.now());
    }

    public void onComplete(Ride ride) {
        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new IllegalStateException("Ride must be IN_PROGRESS");
        }
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndedAt(Instant.now());
    }

    public boolean isAtDropLocation(Location current, Location drop) {

        double tolerance = 0.0001; // ~11 meters

        return Math.abs(current.getLat() - drop.getLat()) <= tolerance
                && Math.abs(current.getLon() - drop.getLon()) <= tolerance;
    }

}
