CREATE DATABASE gobooking
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    OWNER = postgres;

-- Note: In PostgreSQL, the USE statement is not needed since each query runs in its own transaction and can reference any database,
-- as long as the user has the necessary permissions to access it.

-- If you want to set the default database


CREATE TABLE "user"
(
    user_id   SERIAL       NOT NULL,
    name      varchar(255) NOT NULL,
    surname   varchar(255) NOT NULL,
    email     varchar(255) NOT NULL,
    password  varchar(255) NOT NULL,
    birth_date timestamp    NOT NULL,
    role      varchar(255) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE admin
(
    user_id    int          NOT NULL,
    admin_role varchar(255),
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES "user" (user_id)
        ON DELETE CASCADE
);



CREATE TABLE app_user
(
    user_id                int       NOT NULL,
    balance                int,
    city                   varchar(255),
    tax_number             varchar(255),
    registration_date      timestamp NOT NULL,
    is_blocked             boolean   NOT NULL,
    is_banned_from_booking boolean   NOT NULL,
    is_banned_from_posting boolean   NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES "user" (user_id)
        ON DELETE CASCADE
);

CREATE TABLE property
(
    property_id     SERIAL PRIMARY KEY NOT NULL,
    title           varchar(255),
    status          varchar(255),
    description     varchar(255),
    max_people      int,
    price_per_night int,
    bathroom_number int,
    room_number     int,
    type            varchar(255)    NOT NULL,
    owner_id        int                NOT NULL,
    city            varchar(255)       NOT NULL,
    district        varchar(255)       NOT NULL,
    neighborhood    varchar(255)       NOT NULL,
    building_no     int                 NOT NULL,
    apartment_no    int                 NOT NULL,
    rating          double precision,
    floor           int                 NOT NULL,
    added_date      timestamp          NOT NULL,
    wifi            boolean,
    kitchen         boolean,
    furnished       boolean,
    parking         boolean,
    ac              boolean,
    start_date      timestamp,
    end_date        timestamp,
    elevator        boolean,
    fire_alarm      boolean,
    FOREIGN KEY (owner_id) REFERENCES "user" (user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE booking
(
    booking_id  SERIAL PRIMARY KEY NOT NULL,
    start_date  TIMESTAMP          NOT NULL,
    end_date    TIMESTAMP,
    status      varchar(255),
    booker_id   int                NOT NULL,
    property_id int                NOT NULL,
    total_price int NOT NULL,
    FOREIGN KEY (booker_id) REFERENCES app_user (user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES property (property_id)
        ON DELETE CASCADE
);

CREATE TABLE review
(
    review_id    SERIAL PRIMARY KEY NOT NULL,
    reviewer_id  int                NOT NULL,
    rating       int                NOT NULL,
    review_title varchar(255)       NOT NULL,
    review_body  text,
    booking_id   int                NOT NULL,
    review_date  timestamp          NOT NULL,
    likes        int                NOT NULL,
    FOREIGN KEY (reviewer_id) REFERENCES app_user (user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (booking_id) REFERENCES booking (booking_id)
        ON DELETE CASCADE
);

CREATE TABLE likes
(
    review_id   int   NOT NULL,
    user_id   int     NOT NULL,
    PRIMARY KEY (review_id, user_id),
    FOREIGN KEY (review_id) references review (review_id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES app_user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE pays
(
    booking_id   int      NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    "cost"         float    NOT NULL,
    PRIMARY KEY (booking_id),
    FOREIGN KEY (booking_id) REFERENCES booking (booking_id)
        ON DELETE CASCADE
);

-- TRIGGERS & their functions
CREATE LANGUAGE plpgsql;

-- Assertions & additional constraints
-- no Assertion in PostgreSQL, use Rule instead

CREATE OR REPLACE FUNCTION check_booking_overlap()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
            SELECT *
            FROM booking
            WHERE property_id = NEW.property_id
              AND status <> 'cancelled'
              AND ((NEW.start_date <= start_date AND NEW.end_date > start_date)
                OR (NEW.start_date >= start_date AND end_date > NEW.start_date))
        ) THEN
        -- Print the message
        RAISE NOTICE 'Booking overlaps with existing bookings';
        -- Return NULL to prevent the insertion
        RETURN NULL;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_check_booking_overlap
    BEFORE INSERT ON booking
    FOR EACH ROW
EXECUTE FUNCTION check_booking_overlap();

-- unique_user_email assertion becomes a constraint in PostgreSQL
ALTER TABLE "user" ADD CONSTRAINT unique_user_email UNIQUE (email);

-- unique_review_per_booking assertion becomes a constraint in PostgreSQL
ALTER TABLE review ADD CONSTRAINT unique_review_per_booking UNIQUE (booking_id, reviewer_id);

-- unique_property_owner assertion becomes a constraint in PostgreSQL

-- valid_booking_status assertion becomes a constraint postgresql
ALTER TABLE booking ADD CONSTRAINT valid_booking_status
    CHECK ( status IN ('blocked', 'booked', 'available', 'completed'));

CREATE OR REPLACE FUNCTION delete_reviews()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_blocked = true THEN
        DELETE FROM review WHERE reviewer_id = NEW.user_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_reviews_trigger
    AFTER UPDATE ON app_user
    FOR EACH ROW
EXECUTE FUNCTION delete_reviews();


CREATE OR REPLACE FUNCTION update_balances()
    RETURNS TRIGGER AS $$
BEGIN
    -- Deduct booking cost from travelers' balance
    UPDATE app_user
    SET balance = balance - (SELECT total_price
                             FROM booking
                             WHERE booking_id = NEW.booking_id)
    WHERE user_id = NEW.booker_id;

    -- Add booking cost to house owner's balance
    UPDATE app_user
    SET balance = balance + (SELECT cost
                             FROM pays
                             WHERE booking_id = NEW.booking_id)
    WHERE user_id = (SELECT property.owner_id
                     FROM property
                     WHERE property.property_id = NEW.property_id);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_balances
    AFTER INSERT
    ON booking
    FOR EACH ROW
EXECUTE FUNCTION update_balances();

-- VIEW

CREATE VIEW homeowner AS
SELECT app_user.*
FROM app_user
WHERE EXISTS (
              SELECT *
              FROM property
              WHERE property.owner_id = app_user.user_id
          );
