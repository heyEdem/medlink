-- Drop the existing start_time and end_time columns
ALTER TABLE appointment
DROP COLUMN IF EXISTS end_time;

ALTER TABLE appointment
DROP COLUMN IF EXISTS start_time;

-- Add start_time and end_time as TIME WITHOUT TIME ZONE
ALTER TABLE appointment
    ADD COLUMN start_time TIME WITHOUT TIME ZONE;

ALTER TABLE appointment
    ADD COLUMN end_time TIME WITHOUT TIME ZONE;