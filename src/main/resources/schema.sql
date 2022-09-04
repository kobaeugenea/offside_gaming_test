CREATE TABLE user (
id INTEGER IDENTITY PRIMARY KEY,
name VARCHAR(64) NOT NULL);
CREATE UNIQUE INDEX uidx_name ON user (name);

CREATE TABLE measurement (
id INTEGER IDENTITY PRIMARY KEY,
user_id INTEGER NOT NULL,
cold_water_used_liter INTEGER NOT NULL,
hot_water_used_liter INTEGER NOT NULL,
gas_used_cubic_meter INTEGER NOT NULL,
FOREIGN KEY (user_id) REFERENCES user(id));