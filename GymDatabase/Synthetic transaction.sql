USE GymProject
GO

CREATE OR ALTER PROCEDURE wrapper_insert_RoomVisit
@Run INT
AS
DECLARE @RooName2 varchar(50), @JimName2 varchar(50), @MFName2 varchar(50), @MLName2 varchar(50), @MDOB2 DATE
DECLARE @VIS2 DATE, @RooMV1 INT
DECLARE @RooCOUNT INT = (SELECT COUNT(*) FROM tblRoom)
DECLARE @MemIDMA INT = (SELECT MAX(MembershipID) FROM tblVISIT)
DECLARE @MemIDMI INT = (SELECT MIN(MembershipID) FROM tblVISIT)
DECLARE @RTCOUNT INT = (SELECT COUNT(*) FROM tblRoomType)
DECLARE @Room1ID INT, @Visit1ID INT, @Membership1ID INT, @Gym1ID INT, @RoTyID INT


WHILE @Run > 0
BEGIN
SET @Room1ID = (SELECT RAND() * @RooCOUNT +1)
SET @RooName2 = (SELECT Room_Name FROM tblRoom WHERE RoomID = @Room1ID)

SET @Membership1ID = FLOOR(@MemIDMI + RAND() * (@MemIDMA - @MemIDMI))
SET @VIS2 = (SELECT TOP 1 VisitDate FROM tblVISIT WHERE MembershipID = @Membership1ID ORDER BY NEWID())
SET @Gym1ID = (SELECT GymID FROM tblVISIT WHERE MembershipID = @Membership1ID AND VisitDate = @VIS2)
SET @JimName2 = (SELECT GymName FROM tblGym WHERE GymID = @Gym1ID)
SET @MFName2 = (SELECT MembershipFname FROM tblMEMBERSHIP WHERE MembershipID = @Membership1ID)
SET @MLName2 = (SELECT MembershipLname FROM tblMEMBERSHIP WHERE MembershipID = @Membership1ID)
SET @MDOB2 = (SELECT MembershipBirth FROM tblMEMBERSHIP WHERE MembershipID = @Membership1ID)


SET @RooMV1 = (SELECT RAND() * 5 + 1)


EXEC Insert_RoomVisit
@RooName1 = @RooName2,
@JimName1 = @JimName2,
@MFName1 =  @MFName2,
@MLName1 = @MLName2,
@MDOB1 = @MDOB2,
@VIS1 = @VIS2,
@RooMV = @RooMV1


SET @Run = @Run -1
END
GO
