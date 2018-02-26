CREATE TABLE IF NOT EXISTS drugs (
  id SERIAL PRIMARY KEY,
  name VARCHAR(80),
  package_size INTEGER,
  pzn VARCHAR(8)
);

-- CREATE INDEX drugs_name ON drugs (name);
