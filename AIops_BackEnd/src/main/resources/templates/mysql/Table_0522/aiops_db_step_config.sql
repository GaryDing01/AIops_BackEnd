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
-- Table structure for table `step_config`
--

DROP TABLE IF EXISTS `step_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step_config` (
  `step_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int DEFAULT NULL,
  `step_num` int DEFAULT NULL,
  `param` text,
  `alg_id` int DEFAULT NULL,
  `wf_id` int DEFAULT NULL,
  PRIMARY KEY (`step_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step_config`
--

LOCK TABLES `step_config` WRITE;
/*!40000 ALTER TABLE `step_config` DISABLE KEYS */;
INSERT INTO `step_config` VALUES (1,1,1,'[]',-1,1),(2,1,1,'[{\"dataType\": 1}]',-1,2),(6,1,1,'[{\"dataType\": 1}]',-1,6),(7,1,1,'[{\"dataType\": 1}]',-1,7),(8,1,1,'[{\"dataType\": 1}]',-1,8),(9,2,2,'[]',3,1),(10,1,1,'[]',-1,10),(11,2,2,'[]',3,10),(13,4,3,'[]',4,2),(16,1,1,'[]',-1,12),(17,3,2,'[]',3,12),(28,4,3,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',4,12),(29,5,4,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',5,12),(30,6,5,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',6,12),(31,7,6,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',7,12),(32,1,1,'[]',-1,15),(33,3,2,'[]',3,15),(34,4,3,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',4,15),(35,5,4,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',5,15),(36,6,5,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',6,15),(37,7,6,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',7,15),(38,1,1,'[]',-1,16),(39,3,2,'[]',3,16),(40,4,3,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',4,16),(41,5,4,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',5,16),(42,6,5,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',6,16),(43,7,6,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',7,16),(44,1,1,'[]',-1,17),(45,3,2,'[]',3,17),(46,4,3,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',4,17),(47,5,4,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',5,17),(48,6,5,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',6,17),(49,7,6,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]',7,17),(50,1,1,'[]',-1,18),(51,5,2,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]',9,18),(52,6,3,'[{\"name\": \"input_dir\", \"value\": \"\"}]',5,18),(53,7,4,'[{\"name\": \"log_record\", \"value\": \"\"}]',6,18);
/*!40000 ALTER TABLE `step_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-22 17:42:45
