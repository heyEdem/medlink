ALTER TABLE "user"
    ADD name VARCHAR(255);

ALTER TABLE "user"
    ALTER COLUMN name SET NOT NULL;