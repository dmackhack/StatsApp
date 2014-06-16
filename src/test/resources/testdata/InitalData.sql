delete from ZPARTICIPANTSTATISTICS;
delete from ZPARTICIPANT;
delete from ZMATCH;
delete from ZROUND;
delete from ZSEASON;
delete from ZDIVISION;
delete from ZLEAGUE;
delete from ZSPORT;
delete from ZTEAM;
delete from ZPLAYERCLUB;
delete from ZCLUB;
delete from ZUSER;

insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (1,1,1,'Old Haileybury Amateur Football Club');
insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (2,1,1,'Melbourne Demons Football Club');
insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (3,1,1,'Old Trinity Football Club');
insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (4,1,1,'Old Xavier Amateur Football Club');
insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (5,1,1,'Old Melbournians Amateur Football Club');
insert into ZDIVISION (Z_PK, Z_ENT, Z_OPT, ZRANK, ZLEAGUE, ZNAME) values (1,2,1,1,2,'A Grade');
insert into ZDIVISION (Z_PK, Z_ENT, Z_OPT, ZRANK, ZLEAGUE, ZNAME) values (2,2,1,2,2,'B Grade');
insert into ZDIVISION (Z_PK, Z_ENT, Z_OPT, ZRANK, ZLEAGUE, ZNAME) values (3,2,1,1,1,'AFL');
insert into ZLEAGUE (Z_PK, Z_ENT, Z_OPT, ZSPORT, ZNAME) values (1,3,1,1,'AFL');
insert into ZLEAGUE (Z_PK, Z_ENT, Z_OPT, ZSPORT, ZNAME) values (2,3,1,1,'VAFA');

-- Match Between Old Haileybury Seniors VS Trinity Seniors
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (1,4,1,1,'2012-05-01');
-- Old Haileybury Seniors VS Xavier Seniors
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (2,4,1,1,'2012-05-08');
-- Xavier Seniors VS Trinity Seniors
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (3,4,1,1,'2012-05-15');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (4,4,1,1,'2012-05-23');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (5,4,1,1,'2012-05-30');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (6,4,1,2,'2012-06-6');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (7,4,1,2,'2012-06-13');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (8,4,1,2,'2012-06-20');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (9,4,1,2,'2012-06-27');
insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (10,4,1,2,'2013-07-04');

-- Match 1 Participants: Old Haileybury Seniors (1) VS Trinity Seniors (4)
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (1,7,1,1,NULL,NULL,NULL,NULL,1,1);
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (2,7,1,1,NULL,NULL,NULL,NULL,4,0);

-- Match 2 Participants: Old Haileybury Seniors (1) VS Old Xavier Seniors (7)
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (3,7,1,2,NULL,NULL,NULL,NULL,1,1);
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (4,7,1,2,NULL,NULL,NULL,NULL,7,0);

-- Match 3 Participants: Old Haileybury Seniors (1) VS Old Xavier Seniors (7)
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (5,7,1,3,NULL,NULL,NULL,NULL,4,1);
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (6,7,1,3,NULL,NULL,NULL,NULL,7,0);

-- Match 4 Participants: Old Haileybury Seniors (1) VS Old Melbournians Seniors (10)
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (7,7,1,4,NULL,NULL,NULL,NULL,1,1);
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (8,7,1,4,NULL,NULL,NULL,NULL,10,0);

-- Player Participant in Team Particpant 1
insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (50,6,1,NULL,NULL,1,1,1,NULL,0);

-- Player Participant Stats 1 taken by User 1
insert into ZPARTICIPANTSTATISTICS (Z_PK, Z_ENT, Z_OPT, ZPARTICIPANT, Z5_PARTICIPANT, ZUSER, Z15_USER, ZTACKLES, ZBEHINDS, ZMARKS, ZGOALS, ZHANDBALLS, ZKICKS) values (1,9,1,NULL,50,NULL,1,0,0,0,0,0,0);

insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (1,10,1,1,1,1); -- boolean 0 false; 1 true
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (2,10,1,1,1,2);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (3,10,1,1,2,3);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (4,10,1,1,2,4);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (5,10,1,1,4,5);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (6,10,1,1,4,6);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (7,10,1,1,5,7);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (8,10,1,1,5,8);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (9,10,1,1,3,9);
insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (10,10,1,1,3,10);
insert into ZROUND (Z_PK, Z_ENT, Z_OPT, ZNUMBER, ZSEASON, ZDATE) values (1,11,1,1,1,'2012-05-01');
insert into ZROUND (Z_PK, Z_ENT, Z_OPT, ZNUMBER, ZSEASON, ZDATE) values (2,11,1,2,1,'2012-05-07');
insert into ZROUND (Z_PK, Z_ENT, Z_OPT, ZNUMBER, ZSEASON, ZDATE) values (3,11,1,3,1,'2012-05-14');
insert into ZROUND (Z_PK, Z_ENT, Z_OPT, ZNUMBER, ZSEASON, ZDATE) values (4,11,1,1,2,'2013-03-11');
insert into ZSEASON (Z_PK, Z_ENT, Z_OPT, ZDIVISION, ZYEAR) values (1,12,1,2,'2012');
insert into ZSEASON (Z_PK, Z_ENT, Z_OPT, ZDIVISION, ZYEAR) values (2,12,1,2,'2013');
insert into ZSPORT (Z_PK, Z_ENT, Z_OPT, ZNAME) values (1,13,1, 'Aussie Rules Football');

-- Old Haileybury Teams
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (1,14,1,1,'Seniors'); -- Teams need a rank
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (2,14,1,1,'Reserves');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (3,14,1,1,'U19s');

-- Trinity Teams
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (4,14,1,3,'Seniors');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (5,14,1,3,'Reserves');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (6,14,1,3,'U19s');

-- Old Xavier Teams
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (7,14,1,4,'Seniors');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (8,14,1,4,'Reserves');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (9,14,1,4,'U19s');

-- Old Melbournians Teams
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (10,14,1,5,'Seniors');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (11,14,1,6,'Reserves');
insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (12,14,1,6,'U19s');

-- Z_ENT = 16 for Player type User
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (1,16,1,1,'','dmackenz1981@gmail.com','Mackenzie', 'David');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (2,16,1,2,'','mkeanie11@gmail.com','Keane', 'Michelle');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (3,16,1,3,'','njones@gmail.com','Nathan', 'Jones');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (4,16,1,4,'','mjamar@gmail.com','Mark', 'Jamar');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (5,16,1,5,'','xav1@gmail.com','Xav1', 'Dude1');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (6,16,1,6,'','xav2@gmail.com','Xav2', 'Dude2');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (7,16,1,7,'','om1@gmail.com','Om1', 'Dude1');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (8,16,1,8,'','om2@gmail.com','Om2', 'Dude2');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (9,16,1,9,'','ot1@gmail.com','Ot1', 'Dude1');
insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZPASSWORD, ZUSERNAME, ZLASTNAME, ZFIRSTNAME) values (10,16,1,10,'','ot2@gmail.com','Ot2', 'Dude2');

update z_primarykey set z_max=1000;
