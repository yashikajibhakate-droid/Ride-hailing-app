CREATE TABLE driver (
    driver_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    license_no VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL, -- ONLINE / OFFLINE
    completed_rides INT DEFAULT 0
);
