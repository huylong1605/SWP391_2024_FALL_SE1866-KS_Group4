-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: kindergartenmanagementsystem
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `activity_registrations`
--

DROP TABLE IF EXISTS `activity_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_registrations` (
  `registration_id` int NOT NULL AUTO_INCREMENT,
  `activity_id` int NOT NULL,
  `student_id` int NOT NULL,
  `registration_date` date NOT NULL,
  `status` varchar(10) NOT NULL,
  `parent_approval` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`registration_id`),
  KEY `activity_id` (`activity_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `activity_registrations_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `extracurricular_activities` (`activity_id`),
  CONSTRAINT `activity_registrations_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_ID`),
  CONSTRAINT `activity_registrations_chk_1` CHECK ((`status` in (_utf8mb4'Registered',_utf8mb4'Attended',_utf8mb4'Cancelled')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_registrations`
--

LOCK TABLES `activity_registrations` WRITE;
/*!40000 ALTER TABLE `activity_registrations` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_registrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `application_ID` int NOT NULL AUTO_INCREMENT,
  `application_content` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `date_create` date DEFAULT NULL,
  `application_response` varchar(255) DEFAULT NULL,
  `date_response` datetime DEFAULT NULL,
  `status` enum('Approved','Pending','Rejected') NOT NULL DEFAULT 'Pending',
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`application_ID`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'Request for leave',1,'2024-01-01','Approved','2024-01-02 00:00:00','Pending',''),(2,'Request for schedule change',2,'2024-01-03','Pending',NULL,'Pending',''),(3,'Request for extra class',3,'2024-01-05','Denied','2024-01-06 00:00:00','Pending',''),(4,'Request for new materials',4,'2024-01-07','Approved','2024-01-08 00:00:00','Pending',''),(5,'Request for parent-teacher meeting',5,'2024-01-09','Approved','2024-01-10 00:00:00','Pending',''),(8,'absence',36,'2024-11-07',NULL,NULL,'Pending','Leave of Absence Form');
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `attendence_id` int NOT NULL AUTO_INCREMENT,
  `attend_status` bit(1) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `scheduledend_time` int DEFAULT NULL,
  `student_ID` int DEFAULT NULL,
  `slotId` int DEFAULT NULL,
  `attendanceMarked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`attendence_id`),
  UNIQUE KEY `unique_attendance` (`student_ID`,`date`,`slotId`),
  KEY `slotId` (`slotId`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`student_ID`) REFERENCES `student` (`student_ID`),
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`slotId`) REFERENCES `slot` (`slot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (62,_binary '','2024-11-06',NULL,1,1,1),(63,_binary '\0','2024-11-06',NULL,40,1,1),(64,_binary '\0','2024-11-06',NULL,45,1,1),(65,_binary '\0','2024-11-06',NULL,50,1,1),(66,_binary '\0','2024-11-06',NULL,55,1,1),(67,_binary '\0','2024-11-06',NULL,57,1,1),(68,_binary '\0','2024-11-06',NULL,58,1,1),(69,_binary '\0','2024-11-06',NULL,59,1,1),(70,_binary '\0','2024-11-06',NULL,60,1,1),(71,_binary '\0','2024-11-06',NULL,61,1,1),(72,_binary '\0','2024-11-06',NULL,62,1,1),(73,_binary '\0','2024-11-06',NULL,63,1,1),(74,_binary '\0','2024-11-06',NULL,64,1,1),(75,_binary '\0','2024-11-06',NULL,65,1,1),(76,_binary '\0','2024-11-06',NULL,66,1,1),(77,_binary '\0','2024-11-06',NULL,67,1,1),(78,_binary '\0','2024-11-06',NULL,68,1,1),(79,_binary '\0','2024-11-06',NULL,69,1,1),(80,_binary '\0','2024-11-06',NULL,70,1,1),(81,_binary '\0','2024-11-06',NULL,71,1,1),(82,_binary '\0','2024-11-06',NULL,72,1,1),(83,_binary '\0','2024-11-06',NULL,73,1,1),(84,_binary '\0','2024-11-06',NULL,74,1,1),(85,_binary '\0','2024-11-06',NULL,75,1,1),(86,_binary '\0','2024-11-06',NULL,76,1,1),(87,_binary '\0','2024-11-06',NULL,77,1,1);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `class_ID` int NOT NULL AUTO_INCREMENT,
  `class_name` varchar(50) DEFAULT NULL,
  `class_level_ID` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `room_ID` int DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`class_ID`),
  KEY `user_id` (`user_id`),
  KEY `room_ID` (`room_ID`),
  KEY `class_level_ID` (`class_level_ID`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `class_ibfk_2` FOREIGN KEY (`room_ID`) REFERENCES `room` (`room_ID`),
  CONSTRAINT `class_ibfk_3` FOREIGN KEY (`class_level_ID`) REFERENCES `class_level` (`class_level_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'Little Learners',1,37,1,NULL),(2,'Tiny Explorers',2,2,2,NULL),(3,'Young Scholars',3,4,3,NULL),(4,'Discovery Class',4,3,4,NULL),(5,'Creative Kids',5,2,5,NULL);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_level`
--

DROP TABLE IF EXISTS `class_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_level` (
  `class_level_ID` int NOT NULL AUTO_INCREMENT,
  `class_level_name` varchar(50) DEFAULT NULL,
  `ageRange` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`class_level_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_level`
--

LOCK TABLES `class_level` WRITE;
/*!40000 ALTER TABLE `class_level` DISABLE KEYS */;
INSERT INTO `class_level` VALUES (1,'Pre-Nursery',1,'1 years to 2 years old'),(2,'Nursery',2,'2 to 3 years old'),(3,'Kindergarten 1',3,'3 to 4 years old'),(4,'Kindergarten 2',4,'4 to 5 years old'),(5,'Preparatory',5,'5 to 6 years old');
/*!40000 ALTER TABLE `class_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extracurricular_activities`
--

DROP TABLE IF EXISTS `extracurricular_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `extracurricular_activities` (
  `activity_id` int NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(255) NOT NULL,
  `description` text,
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `materials_needed` text,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `extracurricular_activities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `extracurricular_activities_chk_1` CHECK ((`status` in (_utf8mb4'Planned',_utf8mb4'Completed',_utf8mb4'Cancelled')))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extracurricular_activities`
--

LOCK TABLES `extracurricular_activities` WRITE;
/*!40000 ALTER TABLE `extracurricular_activities` DISABLE KEYS */;
INSERT INTO `extracurricular_activities` VALUES (1,'111112','1111111','2024-11-06','01:20:00','13:21:00','sÃÂ',36,'dd','Planned'),(4,'Basketball Training','Weekly basketball training sessions for beginners.','2024-11-07','09:00:00','11:00:00','Gymnasium',1,'Basketballs, cones','Completed'),(5,'Art and Craft','Creative art and craft workshop for kids.','2024-11-08','10:00:00','12:00:00','Room 201',2,'Color papers, glue, crayons','Planned'),(6,'Music Class','Beginner guitar class for kids.','2024-11-09','13:00:00','15:00:00','Music Room',3,'Guitars, music sheets','Planned'),(7,'Swimming Lessons','Swimming lessons for all levels.','2024-11-10','08:00:00','10:00:00','Swimming Pool',4,'Swim caps, towels','Planned'),(8,'Science Experiment','Fun science experiments for young minds.','2024-11-11','14:00:00','16:00:00','Lab 1',5,'Lab equipment, safety goggles','Cancelled');
/*!40000 ALTER TABLE `extracurricular_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `notification_ID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`notification_ID`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (2,'Event Notice','Annual event on 5th March','2024-03-05',2),(3,'Exam Reminder','Exams starting next week','2024-06-10',3),(4,'Sports Day','Sports day event on 22nd July','2024-07-22',4),(5,'Fee Reminder','Fee payment due on 30th August','2024-08-30',5),(6,'oke','oke','2024-11-06',NULL),(7,'Holiday Announcement','Kindergarten will be closed from 8th to 10th February for Lunar New Year celebrations. We wish all families a joyful holiday!','2024-02-10',1),(8,'Parent-Teacher Meeting','We invite all parents to attend the Parent-Teacher Meeting on 12th February at 10 AM in the main hall. Topics will include academic progress, behavioral insights, and upcoming events. Light refreshments will be served.','2024-02-12',2),(9,'Health Checkup','The annual health checkup is scheduled for 15th March. Please ensure your child has breakfast before arrival. Health reports will be shared individually with parents.','2024-03-15',3),(10,'Field Trip Notice','A field trip to the City Zoo is planned on 20th April. The bus will leave at 8:30 AM and return by 3:00 PM. Please pack lunch, water, and a cap for your child. Permission slips due by 15th April.','2024-04-20',4),(11,'Immunization Day','Our annual vaccination drive is scheduled for 25th May. Immunizations include Measles, Mumps, and Rubella (MMR). Parents who wish to opt out must notify us by 20th May.','2024-05-25',5),(12,'Fee Reminder','This is a reminder that the term fee payment is due on 10th June. Payments can be made via online transfer, check, or in person at the office. Late fees apply after the due date.','2024-06-10',6),(13,'Summer Camp Registration','Registrations for the Summer Camp are open! The camp will run from 1st to 20th July and includes activities like swimming, art, and sports. Limited spots available. Register by 20th June.','2024-06-15',7),(14,'Library Day','Join us for Library Day on 14th August. This year, we’ll have a special storytelling session by children’s author Lila Brown. Children are welcome to dress up as their favorite book character.','2024-08-14',8),(15,'New Admission','Admissions for the new academic session are now open! Visit our office from 9 AM to 3 PM, Monday to Friday, for a tour and to discuss the enrollment process. Limited seats available.','2024-09-01',9);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_Id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  PRIMARY KEY (`role_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Teacher'),(3,'Parent'),(4,'Manager'),(5,'Enrollment');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_ID` int NOT NULL AUTO_INCREMENT,
  `room_number` varchar(30) DEFAULT NULL,
  `status` bit(1) DEFAULT b'1',
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`room_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'Daisy Room',_binary '',20),(2,'Sunflower Room',_binary '',25),(3,'Tulip Room',_binary '',30),(4,'Lily Room',_binary '',15),(5,'Rose Room',_binary '',18),(6,'Bluebell Room',_binary '',22),(7,'Buttercup Room',_binary '',28),(8,'Lavender Room',_binary '',24),(9,'Orchid Room',_binary '',26),(10,'Peony Room',_binary '',19);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `schedule_ID` int NOT NULL AUTO_INCREMENT,
  `day_of_week` varchar(15) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `term_ID` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `slotId` int DEFAULT NULL,
  PRIMARY KEY (`schedule_ID`),
  KEY `class_id` (`class_id`),
  KEY `slotId` (`slotId`),
  KEY `term_ID` (`term_ID`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_ID`),
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`slotId`) REFERENCES `slot` (`slot_id`),
  CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`term_ID`) REFERENCES `term` (`term_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (18,'thursday','2024-11-07',4,1,1),(19,'thursday','2024-11-07',4,1,2),(20,'friday','2024-11-08',4,1,1),(21,'friday','2024-11-08',4,1,2),(22,'saturday','2024-11-09',4,1,1);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slot` (
  `slot_id` int NOT NULL AUTO_INCREMENT,
  `slot_name` varchar(50) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  PRIMARY KEY (`slot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slot`
--

LOCK TABLES `slot` WRITE;
/*!40000 ALTER TABLE `slot` DISABLE KEYS */;
INSERT INTO `slot` VALUES (1,'Morning Slot','08:00:00','10:00:00'),(2,'Mid-Morning Slot','10:30:00','12:00:00'),(3,'Afternoon Slot','13:00:00','15:00:00'),(4,'Late Afternoon Slot','15:30:00','17:00:00'),(5,'Evening Slot','18:00:00','20:00:00');
/*!40000 ALTER TABLE `slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_ID` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `student_name` varchar(50) DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`student_ID`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'2010-05-10',_binary '','Student A',1,36),(2,'2011-08-22',_binary '\0','Student B',2,4),(3,'2012-01-15',_binary '','Student C',3,4),(4,'2013-03-18',_binary '\0','Student D',4,4),(5,'2014-07-25',_binary '','Student E',5,4),(6,'2024-11-16',_binary '','huy long 1',NULL,3),(7,'2024-11-13',_binary '','huy long 2',NULL,4),(8,'2024-11-07',_binary '\0','huy long 3',2,47),(9,'2024-11-01',_binary '\0','thang',5,48),(10,'2024-11-10',_binary '\0','chuc',3,49),(11,'2024-11-14',_binary '\0','huy long',4,4),(40,'2010-01-01',_binary '','Nguyen Van A',1,40),(41,'2010-02-02',_binary '\0','Tran Thi B',2,41),(42,'2010-03-03',_binary '','Le Van C',3,42),(43,'2010-04-04',_binary '\0','Pham Thi D',4,43),(44,'2010-05-05',_binary '','Hoang Van E',5,44),(45,'2010-06-06',_binary '\0','Ngo Thi F',1,45),(46,'2010-07-07',_binary '','Do Van G',2,46),(47,'2010-08-08',_binary '\0','Bui Thi H',3,47),(48,'2010-09-09',_binary '','Pham Van I',4,48),(49,'2010-10-10',_binary '\0','Tran Thi J',5,49),(50,'2010-01-11',_binary '','Nguyen Van K',1,50),(51,'2010-02-12',_binary '\0','Le Thi L',2,51),(52,'2010-03-13',_binary '','Hoang Van M',3,52),(53,'2010-04-14',_binary '\0','Pham Thi N',4,53),(54,'2010-05-15',_binary '','Tran Van O',5,54),(55,'2010-06-16',_binary '\0','Ngo Thi P',1,7),(57,'2000-01-15',_binary '','James Smith',1,1),(58,'2001-02-25',_binary '\0','Michael Johnson',1,2),(59,'2000-03-05',_binary '','William Brown',1,3),(60,'2002-04-18',_binary '\0','David Jones',1,4),(61,'2003-05-23',_binary '','Richard Garcia',1,5),(62,'2000-06-12',_binary '\0','Joseph Miller',1,1),(63,'2001-07-14',_binary '','Thomas Martinez',1,2),(64,'2002-08-19',_binary '\0','Charles Anderson',1,3),(65,'2003-09-10',_binary '','Christopher Taylor',1,4),(66,'2000-10-15',_binary '\0','Daniel Thomas',1,5),(67,'2001-11-20',_binary '','Matthew Jackson',1,1),(68,'2002-12-25',_binary '\0','Anthony White',1,2),(69,'2003-01-05',_binary '','Donald Harris',1,3),(70,'2000-02-10',_binary '\0','Paul Martin',1,4),(71,'2001-03-15',_binary '','Mark Thompson',1,5),(72,'2002-04-20',_binary '\0','George Lee',1,1),(73,'2003-05-25',_binary '','Steven Perez',1,2),(74,'2000-06-30',_binary '\0','Edward Wilson',1,3),(75,'2001-07-05',_binary '','Brian Clark',1,4),(76,'2002-08-10',_binary '\0','Ronald Lewis',1,5),(77,'2003-09-15',_binary '','Kenneth Walker',1,1),(78,'2024-10-29',_binary '','testee',NULL,1),(79,'2024-10-29',_binary '\0','chuan pro',NULL,59);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_evaluation`
--

DROP TABLE IF EXISTS `student_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_evaluation` (
  `evaluation_ID` int NOT NULL AUTO_INCREMENT,
  `student_ID` int DEFAULT NULL,
  `ranking` enum('Excellent','Good','Average') DEFAULT NULL,
  `description` text,
  `evaluation_date` date DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `term_id` int DEFAULT NULL,
  PRIMARY KEY (`evaluation_ID`),
  KEY `student_ID` (`student_ID`),
  KEY `teacher_id` (`teacher_id`),
  KEY `term_id` (`term_id`),
  CONSTRAINT `student_evaluation_ibfk_1` FOREIGN KEY (`student_ID`) REFERENCES `student` (`student_ID`),
  CONSTRAINT `student_evaluation_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `student_evaluation_ibfk_3` FOREIGN KEY (`term_id`) REFERENCES `term` (`term_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_evaluation`
--

LOCK TABLES `student_evaluation` WRITE;
/*!40000 ALTER TABLE `student_evaluation` DISABLE KEYS */;
INSERT INTO `student_evaluation` VALUES (40,40,'Excellent','Excellent performance','2024-01-01',2,2),(41,41,'Good','Good performance','2024-01-01',2,2),(42,42,'Average','Average performance','2024-01-01',2,2),(43,43,'Excellent','Excellent performance','2024-01-01',2,2),(44,44,'Good','Good performance','2024-01-01',2,2),(45,45,'Average','Average performance','2024-01-01',2,2),(46,46,'Excellent','Excellent performance','2024-01-01',2,2),(47,47,'Good','Good performance','2024-01-01',2,2),(48,48,'Average','Average performance','2024-01-01',2,2),(49,49,'Excellent','Excellent performance','2024-01-01',2,2),(50,50,'Good','Good performance','2024-01-01',2,2),(51,51,'Average','Average performance','2024-01-01',2,2),(52,52,'Excellent','Excellent performance','2024-01-01',2,2),(53,53,'Good','Good performance','2024-01-01',2,2),(54,54,'Average','Average performance','2024-01-01',2,2),(55,55,'Excellent','Excellent performance','2024-01-01',2,2),(56,5,NULL,NULL,NULL,37,1),(57,9,NULL,NULL,NULL,37,1),(58,44,NULL,NULL,NULL,37,1),(59,49,NULL,NULL,NULL,37,1),(60,54,NULL,NULL,NULL,37,1),(61,1,'Good','hoc gioi','2024-11-06',2,1),(62,40,NULL,NULL,NULL,2,1),(63,45,NULL,NULL,NULL,2,1),(64,50,NULL,NULL,NULL,2,1),(65,55,NULL,NULL,NULL,2,1),(66,57,NULL,NULL,NULL,2,1),(67,58,NULL,NULL,NULL,2,1),(68,59,NULL,NULL,NULL,2,1),(69,60,NULL,NULL,NULL,2,1),(70,61,NULL,NULL,NULL,2,1),(71,62,NULL,NULL,NULL,2,1),(72,63,NULL,NULL,NULL,2,1),(73,64,NULL,NULL,NULL,2,1),(74,65,NULL,NULL,NULL,2,1),(75,66,NULL,NULL,NULL,2,1),(76,67,NULL,NULL,NULL,2,1),(77,68,NULL,NULL,NULL,2,1),(78,69,NULL,NULL,NULL,2,1),(79,70,NULL,NULL,NULL,2,1),(80,71,NULL,NULL,NULL,2,1),(81,72,NULL,NULL,NULL,2,1),(82,73,NULL,NULL,NULL,2,1),(83,74,NULL,NULL,NULL,2,1),(84,75,NULL,NULL,NULL,2,1),(85,76,NULL,NULL,NULL,2,1),(86,77,NULL,NULL,NULL,2,1),(87,2,'Good','aaa','2024-11-04',2,1),(88,8,NULL,NULL,NULL,2,1),(89,41,NULL,NULL,NULL,2,1),(90,46,NULL,NULL,NULL,2,1),(91,51,NULL,NULL,NULL,2,1);
/*!40000 ALTER TABLE `student_evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `subject_ID` int NOT NULL AUTO_INCREMENT,
  `subject_Code` varchar(10) DEFAULT NULL,
  `subject_name` varchar(50) DEFAULT NULL,
  `description` varchar(225) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`subject_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'ENG101','English','Basic English skills','Active'),(2,'MATH101','Mathematics','Basic Math skills','Active'),(3,'SCI101','Science','Basic Science introduction','Active'),(4,'ART101','Art','Basic Art and creativity','Active'),(5,'MUS101','Music','Basic Music theory','Active');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_schedule`
--

DROP TABLE IF EXISTS `subject_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_schedule` (
  `schedule_ID` int NOT NULL,
  `subject_ID` int NOT NULL,
  PRIMARY KEY (`schedule_ID`,`subject_ID`),
  KEY `subject_ID` (`subject_ID`),
  CONSTRAINT `subject_schedule_ibfk_1` FOREIGN KEY (`schedule_ID`) REFERENCES `schedule` (`schedule_ID`),
  CONSTRAINT `subject_schedule_ibfk_2` FOREIGN KEY (`subject_ID`) REFERENCES `subject` (`subject_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_schedule`
--

LOCK TABLES `subject_schedule` WRITE;
/*!40000 ALTER TABLE `subject_schedule` DISABLE KEYS */;
INSERT INTO `subject_schedule` VALUES (18,1),(21,1),(20,2),(22,2),(19,5);
/*!40000 ALTER TABLE `subject_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `term` (
  `term_ID` int NOT NULL AUTO_INCREMENT,
  `term_name` varchar(50) DEFAULT NULL,
  `start_Date` date DEFAULT NULL,
  `end_Date` date DEFAULT NULL,
  `year` int DEFAULT NULL,
  PRIMARY KEY (`term_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
INSERT INTO `term` VALUES (1,'Term 1','2024-01-01','2024-03-31',2024),(2,'Term 2','2024-04-01','2024-06-30',2024),(3,'Term 3','2024-07-01','2024-09-30',2024),(4,'Term 4','2024-10-01','2024-12-31',2024),(5,'Term 5','2025-01-01','2025-03-31',2025);
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(50) NOT NULL,
  `date_Of_birth` date DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `phoneNumber` varchar(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT b'1',
  `code` varchar(7) DEFAULT NULL,
  `role_id` int DEFAULT '3',
  PRIMARY KEY (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'John Doe','1985-06-12','johndoe@gmail.com','password123',_binary '','1234567890','123 Main St','img1.jpg',_binary '','ABC123',1),(2,'Jane Smith','1990-09-15','janesmith@gmail.com','$2a$10$a0JjhgTpKprugyKhUeb.DeBTB3kfkppJfPRcbRzkbev0/uATFoQli',_binary '\0','0987654321','456 Maple St','img2.jpg',_binary '','DEF456',2),(3,'Alice Johnson','1987-03-22','alicejohnson@gmail.com','password789',_binary '\0','1112223333','789 Oak St','img3.jpg',_binary '','GHI789',3),(4,'Bob Brown','1992-07-10','bobbrown@gmail.com','password012',_binary '','2223334444','321 Pine St','img4.jpg',_binary '','JKL012',4),(5,'Carol Davis','1989-11-08','caroldavis@gmail.com','password345',_binary '\0','3334445555','654 Elm St','img5.jpg',_binary '','MNO345',5),(6,'Pham Minh D','1985-04-04','phamminhd@example.com','password4',_binary '','0901234567','111 First St','image4.jpg',_binary '','D111',4),(7,'Hoang Thi E','1992-05-05','hoangthie@example.com','password5',_binary '\0','0902345678','222 Second Ave','image5.jpg',_binary '\0','E222',4),(8,'Vu Van F','1987-06-06','vuvanf@example.com','password6',_binary '','0903456789','333 Third Blvd','image6.jpg',_binary '','F333',4),(9,'Do Thi G','1991-07-07','dothig@example.com','password7',_binary '\0','0904567890','444 Fourth St','image7.jpg',_binary '','G444',4),(10,'Le Van H','1983-08-08','levanh@example.com','password8',_binary '','0905678901','555 Fifth Ave','image8.jpg',_binary '\0','H555',4),(11,'Tran Thi I','1994-09-09','tranthii@example.com','password9',_binary '\0','0906789012','666 Sixth Blvd','image9.jpg',_binary '','I666',4),(12,'Nguyen Van J','1978-10-10','nguyenvanj@example.com','password10',_binary '','0907890123','777 Seventh St','image10.jpg',_binary '','J777',4),(13,'Pham Thi K','1989-11-11','phamthik@example.com','password11',_binary '\0','0908901234','888 Eighth Ave','image11.jpg',_binary '\0','K888',4),(14,'Le Van L','1993-12-12','levanl@example.com','password12',_binary '','0909012345','999 Ninth Blvd','image12.jpg',_binary '','L999',4),(15,'Hoang Thi M','1982-01-13','hoangthim@example.com','password13',_binary '\0','0910123456','1010 Tenth St','image13.jpg',_binary '\0','M1010',4),(16,'Do Van N','1990-02-14','dovann@example.com','password14',_binary '','0911234567','1111 Eleventh Ave','image14.jpg',_binary '','N1111',4),(17,'Nguyen Thi O','1986-03-15','nguyenthio@example.com','password15',_binary '\0','0912345678','1212 Twelfth Blvd','image15.jpg',_binary '\0','O1212',4),(18,'Vu Van P','1995-04-16','vuvanp@example.com','password16',_binary '','0913456789','1313 Thirteenth St','image16.jpg',_binary '','P1313',4),(19,'Tran Thi Q','1979-05-17','tranthiq@example.com','password17',_binary '\0','0914567890','1414 Fourteenth Ave','image17.jpg',_binary '\0','Q1414',4),(20,'Pham Van R','1988-06-18','phamvanr@example.com','password18',_binary '','0915678901','1515 Fifteenth Blvd','image18.jpg',_binary '','R1515',4),(21,'John Doe','1990-01-15','john.doe@example.com','password123',_binary '','123456789','123 Main St','john_doe.jpg',_binary '','AB123',2),(22,'Jane Smith','1985-02-20','jane.smith@example.com','password456',_binary '\0','987654321','456 Market Ave','jane_smith.jpg',_binary '','CD456',3),(23,'Michael Johnson','1992-03-30','michael.johnson@example.com','password789',_binary '','567890123','789 Broadway Blvd','michael_johnson.jpg',_binary '\0','EF789',4),(24,'Emily Davis','1988-04-10','emily.davis@example.com','password101',_binary '\0','234567890','234 Oak St','emily_davis.jpg',_binary '\0','GH012',5),(25,'David Brown','1995-05-25','david.brown@example.com','password202',_binary '','345678901','567 Pine Ln','david_brown.jpg',_binary '','IJ345',2),(26,'Alice Walker','1993-06-12','alice.walker@example.com','password303',_binary '\0','112233445','100 Maple St','alice_walker.jpg',_binary '','KL678',3),(27,'Bob Martin','1990-08-22','bob.martin@example.com','password404',_binary '','223344556','200 Cedar St','bob_martin.jpg',_binary '\0','MN789',2),(28,'Carol King','1987-09-14','carol.king@example.com','password505',_binary '\0','334455667','300 Birch St','carol_king.jpg',_binary '','OP901',5),(29,'David White','1991-11-18','david.white@example.com','password606',_binary '','445566778','400 Elm St','david_white.jpg',_binary '\0','QR234',2),(30,'Eve Black','1995-12-01','eve.black@example.com','password707',_binary '\0','556677889','500 Oak St','eve_black.jpg',_binary '','ST345',3),(31,'Frank Green','1989-07-03','frank.green@example.com','password808',_binary '','667788990','600 Pine St','frank_green.jpg',_binary '','UV567',3),(32,'Grace Lee','1994-02-28','grace.lee@example.com','password909',_binary '\0','778899001','700 Fir St','grace_lee.jpg',_binary '\0','WX678',5),(33,'Henry Scott','1986-03-15','henry.scott@example.com','password101',_binary '','889900112','800 Spruce St','henry_scott.jpg',_binary '','YZ789',2),(34,'Ivy Harris','1992-05-20','ivy.harris@example.com','password202',_binary '\0','990011223','900 Ash St','ivy_harris.jpg',_binary '\0','AB890',3),(35,'Jack Young','1990-10-25','jack.young@example.com','password303',_binary '','111222333','1000 Redwood St','jack_young.jpg',_binary '','CD901',2),(36,'Nguyen huy long',NULL,'longaaccbb1605@gmail.com','$2a$10$TJMcpcREbtlC7gAVxtueK.3/s.4yB4pJtmFpK80BeO2NZOcPOxmwy',_binary '','0969792482','trieu khuc - tan trieu - ha noi',NULL,_binary '','232906',3),(37,'long',NULL,'longnhhe160140@fpt.edu.vn','$2a$10$a0JjhgTpKprugyKhUeb.DeBTB3kfkppJfPRcbRzkbev0/uATFoQli',_binary '','0969792481','trieu khuc - tan trieu - ha noi',NULL,_binary '',NULL,2),(40,'Nguyen Van A','2010-01-01','nguyenvana@example.com','$2a$10$a0JjhgTpKprugyKhUeb.DeBTB3kfkppJfPRcbRzkbev0/uATFoQli',_binary '','0981000001','Address 1',NULL,_binary '','S00001',3),(41,'Tran Thi B','2010-02-02','tranthib@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000002','Address 2',NULL,_binary '','S00002',3),(42,'Le Van C','2010-03-03','levanc@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000003','Address 3',NULL,_binary '','S00003',3),(43,'Pham Thi D','2010-04-04','phamthid@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000004','Address 4',NULL,_binary '','S00004',3),(44,'Hoang Van E','2010-05-05','hoangvane@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000005','Address 5',NULL,_binary '','S00005',3),(45,'Ngo Thi F','2010-06-06','ngothif@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000006','Address 6',NULL,_binary '','S00006',3),(46,'Do Van G','2010-07-07','dovang@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000007','Address 7',NULL,_binary '','S00007',3),(47,'Bui Thi H','2010-08-08','buithih@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000008','Address 8',NULL,_binary '','S00008',3),(48,'Pham Van I','2010-09-09','phamvani@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000009','Address 9',NULL,_binary '','S00009',3),(49,'Tran Thi J','2010-10-10','tranthij@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000010','Address 10',NULL,_binary '','S00010',3),(50,'Nguyen Van K','2010-01-11','nguyenvank@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000011','Address 11',NULL,_binary '','S00011',3),(51,'Le Thi L','2010-02-12','lethil@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000012','Address 12',NULL,_binary '','S00012',3),(52,'Hoang Van M','2010-03-13','hoangvanm@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000013','Address 13',NULL,_binary '','S00013',3),(53,'Pham Thi N','2010-04-14','phamthin@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000014','Address 14',NULL,_binary '','S00014',3),(54,'Tran Van O','2010-05-15','tranvano@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '','0981000015','Address 15',NULL,_binary '','S00015',3),(55,'Ngo Thi P','2010-06-16','ngothip@example.com','$2a$10$N4YZQf58SdVl/13APVbAHeTsMBc1AY2s3tjthzJfdaf0L.OYEzmxu',_binary '\0','0981000016','Address 16',NULL,_binary '','S00016',3),(56,'chuc',NULL,'chucvvhe150977@fpt.edu.vn','$2a$10$c5JY/jYTEVnCeJjcJaXzGe8hg6lY5CHI9TwXUmr7eMCfcf6tHTA.6',_binary '','0969792489','ha noi',NULL,_binary '',NULL,1),(57,'chuc 2',NULL,'robert.anime.vn@gmail.com','$2a$10$AJIJCKuh7FgLaDrDwNPqRuK2pwIm57YmzxASpCWUP8NDcbZz9QTUm',_binary '\0','0972542687','ha noi',NULL,_binary '',NULL,4),(58,'binh',NULL,'binhdxhe163115@fpt.edu.vn','$2a$10$TlfizA9.CB4KDO8oMFgrM.h0dANWiHArXo4wVPaDz5qDwhWTWxEL6',_binary '','0972542684','hai duong',NULL,_binary '',NULL,5),(59,'555555511111222222222222111',NULL,'longaaccbb@gmail.com','$2a$10$cZGDaHP/nhGvSEEqmbBlr.ynxZkR2FnVOWp2xFqTirgURacThihGO',_binary '','0969792476','trieu khuc - tan trieu - ha noi',NULL,_binary '',NULL,3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-07  1:07:45
