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
-- Table structure for table `workflow_exec`
--

DROP TABLE IF EXISTS `workflow_exec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_exec` (
  `exec_id` int NOT NULL AUTO_INCREMENT,
  `tstamp` timestamp NOT NULL,
  `report_id` int NOT NULL,
  `step_id` int NOT NULL,
  `input_type_id` int NOT NULL,
  `output_type_id` int NOT NULL,
  `input_id` varchar(255) NOT NULL,
  `output_id` varchar(255) NOT NULL,
  PRIMARY KEY (`exec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_exec`
--

LOCK TABLES `workflow_exec` WRITE;
/*!40000 ALTER TABLE `workflow_exec` DISABLE KEYS */;
INSERT INTO `workflow_exec` VALUES (7,'2023-05-02 06:10:53',1,16,1,1,'1|1|100','1|1|100'),(9,'2023-05-02 07:35:36',3,17,1,3,'1|1|100','201|300'),(12,'2023-05-02 07:49:44',5,28,3,4,'201|300','1|101'),(16,'2023-05-04 13:36:57',9,29,4,5,'1|101','7|8'),(17,'2023-05-06 17:25:12',10,30,5,6,'7|8','3|4'),(18,'2023-05-08 09:47:30',11,31,6,7,'3|4','1|2'),(20,'2023-05-09 13:04:51',13,32,1,1,'8|1|100','8|1|100'),(21,'2023-05-09 13:09:01',14,33,1,3,'8|1|100','201|300'),(22,'2023-05-09 13:09:23',15,34,3,4,'201|300','1|101'),(24,'2023-05-09 13:14:54',17,35,4,5,'1|101','11|12'),(25,'2023-05-09 13:16:10',18,36,5,6,'11|12','5|6'),(26,'2023-05-09 13:16:44',19,37,6,7,'5|6','3|4');
/*!40000 ALTER TABLE `workflow_exec` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-09 21:19:14
