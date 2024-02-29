DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'auth'
        AND column_name = 'client_ip'
    ) THEN
ALTER TABLE auth ADD COLUMN client_ip VARCHAR;
END IF;
END $$;