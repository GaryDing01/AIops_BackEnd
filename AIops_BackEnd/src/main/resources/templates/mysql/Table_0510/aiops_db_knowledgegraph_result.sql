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
-- Table structure for table `knowledgegraph_result`
--

DROP TABLE IF EXISTS `knowledgegraph_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledgegraph_result` (
  `kgr_id` bigint NOT NULL AUTO_INCREMENT,
  `source_data_section` varchar(45) NOT NULL,
  `all_node_ids` varchar(255) NOT NULL,
  `all_relation_ids` varchar(255) NOT NULL,
  `rootcause_node_names` varchar(255) DEFAULT NULL,
  `rootcause_node_ids` varchar(255) NOT NULL,
  `rootcause_relation_ids` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`kgr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledgegraph_result`
--

LOCK TABLES `knowledgegraph_result` WRITE;
/*!40000 ALTER TABLE `knowledgegraph_result` DISABLE KEYS */;
INSERT INTO `knowledgegraph_result` VALUES (1,'100-200','352|74|75|78|80|81|23|25|345|346|29|349|31|351','360|359|357|354|353|89|88|86|83|82|20|24|26|410|411|414|416|417|473|493|474|491|477|492|479|480','[ts-train-service->ts-travel-service->ts-travel2-service, ts-station-service->ts-station-food-service]','[349|351|352, 346|345]','[491|492, 493]',0),(2,'300-400','32|36|37|38|40|41|307|308|309|23|311|24|312|26|27','320|319|317|316|315|0|21|22|27|43|44|45|47|48|371|372|373|375|376|494|434|433|432|436|435|495|437|496|439|440','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower, skywalking->skywalking-ui]','[307|309|308, 311|312]','[494|495, 496]',0),(3,'100-200','352|74|75|78|80|81|23|25|345|346|29|349|31|351','360|359|357|354|353|89|88|86|83|82|20|24|26|410|411|414|416|417|473|493|474|491|477|492|479|480','[ts-train-service->ts-travel-service->ts-travel2-service, ts-station-service->ts-station-food-service]','[349|351|352, 346|345]','[491|492, 493]',1),(4,'300-400','32|36|37|38|40|41|307|308|309|23|311|24|312|26|27','320|319|317|316|315|0|21|22|27|43|44|45|47|48|371|372|373|375|376|494|434|433|432|436|435|495|437|496|439|440','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower, skywalking->skywalking-ui]','[307|309|308, 311|312]','[494|495, 496]',1),(5,'100-200','352|74|75|78|80|81|23|25|345|346|29|349|31|351','360|359|357|354|353|89|88|86|83|82|20|24|26|410|411|414|416|417|473|493|474|491|477|492|479|480','[ts-train-service->ts-travel-service->ts-travel2-service, ts-station-service->ts-station-food-service]','[349|351|352, 346|345]','[491|492, 493]',0),(6,'300-400','32|36|37|38|40|41|307|308|309|23|311|24|312|26|27','320|319|317|316|315|0|21|22|27|43|44|45|47|48|371|372|373|375|376|494|434|433|432|436|435|495|437|496|439|440','[nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower, skywalking->skywalking-ui]','[307|309|308, 311|312]','[494|495, 496]',0);
/*!40000 ALTER TABLE `knowledgegraph_result` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-10 18:36:33
