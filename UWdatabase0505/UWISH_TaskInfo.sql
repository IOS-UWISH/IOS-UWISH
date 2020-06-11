-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: UWISH
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `TaskInfo`
--

DROP TABLE IF EXISTS `TaskInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TaskInfo` (
  `UUID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(9) NOT NULL,
  `User_id_post` int NOT NULL COMMENT '發案者ID',
  `Category` int NOT NULL COMMENT '案件類別（0：買東西、1：遛狗、2：清潔、3：搬家、4：快遞、5：煮飯）',
  `Due_date` date NOT NULL COMMENT '截止日',
  `District_id` int NOT NULL,
  `Address` varchar(40) DEFAULT NULL,
  `Latitude` double DEFAULT NULL,
  `Longitude` double DEFAULT NULL,
  `Budget` int NOT NULL COMMENT '發案金額',
  `Detail` varchar(30) DEFAULT NULL COMMENT '發案細節',
  `Image_1` longblob,
  `Image_2` longblob,
  `Status` int DEFAULT '0' COMMENT '案件狀態（0：待指派、1：已成立、2：進行中、3：已完成、4：未完成）',
  `Upate_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Show` tinyint DEFAULT '1' COMMENT '是否顯示於首頁（0：不顯示、1：顯示）',
  PRIMARY KEY (`UUID`),
  KEY `fk_User_id_post_idx` (`User_id_post`),
  KEY `fk_district_idx` (`District_id`),
  CONSTRAINT `fk_district` FOREIGN KEY (`District_id`) REFERENCES `CityInfo` (`UUID`),
  CONSTRAINT `fk_user_id_post` FOREIGN KEY (`User_id_post`) REFERENCES `UserInfo` (`UUID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaskInfo`
--

LOCK TABLES `TaskInfo` WRITE;
/*!40000 ALTER TABLE `TaskInfo` DISABLE KEYS */;
INSERT INTO `TaskInfo` VALUES (1,'幫我煮晚餐',1,5,'2020-04-30',1,'123123',NULL,NULL,500,'我是最可(ㄆ)愛(ㄤˋ)的黃阿瑪',NULL,NULL,1,'2020-05-04 14:51:54',1),(2,'幫我倒垃圾',2,2,'2020-04-25',67,'123123',NULL,NULL,300,'我的飯飯呢',NULL,NULL,0,'2020-04-30 09:44:12',1),(3,'買罐罐',1,0,'2020-04-22',3,'123123',NULL,NULL,50,'鮪魚口味',NULL,NULL,0,'2020-05-04 14:51:54',1),(4,'帶我出去玩',3,1,'2020-04-24',5,'123123',NULL,NULL,30,'遛我',NULL,NULL,1,'2020-05-04 14:51:54',1),(5,'搬家',4,3,'2020-04-25',8,'123123',NULL,NULL,20,'搬家',NULL,NULL,0,'2020-05-04 14:51:54',1),(6,'測試資料',5,1,'2020-04-25',7,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,0,'2020-05-04 14:51:54',1),(7,'測試資料',6,1,'2020-04-25',5,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,2,'2020-05-04 14:51:54',1),(8,'測試資料',2,2,'2020-04-25',23,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,3,'2020-05-04 14:51:54',1),(9,'測試資料',3,2,'2020-04-25',9,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,4,'2020-05-04 14:51:54',1),(10,'測試資料',4,3,'2020-04-25',17,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,0,'2020-04-30 09:44:12',1),(11,'測試資料',5,4,'2020-04-25',42,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,0,'2020-04-30 09:44:12',1),(12,'測試資料',6,4,'2020-04-25',36,'123123',NULL,NULL,50,'這只是測試資料懶得想要寫什麼，這只是測試資料懶得想要寫什麼。',NULL,NULL,0,'2020-04-30 09:44:12',1);
/*!40000 ALTER TABLE `TaskInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-05 19:43:42
