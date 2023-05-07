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
-- Table structure for table `data_introducing`
--

DROP TABLE IF EXISTS `data_introducing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_introducing` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `tstamp` timestamp NOT NULL,
  `source` varchar(45) DEFAULT NULL,
  `file_path` text,
  `intro` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `obj_id` int NOT NULL,
  `data_num` bigint DEFAULT NULL,
  `data_sample` text,
  `user_id` int NOT NULL,
  `place` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_introducing`
--

LOCK TABLES `data_introducing` WRITE;
/*!40000 ALTER TABLE `data_introducing` DISABLE KEYS */;
INSERT INTO `data_introducing` VALUES (1,'2023-04-26 08:01:50','实验室','E:\\F0101raw_log2021-08-14_10-22-51.log','enim id nostrud mollit','源数据1',1,10000,'[OriginalData(batchId=1, content=2021-08-14 03:45:27.114 [SW_CTX:[ts-order-service,8dda43e5f738415aad44cde5dde47c4a@10.244.1.164,a8ac2bc13e5c423d83743ee2d661482b.40.16288839234110001,411dca6aef3349cbaba94b8c741ee0ff.40.16288839271130966,0]] [http-nio-12031-exec-3] INFO  order.controller.OrderController -[Get Order By Id] Order Id: 518f507e-3a0d-437c-bdf5-b6730fa114c9 , objId=1, calcId=5, relaId=5, deleted=0, _class=null), OriginalData(batchId=1, content=2021-08-14 03:45:27.413 [SW_CTX:[ts-cancel-service,884b6aeda0874c3e8bf2e0f368527e6a@10.244.1.168,a8ac2bc13e5c423d83743ee2d661482b.40.16288839234110001,a8ac2bc13e5c423d83743ee2d661482b.55.16288839274070000,0]] [MyExecutor-2] INFO  cancel.async.AsyncTask -[Draw Back Money] Draw back money... , objId=1, calcId=2, relaId=2, deleted=0, _class=null), OriginalData(batchId=1, content=2021-08-14 03:45:27.412 [SW_CTX:[ts-cancel-service,884b6aeda0874c3e8bf2e0f368527e6a@10.244.1.168,a8ac2bc13e5c423d83743ee2d661482b.40.16288839234110001,a8ac2bc13e5c423d83743ee2d661482b.54.16288839274050000,0]] [MyExecutor-1] ERROR cancel.async.AsyncTask -[Cancel Order Service] Delay Process，Wrong Cancel Process , objId=1, calcId=3, relaId=3, deleted=0, _class=null), OriginalData(batchId=1, content=2021-08-14 03:45:27.309 [SW_CTX:[ts-cancel-service,884b6aeda0874c3e8bf2e0f368527e6a@10.244.1.168,a8ac2bc13e5c423d83743ee2d661482b.40.16288839234110001,a8ac2bc13e5c423d83743ee2d661482b.40.16288839234110000,0]] [http-nio-18885-exec-5] INFO  cancel.service.CancelServiceImpl -[Cancel Order] Order found G|H , objId=1, calcId=4, relaId=4, deleted=0, _class=null), OriginalData(batchId=1, content=2021-08-14 03:45:27.613 [SW_CTX:[ts-inside-payment-service,882525b0797d402abad01f319ebea084@10.244.2.153,a8ac2bc13e5c423d83743ee2d661482b.40.16288839234110001,e011366013074feda29701dc14e41305.40.16288839276120072,0]] [http-nio-18673-exec-3] INFO  i.controller.InsidePaymentController -draw back payment, userId: 4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f, money: 0 , objId=1, calcId=1, relaId=1, deleted=0, _class=null)]',1,'OriginalDataLake'),(2,'2023-04-26 08:18:09','企业','E:\\normal2_2021-08-22_22-05-06.log','minim irure','源数据2',1,10000,'[]',1,'OriginalDataLake');
/*!40000 ALTER TABLE `data_introducing` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-07 14:28:58
