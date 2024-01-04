SET SEARCH_PATH TO "garage-testing";
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
    CONCAT(p2.phone_code, p2.phone_no) AS client_phone_no,
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
    sb.gross_price AS cost
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
    sb.gross_price AS cost
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
END; $$ LANGUAGE plpgsql;
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