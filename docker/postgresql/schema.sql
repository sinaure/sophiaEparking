CREATE TABLE parking(id text  PRIMARY KEY,parking_name character(35));
CREATE TABLE rule(id integer  PRIMARY KEY, fix bigint, variable bigint);
CREATE TABLE slot(id SERIAL  PRIMARY KEY,slot_type text CHECK (slot_type = 'standard' OR slot_type = '20kw' OR slot_type = '50kw') , available boolean DEFAULT(true),  plaque character(35), parking_code text REFERENCES parking(id), rule_id integer REFERENCES rule(id));
CREATE TABLE logs(id SERIAL  PRIMARY KEY,start_at timestamp,end_at timestamp, plaque character(35));


INSERT INTO parking(id, parking_name) VALUES    ("jsdhgfjybhgjhgjyygysd","saint philippe");
INSERT INTO parking(id, parking_name) VALUES    ("wbcxkscdjsgcsjycg","garbajaire");
INSERT INTO parking(id, parking_name) VALUES    ("jhsqgyqvqhgvqhvhqg","plage gravette");

INSERT INTO rule(id, fix, variable) VALUES    (1, 10, 2.7)
INSERT INTO rule(id, fix, variable) VALUES    (2, 7, 3.1)

INSERT INTO slot(slot_type,  parking_code, rule_id) VALUES    ("standard", "jsdhgfjybhgjhgjyygysd", 1)
INSERT INTO slot(slot_type,  parking_code, rule_id) VALUES    ("standard", "jsdhgfjybhgjhgjyygysd", 1)
INSERT INTO slot(slot_type,  parking_code, rule_id) VALUES    ("20kw", "jsdhgfjybhgjhgjyygysd", 2)
INSERT INTO slot(slot_type,  parking_code, rule_id) VALUES    ("20kw", "jsdhgfjybhgjhgjyygysd", 2)
INSERT INTO slot(slot_type,  parking_code, rule_id) VALUES    ("20kw", "jsdhgfjybhgjhgjyygysd", 2)

