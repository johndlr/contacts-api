CREATE TABLE IF NOT EXISTS contacts (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(45) NOT NULL,
last_name VARCHAR(45) NOT NULL,
phone_number VARCHAR(45) NOT NULL UNIQUE,
email VARCHAR(45) NULL DEFAULT 'Does not have',
created_at DATETIME NOT NULL,
created_by VARCHAR(45) NOT NULL,
updated_at DATETIME NULL,
updated_by VARCHAR(45) NULL
);


INSERT INTO contacts (name, last_name, phone_number, email, created_at, created_by)
VALUES ('John', 'Doe', '5514151617', 'johndoe@example.com', NOW(), 'Juan DLR');

INSERT INTO contacts (name, last_name, phone_number, created_at, created_by)
VALUES ('Jane', 'Smith', '5547958671', NOW(), 'Juan DLR');

INSERT INTO contacts (name, last_name, phone_number, email, created_at, created_by)
VALUES ('Michael', 'Johnson', '5547521238', 'michaeljohnson@example.com', NOW(), 'Juan DLR');

INSERT INTO contacts (name, last_name, phone_number, email, created_at, created_by)
VALUES ('Bruce', 'Banner', '5518221827', 'greenhulk@example.com', NOW(), 'Juan DLR');



