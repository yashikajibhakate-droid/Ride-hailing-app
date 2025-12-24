// package com.ride.domain.ride;

// import com.ride.domain.driver.Driver;

// public class RideStateMachine {

//     private static final double PICKUP_RADIUS_METERS = 50;
//     private static final double DROPOFF_RADIUS_METERS = 50;

//     public static void acceptRide(Ride ride, Driver driver) {
//         if (ride.getStatus() != RideStatus.REQUESTED) {
//             throw new IllegalStateException("Ride cannot be accepted from state: " + ride.getStatus());
//         }

//         ride.status = RideStatus.ACCEPTED;
//         ride.driverId = driver.getDriverId();
//         ride.setAcceptedAt(java.time.LocalDateTime.now());
//     }


//     public static void beginRide(Ride ride, Location currentDriverLocation) {

//         if (ride.getStatus() != RideStatus.ACCEPTED) {
//             throw new IllegalStateException("Ride cannot begin from state: " + ride.getStatus());
//         }

//         if (!isWithinRadius(ride.pickupLocation, currentDriverLocation, PICKUP_RADIUS_METERS)) {
//             throw new IllegalStateException("Driver not at pickup location");
//         }

//         ride.status = RideStatus.IN_PROGRESS;
//         ride.setStartedAt(java.time.LocalDateTime.now());
//     }


//     public static void endRide(Ride ride, Location currentDriverLocation) {

//         if (ride.getStatus() != RideStatus.IN_PROGRESS) {
//             throw new IllegalStateException("Ride cannot be completed from " + ride.getStatus());
//         }

//         if (!isWithinRadius(ride.dropoffLocation, currentDriverLocation, DROPOFF_RADIUS_METERS)) {
//             throw new IllegalStateException("Driver not near dropoff location");
//         }

//         ride.status = RideStatus.COMPLETED;
//         ride.setEndedAt(java.time.LocalDateTime.now()); 
//     }


//     public static void riderCancel(Ride ride) {
//         if (ride.getStatus() == RideStatus.IN_PROGRESS) {
//             throw new IllegalStateException("Cannot cancel once ride started");
//         }
//         ride.status = RideStatus.CANCELED;
//     }


//     public static void driverCancel(Ride ride) {
//         if (ride.getStatus() == RideStatus.IN_PROGRESS) {
//             throw new IllegalStateException("Cannot cancel once ride started");
//         }
//         ride.status = RideStatus.DRIVER_CANCELED;
//     }


//     private static boolean isWithinRadius(Location a, Location b, double maxMeters) {
//         double d = haversine(a.getLat(), a.getLon(), b.getLat(), b.getLon());
//         return d <= maxMeters;
//     }

//     private static double haversine(double lat1, double lon1, double lat2, double lon2) {
//         double R = 6371000; 
//         double dLat = Math.toRadians(lat2 - lat1);
//         double dLon = Math.toRadians(lon2 - lon1);

//         double x = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                 Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                 Math.sin(dLon/2) * Math.sin(dLon/2);

//         double c = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
//         return R * c;
//     }
// }
