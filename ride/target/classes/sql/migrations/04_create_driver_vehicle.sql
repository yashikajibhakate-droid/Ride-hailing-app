CREATE TABLE driver_vehicle (
    driver_id INT REFERENCES driver(driver_id),
    vehicle_no VARCHAR(20) REFERENCES vehicle(vehicle_no),
    PRIMARY KEY (driver_id, vehicle_no)
);
