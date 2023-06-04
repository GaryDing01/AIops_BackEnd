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
-- Table structure for table `workflow_config`
--

DROP TABLE IF EXISTS `workflow_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_config` (
  `wf_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `status_id` int NOT NULL,
  `current_step` int NOT NULL,
  `template` int NOT NULL,
  `report_ids` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`wf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_config`
--

LOCK TABLES `workflow_config` WRITE;
/*!40000 ALTER TABLE `workflow_config` DISABLE KEYS */;
INSERT INTO `workflow_config` VALUES (1,'流程1',1,-1,0,'',1),(2,'流程2',1,-1,0,'',2),(6,'流程2',1,-1,0,'',2),(7,'流程2',1,-1,0,'',1),(8,'流程2',2,1,0,'',1),(10,'模板1',3,-1,1,'',1),(12,'流程1',3,-1,0,'[1, 3, 5, 9, 10, 11]',1),(13,'模板13',3,-1,1,'',2),(14,'流程14',1,-1,0,'',2),(15,'流程15',3,-1,0,'[13, 14, 15, 17, 18, 19]',1),(16,'模板2',3,-1,1,'',1),(17,'流程15',2,6,0,'',1),(18,'新增流程',3,-1,0,'[44, 45, 46, 47]',1),(19,'流程_20230524',3,-1,0,'[48, 49]',1),(20,'流程_20230524_1',3,-1,0,'[51, 52]',1),(21,'流程_20230524_2',2,2,0,NULL,1),(22,'新增流程',3,-1,0,'[55, 57]',1),(23,'模板3',3,-1,1,'',1),(24,'新增流程',1,-1,0,NULL,1),(25,'新增流程',1,-1,0,NULL,1),(26,'流程26',3,-1,0,'[58, 59, 60, 61, 62, 63]',1),(27,'流程27',3,-1,0,'[64, 66]',1),(28,'流程_20230602_1',3,-1,0,'[68, 69]',1),(29,'流程29',3,-1,0,'[72, 73]',1),(30,'流程30',3,-1,0,'[74, 75]',1),(31,'流程31',3,-1,0,'[78]',1),(32,'流程32',3,-1,0,'[83, 84]',1),(33,'流程33',3,-1,0,'[110, 111]',1),(34,'流程_20230602_2',3,-1,0,'[116, 117]',1),(35,'新增流程',3,-1,0,'[118, 119]',1),(36,'流程_20230603_1',3,-1,0,'[120, 121]',1),(37,'流程_20230603_2',3,-1,0,'[122, 123]',1),(38,'流程38',3,-1,0,'[124, 125, 126, 127, 128, 129]',1),(39,'流程39',1,-1,0,'',1),(40,'流程40',3,-1,0,'[143, 144]',1),(41,'流程41',3,-1,0,'[145, 146]',1),(42,'流程42',3,-1,0,'[147, 148]',1),(43,'流程43',3,-1,0,'[149, 150]',1),(44,'流程44',3,-1,0,'[151, 152]',1),(45,'流程45',3,-1,0,'[153, 154]',1),(46,'流程46',3,-1,0,'[155, 156]',1),(47,'流程47',3,-1,0,'[157, 158, 159, 160, 161]',1),(48,'模板4',3,-1,1,'',1),(49,'新增流程',1,-1,0,NULL,1),(50,'流程50',3,-1,0,'[162, 163]',1),(51,'流程51',3,-1,0,'[164, 165]',1),(52,'流程52',3,-1,0,'[166, 167]',1),(53,'流程53',3,-1,0,'[168, 169]',1),(54,'流程54',3,-1,0,'[170, 171]',1),(55,'流程55',3,-1,0,'[172, 173]',1),(58,'流程58',3,-1,0,'[174, 175]',1);
/*!40000 ALTER TABLE `workflow_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-04 11:24:49
