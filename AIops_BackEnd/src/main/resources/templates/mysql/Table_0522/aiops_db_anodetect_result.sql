-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: aiops_db
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
-- Table structure for table `anodetect_result`
--

DROP TABLE IF EXISTS `anodetect_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anodetect_result` (
  `adr_id` bigint NOT NULL AUTO_INCREMENT,
  `source_data_section` varchar(255) NOT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`adr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anodetect_result`
--

LOCK TABLES `anodetect_result` WRITE;
/*!40000 ALTER TABLE `anodetect_result` DISABLE KEYS */;
INSERT INTO `anodetect_result` VALUES (3,'10-20',1),(4,'30-40',1),(5,'10-20',1),(6,'30-40',1),(7,'10-20',0),(8,'30-40',0),(9,'10-20',1),(10,'30-40',1),(11,'10-20',0),(12,'30-40',0),(13,'11-110',1),(14,'31-130',1),(15,'11-110',1),(16,'31-130',1),(17,'11-11',1),(18,'31-31',1),(19,'11-21',1),(20,'31-41',1),(21,'10-20',1),(22,'30-40',1),(23,'10-20',1),(24,'30-40',1),(25,'10-20',1),(26,'30-40',1),(27,'10-20',0),(28,'30-40',0),(29,'10-20',0),(30,'30-40',0);
/*!40000 ALTER TABLE `anodetect_result` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-22 17:10:42
