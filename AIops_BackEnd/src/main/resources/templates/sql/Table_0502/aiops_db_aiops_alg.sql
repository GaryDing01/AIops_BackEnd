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
  `name` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `update_tstamp` timestamp NULL DEFAULT NULL,
  `update_num` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `param` json DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`alg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aiops_alg`
--

LOCK TABLES `aiops_alg` WRITE;
/*!40000 ALTER TABLE `aiops_alg` DISABLE KEYS */;
INSERT INTO `aiops_alg` VALUES (1,10,'正则表达式过滤','根据正则表达式替换日志某些变量','多个SOTA的日志异常检测论文常用操作','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]','内容'),(2,1,'Drain','根据固定深度树和预设的规则对日志进行分流，分流至同一组的日志共享一个日志模板。','《Drain: An Online Log Parsing Approach with Fixed Depth Tree》','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(3,1,'METING','提取日志语句中的n-gram进行频繁模式挖掘，通过层次聚类方法划分出多个日志组，每个日志组提取一个日志模板。','《METING: A Robust Log Parser Based on Frequent n-Gram Mining》','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(4,1,'SPINE','使用初始分组+渐进聚类方式提取日志模板，支持并行化解析和轻量级用户反馈。','《SPINE: A Scalable Log Parser with Feedback Guidance》','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(5,2,'GloVe词嵌入模型，TF-IDF聚合','使用GloVe得到词向量，使用TF-IDF对词向量进行聚合得到句向量','《PLELog》有用到','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"\"}]','内容'),(6,2,'bert-as-service','使用bert模型直接得到句向量','BERT','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"log_record\", \"value\": \"\"}]','内容'),(7,2,'日志语言模型','训练：使用日志语料对预训练语言模型进行微调;向量化：使用日志语言模型得到句向量','自研','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(8,3,'DeepLog','DeepLog是一个深层神经网络，使用LSTM对日志序列进行建模，可以从正常执行中自动学习日志模式的模型，并将偏离正常运行的行为标识为异常。','《DeepLog: Anomaly Detection and Diagnosis from System Logs through Deep Learning》','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(9,3,'LogAnomaly','一个将日志流建模为一个自然语言序列的框架，同时提出了template2vec，一种新颖的、简单而有效的语义信息提取方法，可以同时检测序列和定量日志异常。','《LogAnomaly: Unsupervised Detection of Sequential and Quantitative Anomalies in Unstructured Logs》','2023-04-21 10:25:47',1,2053677,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容');
/*!40000 ALTER TABLE `aiops_alg` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-02 20:20:15
