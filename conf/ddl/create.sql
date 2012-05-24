CREATE DOMAIN IF NOT EXISTS COLOR_HEX AS
  VARCHAR2(6) CHECK (
        REGEXP_REPLACE(VALUE, '[0-9]+', 'X') IN ('XXX','XXXXXX')
        );

CREATE DOMAIN IF NOT EXISTS STROKE_TYPE AS
  VARCHAR2(5) CHECK (
        VALUE IN ('DOT','DASH','SOLID')
        );

CREATE TABLE IF NOT EXISTS rooms (
  id INT PRIMARY KEY,
  overview_vg_id INT NOT NULL,
  detail_vg_id INT NOT NULL,
  num INT UNIQUE NOT NULL,
  x_pos INT NOT NULL,
  y_pos INT NOT NULL
);

CREATE TABLE IF NOT EXISTS stands (
  id INT PRIMARY KEY,
  room_id INT NOT NULL,
  num INT NOT NULL
);

CREATE TABLE IF NOT EXISTS devices (
  id INT PRIMARY KEY,
  stand_id INT NOT NULL,
  num INT NOT NULL,
  type VARCHAR2(256)
);

CREATE TABLE IF NOT EXISTS fill_types (
  id INT PRIMARY KEY,
  name VARCHAR(128),
  color color_hex
);

CREATE TABLE IF NOT EXISTS stroke_types (
  id INT PRIMARY KEY,
  name VARCHAR(128),
  color color_hex,
  type stroke_type
);

CREATE TABLE IF NOT EXISTS vector_groups (
  id INT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS vectors (
  id INT PRIMARY KEY,
  group_id INT NOT NULL,
  type VARCHAR2(12),
  content VARCHAR2(1024)
);

ALTER TABLE stands ADD FOREIGN KEY (room_id) REFERENCES rooms(id);

ALTER TABLE devices ADD FOREIGN KEY (stand_id) REFERENCES stands(id);

RUNSCRIPT FROM 'conf/ddl/data.sql';