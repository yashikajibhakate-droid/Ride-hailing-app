CREATE TABLE ride (
    ride_id SERIAL PRIMARY KEY,

    rider_id INT NOT NULL REFERENCES rider(rider_id),
    driver_id INT REFERENCES driver(driver_id),

    pickup VARCHAR(255) NOT NULL,
    dropoff VARCHAR(255) NOT NULL,

    pickup_lat DOUBLE PRECISION NOT NULL,
    pickup_lon DOUBLE PRECISION NOT NULL,

    dropoff_lat DOUBLE PRECISION NOT NULL,
    dropoff_lon DOUBLE PRECISION NOT NULL,

    status VARCHAR(30) NOT NULL,

    requested_at TIMESTAMP NOT NULL,
    accepted_at TIMESTAMP,
    started_at TIMESTAMP,
    ended_at TIMESTAMP
);
