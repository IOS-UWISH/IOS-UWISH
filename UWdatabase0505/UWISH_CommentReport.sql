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
-- Table structure for table `CommentReport`
--

DROP TABLE IF EXISTS `CommentReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CommentReport` (
  `UUID` int NOT NULL AUTO_INCREMENT,
  `Comment_id` int NOT NULL,
  `Reporter_id` int NOT NULL,
  `Reason` int NOT NULL COMMENT '檢舉原因（0：罵人、說髒話、1：恐嚇、言語暴力、2：色情、騷擾、3：張貼廣告訊息、4：其他）',
  `Report_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` int DEFAULT '0' COMMENT '審核狀態（0：未處理、1：忽略、2：刪除、3：停權）',
  PRIMARY KEY (`UUID`),
  KEY `fk_comment_report_idx` (`Comment_id`),
  KEY `fk_user_report_idx` (`Reporter_id`),
  CONSTRAINT `fk_comment_report` FOREIGN KEY (`Comment_id`) REFERENCES `CommentInfo` (`UUID`),
  CONSTRAINT `fk_user_comment_report` FOREIGN KEY (`Reporter_id`) REFERENCES `UserInfo` (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentReport`
--

LOCK TABLES `CommentReport` WRITE;
/*!40000 ALTER TABLE `CommentReport` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommentReport` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-05 19:43:40
