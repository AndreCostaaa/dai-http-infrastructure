CREATE SCHEMA "garage";
SET SEARCH_PATH TO "garage";
CREATE FUNCTION isMechanic(employee_id INT) RETURNS BOOLEAN AS $$ BEGIN RETURN (
    SELECT role.is_mechanic
    FROM role
    WHERE role.id = (
            SELECT role_id
            FROM employee
            WHERE employee.id = employee_id
        )
);
END $$ LANGUAGE plpgsql;
CREATE TABLE role (
    id SERIAL,
    name VARCHAR(15),
    can_create BOOLEAN NOT NULL,
    can_assign_others BOOLEAN NOT NULL,
    is_mechanic BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE specialization(
    id SERIAL,
    name VARCHAR(15),
    hourly_rate DECIMAL(5, 2),
    PRIMARY KEY (id)
);
CREATE TABLE person (
    id SERIAL,
    fname VARCHAR(15),
    lname VARCHAR(15),
    phone_no VARCHAR(15),
    PRIMARY KEY (id)
);
CREATE TABLE employee (
    id INTEGER,
    role_id INTEGER NOT NULL,
    specialization_id INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT e_p_fk FOREIGN KEY (id) REFERENCES person,
    CONSTRAINT e_r_fk FOREIGN KEY (role_id) REFERENCES role,
    CONSTRAINT e_s_fk FOREIGN KEY (specialization_id) REFERENCES specialization
);
CREATE TABLE client (
    id INTEGER,
    email VARCHAR(30),
    street VARCHAR(30),
    street_no INTEGER,
    npa INTEGER,
    country VARCHAR(15),
    PRIMARY KEY (id),
    CONSTRAINT c_p_fk FOREIGN KEY (id) REFERENCES person
);
CREATE TABLE car (
    id SERIAL,
    owner_id INT,
    chassis_no CHAR(17) NOT NULL,
    rec_type CHAR(6) NOT NULL,
    brand VARCHAR(15),
    model VARCHAR(15),
    color VARCHAR(15),
    PRIMARY KEY (id),
    CONSTRAINT owner_id FOREIGN KEY(owner_id) REFERENCES client
);
CREATE TABLE service_state (
    id SERIAL,
    title VARCHAR(15),
    description VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE service (
    id SERIAL,
    mechanic_id INT,
    client_id INT NOT NULL,
    car_id INT NOT NULL,
    hours_worked INT,
    comments VARCHAR(500),
    has_pictures BOOLEAN NOT NULL,
    state_id INT NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT now(),
    date_car_arrival TIMESTAMP,
    date_car_processing TIMESTAMP,
    date_car_done TIMESTAMP,
    date_car_left TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT s_e_fk FOREIGN KEY (mechanic_id) REFERENCES employee,
    CONSTRAINT s_ca_fk FOREIGN KEY (car_id) REFERENCES car,
    CONSTRAINT s_cl_fk FOREIGN KEY (client_id) REFERENCES client,
    CONSTRAINT s_ss_fk FOREIGN KEY (state_id) REFERENCES service_state,
    CONSTRAINT chk_CheckEmployeeIsMechanic check (isMechanic (mechanic_id))
);
CREATE TABLE car_part (
    id SERIAL,
    service_id INT,
    supplier VARCHAR(30),
    supplier_ref VARCHAR(20),
    name VARCHAR(50),
    description VARCHAR(100),
    buy_price DECIMAL(8, 2),
    sell_price DECIMAL(8, 2),
    CONSTRAINT si_s_fk FOREIGN KEY(service_id) REFERENCES service
);
CREATE TABLE service_bill (
    id INT,
    price DECIMAL(8, 2),
    delivered BOOLEAN NOT NULL,
    paid BOOLEAN NOT NULL,
    discount_percentage SMALLINT,
    PRIMARY KEY (id),
    CONSTRAINT sb_s_fk FOREIGN KEY (id) REFERENCES service ON DELETE CASCADE
);
--Views
CREATE VIEW car_parts_in_stock AS
SELECT *
from car_part
WHERE car_part.service_id IS NULL;
--Views
CREATE VIEW services_processing AS
SELECT service.id,
    date_car_processing AS started_on,
    chassis_no,
    rec_type,
    brand,
    model,
    color,
    p.fname AS mechanic_fname,
    p.lname AS mechanic_lname,
    p2.fname AS client_fname,
    p2.lname AS client_lname,
    p2.phone_no AS client_phone_no,
    cl.email AS client_email
FROM service
    JOIN car c ON c.id = service.car_id
    JOIN employee e ON service.mechanic_id = e.id
    JOIN person p ON p.id = e.id
    JOIN person p2 ON p2.id = service.client_id
    JOIN client cl ON cl.id = service.client_id
WHERE service.state_id = 2;
CREATE VIEW cars_waiting_on_mechanics AS
SELECT date_car_arrival AS arrived_on,
    chassis_no,
    rec_type,
    brand,
    model,
    color,
    p.fname AS client_fname,
    p.lname AS client_lname
FROM service
    JOIN service_state state ON service.state_id = state.id
    JOIN car c ON c.id = service.car_id
    JOIN person p ON p.id = service.client_id
WHERE state_id = 1;
CREATE VIEW cars_waiting_on_pickup AS
SELECT service.id AS service_id,
    date_car_done AS arrived_on,
    chassis_no,
    rec_type,
    brand,
    model,
    color,
    p.fname AS client_fname,
    p.lname AS client_lname,
    sb.price AS cost
FROM service
    JOIN car c ON c.id = service.car_id
    JOIN person p ON p.id = service.client_id
    LEFT JOIN service_bill sb ON service.id = sb.id
WHERE state_id = 3;
-- View
CREATE VIEW services_not_yet_paid AS
SELECT service.id AS service_id,
    date_car_done AS arrived_on,
    chassis_no,
    rec_type,
    brand,
    model,
    color,
    p.fname AS client_fname,
    p.lname AS client_lname,
    sb.price AS cost
FROM service
    JOIN car c ON c.id = service.car_id
    JOIN person p ON p.id = service.client_id
    LEFT JOIN service_bill sb ON service.id = sb.id
WHERE state_id = 3
    AND sb.paid = false;
CREATE OR REPLACE FUNCTION service_auto_update_date() RETURNS TRIGGER AS $$ BEGIN IF NEW.state_id = 0 THEN NEW.date_created = now();
ELSEIF NEW.state_id = 1 THEN NEW.date_car_arrival = now();
ELSEIF NEW.state_id = 2 THEN NEW.date_car_processing = now();
ELSEIF NEW.state_id = 3 THEN NEW.date_car_done = now();
ELSEIF NEW.state_id = 4 THEN NEW.date_car_left = now();
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER service_auto_update_date_trigger BEFORE
UPDATE ON service FOR EACH ROW EXECUTE FUNCTION service_auto_update_date();
CREATE OR REPLACE FUNCTION calculate_service_total_cost(target_service_id INT) RETURNS DECIMAL(8, 2) AS $$ BEGIN RETURN calculate_service_material_cost(target_service_id) + calculate_service_labour_cost(target_service_id);
END;
$$ LANGUAGE plpgsql;
end;
CREATE OR REPLACE FUNCTION calculate_service_material_cost(target_service_id INT) RETURNS DECIMAL(8, 2) AS $$ BEGIN RETURN COALESCE(
        (
            SELECT SUM(sell_price)
            FROM car_part
            WHERE service_id = target_service_id
            GROUP BY service_id
        ),
        0
    );
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION calculate_service_labour_cost(target_service_id INT) RETURNS DECIMAL(8, 2) AS $$ BEGIN RETURN COALESCE(
        (
            SELECT mechanic_rate(mechanic_id)
            FROM service
            WHERE id = target_service_id
        ),
        0
    ) * COALESCE(
        (
            SELECT hours_worked
            FROM service
            WHERE id = target_service_id
        ),
        1
    );
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION mechanic_rate(mechanic_id INT) RETURNS INTEGER AS $$ BEGIN RETURN (
        SELECT hourly_rate
        FROM employee
            JOIN specialization ON employee.specialization_id = specialization.id
        WHERE employee.id = mechanic_id
    );
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION create_service_bill() RETURNS TRIGGER AS $$ BEGIN IF NEW.state_id = 3 THEN
INSERT INTO service_bill (id, price, delivered, paid, discount_percentage)
VALUES (
        NEW.id,
        calculate_service_total_cost(NEW.id),
        false,
        false,
        0
    );
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER service_on_done BEFORE
UPDATE ON service FOR EACH ROW EXECUTE FUNCTION create_service_bill();