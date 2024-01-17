ALTER TABLE reported_product
    ALTER COLUMN stddusqy TYPE TEXT USING stddusqy::TEXT,
    ALTER COLUMN safe_sd TYPE TEXT USING safe_sd::TEXT,
    ALTER COLUMN propos TYPE TEXT USING propos::TEXT;
