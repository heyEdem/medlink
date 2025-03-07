-- ALTER TABLE users
--     ADD firstname VARCHAR(255);
--
-- ALTER TABLE users
--     ADD lastname VARCHAR(255);
--
-- ALTER TABLE users
--     ALTER COLUMN firstname SET NOT NULL;
--
-- ALTER TABLE users
--     ALTER COLUMN lastname SET NOT NULL;
--
-- ALTER TABLE users
-- DROP
-- COLUMN name;

ALTER TABLE users ADD firstname VARCHAR(255);
ALTER TABLE users ADD lastname VARCHAR(255);

-- Assuming `name` is "Firstname Lastname", split it
UPDATE users
SET firstname = SPLIT_PART(name, ' ', 1),
    lastname = SPLIT_PART(name, ' ', 2);

ALTER TABLE users ALTER COLUMN firstname SET NOT NULL;
ALTER TABLE users ALTER COLUMN lastname SET NOT NULL;

ALTER TABLE users DROP COLUMN name;
