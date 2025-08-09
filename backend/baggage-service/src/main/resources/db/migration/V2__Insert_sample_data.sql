-- Insert sample airlines
INSERT INTO airlines (iata_code, icao_code, name, country) VALUES
('AA', 'AAL', 'American Airlines', 'United States'),
('DL', 'DAL', 'Delta Air Lines', 'United States'),
('UA', 'UAL', 'United Airlines', 'United States'),
('WN', 'SWA', 'Southwest Airlines', 'United States');

-- Insert sample airports
INSERT INTO airports (iata_code, icao_code, name, city, country, timezone) VALUES
('JFK', 'KJFK', 'John F. Kennedy International Airport', 'New York', 'United States', 'America/New_York'),
('LAX', 'KLAX', 'Los Angeles International Airport', 'Los Angeles', 'United States', 'America/Los_Angeles'),
('ORD', 'KORD', 'O''Hare International Airport', 'Chicago', 'United States', 'America/Chicago'),
('ATL', 'KATL', 'Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'United States', 'America/New_York'),
('DFW', 'KDFW', 'Dallas/Fort Worth International Airport', 'Dallas', 'United States', 'America/Chicago');

-- Insert sample aircraft
INSERT INTO aircraft (registration, aircraft_type, airline_id, capacity) VALUES
('N123AA', 'Boeing 737-800', 1, 160),
('N456DL', 'Airbus A320', 2, 150),
('N789UA', 'Boeing 777-200', 3, 300),
('N101WN', 'Boeing 737-700', 4, 140);

-- Insert sample scanning locations
INSERT INTO scanning_locations (airport_id, location_type, location_name, location_code) VALUES
(1, 'CHECK_IN', 'Terminal 1 Check-in Counter A', 'T1-CKA'),
(1, 'SECURITY', 'Terminal 1 Security Checkpoint', 'T1-SEC'),
(1, 'SORTING', 'Baggage Sorting Facility', 'T1-SORT'),
(1, 'LOADING', 'Gate A1 Loading Area', 'T1-A1'),
(2, 'CHECK_IN', 'Terminal 2 Check-in Counter B', 'T2-CKB'),
(2, 'SECURITY', 'Terminal 2 Security Checkpoint', 'T2-SEC');

-- Insert sample user
INSERT INTO users (username, email, password_hash, first_name, last_name, role, airport_id) VALUES
('admin', 'admin@airline.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'System', 'Administrator', 'ADMIN', 1),
('baggage_handler', 'handler@airline.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John', 'Handler', 'BAGGAGE_HANDLER', 1);
-- Password for both users is 'password'