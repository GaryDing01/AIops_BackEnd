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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anomaly_info`
--

LOCK TABLES `anomaly_info` WRITE;
/*!40000 ALTER TABLE `anomaly_info` DISABLE KEYS */;
INSERT INTO `anomaly_info` VALUES (1,2,1,5,'trace0','2023-05-02 09:05:58',NULL,'2023-05-02 09:05:58','批次为1, 相对位次序号为100-200的数据出现故障','1|100|200','[]',1,-1,0),(2,2,1,5,'trace1','2023-05-02 09:05:58',NULL,'2023-05-02 09:05:58','批次为1, 相对位次序号为300-400的数据出现故障','1|300|400','[]',1,-1,0),(3,3,1,5,'metrics0','2023-05-02 10:56:38',NULL,'2023-05-02 10:56:38','批次为1, 相对位次序号为100-200的数据出现故障','1|100|200','[]',1,-1,0),(4,3,1,5,'metrics1','2023-05-02 10:56:38',NULL,'2023-05-02 10:56:38','批次为1, 相对位次序号为300-400的数据出现故障','1|300|400','[]',1,-1,0),(5,2,1,5,'trace2','2023-05-02 10:59:00',NULL,'2023-05-02 10:59:00','批次为1, 相对位次序号为100-200的数据出现故障','1|100|200','[]',1,-1,0),(6,2,1,5,'trace3','2023-05-02 10:59:00',NULL,'2023-05-02 10:59:00','批次为1, 相对位次序号为300-400的数据出现故障','1|300|400','[]',1,-1,0),(7,1,1,5,'cart0','2023-05-04 13:36:58',NULL,'2023-05-04 13:36:58','批次为1, 相对位次序号为:100-200的数据出现故障','1|100|200','[{\"OriginalDataId\": \"100\", \"BatchId\": \"7\", \"RelativeId\": \"100\", \"Content\": \"2021-08-22 15:30:14.839 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,13c6863a950e4143a31ea00547c55798.38.16296174148306830,0]] [http-nio-15680-exec-3] INFO  f.m.service.BasicServiceImpl -[Query For Station Id] stationName: Shang Hai \"}, {\"OriginalDataId\": \"102\", \"BatchId\": \"7\", \"RelativeId\": \"102\", \"Content\": \"2021-08-22 15:30:14.838 [SW_CTX:[ts-price-service,0af42e2356e54e6c9a15577dee466779@10.244.3.93,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,11dd8ba2a2d647ec886f6aa5940fb86c.47.16296174148371774,0]] [http-nio-16579-exec-10] INFO  price.service.PriceServiceImpl -[Find By Route And Train Type] Rote: f3d4d4ef-693b-4456-8eed-59c0d717dd08   Train Type: DongCheOne \"}, {\"OriginalDataId\": \"103\", \"BatchId\": \"7\", \"RelativeId\": \"103\", \"Content\": \"2021-08-22 15:30:14.838 [SW_CTX:[ts-price-service,0af42e2356e54e6c9a15577dee466779@10.244.3.93,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,11dd8ba2a2d647ec886f6aa5940fb86c.47.16296174148371774,0]] [http-nio-16579-exec-10] INFO  price.service.PriceServiceImpl -[Find By Route Id And Train Type] \"}, {\"OriginalDataId\": \"104\", \"BatchId\": \"7\", \"RelativeId\": \"104\", \"Content\": \"2021-08-22 15:30:14.837 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,13c6863a950e4143a31ea00547c55798.38.16296174148306830,0]] [http-nio-15680-exec-3] INFO  f.m.service.BasicServiceImpl -[Query For Price Config] RouteId: f3d4d4ef-693b-4456-8eed-59c0d717dd08 ,TrainType: DongCheOne \"}, {\"OriginalDataId\": \"109\", \"BatchId\": \"7\", \"RelativeId\": \"109\", \"Content\": \"2021-08-22 15:30:14.832 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,13c6863a950e4143a31ea00547c55798.38.16296174148306830,0]] [http-nio-15680-exec-3] INFO  f.m.service.BasicServiceImpl -[Query Train Type] Train Type: DongCheOne \"}]',1,12,0),(8,1,1,5,'cart1','2023-05-04 13:36:58',NULL,'2023-05-04 13:36:58','批次为1, 相对位次序号为:300-400的数据出现故障','1|300|400','[{\"OriginalDataId\": \"308\", \"BatchId\": \"7\", \"RelativeId\": \"308\", \"Content\": \"2021-08-22 15:30:19.913 [SW_CTX:[ts-seat-service,737a26438fd04956a342fb004253de14@10.244.2.49,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,671f72cd141d4ec282639796030d0d11.40.16296174199132592,0]] [http-nio-18898-exec-5] INFO  seat.controller.SeatController -Get left ticket of interval,TravelDate: Sun Aug 22 08:00:00 CST 2021,TrainNumber: D1345,SeatType: 3 \"}, {\"OriginalDataId\": \"312\", \"BatchId\": \"7\", \"RelativeId\": \"312\", \"Content\": \"2021-08-22 15:30:19.910 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,13c6863a950e4143a31ea00547c55798.37.16296174199096852,0]] [http-nio-15680-exec-2] INFO  f.m.service.BasicServiceImpl -[Query For Station Id] stationName: Su Zhou \"}, {\"OriginalDataId\": \"314\", \"BatchId\": \"7\", \"RelativeId\": \"314\", \"Content\": \"2021-08-22 15:30:19.909 [SW_CTX:[ts-travel-service,066297fa39da4e1ead92390d7095dd4f@10.244.1.35,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,54e0659c0b7f4d8bae79e430c7f65286.43.16296174198516038,0]] [http-nio-12346-exec-7] INFO  travel.service.TravelServiceImpl -Query for Station id is: Response(status=1, msg=Success, data=shanghai) \"}, {\"OriginalDataId\": \"322\", \"BatchId\": \"7\", \"RelativeId\": \"322\", \"Content\": \"2021-08-22 15:30:19.904 [SW_CTX:[ts-config-service,0a7bd4e652e9430baceaa42568821434@10.244.1.27,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,961e662b86db447db3b6ff2ba16119f6.45.16296174199031654,0]] [http-nio-15679-exec-9] INFO  config.controller.ConfigController -Retrieve config: DirectTicketAllocationProportion \"}, {\"OriginalDataId\": \"325\", \"BatchId\": \"7\", \"RelativeId\": \"325\", \"Content\": \"2021-08-22 15:30:19.902 [SW_CTX:[ts-seat-service,737a26438fd04956a342fb004253de14@10.244.2.49,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,671f72cd141d4ec282639796030d0d11.44.16296174198882616,0]] [http-nio-18898-exec-9] INFO  seat.service.SeatServiceImpl -[getLeftTicketOfInterval] The result of getTrainTypeResult is Response(status=1, msg=Success, data=TrainType(id=DongCheOne, economyClass=2147483647, confortClass=2147483647, averageSpeed=180)) \"}]',1,12,0),(9,1,1,5,'cart0','2023-05-09 13:14:54',NULL,'2023-05-09 13:14:54','批次为8, 相对位次序号为100-200的数据出现故障','8|100|200','[{\"OriginalDataId\": \"100\", \"BatchId\": \"7\", \"RelativeId\": \"100\", \"Content\": \"2021-08-22 15:30:14.839 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,13c6863a950e4143a31ea00547c55798.38.16296174148306830,0]] [http-nio-15680-exec-3] INFO  f.m.service.BasicServiceImpl -[Query For Station Id] stationName: Shang Hai \"}, {\"OriginalDataId\": \"102\", \"BatchId\": \"7\", \"RelativeId\": \"102\", \"Content\": \"2021-08-22 15:30:14.838 [SW_CTX:[ts-price-service,0af42e2356e54e6c9a15577dee466779@10.244.3.93,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,11dd8ba2a2d647ec886f6aa5940fb86c.47.16296174148371774,0]] [http-nio-16579-exec-10] INFO  price.service.PriceServiceImpl -[Find By Route And Train Type] Rote: f3d4d4ef-693b-4456-8eed-59c0d717dd08   Train Type: DongCheOne \"}, {\"OriginalDataId\": \"103\", \"BatchId\": \"7\", \"RelativeId\": \"103\", \"Content\": \"2021-08-22 15:30:14.838 [SW_CTX:[ts-price-service,0af42e2356e54e6c9a15577dee466779@10.244.3.93,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,11dd8ba2a2d647ec886f6aa5940fb86c.47.16296174148371774,0]] [http-nio-16579-exec-10] INFO  price.service.PriceServiceImpl -[Find By Route Id And Train Type] \"}, {\"OriginalDataId\": \"104\", \"BatchId\": \"7\", \"RelativeId\": \"104\", \"Content\": \"2021-08-22 15:30:14.837 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,13c6863a950e4143a31ea00547c55798.38.16296174148306830,0]] [http-nio-15680-exec-3] INFO  f.m.service.BasicServiceImpl -[Query For Price Config] RouteId: f3d4d4ef-693b-4456-8eed-59c0d717dd08 ,TrainType: DongCheOne \"}, {\"OriginalDataId\": \"109\", \"BatchId\": \"7\", \"RelativeId\": \"109\", \"Content\": \"2021-08-22 15:30:14.832 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.37.16296174148000001,13c6863a950e4143a31ea00547c55798.38.16296174148306830,0]] [http-nio-15680-exec-3] INFO  f.m.service.BasicServiceImpl -[Query Train Type] Train Type: DongCheOne \"}]',1,15,0),(10,1,1,5,'cart1','2023-05-09 13:14:55',NULL,'2023-05-09 13:14:55','批次为8, 相对位次序号为300-400的数据出现故障','8|300|400','[{\"OriginalDataId\": \"308\", \"BatchId\": \"7\", \"RelativeId\": \"308\", \"Content\": \"2021-08-22 15:30:19.913 [SW_CTX:[ts-seat-service,737a26438fd04956a342fb004253de14@10.244.2.49,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,671f72cd141d4ec282639796030d0d11.40.16296174199132592,0]] [http-nio-18898-exec-5] INFO  seat.controller.SeatController -Get left ticket of interval,TravelDate: Sun Aug 22 08:00:00 CST 2021,TrainNumber: D1345,SeatType: 3 \"}, {\"OriginalDataId\": \"312\", \"BatchId\": \"7\", \"RelativeId\": \"312\", \"Content\": \"2021-08-22 15:30:19.910 [SW_CTX:[ts-basic-service,8acf0a52701e41c2aca24fb8cde0782f@10.244.2.42,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,13c6863a950e4143a31ea00547c55798.37.16296174199096852,0]] [http-nio-15680-exec-2] INFO  f.m.service.BasicServiceImpl -[Query For Station Id] stationName: Su Zhou \"}, {\"OriginalDataId\": \"314\", \"BatchId\": \"7\", \"RelativeId\": \"314\", \"Content\": \"2021-08-22 15:30:19.909 [SW_CTX:[ts-travel-service,066297fa39da4e1ead92390d7095dd4f@10.244.1.35,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,54e0659c0b7f4d8bae79e430c7f65286.43.16296174198516038,0]] [http-nio-12346-exec-7] INFO  travel.service.TravelServiceImpl -Query for Station id is: Response(status=1, msg=Success, data=shanghai) \"}, {\"OriginalDataId\": \"322\", \"BatchId\": \"7\", \"RelativeId\": \"322\", \"Content\": \"2021-08-22 15:30:19.904 [SW_CTX:[ts-config-service,0a7bd4e652e9430baceaa42568821434@10.244.1.27,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,961e662b86db447db3b6ff2ba16119f6.45.16296174199031654,0]] [http-nio-15679-exec-9] INFO  config.controller.ConfigController -Retrieve config: DirectTicketAllocationProportion \"}, {\"OriginalDataId\": \"325\", \"BatchId\": \"7\", \"RelativeId\": \"325\", \"Content\": \"2021-08-22 15:30:19.902 [SW_CTX:[ts-seat-service,737a26438fd04956a342fb004253de14@10.244.2.49,5a770bbebafb4a7f801a746f7d7935d7.45.16296174198370007,671f72cd141d4ec282639796030d0d11.44.16296174198882616,0]] [http-nio-18898-exec-9] INFO  seat.service.SeatServiceImpl -[getLeftTicketOfInterval] The result of getTrainTypeResult is Response(status=1, msg=Success, data=TrainType(id=DongCheOne, economyClass=2147483647, confortClass=2147483647, averageSpeed=180)) \"}]',1,15,0);
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

-- Dump completed on 2023-05-09 21:19:16
