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
-- Table structure for table `anomaly_info`
--

DROP TABLE IF EXISTS `anomaly_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anomaly_info` (
  `ano_id` int NOT NULL AUTO_INCREMENT,
  `obj_id` int NOT NULL,
  `status_id` int DEFAULT NULL,
  `unitnode_type_id` int DEFAULT NULL,
  `unitnode_name` varchar(45) DEFAULT NULL,
  `detect_tstamp` timestamp NULL DEFAULT NULL,
  `predict_tstamp` timestamp NULL DEFAULT NULL,
  `update_tstamp` timestamp NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `source_data_id` varchar(255) DEFAULT NULL,
  `data_sample` mediumtext,
  `user_id` int NOT NULL,
  `wf_id` int NOT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`ano_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anomaly_info`
--

LOCK TABLES `anomaly_info` WRITE;
/*!40000 ALTER TABLE `anomaly_info` DISABLE KEYS */;
INSERT INTO `anomaly_info` VALUES (1,2,1,5,'trace0','2023-05-02 09:05:58',NULL,'2023-05-02 09:05:58','序号为100-200的数据出现故障','100-200','[]',1,12,0),(2,2,1,5,'trace1','2023-05-02 09:05:58',NULL,'2023-05-02 09:05:58','序号为300-400的数据出现故障','300-400','[]',1,12,0),(3,3,1,5,'metrics0','2023-05-02 10:56:38',NULL,'2023-05-02 10:56:38','序号为100-200的数据出现故障','100-200','[]',1,12,0),(4,3,1,5,'metrics1','2023-05-02 10:56:38',NULL,'2023-05-02 10:56:38','序号为300-400的数据出现故障','300-400','[]',1,12,0),(5,1,1,5,'cart0','2023-05-02 10:59:00',NULL,'2023-05-02 10:59:00','序号为100-200的数据出现故障','100-200','[]',1,12,0),(6,1,1,5,'cart1','2023-05-02 10:59:00',NULL,'2023-05-02 10:59:00','序号为300-400的数据出现故障','300-400','[]',1,12,0),(7,1,1,5,'cart0','2023-05-04 13:36:58',NULL,'2023-05-04 13:36:58','序号为:100-200的数据出现故障','100-200','[]',1,12,0),(8,1,1,5,'cart1','2023-05-04 13:36:58',NULL,'2023-05-04 13:36:58','序号为:300-400的数据出现故障','300-400','[]',1,12,0);
/*!40000 ALTER TABLE `anomaly_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-07 14:29:01
