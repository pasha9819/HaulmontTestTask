CREATE TABLE IF NOT EXISTS doctor(
  id BIGINT primary key identity,
  name VARCHAR(255) not null,
  patronymic VARCHAR(255) not null,
  surname VARCHAR(255) not null,
  specialization VARCHAR(255) not null
);

CREATE TABLE IF NOT EXISTS patient(
  id BIGINT primary key identity,
  name VARCHAR(255) not null,
  patronymic VARCHAR(255) not null,
  surname VARCHAR(255) not null,
  number VARCHAR(255) not null
);

CREATE TABLE IF NOT EXISTS recipe(
  id BIGINT primary key identity,
  creation_date DATE not null,
  description VARCHAR(255) not null,
  priority INTEGER not null,
  validity DATE not null,
  doctor_id BIGINT not null,
  patient_id BIGINT not null,
  FOREIGN KEY (doctor_id) REFERENCES doctor(id),
  FOREIGN KEY (patient_id) REFERENCES patient(id)
);

