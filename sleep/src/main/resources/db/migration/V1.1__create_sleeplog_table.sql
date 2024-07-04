CREATE TABLE sleep_log
(
    id                SERIAL PRIMARY KEY,
    user_id           INT  NOT NULL,
    sleep_date        DATE NOT NULL,
    time_in_bed_start TIME NOT NULL,
    time_in_bed_end   TIME NOT NULL,
    total_time_in_bed INT  NOT NULL,
    user_feeling      VARCHAR(10) CHECK (user_feeling IN ('BAD', 'OK', 'GOOD'))
);
