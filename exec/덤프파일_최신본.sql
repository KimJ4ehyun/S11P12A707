-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: top_db
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_focus_times`
--

DROP TABLE IF EXISTS `app_focus_times`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_focus_times` (
  `focus_time` int NOT NULL,
  `start_time` int NOT NULL,
  `app_focus_time_id` bigint NOT NULL AUTO_INCREMENT,
  `one_day_id` bigint DEFAULT NULL,
  `app` varchar(255) DEFAULT NULL,
  `time_unit` int NOT NULL,
  PRIMARY KEY (`app_focus_time_id`),
  KEY `FK751rj4id3has3giu2l7j04xo9` (`one_day_id`),
  CONSTRAINT `FK751rj4id3has3giu2l7j04xo9` FOREIGN KEY (`one_day_id`) REFERENCES `one_days` (`one_day_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1691 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_focus_times`
--

LOCK TABLES `app_focus_times` WRITE;
/*!40000 ALTER TABLE `app_focus_times` DISABLE KEYS */;
INSERT INTO `app_focus_times` VALUES (3788,56808,930,439,'i11a707.p.ssafy.io',15),(4333,56766,931,441,'i11a707.p.ssafy.io',15),(110,56738,932,441,'kakaotalk.exe',15),(0,56739,933,441,'chrome.exe',15),(464,55797,934,441,'mysqlworkbench.exe',15),(3977,56851,935,440,'i11a707.p.ssafy.io',15),(316,55530,936,440,'mattermost.exe',15),(5,55501,937,441,'system idle process',15),(573,56803,938,441,'idea64.exe',15),(71,55235,939,441,'githubdesktop.exe',15),(0,56290,940,440,'chrome.exe',15),(2,54611,941,440,'system idle process',15),(54,54789,942,440,'mysqlworkbench.exe',15),(396,56729,943,439,'mattermost.exe',15),(0,56808,944,439,'chrome.exe',15),(48,56251,945,440,'idea64.exe',15),(44,54961,946,440,'mintty.exe',15),(30,54760,947,440,'notion.exe',15),(1586,56850,948,444,'i11a707.p.ssafy.io',15),(654,56372,949,444,'chatgpt.com',15),(2,55737,950,444,'edu.ssafy.com',15),(1,54694,951,444,'docs.google.com',15),(1,56846,952,444,'www.erdcloud.com',15),(1641,55839,953,444,'www.notion.so',15),(39,56826,954,444,'lab.ssafy.com',15),(10,54708,955,444,'music.youtube.com',15),(1,54698,956,444,'mysqlworkbench.exe',15),(86,56830,957,444,'mintty.exe',15),(2,55803,958,444,'mattermost.exe',15),(0,56847,959,444,'chrome.exe',15),(826,56488,960,444,'idea64.exe',15),(1,54767,961,444,'system idle process',15),(9,54833,962,444,'newtab',15),(1,54814,963,440,'explorer.exe',15),(34,56722,964,440,'newtab',15),(615,56823,965,439,'code.exe',15),(7,56545,966,439,'explorer.exe',15),(22,55530,967,441,'explorer.exe',15),(15,55062,968,441,'notepad.exe',15),(14,56742,969,441,'newtab',15),(5,56393,970,441,'www.google.com',15),(9,55094,971,441,'project.ssafy.com',15),(377,55288,972,441,'lab.ssafy.com',15),(16,55508,973,441,'www.notion.so',15),(1,55529,974,441,'ciscocollabhost.exe',15),(3,55556,975,441,'mattermost.exe',15),(58,55611,976,441,'app.mitchinmat.com',15),(2,56600,977,440,'docs.spring.io',15),(16,56810,978,444,'explorer.exe',15),(2,55794,979,444,'shellexperiencehost.exe',15),(2,55800,980,444,'applicationframehost.exe',15),(3,56208,981,439,'system idle process',15),(25,56008,982,440,'www.google.com',15),(18,55971,983,440,'velog.io',15),(267,56362,984,444,'notepad.exe',15),(5,55997,985,440,'he-ya.tistory.com',15),(4,56004,986,440,'5bong2-develop.tistory.com',15),(12,56596,987,440,'java119.tistory.com',15),(463,56039,988,440,'chatgpt.com',15),(455,56751,989,441,'www.acmicpc.net',15),(1,56623,990,440,'devopsnet.tistory.com',15),(16,56758,991,441,'solved.ac',15),(3,57819,992,439,'독서',16),(7,58301,993,443,'accounts.google.com',16),(357,58682,994,442,'i11a707.p.ssafy.io',16),(11,58682,995,442,'extensions',16),(3,58684,996,442,'dblgchclanfijknplhbfalepmbpfknlb',16),(502,58685,997,442,'localhost',16),(12,58741,998,442,'notion.exe',16),(44,58713,999,443,'i11a707.p.ssafy.io',16),(36,58714,1000,443,'localhost',16),(12,58722,1001,443,'newtab',16),(97,58726,1002,443,'www.google.com',16),(1,58727,1003,443,'medium.com',16),(949,58802,1004,442,'code.exe',16),(125,58806,1005,440,'extensions',16),(17,58806,1006,440,'gkjmdfjejggeeimkmognhbfngpnmjgpe',16),(1682,58817,1007,440,'i11a707.p.ssafy.io',16),(0,58903,1008,442,'',16),(116,59050,1009,440,'mysqlworkbench.exe',16),(83,59097,1010,442,'system idle process',16),(0,59177,1011,440,'shellexperiencehost.exe',16),(7,59186,1012,440,'code.exe',16),(4,59192,1013,440,'notion.exe',16),(10,59230,1014,440,'settings',16),(24,59318,1015,443,'getbootstrap.com',16),(96,59349,1016,440,'idea64.exe',16),(37,59397,1017,443,'getbootstrap.kr',16),(220,59432,1018,440,'explorer.exe',16),(1,59453,1019,440,'mattermost.exe',16),(95,59791,1020,442,'lab.ssafy.com',16),(190,59944,1021,441,'i11a707.p.ssafy.io',16),(1,59945,1022,441,'chgbgdipicmfploomogmnednamomjnoc',16),(56,59965,1023,441,'extensions',16),(25,60316,1024,444,'i11a707.p.ssafy.io',16),(1,60316,1025,444,'dhiplndhoendgfblbijaipaplepgeomk',16),(20,60327,1026,444,'www.notion.so',16),(1,60416,1027,443,'edu.ssafy.com',16),(1,60417,1028,443,'www.notion.so',16),(22,60747,1029,441,'code.exe',16),(5,60789,1030,441,'mattermost.exe',16),(5,60814,1031,441,'explorer.exe',16),(17,60832,1032,441,'githubdesktop.exe',16),(3,60838,1033,443,'chatgpt.com',16),(30,61048,1034,443,'withwltn.tistory.com',16),(334,61121,1035,441,'idea64.exe',16),(97,61208,1036,442,'system idle process',17),(487,61209,1037,442,'localhost',17),(12,61220,1038,442,'explorer.exe',17),(84,61255,1039,442,'kakaotalk.exe',17),(1436,61232,1040,440,'i11a707.p.ssafy.io',17),(36,61233,1041,440,'extensions',17),(499,61237,1042,441,'idea64.exe',17),(1159,61272,1043,442,'code.exe',17),(2,61278,1044,444,'edu.ssafy.com',17),(6,61281,1045,444,'www.notion.so',17),(72,61282,1046,444,'lab.ssafy.com',17),(548,61296,1047,441,'i11a707.p.ssafy.io',17),(165,61338,1048,440,'idea64.exe',17),(1,61603,1049,440,'www.erdcloud.com',17),(890,61721,1050,442,'i11a707.p.ssafy.io',17),(26,61720,1051,441,'extensions',17),(2,61763,1052,442,'newtab',17),(183,61768,1053,442,'lab.ssafy.com',17),(2,61762,1054,441,'searchapp.exe',17),(58,61782,1055,440,'mysqlworkbench.exe',17),(1,61823,1056,442,'',17),(916,61828,1057,441,'kakaotalk.exe',17),(6,61881,1058,444,'newtab',17),(9,61884,1059,441,'newtab',17),(4,61885,1060,441,'www.google.com',17),(64,61912,1061,441,'www.youtube.com',17),(45,61969,1062,441,'www.notion.so',17),(14,61974,1063,440,'explorer.exe',17),(19,61983,1064,440,'notion.exe',17),(2,62021,1065,441,'githubdesktop.exe',17),(10,62084,1066,443,'chatgpt.com',17),(7,62132,1067,444,'i11a707.p.ssafy.io',17),(1,62138,1068,444,'extensions',17),(0,62797,1069,442,'kyounghwan01.github.io',17),(39,63769,1070,440,'mattermost.exe',17),(1,64083,1071,443,'accounts.google.com',17),(2,64096,1072,444,'file:///c:/users/ssafy/desktop/s11p12a707/backend/top/build/reports/tests/test/index.html',17),(4,64102,1073,444,'file:///c:/users/ssafy/desktop/s11p12a707/backend/top/build/reports/tests/test/classes/com.ssafy.top.topapplicationtests.html',17),(4,64145,1074,442,'mattermost.exe',17),(1,64787,1075,443,'lab.ssafy.com',17),(51,64820,1076,442,'code.exe',18),(2,64822,1077,442,'localhost',18),(3,64824,1078,442,'lab.ssafy.com',18),(14,64825,1079,442,'i11a707.p.ssafy.io',18),(10,64869,1080,442,'newtab',18),(3,64883,1081,442,'kakaotalk.exe',18),(6,64887,1082,442,'edu.ssafy.com',18),(13,64873,1083,443,'chatgpt.com',18),(2,64874,1084,443,'www.notion.so',18),(127,64875,1085,443,'lab.ssafy.com',18),(87,64875,1086,443,'localhost',18),(7,64876,1087,443,'edu.ssafy.com',18),(1,64884,1088,443,'shan0325.tistory.com',18),(3,65510,1089,443,'newtab',18),(30,65820,1090,443,'i11a707.p.ssafy.io',18),(7,74175,1091,444,'newtab',20),(0,74792,1092,444,'chatgpt.com',20),(11,74802,1093,444,'edu.ssafy.com',20),(8,74804,1094,444,'project.ssafy.com',20),(19,74815,1095,444,'lab.ssafy.com',20),(18,75785,1096,444,'edu.ssafy.com',21),(501,75797,1097,444,'i11a707.p.ssafy.io',21),(140,75797,1098,444,'lab.ssafy.com',21),(37,76267,1099,444,'newtab',21),(4,76746,1100,444,'메모장',21),(22,77057,1101,444,'www.google.com',21),(14,77482,1102,444,'chatgpt.com',21),(46,77682,1103,444,'www.notion.so',21),(33,78609,1104,444,'localhost',21),(0,79121,1105,444,'project.ssafy.com',21),(22,79230,1106,444,'newtab',22),(10,79236,1107,444,'i11a707.p.ssafy.io',22),(45,33362,1108,455,'i11a707.p.ssafy.io',9),(7,33363,1109,455,'chatgpt.com',9),(98,33370,1110,452,'mysqlworkbench.exe',9),(1,33371,1111,452,'121202.tistory.com',9),(3,33373,1112,455,'music.youtube.com',9),(11,33406,1113,455,'newtab',9),(45,33416,1114,455,'lab.ssafy.com',9),(4,33420,1115,455,'edu.ssafy.com',9),(783,33477,1116,457,'i11a707.p.ssafy.io',9),(31,33481,1117,457,'newtab',9),(46,33510,1118,457,'mysqlworkbench.exe',9),(2,33513,1119,457,'edu.ssafy.com',9),(278,33553,1120,451,'code.exe',9),(170,33563,1121,451,'i11a707.p.ssafy.io',9),(416,33533,1122,457,'mintty.exe',9),(19,33546,1123,455,'accounts.google.com',9),(428,33553,1124,452,'i11a707.p.ssafy.io',9),(6,33564,1125,455,'gds.google.com',9),(16,33569,1126,452,'githubdesktop.exe',9),(59,33641,1127,452,'explorer.exe',9),(5,33650,1128,452,'newtab',9),(3,33652,1129,452,'www.google.com',9),(3,33654,1130,455,'myaccount.google.com',9),(195,33658,1131,452,'www.notion.so',9),(473,33764,1132,457,'idea64.exe',9),(89,33797,1133,451,'mattermost.exe',9),(12,33771,1134,457,'mattermost.exe',9),(39,33824,1135,460,'i11a707.p.ssafy.io',9),(2,33829,1136,460,'chatgpt.com',9),(47,34080,1137,460,'lab.ssafy.com',9),(37,34315,1138,457,'system idle process',9),(23,34382,1139,451,'system idle process',9),(30,34434,1140,457,'www.google.com',9),(0,34467,1141,451,'',9),(170,34485,1142,451,'localhost',9),(58,34471,1143,457,'hseungyeon.tistory.com',9),(12,34712,1144,460,'music.youtube.com',9),(22,35044,1145,457,'wwwnghks.tistory.com',9),(7,35104,1146,457,'dbaant.tistory.com',9),(82,35128,1147,457,'luvris2.tistory.com',9),(24,35131,1148,463,'i11a707.p.ssafy.io',9),(1,35173,1149,463,'extensions',9),(4,35183,1150,463,'newtab',9),(3,35186,1151,463,'www.google.com',9),(65,35192,1152,463,'m.sports.naver.com',9),(14,35229,1153,457,'undefinedp.github.io',9),(30,35259,1154,457,'chatgpt.com',9),(31,35523,1155,452,'www.youtube.com',9),(231,35674,1156,452,'system idle process',9),(50,35731,1157,452,'idea64.exe',9),(10,35846,1158,457,'notion.exe',9),(4,35943,1159,460,'newtab',9),(7,35947,1160,460,'www.notion.so',9),(1525,36007,1161,457,'i11a707.p.ssafy.io',10),(186,36032,1162,460,'i11a707.p.ssafy.io',10),(53,36033,1163,460,'chatgpt.com',10),(455,36034,1164,460,'lab.ssafy.com',10),(1,36035,1165,460,'edu.ssafy.com',10),(27,36035,1166,460,'www.erdcloud.com',10),(40,36046,1167,460,'www.notion.so',10),(393,36102,1168,452,'mysqlworkbench.exe',10),(454,36132,1169,452,'i11a707.p.ssafy.io',10),(248,36153,1170,457,'mysqlworkbench.exe',10),(140,36201,1171,457,'mattermost.exe',10),(1601,36246,1172,452,'idea64.exe',10),(120,36335,1173,457,'notion.exe',10),(273,37355,1174,452,'system idle process',10),(1,37357,1175,452,'devpouch.tistory.com',10),(4,37360,1176,452,'extensions',10),(30,37369,1177,452,'newtab',10),(113,37371,1178,452,'www.google.com',10),(8,37371,1179,452,'project.ssafy.com',10),(4,37376,1180,452,'www.notion.so',10),(93,37389,1181,452,'lab.ssafy.com',10),(162,37610,1182,452,'githubdesktop.exe',10),(8,37661,1183,460,'newtab',10),(5,37665,1184,452,'notepad.exe',10),(92,37851,1185,452,'www.inflearn.com',10),(317,38044,1186,451,'i11a707.p.ssafy.io',10),(927,38044,1187,451,'code.exe',10),(45,38083,1188,457,'taskmgr.exe',10),(15,38167,1189,460,'www.google.com',10),(123,38219,1190,451,'localhost',10),(40,38189,1191,460,'jlog1016.tistory.com',10),(66,38329,1192,456,'extensions',10),(3,38329,1193,456,'chgbgdipicmfploomogmnednamomjnoc',10),(355,38332,1194,456,'i11a707.p.ssafy.io',10),(3,38365,1195,452,'explorer.exe',10),(115,38377,1196,452,'brocess.tistory.com',10),(7,38382,1197,456,'www.notion.so',10),(1,38390,1198,460,'unit-15.tistory.com',10),(5,38400,1199,460,'learncom1234.tistory.com',10),(2,38471,1200,456,'chatgpt.com',10),(2,38475,1201,456,'lab.ssafy.com',10),(2,38477,1202,456,'edu.ssafy.com',10),(49,38564,1203,451,'newtab',10),(75,38583,1204,451,'system idle process',10),(14,38590,1205,452,'2minmin2.tistory.com',10),(3,38635,1206,452,'mattermost.exe',10),(100,38693,1207,463,'i11a707.p.ssafy.io',10),(2,38694,1208,463,'www.ssafy.com',10),(9,38709,1209,463,'newtab',10),(13,38711,1210,463,'www.google.com',10),(20,38715,1211,463,'m.sports.naver.com',10),(0,38773,1212,451,'shellexperiencehost.exe',10),(20,38836,1213,463,'plusblog.co.kr',10),(3,38851,1214,463,'www.naver.com',10),(434,38872,1215,456,'code.exe',10),(7,39098,1216,451,'extensions',10),(7,39124,1217,451,'kakaotalk.exe',10),(39,39103,1218,457,'idea64.exe',10),(3,39112,1219,457,'www.google.com',10),(425,39607,1220,451,'code.exe',11),(1556,39612,1221,451,'i11a707.p.ssafy.io',11),(37,39612,1222,451,'system idle process',11),(2,39586,1223,452,'kakaotalk.exe',10),(54,39618,1224,463,'i11a707.p.ssafy.io',11),(1299,39626,1225,452,'i11a707.p.ssafy.io',11),(13,39639,1226,452,'www.notion.so',11),(537,39648,1227,452,'idea64.exe',11),(15,39668,1228,452,'mysqlworkbench.exe',11),(3,39683,1229,452,'www.inflearn.com',11),(29,39683,1230,452,'brocess.tistory.com',11),(1,39715,1231,452,'newtab',11),(38,39770,1232,452,'mattermost.exe',11),(2,39826,1233,452,'githubdesktop.exe',11),(0,39863,1234,451,'shellexperiencehost.exe',11),(4,39984,1235,456,'accounts.google.com',11),(579,40005,1236,456,'i11a707.p.ssafy.io',11),(424,40042,1237,463,'finance.naver.com',11),(1,40043,1238,463,'mspcmanager.exe',11),(392,40067,1239,456,'code.exe',11),(2,40069,1240,456,'lab.ssafy.com',11),(21,40071,1241,456,'edu.ssafy.com',11),(3,40094,1242,456,'www.notion.so',11),(1,40099,1243,456,'extensions',11),(50,40116,1244,456,'mattermost.exe',11),(0,40211,1245,452,'extensions',11),(235,40214,1246,452,'lab.ssafy.com',11),(382,40278,1247,451,'mattermost.exe',11),(799,41761,1248,451,'lockapp.exe',11),(1,41765,1249,451,'kakaotalk.exe',11),(1420,41735,1250,452,'explorer.exe',11),(813,41908,1251,457,'i11a707.p.ssafy.io',11),(236,42021,1252,451,'notion.exe',11),(8,42046,1253,452,'kakaotalk.exe',11),(37,42204,1254,451,'explorer.exe',11),(319,42174,1255,459,'i11a707.p.ssafy.io',11),(21,42174,1256,459,'extensions',11),(9,42235,1258,459,'settings',11),(6,42361,1259,457,'idea64.exe',11),(10,42432,1260,457,'notion.exe',11),(46,42444,1261,456,'system idle process',11),(13,42449,1262,457,'mysqlworkbench.exe',11),(106,42475,1263,457,'mintty.exe',11),(395,42489,1264,459,'explorer.exe',11),(6,42571,1265,451,'newtab',11),(4,42574,1266,451,'www.google.com',11),(176,42623,1267,459,'code.exe',11),(1,42644,1268,457,'system idle process',11),(384,42792,1269,457,'extensions',11),(5,42796,1270,463,'mattermost.exe',11),(3,42797,1271,457,'settings',11),(5,42840,1272,463,'newtab',11),(2,42842,1273,463,'www.google.com',11),(28,42865,1274,459,'system idle process',11),(35,42868,1275,459,'www.notion.so',11),(5,42877,1276,460,'i11a707.p.ssafy.io',11),(2,42879,1277,460,'chatgpt.com',11),(2,43102,1278,460,'www.notion.so',11),(7,43116,1279,456,'localhost',11),(56,43153,1280,456,'chatgpt.com',11),(3,43197,1281,460,'newtab',11),(4,43202,1282,460,'newtab',12),(66,43208,1283,460,'i11a707.p.ssafy.io',12),(1061,43226,1284,452,'i11a707.p.ssafy.io',12),(3,43228,1285,452,'www.inflearn.com',12),(1,43230,1286,452,'lab.ssafy.com',12),(26,43232,1287,452,'extensions',12),(45,43242,1288,456,'chatgpt.com',12),(156,43245,1289,456,'localhost',12),(358,43288,1290,463,'www.youtube.com',11),(88,43288,1291,463,'www.youtube.com',12),(834,43312,1292,463,'i11a707.p.ssafy.io',12),(4,43312,1293,463,'explorer.exe',12),(946,43318,1294,456,'code.exe',12),(39,43325,1295,463,'newtab',12),(10,43327,1296,463,'www.google.com',12),(548,43334,1297,459,'explorer.exe',12),(1997,43335,1298,459,'i11a707.p.ssafy.io',12),(5,43337,1299,463,'openai.com',12),(909,43355,1300,463,'www.netflix.com',12),(295,43361,1301,459,'kakaotalk.exe',12),(139,43371,1302,452,'www.notion.so',12),(38,43404,1303,451,'system idle process',12),(1178,43404,1304,451,'i11a707.p.ssafy.io',12),(206,43385,1305,456,'mattermost.exe',12),(42,43446,1306,451,'lockapp.exe',12),(37,43447,1307,451,'kakaotalk.exe',12),(2481,43445,1308,457,'i11a707.p.ssafy.io',12),(2,43447,1309,457,'searchapp.exe',12),(318,43480,1310,451,'code.exe',12),(1,43471,1311,457,'mintty.exe',12),(15,43487,1312,452,'kakaotalk.exe',12),(15,43528,1313,456,'newtab',12),(14,43552,1314,452,'newtab',12),(7,43555,1315,452,'www.google.com',12),(16,43564,1316,459,'www.notion.so',12),(137,43572,1317,452,'www.youtube.com',12),(14,43591,1318,459,'newtab',12),(1176,43594,1319,456,'i11a707.p.ssafy.io',12),(144,43642,1320,451,'lab.ssafy.com',12),(10,43781,1321,451,'mattermost.exe',12),(0,43764,1322,456,'',12),(28,43808,1323,457,'newtab',12),(130,43809,1324,456,'lab.ssafy.com',12),(0,43853,1325,451,'notion calendar.exe',12),(62,43853,1326,451,'extensions',12),(8,43865,1327,451,'newtab',12),(4,43867,1328,451,'www.google.com',12),(13,43880,1329,451,'git-scm.com',12),(29,43870,1330,456,'system idle process',12),(141,43891,1331,456,'www.google.com',12),(14,43905,1332,456,'f-lab.kr',12),(2,43994,1333,457,'system idle process',12),(89,43994,1334,456,'jasonspace.tistory.com',12),(7,44006,1335,457,'www.google.com',12),(127,44021,1336,459,'mattermost.exe',12),(3,44019,1337,460,'www.erdcloud.com',12),(25,44075,1338,452,'explorer.exe',12),(5,44083,1339,459,'notepad.exe',12),(41,44118,1340,452,'mattermost.exe',12),(205,44204,1341,457,'chatgpt.com',12),(91,44206,1342,457,'extensions',12),(603,44261,1343,463,'system idle process',12),(0,44336,1344,456,'extensions',12),(28,44367,1345,457,'shellexperiencehost.exe',12),(36,44374,1346,452,'settings',12),(8,44610,1347,452,'mysqlworkbench.exe',12),(47,44686,1349,459,'docs.google.com',12),(0,44721,1350,457,'wwwnghks.tistory.com',12),(0,44722,1351,457,'hseungyeon.tistory.com',12),(1,44724,1352,457,'lab.ssafy.com',12),(2,44728,1353,457,'taskmgr.exe',12),(2,44747,1354,452,'dfcfflmgmkapdodnkbmbpomeecnklmfo',12),(122,44751,1355,459,'code.exe',12),(8,44790,1356,459,'mspaint.exe',12),(10,44794,1357,452,'system idle process',12),(5,44794,1358,452,'about:blank',12),(5,45002,1359,457,'mattermost.exe',12),(2,45005,1360,463,'chgbgdipicmfploomogmnednamomjnoc',12),(23,45016,1361,463,'extensions',12),(742,45077,1362,451,'settings',12),(74,45067,1363,463,'mattermost.exe',12),(1,45158,1364,451,'lehghhlgancobehndemlocmjmiljffob',12),(2,45166,1365,451,'wireguard.exe',12),(0,45169,1366,451,'explorer.exe',12),(176,45343,1367,463,'kakaotalk.exe',12),(253,45390,1368,463,'m.sports.naver.com',12),(239,45389,1369,457,'jasoseol.com',12),(15,45472,1370,451,'wooribank.careerlink.kr',12),(58,45483,1371,451,'notion.exe',12),(173,45498,1372,451,'jasoseol.com',12),(1,45499,1373,451,'edu.ssafy.com',12),(12,45739,1374,463,'project.ssafy.com',12),(2,45753,1375,456,'www.notion.so',12),(98,45755,1376,463,'lab.ssafy.com',12),(1,45754,1377,456,'edu.ssafy.com',12),(20,45808,1378,457,'www.samsungcareers.com',12),(7,45886,1379,451,'www.coupang.com',12),(55,46055,1381,456,'nh0404.tistory.com',12),(111,46073,1382,460,'lab.ssafy.com',12),(2,46249,1383,465,'accounts.google.com',12),(3,46252,1384,465,'i11a707.p.ssafy.io',12),(2,46384,1385,457,'edu.ssafy.com',12),(6,46384,1386,457,'project.ssafy.com',12),(1,46383,1387,460,'chatgpt.com',12),(5,46387,1388,456,'accounts.google.com',12),(116,46390,1389,463,'jasoseol.com',12),(2,46558,1390,457,'mysqlworkbench.exe',12),(104,46660,1391,463,'mysqlworkbench.exe',12),(3,46779,1392,460,'www.notion.so',12),(4,46821,1393,456,'accounts.google.com',13),(449,46835,1394,463,'i11a707.p.ssafy.io',13),(40,46840,1395,463,'newtab',13),(87,46843,1396,463,'www.google.com',13),(365,46853,1397,456,'i11a707.p.ssafy.io',13),(19,46862,1398,463,'www.gong-cha.co.kr',13),(188,46880,1399,456,'mattermost.exe',13),(553,46894,1400,463,'kakaotalk.exe',13),(973,46934,1401,451,'i11a707.p.ssafy.io',13),(5,46939,1402,451,'notion.exe',13),(14,46942,1403,451,'newtab',13),(649,46911,1404,457,'i11a707.p.ssafy.io',13),(10,46952,1405,451,'searchapp.exe',13),(47,46922,1406,460,'i11a707.p.ssafy.io',13),(3,46956,1407,451,'www.google.com',13),(20,46929,1408,463,'mattermost.exe',13),(777,46931,1409,459,'i11a707.p.ssafy.io',13),(26,46963,1410,451,'www.gong-cha.co.kr',13),(2171,46931,1411,456,'code.exe',13),(42,46946,1412,459,'mattermost.exe',13),(80,46988,1413,451,'kakaotalk.exe',13),(1,46969,1414,459,'code.exe',13),(0,46973,1415,460,'chatgpt.com',13),(105,46973,1416,460,'lab.ssafy.com',13),(47,47006,1417,456,'system idle process',13),(0,47188,1418,457,'chatgpt.com',13),(1,47275,1419,463,'jasoseol.com',13),(246,47292,1420,459,'kakaotalk.exe',13),(790,47379,1421,451,'www.youtube.com',13),(33,47389,1422,451,'mattermost.exe',13),(0,47389,1423,451,'',13),(111,47389,1424,451,'docs.google.com',13),(67,47422,1425,463,'www.youtube.com',13),(13,47425,1426,456,'newtab',13),(87,47427,1427,463,'explorer.exe',13),(2400,12,1428,466,'www.youtube.com',10),(272,47434,1429,456,'openvidu.io',13),(446,47538,1430,451,'code.exe',13),(138,47521,1431,457,'mattermost.exe',13),(2400,12,1432,467,'www.youtube.com',10),(2400,12,1433,468,'www.youtube.com',10),(4,47606,1434,459,'newtab',13),(4,47609,1435,459,'www.google.com',13),(23,47610,1436,460,'www.google.com',13),(7,47619,1437,459,'www.naver.com',13),(171,47628,1438,459,'search.naver.com',13),(18,47634,1439,460,'www.gong-cha.co.kr',13),(41,47675,1440,456,'www.naver.com',13),(37,47707,1441,456,'search.naver.com',13),(63,47718,1442,459,'map.naver.com',13),(15,47757,1443,456,'www.google.com',13),(2,47763,1444,463,'system idle process',13),(7,47789,1445,459,'mspaint.exe',13),(105,47830,1446,459,'blog.naver.com',13),(138,47895,1447,456,'velog.io',13),(143,47975,1448,456,'comic.naver.com',13),(43,48002,1449,459,'explorer.exe',13),(19,48053,1450,459,'www.tving.com',13),(2,48054,1451,459,'user.tving.com',13),(7,48092,1452,460,'www.erdcloud.com',13),(144,48129,1453,451,'edu.ssafy.com',13),(459,48122,1454,457,'www.google.com',13),(149,48123,1455,457,'jasoseol.com',13),(1,48124,1456,457,'project.ssafy.com',13),(2,48126,1457,457,'edu.ssafy.com',13),(0,48233,1458,463,'extensions',13),(52,48269,1459,456,'jasoseol.com',13),(265,48356,1460,459,'www.youtube.com',13),(1280,48494,1461,452,'i11a707.p.ssafy.io',13),(39,48509,1462,452,'newtab',13),(39,48548,1463,452,'system idle process',13),(15,48554,1464,452,'mattermost.exe',13),(65,48568,1465,452,'notepad.exe',13),(47,48622,1466,452,'webstorm64.exe',13),(968,48660,1467,465,'i11a707.p.ssafy.io',13),(21,48661,1468,465,'newtab',13),(8,48691,1469,451,'www.naver.com',13),(37,48692,1470,451,'comic.naver.com',13),(50,48703,1471,465,'www.naver.com',13),(34,48763,1472,465,'media.naver.com',13),(4,48785,1473,465,'news.naver.com',13),(9,48809,1474,465,'cafe.naver.com',13),(1,48817,1475,465,'mail.naver.com',13),(7,48838,1476,465,'finance.naver.com',13),(16,48863,1477,465,'silbi.goodrichmall.com',13),(184,48893,1478,465,'www.daum.net',13),(4,49080,1479,452,'www.google.com',13),(181,49084,1480,452,'comic.naver.com',13),(2,49289,1481,465,'msedge.exe',13),(0,49318,1482,452,'explorer.exe',13),(135,49380,1483,465,'explorer.exe',13),(9,49420,1484,465,'finance.daum.net',13),(7,49483,1485,465,'shellexperiencehost.exe',13),(8,49782,1486,452,'www.netflix.com',13),(15,49835,1488,465,'applicationframehost.exe',13),(9,50126,1489,465,'www.coupang.com',13),(174,50228,1490,451,'system idle process',13),(18,52397,1492,459,'i11a707.p.ssafy.io',14),(2016,52408,1493,456,'code.exe',14),(3,52411,1494,456,'jasoseol.com',14),(43,52440,1495,459,'mattermost.exe',14),(0,52440,1496,459,'',14),(6,52446,1497,459,'docs.google.com',14),(41,52450,1498,456,'i11a707.p.ssafy.io',14),(3,52456,1499,456,'chatgpt.com',14),(1,52457,1500,456,'www.notion.so',14),(1,52459,1501,456,'lab.ssafy.com',14),(164,52503,1502,456,'edu.ssafy.com',14),(578,52548,1503,456,'newtab',14),(54,52579,1504,460,'i11a707.p.ssafy.io',14),(0,52579,1505,460,'chatgpt.com',14),(3,52907,1506,460,'newtab',14),(34,52912,1507,460,'lolchess.gg',14),(14,52927,1508,460,'www.notion.so',14),(596,53142,1509,456,'www.google.com',14),(16,53601,1510,456,'mattermost.exe',14),(9,53692,1511,456,'www.naver.com',14),(1298,53722,1512,459,'kakaotalk.exe',14),(93,53827,1513,456,'www.acmicpc.net',14),(6,53831,1514,456,'system idle process',14),(0,53831,1515,456,'explorer.exe',14),(30,53887,1516,456,'github.com',14),(43,53958,1517,456,'pycharm64.exe',14),(297,54014,1518,456,'pycharm64.exe',15),(33,54017,1519,456,'www.acmicpc.net',15),(16,54046,1520,456,'newtab',15),(102,54085,1521,456,'chatgpt.com',15),(20,54118,1522,456,'www.google.com',15),(37,54195,1523,460,'lolchess.gg',15),(3,54198,1524,460,'newtab',15),(236,54285,1525,456,'velog.io',15),(1,54384,1526,456,'edu.ssafy.com',15),(24,54392,1527,460,'www.notion.so',15),(622,54548,1528,456,'system idle process',15),(44,54652,1529,456,'pythontutor.com',15),(43,54674,1530,456,'mattermost.exe',15),(47,54832,1531,456,'fubabaz.tistory.com',15),(466,54914,1532,456,'day-moonlight.tistory.com',15),(2,54978,1533,456,'',15),(1486,55343,1534,456,'www.canva.com',15),(242,55427,1535,459,'explorer.exe',14),(1611,55427,1536,459,'explorer.exe',15),(609,55428,1537,459,'i11a707.p.ssafy.io',15),(122,55523,1538,459,'videoeditorplus.exe',15),(8,55525,1539,459,'',15),(175,55526,1540,459,'www.movavi.com',15),(8,55688,1541,459,'www.youtube.com',15),(18,55689,1542,459,'mattermost.exe',15),(19,55725,1543,459,'store.movavi.com',15),(2,55757,1544,459,'code.exe',15),(34,56008,1545,459,'newtab',15),(931,56089,1546,457,'i11a707.p.ssafy.io',15),(1,56090,1547,457,'notion.exe',15),(0,56107,1548,457,'dc.wondershare.kr',15),(0,56188,1549,459,'kakaotalk.exe',15),(1,56339,1550,459,'password-manager',15),(0,57193,1552,459,'extensions',15),(3,57282,1553,459,'mspaint.exe',15),(20,57362,1554,456,'sellbuymusic.tistory.com',15),(132,57415,1555,459,'notepad.exe',15),(95,57418,1556,456,'www.sellbuymusic.com',15),(29,57420,1557,456,'explorer.exe',15),(41,57441,1558,456,'nid.naver.com',15),(2682,57645,1559,459,'i11a707.p.ssafy.io',16),(6,57646,1560,459,'system idle process',16),(204,57834,1561,459,'kakaotalk.exe',16),(589,58016,1562,457,'system idle process',15),(416,58016,1563,457,'system idle process',16),(1276,58018,1564,457,'i11a707.p.ssafy.io',16),(1,58019,1565,457,'dc.wondershare.kr',16),(388,58023,1566,457,'www.naver.com',16),(1355,58026,1567,457,'map.naver.com',16),(497,58097,1568,456,'explorer.exe',16),(2,58099,1569,456,'day-moonlight.tistory.com',16),(109,58100,1570,456,'chatgpt.com',16),(232,58161,1571,459,'mattermost.exe',16),(0,58161,1572,459,'',16),(32,58167,1573,459,'swexpertacademy.com',16),(589,58238,1574,456,'www.acmicpc.net',16),(2183,58253,1575,456,'pycharm64.exe',16),(23,58438,1576,456,'newtab',16),(31,58441,1577,456,'github.com',16),(5,58556,1578,460,'newtab',16),(57,58560,1579,460,'www.youtube.com',16),(2,58562,1580,460,'www.canva.com',16),(1,58582,1581,457,'news.naver.com',16),(2,58588,1582,460,'music.youtube.com',16),(9,58601,1583,460,'i11a707.p.ssafy.io',16),(3,58604,1584,460,'www.erdcloud.com',16),(34,58618,1585,457,'mattermost.exe',16),(8,58636,1586,456,'mattermost.exe',16),(2,58870,1587,456,'www.google.com',16),(36,58883,1588,456,'velog.io',16),(19,58930,1589,460,'www.op.gg',16),(3,58999,1590,457,'shellexperiencehost.exe',16),(11,59028,1591,457,'newtab',16),(57,59035,1592,457,'www.google.com',16),(25,59060,1593,457,'m.blog.naver.com',16),(28,59125,1594,457,'www.diningcode.com',16),(389,59225,1595,451,'i11a707.p.ssafy.io',16),(8,59233,1596,451,'newtab',16),(1236,59288,1597,451,'code.exe',16),(35,59324,1598,451,'notion.exe',16),(5,59310,1599,457,'nid.naver.com',16),(365,59361,1600,451,'system idle process',16),(159,59652,1601,459,'notepad.exe',16),(205,59698,1602,459,'explorer.exe',16),(51,59883,1603,451,'kakaotalk.exe',16),(0,59905,1604,456,'system idle process',16),(104,59971,1605,456,'i11a707.p.ssafy.io',16),(16,60026,1606,456,'edu.ssafy.com',16),(40,60026,1607,460,'extensions',16),(249,60187,1608,452,'i11a707.p.ssafy.io',16),(7,60191,1609,452,'idea64.exe',16),(29,60317,1610,452,'kakaotalk.exe',16),(3,60564,1611,459,'newtab',16),(19,60565,1612,459,'www.youtube.com',16),(23,60615,1613,459,'www.naver.com',16),(21,60639,1614,459,'code.exe',16),(0,60645,1615,459,'searchapp.exe',16),(14,61003,1616,459,'settings',16),(0,61200,1617,459,'notepad.exe',17),(30,61209,1618,459,'i11a707.p.ssafy.io',17),(1123,61254,1619,459,'videoeditorplus.exe',17),(876,61328,1620,456,'pycharm64.exe',17),(7,61399,1621,459,'newtab',17),(4,61397,1622,460,'newtab',17),(509,61398,1623,456,'www.acmicpc.net',17),(12,61403,1624,459,'www.naver.com',17),(2,61401,1625,460,'www.google.com',17),(42,61404,1626,459,'search.naver.com',17),(16,61420,1627,459,'clova.ai',17),(365,61470,1628,459,'www.ncloud.com',17),(15,61472,1629,459,'auth.ncloud.com',17),(10,61478,1630,459,'nid.naver.com',17),(189,61487,1631,456,'mattermost.exe',17),(12,61531,1632,459,'mail.naver.com',17),(56,61573,1633,456,'newtab',17),(1,61574,1634,456,'chatgpt.com',17),(12,61648,1635,459,'naver.worksmobile.com',17),(524,61724,1636,451,'system idle process',17),(9,61725,1637,451,'i11a707.p.ssafy.io',17),(2,61727,1638,451,'www.canva.com',17),(195,61750,1639,456,'www.google.com',17),(8,61783,1640,460,'i11a707.p.ssafy.io',17),(13,61805,1641,459,'screenclippinghost.exe',17),(1619,61913,1642,457,'i11a707.p.ssafy.io',17),(7,61916,1643,457,'newtab',17),(30,61921,1644,457,'www.google.com',17),(139,61925,1645,457,'clovadubbing.naver.com',17),(11,61930,1646,457,'nid.naver.com',17),(3,61982,1647,459,'console.ncloud.com',17),(2,62007,1648,459,'www.google.com',17),(942,62018,1649,459,'clovadubbing.naver.com',17),(0,62019,1650,456,'',17),(2,62021,1651,456,'www.jetbrains.com',17),(357,62086,1652,451,'kakaotalk.exe',17),(6,62095,1653,457,'map.naver.com',17),(884,62254,1654,451,'mattermost.exe',17),(70,62478,1655,459,'system idle process',17),(198,62625,1656,459,'explorer.exe',17),(3,62637,1657,459,'kakaotalk.exe',17),(424,62701,1658,456,'edu.ssafy.com',17),(144,62714,1659,456,'i11a707.p.ssafy.io',17),(729,62837,1660,456,'www.naver.com',17),(12,62862,1661,456,'mail.naver.com',17),(683,63485,1662,457,'system idle process',17),(117,63593,1663,459,'winword.exe',17),(0,63593,1664,459,'',17),(5,63596,1665,459,'file:///c:/users/ssafy/desktop/11%ea%b8%b0%20%ea%b3%b5%ed%86%b5%20%ed%94%84%eb%a1%9c%ec%a0%9d%ed%8a%b8%20%ea%b2%b0%ea%b3%bc%eb%ac%bc%20%ed%99%9c%ec%9a%a9%20%eb%8f%99%ec%9d%98%ec%84%9c_%ec%84%9c%ec%9a%b8_a707_%ea%b9%80%ed%83%9c%ea%b2%bd.pdf',17),(138,63650,1666,456,'winword.exe',17),(2,63676,1667,459,'code.exe',17),(260,63708,1668,457,'mattermost.exe',17),(2,63711,1669,457,'www.naver.com',17),(546,63713,1670,457,'edu.ssafy.com',17),(34,63786,1671,460,'edu.ssafy.com',17),(199,63989,1672,456,'search.naver.com',17),(26,64062,1673,460,'www.notion.so',17),(0,64108,1674,457,'explorer.exe',17),(0,64134,1675,457,'idea64.exe',17),(0,64146,1676,460,'www.canva.com',17),(1,64147,1677,460,'extensions',17),(0,64147,1678,460,'settings',17),(0,64148,1679,460,'clovanote.naver.com',17),(0,64150,1680,460,'www.erdcloud.com',17),(3,64151,1681,460,'chatgpt.com',17),(1,64152,1682,460,'lab.ssafy.com',17),(9,64437,1683,456,'xetown.com',17),(297,64441,1684,457,'time.navyism.com',17),(112,64610,1685,456,'time.navyism.com',17),(6,64800,1686,457,'edu.ssafy.com',18),(5,64803,1687,456,'explorer.exe',17),(3,64803,1688,456,'explorer.exe',18),(0,64803,1689,456,'shellexperiencehost.exe',18),(9,64805,1690,456,'edu.ssafy.com',18);
/*!40000 ALTER TABLE `app_focus_times` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps`
--

DROP TABLE IF EXISTS `apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps` (
  `app_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps`
--

LOCK TABLES `apps` WRITE;
/*!40000 ALTER TABLE `apps` DISABLE KEYS */;
/*!40000 ALTER TABLE `apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bans`
--

DROP TABLE IF EXISTS `bans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bans` (
  `is_ban` bit(1) NOT NULL,
  `ban_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ban_id`),
  KEY `FKdshp5tj95xpg7ikybc4teb68q` (`user_id`),
  CONSTRAINT `FKdshp5tj95xpg7ikybc4teb68q` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=770 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bans`
--

LOCK TABLES `bans` WRITE;
/*!40000 ALTER TABLE `bans` DISABLE KEYS */;
INSERT INTO `bans` VALUES (_binary '',525,57,'www.tving.com'),(_binary '',529,58,'www.youtube.com'),(_binary '',530,58,'comic.naver.com'),(_binary '',532,58,'www.coupang.com'),(_binary '',533,58,'www.netflix.com'),(_binary '',534,58,'www.tving.com'),(_binary '',535,58,'kakaotalk.exe'),(_binary '',539,59,'comic.naver.com'),(_binary '',540,59,'chatgpt.com'),(_binary '',541,59,'www.coupang.com'),(_binary '',542,59,'www.netflix.com'),(_binary '',543,59,'www.tving.com'),(_binary '',557,61,'comic.naver.com'),(_binary '',559,61,'www.coupang.com'),(_binary '',560,61,'www.netflix.com'),(_binary '',561,61,'www.tving.com'),(_binary '',562,61,'kakaotalk.exe'),(_binary '',563,61,'mattermost.exe'),(_binary '',573,63,'i11a707.p.ssafy.io'),(_binary '',574,63,'www.youtube.com'),(_binary '',575,63,'comic.naver.com'),(_binary '',576,63,'chatgpt.com'),(_binary '',577,63,'www.coupang.com'),(_binary '',578,63,'www.netflix.com'),(_binary '',579,63,'www.tving.com'),(_binary '',580,63,'kakaotalk.exe'),(_binary '',581,63,'mattermost.exe'),(_binary '',583,58,'chrome.exe'),(_binary '',600,64,'i11a707.p.ssafy.io'),(_binary '',601,64,'www.youtube.com'),(_binary '',602,64,'comic.naver.com'),(_binary '',603,64,'chatgpt.com'),(_binary '',604,64,'www.coupang.com'),(_binary '',605,64,'www.netflix.com'),(_binary '',606,64,'www.tving.com'),(_binary '',607,64,'kakaotalk.exe'),(_binary '',608,64,'mattermost.exe'),(_binary '',609,65,'i11a707.p.ssafy.io'),(_binary '',610,65,'www.youtube.com'),(_binary '',611,65,'comic.naver.com'),(_binary '',612,65,'chatgpt.com'),(_binary '',613,65,'www.coupang.com'),(_binary '',614,65,'www.netflix.com'),(_binary '',615,65,'www.tving.com'),(_binary '',616,65,'kakaotalk.exe'),(_binary '',617,65,'mattermost.exe'),(_binary '',618,66,'i11a707.p.ssafy.io'),(_binary '',619,66,'www.youtube.com'),(_binary '',620,66,'comic.naver.com'),(_binary '',621,66,'chatgpt.com'),(_binary '',622,66,'www.coupang.com'),(_binary '',623,66,'www.netflix.com'),(_binary '',624,66,'www.tving.com'),(_binary '',625,66,'kakaotalk.exe'),(_binary '',626,66,'mattermost.exe'),(_binary '',628,67,'www.youtube.com'),(_binary '',629,67,'comic.naver.com'),(_binary '',630,67,'chatgpt.com'),(_binary '',631,67,'www.coupang.com'),(_binary '',632,67,'www.netflix.com'),(_binary '',633,67,'www.tving.com'),(_binary '',634,67,'kakaotalk.exe'),(_binary '',635,67,'mattermost.exe'),(_binary '',644,59,'www.youtube.com'),(_binary '',645,58,'mattermost.exe'),(_binary '',648,58,'www.google.com'),(_binary '',674,59,'mattermost.exe'),(_binary '',684,57,'mattermost.exe'),(_binary '',689,57,'fnmechklbhpapbafomaidkgdlpogggng'),(_binary '',691,68,'i11a707.p.ssafy.io'),(_binary '',692,68,'www.youtube.com'),(_binary '',697,68,'www.tving.com'),(_binary '',699,68,'mattermost.exe'),(_binary '',708,62,'i11a707.p.ssafy.io'),(_binary '',711,60,'kakaotalk.exe'),(_binary '',716,67,'i11a707.p.ssafy.io'),(_binary '',717,60,'www.youtube.com'),(_binary '',722,60,'comic.naver.com'),(_binary '',732,59,'webstorm64.exe'),(_binary '',735,68,'www.daum.net'),(_binary '',736,59,'notepad.exe'),(_binary '',737,68,'finance.naver.com'),(_binary '',738,68,'explorer.exe'),(_binary '',754,69,'i11a707.p.ssafy.io'),(_binary '',755,69,'www.youtube.com'),(_binary '',756,69,'comic.naver.com'),(_binary '',757,69,'chatgpt.com'),(_binary '',758,69,'www.coupang.com'),(_binary '',759,69,'www.netflix.com'),(_binary '',760,69,'www.tving.com'),(_binary '',761,69,'kakaotalk.exe'),(_binary '',762,69,'mattermost.exe'),(_binary '',763,59,'i11a707.p.ssafy.io'),(_binary '',769,57,'www.naver.com');
/*!40000 ALTER TABLE `bans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `friend_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `relation` enum('ACCEPTED','WAITING') DEFAULT NULL,
  PRIMARY KEY (`friend_id`,`user_id`),
  KEY `FKlh21lfp7th1y1tn9g63ihkda9` (`user_id`),
  CONSTRAINT `FKc42eihjtiryeriy8axlkpejo7` FOREIGN KEY (`friend_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKlh21lfp7th1y1tn9g63ihkda9` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (1,2,'ACCEPTED'),(1,3,'ACCEPTED'),(1,4,'WAITING'),(2,1,'ACCEPTED'),(3,1,'ACCEPTED'),(4,2,'WAITING'),(5,1,'WAITING');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hour_focus_times`
--

DROP TABLE IF EXISTS `hour_focus_times`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hour_focus_times` (
  `focus_time` int NOT NULL,
  `time_unit` int NOT NULL,
  `hour_focus_time_id` bigint NOT NULL AUTO_INCREMENT,
  `one_day_id` bigint DEFAULT NULL,
  PRIMARY KEY (`hour_focus_time_id`),
  KEY `FKkdjw7uqeycx67ngyyk8g31hpe` (`one_day_id`),
  CONSTRAINT `FKkdjw7uqeycx67ngyyk8g31hpe` FOREIGN KEY (`one_day_id`) REFERENCES `one_days` (`one_day_id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hour_focus_times`
--

LOCK TABLES `hour_focus_times` WRITE;
/*!40000 ALTER TABLE `hour_focus_times` DISABLE KEYS */;
/*!40000 ALTER TABLE `hour_focus_times` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `one_days`
--

DROP TABLE IF EXISTS `one_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `one_days` (
  `date_data` date NOT NULL,
  `target_time` int NOT NULL,
  `one_day_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `focus_time` int DEFAULT '0',
  PRIMARY KEY (`one_day_id`),
  UNIQUE KEY `UK5fprev126h78w7s659lciptmg` (`user_id`,`date_data`),
  CONSTRAINT `FK6iuarp8ehaiowb1xjf53h9lrw` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=472 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `one_days`
--

LOCK TABLES `one_days` WRITE;
/*!40000 ALTER TABLE `one_days` DISABLE KEYS */;
INSERT INTO `one_days` VALUES ('2024-08-13',0,439,57,0),('2024-08-13',0,440,58,0),('2024-08-13',0,441,59,0),('2024-08-13',0,442,60,0),('2024-08-13',0,443,61,0),('2024-08-13',7200,444,62,0),('2024-08-14',7200,451,60,0),('2024-08-14',7200,452,59,0),('2024-08-14',0,455,66,0),('2024-08-14',6000,456,61,0),('2024-08-14',2400,457,58,0),('2024-08-14',86400,459,57,0),('2024-08-14',0,460,62,0),('2024-08-14',0,461,64,0),('2024-08-14',6000,463,67,0),('2024-08-12',0,464,62,0),('2024-08-14',0,465,68,0),('2024-08-01',0,466,57,10),('2024-08-11',0,467,57,10),('2024-08-05',0,468,57,10),('2024-08-14',0,469,69,0);
/*!40000 ALTER TABLE `one_days` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `is_active` bit(1) DEFAULT NULL,
  `is_share` bit(1) DEFAULT NULL,
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary '',_binary '\0',57,'rlaxorud1716@gmail.com','rlaxorud1716@gmail.com'),(_binary '',_binary '\0',58,'ktk20521@gmail.com','ktk20521@gmail.com'),(_binary '',_binary '\0',59,'a707coach1@gmail.com','a707coach1@gmail.com'),(_binary '',_binary '\0',60,'oodeng98@gmail.com','oodeng98@gmail.com'),(_binary '',_binary '\0',61,'kevinyeonkr@naver.com','kevinyeonkr@naver.com'),(_binary '',_binary '\0',62,'jaehyun158@gmail.com','jaehyun158@gmail.com'),(_binary '',_binary '\0',64,'illbanclassone@gmail.com','illbanclassone@gmail.com'),(_binary '',_binary '\0',65,'psw1253@naver.com','psw1253@naver.com'),(_binary '',_binary '\0',66,'a707coach2@gmail.com','a707coach2@gmail.com'),(_binary '',_binary '\0',67,'a707consultant@gmail.com','a707consultant@gmail.com'),(_binary '',_binary '\0',68,'sweetchild22.ik@gmail.com','sweetchild22.ik@gmail.com'),(_binary '',_binary '\0',69,'yunji383614@gmail.com','yunji383614@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `widgets`
--

DROP TABLE IF EXISTS `widgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `widgets` (
  `x` int NOT NULL,
  `y` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `widget_id` bigint NOT NULL AUTO_INCREMENT,
  `name` enum('BannedProgramList','CalendarCheck','FocusTimeEachPrograms','FocusTimeEachProgramsPercentage','MonthAchievementBig','MonthAchievementSmall','MonthFocusBig','MonthFocusBigWithoutComparison','MonthFocusSmall','MonthStreakColumn','MonthStreakRow','MonthTargetTime','PercentileRank','SixMonthStreak','TimeLine','TimerCheck','TodayAchievementBig','TodayAchievementSmall','TodayFocusBig','TodayFocusBigWithoutComparison','TodayFocusSmall','TodayTargetTime','TotalFocusBig','TotalFocusSmall','WeekAchievementBig','WeekAchievementSmall','WeekFocusBig','WeekFocusBigWithoutComparison','WeekFocusSmall','WeekTargetTime') DEFAULT NULL,
  `height` int NOT NULL,
  `width` int NOT NULL,
  PRIMARY KEY (`widget_id`),
  KEY `FK2u7ak3kbgkuq4h5lclu6yyxnl` (`user_id`),
  CONSTRAINT `FK2u7ak3kbgkuq4h5lclu6yyxnl` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2016 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `widgets`
--

LOCK TABLES `widgets` WRITE;
/*!40000 ALTER TABLE `widgets` DISABLE KEYS */;
INSERT INTO `widgets` VALUES (0,0,67,1551,'TodayFocusBigWithoutComparison',1,3),(3,0,67,1552,'WeekFocusBigWithoutComparison',1,3),(6,0,67,1553,'MonthFocusBigWithoutComparison',1,3),(9,0,67,1554,'TotalFocusBig',1,3),(0,1,67,1555,'FocusTimeEachProgramsPercentage',4,7),(7,1,67,1556,'BannedProgramList',4,5),(0,5,67,1557,'TimeLine',4,7),(7,5,67,1558,'CalendarCheck',4,5),(0,9,67,1559,'TodayAchievementSmall',1,2),(2,9,67,1560,'TodayAchievementBig',2,2),(4,9,67,1561,'WeekAchievementSmall',1,2),(6,9,67,1562,'WeekAchievementBig',2,2),(8,9,67,1563,'MonthAchievementSmall',1,2),(10,9,67,1564,'MonthAchievementBig',2,2),(0,10,67,1565,'TodayTargetTime',1,2),(4,10,67,1566,'WeekTargetTime',1,2),(8,10,67,1567,'MonthTargetTime',1,2),(0,11,67,1568,'PercentileRank',3,4),(0,0,58,1604,'TodayFocusBigWithoutComparison',1,3),(3,0,58,1605,'WeekFocusBigWithoutComparison',1,3),(6,0,58,1606,'MonthFocusBigWithoutComparison',1,3),(9,0,58,1607,'TotalFocusBig',1,3),(0,1,58,1608,'FocusTimeEachProgramsPercentage',4,7),(7,1,58,1609,'BannedProgramList',4,5),(0,5,58,1610,'TimeLine',4,7),(7,5,58,1611,'CalendarCheck',4,5),(0,9,58,1612,'SixMonthStreak',2,6),(0,0,60,1743,'TodayFocusSmall',1,2),(2,0,60,1744,'TodayFocusBigWithoutComparison',1,3),(5,0,60,1745,'TodayFocusBig',1,3),(8,0,60,1746,'WeekTargetTime',1,2),(10,0,60,1747,'MonthTargetTime',1,2),(0,1,60,1748,'WeekFocusSmall',1,2),(2,1,60,1749,'WeekFocusBigWithoutComparison',1,3),(5,1,60,1750,'WeekFocusBig',1,3),(0,2,60,1751,'MonthFocusSmall',1,2),(2,2,60,1752,'MonthFocusBigWithoutComparison',1,3),(5,2,60,1753,'MonthFocusBig',1,3),(9,2,60,1754,'TotalFocusBig',1,3),(0,3,60,1755,'TodayTargetTime',1,2),(2,3,60,1756,'TodayAchievementBig',2,2),(4,3,60,1757,'WeekAchievementBig',2,2),(6,3,60,1758,'MonthAchievementBig',2,2),(8,3,60,1759,'WeekAchievementSmall',1,2),(10,3,60,1760,'TotalFocusSmall',1,2),(0,4,60,1761,'TodayAchievementSmall',1,2),(8,4,60,1762,'MonthAchievementSmall',1,2),(0,5,60,1763,'FocusTimeEachProgramsPercentage',4,7),(7,5,60,1764,'BannedProgramList',4,5),(0,9,60,1765,'CalendarCheck',4,5),(5,9,60,1766,'TimeLine',4,7),(0,13,60,1767,'SixMonthStreak',2,6),(6,13,60,1768,'PercentileRank',3,4),(0,15,60,1769,'FocusTimeEachPrograms',4,6),(0,0,68,1814,'TotalFocusBig',1,3),(3,0,68,1815,'TodayFocusSmall',1,2),(5,0,68,1816,'TodayFocusBig',1,3),(8,0,68,1817,'TodayFocusBigWithoutComparison',1,3),(0,1,68,1818,'WeekFocusSmall',1,2),(3,1,68,1819,'MonthFocusBigWithoutComparison',1,3),(6,1,68,1820,'WeekFocusBigWithoutComparison',1,3),(9,1,68,1821,'WeekFocusBig',1,3),(0,2,68,1822,'TimeLine',4,7),(7,2,68,1823,'BannedProgramList',4,5),(0,6,68,1824,'MonthFocusSmall',1,2),(2,6,68,1825,'MonthFocusBig',1,3),(5,6,68,1826,'TotalFocusSmall',1,2),(7,6,68,1827,'TodayAchievementBig',2,2),(9,6,68,1828,'TodayAchievementSmall',1,2),(0,7,68,1829,'MonthAchievementSmall',1,2),(2,7,68,1830,'CalendarCheck',4,5),(9,7,68,1831,'WeekAchievementBig',2,2),(7,9,68,1832,'PercentileRank',3,4),(5,12,68,1833,'FocusTimeEachProgramsPercentage',4,7),(0,19,68,1834,'SixMonthStreak',2,6),(6,19,68,1835,'FocusTimeEachPrograms',4,6),(0,0,69,1962,'TodayFocusBigWithoutComparison',1,3),(3,0,69,1963,'WeekFocusBigWithoutComparison',1,3),(6,0,69,1964,'MonthFocusBigWithoutComparison',1,3),(9,0,69,1965,'TotalFocusBig',1,3),(0,1,69,1966,'TimeLine',4,7),(7,1,69,1967,'BannedProgramList',4,5),(0,5,69,1968,'FocusTimeEachProgramsPercentage',4,7),(7,5,69,1969,'CalendarCheck',4,5),(0,0,59,1987,'TodayFocusBigWithoutComparison',1,3),(3,0,59,1988,'WeekFocusBigWithoutComparison',1,3),(6,0,59,1989,'MonthFocusBigWithoutComparison',1,3),(9,0,59,1990,'TotalFocusBig',1,3),(0,1,59,1991,'FocusTimeEachProgramsPercentage',4,7),(7,1,59,1992,'BannedProgramList',4,5),(0,5,59,1993,'TimeLine',4,7),(7,5,59,1994,'CalendarCheck',4,5),(0,0,57,2010,'TodayFocusBigWithoutComparison',1,3),(3,0,57,2011,'WeekFocusBigWithoutComparison',1,3),(6,0,57,2012,'MonthFocusBigWithoutComparison',1,3),(9,0,57,2013,'TotalFocusBig',1,3),(0,1,57,2014,'FocusTimeEachProgramsPercentage',4,7),(7,1,57,2015,'BannedProgramList',4,5);
/*!40000 ALTER TABLE `widgets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-14 19:22:02
