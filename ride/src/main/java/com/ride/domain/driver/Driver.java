package com.ride.domain.driver;

public class Driver {
    private int driverId;
    private String driverName;
    private String phone;
    private String licenseNo;
    private DriverStatus status;
    private int completedRides;

    private String currentlyDrivingVehicleNo;

    public Driver(int driverId, String driverName, String phone,
            String licenseNo, String currentlyDrivingVehicleNo) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.phone = phone;
        this.licenseNo = licenseNo;
        this.currentlyDrivingVehicleNo = currentlyDrivingVehicleNo;
        this.status = DriverStatus.OFFLINE;
    }

    public void goOnline() {
        this.status = DriverStatus.ONLINE;
    }

    public void goOffline() {
        this.status = DriverStatus.OFFLINE;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public String getCurrentlyDrivingVehicleNo() {
        return currentlyDrivingVehicleNo;
    }

    public int getCompletedRides() {
        return completedRides;
    }
}
