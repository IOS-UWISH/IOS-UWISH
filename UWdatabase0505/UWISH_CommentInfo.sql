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
-- Table structure for table `CommentInfo`
--

DROP TABLE IF EXISTS `CommentInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CommentInfo` (
  `UUID` int NOT NULL AUTO_INCREMENT,
  `User_id_comment` int NOT NULL,
  `Detail` varchar(30) DEFAULT NULL COMMENT '留言內容',
  `Comment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Task_id` int NOT NULL,
  `Show` tinyint(1) DEFAULT '1' COMMENT '是否顯示（0：不顯示、1：顯示）',
  PRIMARY KEY (`UUID`),
  KEY `fk_user_comment_idx` (`User_id_comment`),
  KEY `fk_task_comment_idx` (`Task_id`),
  CONSTRAINT `fk_task_comment` FOREIGN KEY (`Task_id`) REFERENCES `TaskInfo` (`UUID`),
  CONSTRAINT `fk_user_comment` FOREIGN KEY (`User_id_comment`) REFERENCES `UserInfo` (`UUID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentInfo`
--

LOCK TABLES `CommentInfo` WRITE;
/*!40000 ALTER TABLE `CommentInfo` DISABLE KEYS */;
INSERT INTO `CommentInfo` VALUES (1,7,'Hello','2020-05-01 03:14:22',1,1),(2,1,'Hi','2020-05-01 03:14:35',1,1),(3,3,'喵','2020-05-01 03:14:55',1,1),(4,7,'一起玩','2020-05-01 03:15:13',1,1),(5,4,'不要','2020-05-01 03:15:41',1,1),(6,6,'要什麼口味','2020-05-01 07:20:23',3,1),(7,1,'全部都要','2020-05-01 07:20:23',3,1);
/*!40000 ALTER TABLE `CommentInfo` ENABLE KEYS */;
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
