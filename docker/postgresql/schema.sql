CREATE TABLE parking(id text  PRIMARY KEY,parking_name character(35));
CREATE TABLE rule(id integer  PRIMARY KEY, fix bigint, variable bigint);
CREATE TABLE slot(id SERIAL  PRIMARY KEY,slot_type text CHECK (slot_type = 'standard' OR slot_type = '20kw' OR slot_type = '50kw') , available boolean DEFAULT(true),  plaque character(35), parking_code text REFERENCES parking(id), rule_id integer REFERENCES rule(id));
CREATE TABLE logs(id SERIAL  PRIMARY KEY,start_at timestamp,end_at timestamp, plaque character(35));




