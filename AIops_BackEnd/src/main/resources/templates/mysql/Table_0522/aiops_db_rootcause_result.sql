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
-- Table structure for table `rootcause_result`
--

DROP TABLE IF EXISTS `rootcause_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rootcause_result` (
  `rcr_id` bigint NOT NULL AUTO_INCREMENT,
  `source_data_section` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`rcr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rootcause_result`
--

LOCK TABLES `rootcause_result` WRITE;
/*!40000 ALTER TABLE `rootcause_result` DISABLE KEYS */;
INSERT INTO `rootcause_result` VALUES (1,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',1),(2,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',1),(3,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',0),(4,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',0),(5,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',0),(6,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',0),(7,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',1),(8,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',1),(9,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',1),(10,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',1),(11,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',0),(12,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',0),(13,'10-20','[ts-train-service->ts-travel-service->ts-travel-plan-service, ts-train-service->ts-travel2-service->ts-travel-plan-service]',0),(14,'30-40','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower->nacos, nacos-headless->nacos]',0);
/*!40000 ALTER TABLE `rootcause_result` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-22 17:10:41
