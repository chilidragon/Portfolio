USE GymProject


CREATE TABLE tblRoomType (
    RoomTypeID INT IDENTITY (1,1) PRIMARY KEY NOT NULL,
    RoomTypeName varchar(50) NOT NULL,
    RoomTypeDescr varchar (1000)
)

CREATE TABLE tblRoom (
    RoomID INT IDENTITY (1,1) PRIMARY KEY,
    RoomTypeID INT FOREIGN KEY REFERENCES tblRoomType(RoomTypeID) NOT NULL,
    Room_Name varchar(50) NOT NULL,
    Room_Descr varchar (1000)
)

CREATE TABLE tblRoomVisit  (
    RoomVisitID INT IDENTITY (1,1) PRIMARY KEY,
    VisitID INT FOREIGN KEY REFERENCES tblVisit(VisitID) NOT NULL,
    RoomID INT FOREIGN KEY REFERENCES tblRoom(RoomID) NOT NULL,
    RoomVisit  INT
)

CREATE TABLE tblIncidentType(
    IncidentTypeID INT IDENTITY (1,1) PRIMARY KEY,
    IncidentTypeName varchar(50) NOT NULL,
    IncidentTypeDescr varchar (1000)
)

CREATE TABLE tblIncident(
    IncidentID INT IDENTITY (1,1) PRIMARY KEY,
    IncidentTypeID INT FOREIGN KEY REFERENCES tblIncidentType(IncidentTypeID) NOT NULL,
    IncidentName varchar(50) NOT NULL,
    IncidentDescr varchar (1000)
)

CREATE TABLE tblRoomVisitIncident(
    RoomVisitIncidentID INT IDENTITY (1,1) PRIMARY KEY,
    IncidentID INT FOREIGN KEY REFERENCES tblIncident(IncidentID) NOT NULL,
    RoomVisitID INT FOREIGN KEY REFERENCES tblRoomVisit(RoomVisitID) NOT NULL
)

INSERT INTO tblRoomType(RoomTypeName, RoomTypeDescr)
VALUES ('Basketball Court', 'Area for playing Basketball'),
    ('Tennis Court','Area for playing Tennis'),
    ('Badminton Court', 'Area for playing Badminton'),
    ('Stretching', 'Area for relax or yoga'),
    ('Cardiovascular', 'Area with fitness equipment such as treadmills and ellipticals'),
    ('Free weights', 'Area for playing Badminton'),
    ('Personal training', 'Area where you can find a personal trainer to help you'),
    ('Functional fitness', 'Area for playing Battle ropes, medicine balls, kettlebells, power plates, kinesis stations'),
    ('Administration', 'Room for all kinds of service'),
    ('Toilet', 'Area for playing Badminton'),
    ('Equipment', 'Room for renting Equipment'),
    ('Storage', 'Space available for storing something'),
    ('Swimming Pool', 'Area for Swimming'),
    ('Shower','Room for shower'),
    ('Medical', 'Room for accident'),
    ('Police', 'Room for emergency'),
    ('Maintenance', 'Room for fixing')


INSERT INTO tblIncidentType(IncidentTypeName, IncidentTypeDescr)
VALUES ('Injury', 'Injury happened in Gym'),
    ('Maintenance', 'Maintenance for broken Gym equipment'),
    ('Other', 'Other incident happened in Gym'),
    ('Emergency', 'Fight/Gunshot happened in Gym which required to call police')



CREATE OR ALTER PROCEDURE GetRoomTypeID
 @RTName varchar(50),
 @RoomTyyID INT OUTPUT
 AS
 SET @RoomTyyID = (SELECT RoomTypeID
                FROM tblRoomType
                WHERE RoomTypeName = @RTName)
GO

CREATE OR ALTER PROCEDURE GetIncidentTypeID
 @ITName varchar(50),
 @IncidentTyyID INT OUTPUT
 AS
 SET @IncidentTyyID = (SELECT IncidentTypeID
                FROM tblIncidentType
                WHERE IncidentTypeName = @ITName)
GO

CREATE OR ALTER PROCEDURE GetIncidentID
@IncTyN varchar(50),
@InciName varchar(50),
@INC_ID INT OUTPUT
AS

SET @Inc_ID = (SELECT IncidentID
            FROM tblIncident INC
                JOIN tblIncidentType INCT ON INC.IncidentTypeID = INCT.IncidentTypeID
            WHERE INCT.IncidentTypeName = @IncTyN
            AND INC.IncidentName = @InciName)
GO