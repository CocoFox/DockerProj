-- MySQL dump 10.17  Distrib 10.3.12-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: Comparateur
-- ------------------------------------------------------
-- Server version	10.3.12-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Administrateurs`
--

DROP TABLE IF EXISTS `Administrateurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Administrateurs` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(20) DEFAULT NULL,
  `prenom` varchar(20) DEFAULT NULL,
  `identifiant` varchar(20) DEFAULT NULL,
  `mdp` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administrateurs`
--

LOCK TABLES `Administrateurs` WRITE;
/*!40000 ALTER TABLE `Administrateurs` DISABLE KEYS */;
INSERT INTO `Administrateurs` VALUES (1,'','Administrateur','admin','f865b53623b121fd34ee5426c792e5c33af8c227');
/*!40000 ALTER TABLE `Administrateurs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Chaussures`
--

DROP TABLE IF EXISTS `Chaussures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Chaussures` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(20) NOT NULL,
  `couleur` varchar(20) DEFAULT NULL,
  `marque` varchar(20) DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `prix` float DEFAULT NULL,
  `filename` varchar(20) DEFAULT NULL,
  `style` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Chaussures`
--

LOCK TABLES `Chaussures` WRITE;
/*!40000 ALTER TABLE `Chaussures` DISABLE KEYS */;
INSERT INTO `Chaussures` VALUES (1,'CONTINENTAL 80','Rose','Adidas',13,55,'continental80','Baskets'),(2,'UNION WHARF OXFORD','Noire','Timberland',5,38,'unionwharf','Baskets'),(3,'KILLINGTON','Beige','Timberland',8,104,'killington','Bottes'),(4,'AAUWEN','Noire','Aldo',7,64.95,'aauwen','Chaussures'),(5,'AIR MONARCH IV','Blanche','Nike',14,54.95,'airmonarch4','Sports'),(6,'GEL NIMBUS','Noire','Asics',18,180,'gelnimbus','Sports'),(7,'STAN SMITH','Blanche','Adidas',12,99.95,'stansmith','Baskets'),(8,'TACCONE','Marron','Aldo',6,99.95,'taccone','Luxe'),(9,'SOBAKOV','Noire','Adidas',0,119.95,'sobakov','Sneakers'),(10,'BAGGY AT 2.0','Grise','Palladium',10,99.95,'baggy','Bottes'),(25,'CONVERSE','Rouge','All Stars',15,60,'converse','Tennis'),(26,'CONVERSE','Bleue','All Stars',15,30,'converse','Tennis'),(27,'M2K TEKNO','Blanche','Nike',10,50,'m2ktekno','Sneakers'),(28,'ZOOM 2K','Rouge','Nike',5,89.95,'zoom2k','Baskets'),(29,'UNION WHARF OXFORD','Cuir','Timberland',9,84.95,'unionwharfoxford','Baskets'),(30,'JFWVISION','Bleue','Jack&Jones',7,24,'jfwvision','Baskets');
/*!40000 ALTER TABLE `Chaussures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Clients`
--

DROP TABLE IF EXISTS `Clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Clients` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(20) DEFAULT NULL,
  `prenom` varchar(20) DEFAULT NULL,
  `identifiant` varchar(20) DEFAULT NULL,
  `mdp` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Clients`
--

LOCK TABLES `Clients` WRITE;
/*!40000 ALTER TABLE `Clients` DISABLE KEYS */;
INSERT INTO `Clients` VALUES (1,'Hajji','Wadii','whajji','7c4a8d09ca3762af61e59520943dc26494f8941b'),(2,'Gord','Quentin','qgord','7c4a8d09ca3762af61e59520943dc26494f8941b'),(3,'Meskini','Nizar','nmeskini','40bd001563085fc35165329ea1ff5c5ecbdbbeef'),(4,'Hajji','Mountassir','mhajji','40bd001563085fc35165329ea1ff5c5ecbdbbeef');
/*!40000 ALTER TABLE `Clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservations`
--

DROP TABLE IF EXISTS `Reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reservations` (
  `ID_client` int(11) NOT NULL,
  `ID_chaussure` int(11) NOT NULL,
  `date_reservation` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservations`
--

LOCK TABLES `Reservations` WRITE;
/*!40000 ALTER TABLE `Reservations` DISABLE KEYS */;
INSERT INTO `Reservations` VALUES (1,7,'2019-02-11 15:15:18'),(1,1,'2019-02-11 22:15:22');
/*!40000 ALTER TABLE `Reservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-13 18:49:48
