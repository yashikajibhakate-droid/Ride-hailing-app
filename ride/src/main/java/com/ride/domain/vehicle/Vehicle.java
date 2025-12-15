package com.ride.domain.vehicle;

public class Vehicle {
    private String vehicleNo;
    private String vehicleType;

    public Vehicle(String vehicleNo, String vehicleType) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNo() { return vehicleNo; }
    public String getVehicleType() { return vehicleType; }
}
