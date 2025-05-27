ALTER TABLE availability DROP COLUMN IF EXISTS doctor_user_id;
-- Migration: Drop digital_address field from user table
ALTER TABLE users DROP COLUMN IF EXISTS digital_address;
