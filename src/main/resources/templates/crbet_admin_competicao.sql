-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: crbet_admin
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `competicao`
--

DROP TABLE IF EXISTS `competicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `competicao` (
  `id` bigint(20) NOT NULL,
  `pais_codigo` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competicao`
--

LOCK TABLES `competicao` WRITE;
/*!40000 ALTER TABLE `competicao` DISABLE KEYS */;
INSERT INTO `competicao` VALUES (35,'GBR','Inglaterra - League 1'),(37,'GBR','Inglaterra - League Two'),(41,'GBR','Inglaterra - English National League Norte'),(55,'FRA','França - Ligue 1'),(57,'FRA','França - Ligue 2'),(81,'ITA','Itália – Série A'),(83,'ITA','Itália - Serie B'),(99,'PRT','Portugal - Primeira liga'),(117,'ESP','Espanha - La Liga'),(119,'ESP','Espanha - Segunda Division'),(121,'ESP','Espanha - Segunda Division B - Grupo 1'),(123,'ESP','Espanha - Segunda Division B - Grupo 2'),(125,'ESP','Espanha - Segunda Division B - Grupo 3'),(127,'ESP','Espanha - Segunda Division B - Grupo 4'),(228,'International','UEFA Champions League'),(9513,'PRT','Portugal - Segunda Liga'),(12801,'ESP','Espanha - Copa do Rei'),(19513,'NLD','Copa da Holanda'),(20351,'GBR','Irlanda do Norte - Premiership'),(43079,'FRA','Copa de França'),(67387,'ARG','Argentina - Primera Division'),(409743,'GBR','Escócia - FA Cup'),(439085,'BRA','Brasil - Gaucho'),(439106,'BRA','Brasil - Paranaense'),(483094,'BRA','Brasil - Goiano'),(821269,'ESP','Espanha - Tercera Division'),(827078,'MEX','México - Sub20'),(827754,'MEX','México - Ascenso MX'),(2129602,'GBR','Inglaterra - Professional Development League'),(2490975,'BRA','Brasil - Paulista Serie A1'),(2517464,'BRA','Brasil - Copa do Nordeste'),(2519611,'BRA','Brasil - Paulista Serie A2'),(3986227,'ESP','Espanha - Division di Honor Sub19'),(4684340,'International','Amigáveis (Feminino)'),(5627174,'MEX','México - Liga MX'),(7129730,'GBR','Inglaterra - Championship'),(7422970,'BRA','Brasil - Paulista Serie A3'),(8423138,'PRT','Portugal - Campeonato Nacional'),(8584102,'FRA','França - Championnat National Sub19'),(8770515,'MEX','México - Liga Premier'),(9404054,'NLD','Holanda - Eredivisie'),(10403285,'GBR','Inglaterra - Premier League Cup'),(10932509,'GBR','Inglaterra - Premier League'),(11463202,'ITA','Itália - Serie C'),(11482388,'ITA','Itália -  Campionato Primavera 1'),(11687921,'MEX','México - Liga MX Feminina'),(11711082,'ITA','Itália -  Campionato Primavera 2'),(12164283,'BRA','Brasil - Carioca Jogos'),(12176194,'BRA','Brasil - Potiguar Jogos'),(12179700,'BRA','Brazilian Paraibano Matches'),(12181991,'BRA','Brasil - Catarinense Jogos'),(12184051,'BRA','Brazilian Gaucho Matches'),(12184071,'BRA','Brazilian Mineiro Matches'),(12184232,'BRA','Brazilian Pernambucano Matches');
/*!40000 ALTER TABLE `competicao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-10 15:59:31
