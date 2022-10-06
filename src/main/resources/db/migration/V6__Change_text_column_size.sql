UPDATE pg_attribute SET atttypmod = 2048+4
WHERE attrelid = 'message'::regclass
  AND attname = 'text'