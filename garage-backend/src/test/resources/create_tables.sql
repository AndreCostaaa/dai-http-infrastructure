SET SEARCH_PATH TO "garage-testing";
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
    CONSTRAINT sb_s_fk FOREIGN KEY (id) REFERENCES service
);