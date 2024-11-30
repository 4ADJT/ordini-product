CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE products
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       NUMERIC(10, 2) NOT NULL CHECK (price > 0),
    stock       INT            NOT NULL CHECK (stock >= 0),
    source_file VARCHAR(255),
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE processed_files
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    file_name    VARCHAR(255) NOT NULL UNIQUE,
    processed_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);
