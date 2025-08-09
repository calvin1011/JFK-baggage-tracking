-- Airlines table
CREATE TABLE airlines (
    id BIGSERIAL PRIMARY KEY,
    iata_code VARCHAR(3) UNIQUE NOT NULL,
    icao_code VARCHAR(4) UNIQUE,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Airports table
CREATE TABLE airports (
    id BIGSERIAL PRIMARY KEY,
    iata_code VARCHAR(3) UNIQUE NOT NULL,
    icao_code VARCHAR(4) UNIQUE,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    timezone VARCHAR(50),
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Aircraft table
CREATE TABLE aircraft (
    id BIGSERIAL PRIMARY KEY,
    registration VARCHAR(20) UNIQUE NOT NULL,
    aircraft_type VARCHAR(50),
    airline_id BIGINT REFERENCES airlines(id),
    capacity INTEGER,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Passengers table
CREATE TABLE passengers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    frequent_flyer_number VARCHAR(50),
    date_of_birth DATE,
    nationality VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Flights table
CREATE TABLE flights (
    id BIGSERIAL PRIMARY KEY,
    flight_number VARCHAR(10) NOT NULL,
    airline_id BIGINT REFERENCES airlines(id),
    aircraft_id BIGINT REFERENCES aircraft(id),
    departure_airport_id BIGINT REFERENCES airports(id),
    arrival_airport_id BIGINT REFERENCES airports(id),
    scheduled_departure TIMESTAMP NOT NULL,
    scheduled_arrival TIMESTAMP NOT NULL,
    actual_departure TIMESTAMP,
    actual_arrival TIMESTAMP,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    gate VARCHAR(10),
    terminal VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Baggage table
CREATE TABLE baggage (
    id BIGSERIAL PRIMARY KEY,
    bag_tag_number VARCHAR(20) UNIQUE NOT NULL,
    passenger_id BIGINT REFERENCES passengers(id),
    flight_id BIGINT REFERENCES flights(id),
    weight DECIMAL(5,2),
    dimensions VARCHAR(50),
    bag_type VARCHAR(20) DEFAULT 'CHECKED',
    priority VARCHAR(20) DEFAULT 'NORMAL',
    current_status VARCHAR(50) DEFAULT 'CHECKED_IN',
    current_location VARCHAR(100),
    final_destination VARCHAR(3),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Baggage Events table (tracking history)
CREATE TABLE baggage_events (
    id BIGSERIAL PRIMARY KEY,
    baggage_id BIGINT REFERENCES baggage(id),
    event_type VARCHAR(50) NOT NULL,
    event_description TEXT,
    location VARCHAR(100),
    airport_id BIGINT REFERENCES airports(id),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    scanned_by VARCHAR(100),
    equipment_id VARCHAR(50),
    notes TEXT
);

-- Scanning Locations table
CREATE TABLE scanning_locations (
    id BIGSERIAL PRIMARY KEY,
    airport_id BIGINT REFERENCES airports(id),
    location_type VARCHAR(50) NOT NULL, -- CHECK_IN, SECURITY, SORTING, LOADING, etc.
    location_name VARCHAR(100) NOT NULL,
    location_code VARCHAR(20),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Users table (for staff authentication)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    airport_id BIGINT REFERENCES airports(id),
    active BOOLEAN DEFAULT true,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for performance
CREATE INDEX idx_baggage_tag_number ON baggage(bag_tag_number);
CREATE INDEX idx_baggage_passenger_id ON baggage(passenger_id);
CREATE INDEX idx_baggage_flight_id ON baggage(flight_id);
CREATE INDEX idx_baggage_status ON baggage(current_status);
CREATE INDEX idx_baggage_events_baggage_id ON baggage_events(baggage_id);
CREATE INDEX idx_baggage_events_timestamp ON baggage_events(timestamp);
CREATE INDEX idx_flights_number ON flights(flight_number);
CREATE INDEX idx_flights_departure ON flights(scheduled_departure);
CREATE INDEX idx_passengers_email ON passengers(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);