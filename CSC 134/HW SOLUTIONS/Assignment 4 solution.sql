-- CREATE TABLES
CREATE TABLE CUSTOMER 
  ( cust_id     CHAR(10)     NOT NULL,
    first       VARCHAR(30),
    last        VARCHAR(30),
    street      VARCHAR(50),
    city        VARCHAR(30),
    state       CHAR(2),
    zip         CHAR(5),
  PRIMARY KEY (cust_id));
/* Could optionally make a unique key for street + city + state + zip, but some
SQL implementations mean that none of those fields could be null, which isn't in our
original spec.
*/
  
CREATE TABLE REWARDS
  ( r_id        CHAR(10)     NOT NULL,
    points      INT          NOT NULL DEFAULT 0,
    cust_id     CHAR(10),
  PRIMARY KEY (r_id),
  FOREIGN KEY (cust_id) REFERENCES CUSTOMER(cust_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE );
/* The NOT NULL DEFAULT 0 constraint on points is very optional, but having NULL here
wouldn't make as much sense as a 0.
*/
  
CREATE TABLE CUSTOMER_PHONE
  ( cust_id     CHAR(10)     NOT NULL,
    phone       CHAR(10)     NOT NULL,
  PRIMARY KEY (cust_id, phone),
  FOREIGN KEY (cust_id) REFERENCES CUSTOMER(cust_id) );
/* Phone numbers don't work well as INTs. INT is not large enough to hold most phone
numbers with area code and we aren't doing math on them.
*/
  
CREATE TABLE CONTRACTOR
  ( bus_name    VARCHAR(25)  NOT NULL,
    discount    FLOAT        NOT NULL DEFAULT 0,
    PRIMARY KEY (bus_name) );
/* Discount really needs to be some numeric type if we ever want to use it for
calculations. I chose float so a 20% discount could be stored as .2 and used 
directly in calculations, but INT is fine too.
*/
    
CREATE TABLE CONTRACTOR_PHONE
  ( bus_name    VARCHAR(50)  NOT NULL,
    phone       CHAR(10)     NOT NULL,
  PRIMARY KEY (bus_name, phone),
  FOREIGN KEY (bus_name) REFERENCES CONTRACTOR(bus_name) );
  
CREATE TABLE HIRE
  ( cust_id     CHAR(10)     NOT NULL,
    bus_name    VARCHAR(50)  NOT NULL,
    hours       FLOAT        NOT NULL DEFAULT 0,
    PRIMARY KEY (cust_id, bus_name),
    FOREIGN KEY (cust_id) REFERENCES CUSTOMER(cust_id),
    FOREIGN KEY (bus_name) REFERENCES CONTRACTOR(bus_name) );
/* Nothing in our spec said hours needed to be an integer, so I chose a float.
*/
    
CREATE TABLE ROOM
  ( name        VARCHAR(50)  NOT NULL,
    cust_id     CHAR(10)     NOT NULL,
    PRIMARY KEY (name, cust_id),
    FOREIGN KEY (cust_id) REFERENCES CUSTOMER(cust_id) );
    
CREATE TABLE PAINT_COLOR
  ( mfg_id      CHAR(10)     NOT NULL,
    color_id    CHAR(10)     NOT NULL,
    name        VARCHAR(30)  NOT NULL,
    PRIMARY KEY (mfg_id, color_id) );
    
CREATE TABLE PAINTED
  ( rm_name     VARCHAR(50)  NOT NULL,
    cust_id     CHAR(10)     NOT NULL,
    mfg_id      CHAR(10)     NOT NULL,
    color_id    CHAR(10)     NOT NULL,
    PRIMARY KEY (rm_name, cust_id, mfg_id, color_id),
    FOREIGN KEY (rm_name, cust_id) REFERENCES ROOM(name, cust_id),
    FOREIGN KEY (mfg_id, color_id) REFERENCES PAINT_COLOR(mfg_id, color_id) );

-- INSERT DATA (note: this is more inserts than needed)
INSERT INTO CUSTOMER VALUES ('1', 'Naomi', 'Nagata', '6000 J St', 'Sacramento', 'CA', '95819');
INSERT INTO CUSTOMER VALUES ('2', 'Alex', 'Kamal', '1600 Penn', 'Washington', 'DC', '00000');
INSERT INTO REWARDS VALUES ('001', 15, '1');
INSERT INTO REWARDS VALUES ('002', 10, '2');
INSERT INTO CUSTOMER_PHONE VALUES ('1', '9165551212');
INSERT INTO CUSTOMER_PHONE VALUES ('1', '9165551213');
INSERT INTO CONTRACTOR VALUES ('Mao Painting', 10);
INSERT INTO CONTRACTOR_PHONE VALUES ('Mao Painting', '8005551212');
INSERT INTO CONTRACTOR_PHONE VALUES ('Mao Painting', '9165551213');
INSERT INTO HIRE VALUES ('1', 'Mao Painting', 12);
INSERT INTO ROOM VALUES ('Dining room', '1');
INSERT INTO ROOM VALUES ('Dining room', '2');
INSERT INTO PAINT_COLOR VALUES ('1', '1013', 'Vintage Claret');
INSERT INTO PAINT_COLOR VALUES ('1', '101', 'Biscuit');
INSERT INTO PAINTED VALUES ('Dining room', '1', '1', '1013');
INSERT INTO PAINTED VALUES ('Dining room', '2', '1', '1013');
INSERT INTO PAINTED VALUES ('Dining room', '2', '1', '101');

DELETE FROM PAINTED WHERE cust_id = '2' AND color_id = '1013';

UPDATE HIRE SET hours = 15 WHERE cust_id = '1' and bus_name = 'Mao Painting';

-- DROP TABLES
DROP TABLE PAINTED;
DROP TABLE PAINT_COLOR;
DROP TABLE ROOM;
DROP TABLE HIRE;
DROP TABLE CONTRACTOR_PHONE;
DROP TABLE CONTRACTOR;
DROP TABLE CUSTOMER_PHONE;
DROP TABLE REWARDS;
DROP TABLE CUSTOMER;
    