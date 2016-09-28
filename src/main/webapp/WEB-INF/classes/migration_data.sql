CREATE DATABASE  IF NOT EXISTS `pearsonbtecquals` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pearsonbtecquals`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: pearsonbtecquals
-- ------------------------------------------------------
-- Server version	5.6.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_unique_key`
--

DROP TABLE IF EXISTS `hibernate_unique_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_unique_key` (
  `next_hi` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_unique_key`
--

LOCK TABLES `hibernate_unique_key` WRITE;
/*!40000 ALTER TABLE `hibernate_unique_key` DISABLE KEYS */;
INSERT INTO `hibernate_unique_key` VALUES (0);
/*!40000 ALTER TABLE `hibernate_unique_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `UUID` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `contentType` varchar(255) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `newFilename` varchar(255) DEFAULT NULL,
  `size_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specunit`
--

DROP TABLE IF EXISTS `specunit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specunit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `UUID` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `qanNo` varchar(255) DEFAULT NULL,
  `VAL_XML` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specunit`
--

LOCK TABLES `specunit` WRITE;
/*!40000 ALTER TABLE `specunit` DISABLE KEYS */;
/*!40000 ALTER TABLE `specunit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `UUID` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `revision` varchar(255) DEFAULT NULL,
  `templateName` varchar(255) DEFAULT NULL,
  `xQueryScriptLocation` varchar(255) DEFAULT NULL,
  `xsdScriptLocation` varchar(255) DEFAULT NULL,
  `xsltScriptLocation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template`
--

LOCK TABLES `template` WRITE;
/*!40000 ALTER TABLE `template` DISABLE KEYS */;
/*!40000 ALTER TABLE `template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template_templatesection`
--

DROP TABLE IF EXISTS `template_templatesection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template_templatesection` (
  `template_id` int(11) NOT NULL,
  `sectionName` varchar(255) DEFAULT NULL,
  `sectionStyle` varchar(255) DEFAULT NULL,
  `sectionType` varchar(255) DEFAULT NULL,
  `sectionValue` varchar(255) DEFAULT NULL,
  `showSection` bit(1) NOT NULL,
  `validateInWordDoc` bit(1) NOT NULL,
  `templateSection_id` bigint(20) NOT NULL,
  PRIMARY KEY (`templateSection_id`),
  KEY `FK_dn5ay7kyeb0gysd78h24uymtg` (`template_id`),
  CONSTRAINT `FK_dn5ay7kyeb0gysd78h24uymtg` FOREIGN KEY (`template_id`) REFERENCES `template` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template_templatesection`
--

LOCK TABLES `template_templatesection` WRITE;
/*!40000 ALTER TABLE `template_templatesection` DISABLE KEYS */;
/*!40000 ALTER TABLE `template_templatesection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transformation`
--

DROP TABLE IF EXISTS `transformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transformation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `UUID` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `generalStatus` varchar(255) DEFAULT NULL,
  `image_id` int(11) NOT NULL,
  `iqsxmlfilename` varchar(255) DEFAULT NULL,
  `lastmodified` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `openxmlfilename` varchar(255) DEFAULT NULL,
  `qanNo` varchar(255) DEFAULT NULL,
  `templateId` int(11) NOT NULL,
  `transformStatus` varchar(255) DEFAULT NULL,
  `unitNo` varchar(255) DEFAULT NULL,
  `unitTitle` varchar(255) DEFAULT NULL,
  `wordfilename` varchar(255) DEFAULT NULL,
  `specunit_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lctg4pxjlvdh02694kfs2infu` (`specunit_id`),
  KEY `FK_5vfypvp8vdvjhf6vrnymvnd7a` (`user_id`),
  CONSTRAINT `FK_5vfypvp8vdvjhf6vrnymvnd7a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_lctg4pxjlvdh02694kfs2infu` FOREIGN KEY (`specunit_id`) REFERENCES `specunit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transformation`
--

LOCK TABLES `transformation` WRITE;
/*!40000 ALTER TABLE `transformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `transformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `UUID` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `passwordDigest` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'5e20ed06-0e33-4990-ae20-15b2946f0297',0,'test@email.com','BtecUser','BtecTest','Password123','ROLE_ADMIN','btectest');
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
