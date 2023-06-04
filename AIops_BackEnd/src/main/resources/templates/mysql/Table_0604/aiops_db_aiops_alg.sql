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
  `file_path` text,
  `update_tstamp` timestamp NULL DEFAULT NULL,
  `update_num` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `param` text,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`alg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aiops_alg`
--

LOCK TABLES `aiops_alg` WRITE;
/*!40000 ALTER TABLE `aiops_alg` DISABLE KEYS */;
INSERT INTO `aiops_alg` VALUES (1,10,'正则表达式过滤','根据正则表达式替换日志某些变量','多个SOTA的日志异常检测论文常用操作','No file path','2023-04-21 10:25:47',1,1,'[{\"name\": \"regex\", \"value\": \"[a-z]\"}, {\"name\": \"log_file\", \"value\": \"test_file\"}]','内容'),(2,1,'Drain','根据固定深度树和预设的规则对日志进行分流，分流至同一组的日志共享一个日志模板。','《Drain: An Online Log Parsing Approach with Fixed Depth Tree》','No file path','2023-04-21 10:25:47',1,2,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(3,1,'METING','提取日志语句中的n-gram进行频繁模式挖掘，通过层次聚类方法划分出多个日志组，每个日志组提取一个日志模板。','《METING: A Robust Log Parser Based on Frequent n-Gram Mining》','No file path','2023-04-21 10:25:47',1,1,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(4,1,'SPINE','使用初始分组+渐进聚类方式提取日志模板，支持并行化解析和轻量级用户反馈。','《SPINE: A Scalable Log Parser with Feedback Guidance》','No file path','2023-04-21 10:25:47',1,2,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(5,2,'GloVe词嵌入模型，TF-IDF聚合','使用GloVe得到词向量，使用TF-IDF对词向量进行聚合得到句向量','《PLELog》有用到','No file path','2023-04-21 10:25:47',1,1,'[{\"name\": \"input_dir\", \"value\": \"\"}]','内容'),(6,2,'bert-as-service','使用bert模型直接得到句向量','BERT','No file path','2023-04-21 10:25:47',1,2,'[{\"name\": \"log_record\", \"value\": \"\"}]','内容'),(7,2,'日志语言模型','训练：使用日志语料对预训练语言模型进行微调;向量化：使用日志语言模型得到句向量','自研','No file path','2023-04-21 10:25:47',1,1,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(8,3,'DeepLog','DeepLog是一个深层神经网络，使用LSTM对日志序列进行建模，可以从正常执行中自动学习日志模式的模型，并将偏离正常运行的行为标识为异常。','《DeepLog: Anomaly Detection and Diagnosis from System Logs through Deep Learning》','No file path','2023-04-21 10:25:47',1,2,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(9,3,'LogAnomaly','一个将日志流建模为一个自然语言序列的框架，同时提出了template2vec，一种新颖的、简单而有效的语义信息提取方法，可以同时检测序列和定量日志异常。','《LogAnomaly: Unsupervised Detection of Sequential and Quantitative Anomalies in Unstructured Logs》','No file path','2023-04-21 10:25:47',1,1,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"output_dir\", \"value\": \"/outputdir\"}, {\"name\": \"log_file\", \"value\": \"file\"}, {\"name\": \"log_format\", \"value\": \"format\"}, {\"name\": \"st\", \"value\": \"0.8\"}, {\"name\": \"depth\", \"value\": \"4\"}, {\"name\": \"regex\", \"value\": \"[1-9]\"}]','内容'),(13,9,'Eadro','基于Metric、Trace、Log的故障定位（服务级）方法，通过输入的Trace、Metric、Log数据提取特征输入图神经网络，判定微服务系统是否存在异常（0/1）、故障根因所在的微服务及概率','《Eadro: An End-to-End Troubleshooting Framework for Microservices on Multi-source Data》','No file path','2023-05-22 09:01:18',0,1,'[{\"name\": \"gpu\", \"value\": \"True\"}, {\"name\": \"epoches\", \"value\": \"50\"}, {\"name\": \"batch_size\", \"value\": \"256\"}, {\"name\": \"lr\", \"value\": \"0.001\"}, {\"name\": \"patience\", \"value\": \"10\"}, {\"name\": \"self-attn\", \"value\": \"True\"}, {\"name\": \"fuse_dim\", \"value\": \"128\"}, {\"name\": \"alpha\", \"value\": \"0.5\"}, {\"name\": \"locate_hiddens\", \"value\": \"[64]\"}, {\"name\": \"detect_hiddens\", \"value\": \"[64]\"}, {\"name\": \"log_dim\", \"value\": \"16\"}, {\"name\": \"trace_kernel_sizes\", \"value\": \"[2]\"}, {\"name\": \"trace_hiddens\", \"value\": \"[64]\"}, {\"name\": \"metric_kernel_sizes\", \"value\": \"[2]\"}, {\"name\": \"metric_hiddens\", \"value\": \"[64]\"}, {\"name\": \"graph_hiddens\", \"value\": \"[64]\"}, {\"name\": \"attn_head\", \"value\": \"4\"}, {\"name\": \"activation\", \"value\": \"0.2\"}, {\"name\": \"data\", \"value\": \"True\"}, {\"name\": \"result_dir\", \"value\": \"/resultdir\"}]','内容'),(14,9,'CMDDiagnostor','基于call metric数据的无监督根因服务定位算法，输入为call metric数据，call metric data为介乎与metric与trace之间的数据形态，类似于服务网格收集的成对数据，相较于metric多了一些结构信息，相较于trace进行了极大的压缩，减少了1000～10000量级的传输成本和存储负担。输出为（故障根因所在的服务，对应分数）的集合。','《CMDiagnostor: An Ambiguity-Aware Root Cause Localization Approach Based on Call Metric Data》','No file path','2023-05-22 09:03:45',0,1,'[{\"name\": \"batch_size\", \"value\": \"256\"}, {\"name\": \"lr\", \"value\": \"0.001\"}, {\"name\": \"data\", \"value\": \"True\"}, {\"name\": \"result_dir\", \"value\": \"/resultdir\"}]','内容'),(15,9,'genKG','根据异常/故障定位、根因分析等步骤生成对应的知识图谱。','自研','No file path','2023-05-22 09:08:04',0,1,'[{\"name\": \"input_dir\", \"value\": \"/inputdir\"}, {\"name\": \"result_dir\", \"value\": \"/resultdir\"}]','内容');
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

-- Dump completed on 2023-06-04 11:24:53
