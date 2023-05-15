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
-- Table structure for table `aiops_alg`
--

DROP TABLE IF EXISTS `aiops_alg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aiops_alg` (
  `alg_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `intro` varchar(100) DEFAULT NULL,
  `source` varchar(45) DEFAULT NULL,
  `update_tstamp` timestamp NULL DEFAULT NULL,
  `update_num` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `param` json DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`alg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aiops_alg`
--

LOCK TABLES `aiops_alg` WRITE;
/*!40000 ALTER TABLE `aiops_alg` DISABLE KEYS */;
/*!40000 ALTER TABLE `aiops_alg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aiops_obj_enum`
--

DROP TABLE IF EXISTS `aiops_obj_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aiops_obj_enum` (
  `obj_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aiops_obj_enum`
--

LOCK TABLES `aiops_obj_enum` WRITE;
/*!40000 ALTER TABLE `aiops_obj_enum` DISABLE KEYS */;
INSERT INTO `aiops_obj_enum` VALUES (1,'log'),(2,'trace'),(3,'metrics');
/*!40000 ALTER TABLE `aiops_obj_enum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alg_type_enum`
--

DROP TABLE IF EXISTS `alg_type_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alg_type_enum` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alg_type_enum`
--

LOCK TABLES `alg_type_enum` WRITE;
/*!40000 ALTER TABLE `alg_type_enum` DISABLE KEYS */;
INSERT INTO `alg_type_enum` VALUES (1,'日志的解析算法'),(2,'日志的语义向量化算法'),(3,'日志的异常检测算法'),(4,'传统的时间序列分析算法'),(5,'传统的时间序列异常检测/预测算法'),(6,'时间序列聚类算法'),(7,'场景识别算法'),(8,'流量模拟算法'),(9,'故障定位和根因分析算法'),(10,'日志的清洗算法');
/*!40000 ALTER TABLE `alg_type_enum` ENABLE KEYS */;
UNLOCK TABLES;

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
  `detect_tstamp` timestamp NULL DEFAULT NULL,
  `predict_tstamp` timestamp NULL DEFAULT NULL,
  `update_tstamp` timestamp NULL DEFAULT NULL,
  `source_data_ids` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  `wf_id` int NOT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`ano_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anomaly_info`
--

LOCK TABLES `anomaly_info` WRITE;
/*!40000 ALTER TABLE `anomaly_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `anomaly_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anomaly_status_enum`
--

DROP TABLE IF EXISTS `anomaly_status_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anomaly_status_enum` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anomaly_status_enum`
--

LOCK TABLES `anomaly_status_enum` WRITE;
/*!40000 ALTER TABLE `anomaly_status_enum` DISABLE KEYS */;
INSERT INTO `anomaly_status_enum` VALUES (1,'未确认'),(2,'未解决'),(3,'正在解决'),(4,'已解决'),(5,'未再现');
/*!40000 ALTER TABLE `anomaly_status_enum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_introducing`
--

DROP TABLE IF EXISTS `data_introducing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_introducing` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `tstamp` timestamp NOT NULL,
  `source` varchar(45) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `obj_id` int NOT NULL,
  `data_sample` text,
  `user_id` int NOT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_introducing`
--

LOCK TABLES `data_introducing` WRITE;
/*!40000 ALTER TABLE `data_introducing` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_introducing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exec_data_type_enum`
--

DROP TABLE IF EXISTS `exec_data_type_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exec_data_type_enum` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exec_data_type_enum`
--

LOCK TABLES `exec_data_type_enum` WRITE;
/*!40000 ALTER TABLE `exec_data_type_enum` DISABLE KEYS */;
INSERT INTO `exec_data_type_enum` VALUES (1,'源日志'),(2,'清洗后日志'),(3,'结构化日志'),(4,'向量化日志'),(5,'异常检测结果'),(6,'根因分析结果');
/*!40000 ALTER TABLE `exec_data_type_enum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parsed_log`
--

DROP TABLE IF EXISTS `parsed_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parsed_log` (
  `parse_id` int NOT NULL AUTO_INCREMENT,
  `tstamp` timestamp NOT NULL,
  `log_lineid` varchar(100) DEFAULT NULL,
  `log_date` varchar(45) DEFAULT NULL,
  `log_timestamp` varchar(45) DEFAULT NULL,
  `log_traceid` varchar(100) DEFAULT NULL,
  `log_spanid` varchar(100) DEFAULT NULL,
  `log_unknown` text,
  `log_level` varchar(45) DEFAULT NULL,
  `log_content` text,
  `log_eventid` varchar(100) DEFAULT NULL,
  `log_eventtemplate` text,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`parse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parsed_log`
--

LOCK TABLES `parsed_log` WRITE;
/*!40000 ALTER TABLE `parsed_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `parsed_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `permit_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(100) NOT NULL,
  PRIMARY KEY (`permit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'个人信息管理'),(2,'用户管理'),(3,'算法管理'),(4,'流程管理'),(5,'故障信息管理'),(6,'故障统计与分析'),(7,'数据源管理'),(8,'知识图谱管理');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationship_enum`
--

DROP TABLE IF EXISTS `relationship_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relationship_enum` (
  `relation_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationship_enum`
--

LOCK TABLES `relationship_enum` WRITE;
/*!40000 ALTER TABLE `relationship_enum` DISABLE KEYS */;
INSERT INTO `relationship_enum` VALUES (1,'contains'),(2,'logical_abstracts'),(3,'runs_in'),(4,'invokes'),(5,'correlates');
/*!40000 ALTER TABLE `relationship_enum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `content` json NOT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `step_config`
--

DROP TABLE IF EXISTS `step_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step_config` (
  `step_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `step_num` int NOT NULL,
  `param` json DEFAULT NULL,
  `alg_id` int NOT NULL,
  `wf_id` varchar(45) NOT NULL,
  PRIMARY KEY (`step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step_config`
--

LOCK TABLES `step_config` WRITE;
/*!40000 ALTER TABLE `step_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `step_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `step_type_enum`
--

DROP TABLE IF EXISTS `step_type_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step_type_enum` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step_type_enum`
--

LOCK TABLES `step_type_enum` WRITE;
/*!40000 ALTER TABLE `step_type_enum` DISABLE KEYS */;
INSERT INTO `step_type_enum` VALUES (1,'日志清洗'),(2,'日志解析'),(3,'日志向量化'),(4,'日志异常检测'),(5,'根因分析');
/*!40000 ALTER TABLE `step_type_enum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unitnode_info`
--

DROP TABLE IF EXISTS `unitnode_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unitnode_info` (
  `unit_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `tstamp` timestamp NULL DEFAULT NULL,
  `content` json DEFAULT NULL,
  PRIMARY KEY (`unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unitnode_info`
--

LOCK TABLES `unitnode_info` WRITE;
/*!40000 ALTER TABLE `unitnode_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `unitnode_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unitnode_type_enum`
--

DROP TABLE IF EXISTS `unitnode_type_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unitnode_type_enum` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unitnode_type_enum`
--

LOCK TABLES `unitnode_type_enum` WRITE;
/*!40000 ALTER TABLE `unitnode_type_enum` DISABLE KEYS */;
INSERT INTO `unitnode_type_enum` VALUES (1,'System'),(2,'Node'),(3,'Pod'),(4,'Container'),(5,'Service'),(6,'Class');
/*!40000 ALTER TABLE `unitnode_type_enum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `permit_ids` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'管理员','张诚','ZhangCheng','1|1|1|1|1|1|1|1'),(2,'算法执行人员','王刚','WangGang','1|0|1|1|0|0|0|0'),(3,'故障解决状态人员','汪淼','WangMiao','1|0|0|0|1|0|0|0'),(4,'统计分析人员','程心','ChengXin','1|0|0|0|0|1|0|0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vectorized_log`
--

DROP TABLE IF EXISTS `vectorized_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vectorized_log` (
  `vector_id` int NOT NULL AUTO_INCREMENT,
  `embedding` text NOT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`vector_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vectorized_log`
--

LOCK TABLES `vectorized_log` WRITE;
/*!40000 ALTER TABLE `vectorized_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `vectorized_log` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_config`
--

LOCK TABLES `workflow_config` WRITE;
/*!40000 ALTER TABLE `workflow_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow_config` ENABLE KEYS */;
UNLOCK TABLES;

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
  `input_id` int NOT NULL,
  `output_id` int NOT NULL,
  PRIMARY KEY (`exec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_exec`
--

LOCK TABLES `workflow_exec` WRITE;
/*!40000 ALTER TABLE `workflow_exec` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow_exec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_status_enum`
--

DROP TABLE IF EXISTS `workflow_status_enum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_status_enum` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_status_enum`
--

LOCK TABLES `workflow_status_enum` WRITE;
/*!40000 ALTER TABLE `workflow_status_enum` DISABLE KEYS */;
INSERT INTO `workflow_status_enum` VALUES (1,'未执行'),(2,'执行中'),(3,'已完成');
/*!40000 ALTER TABLE `workflow_status_enum` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-12 22:31:33
