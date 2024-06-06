-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 06. Jun 2024 um 01:55
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Datenbank: `ghostnetdata`
--
CREATE DATABASE IF NOT EXISTS `ghostnetdata` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ghostnetdata`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `geonamescache`
--

CREATE TABLE IF NOT EXISTS `geonamescache` (
  `ghostnetid` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `timestamp` int(11) NOT NULL,
  PRIMARY KEY (`ghostnetid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `geonamescache`
--

INSERT INTO `geonamescache` (`ghostnetid`, `name`, `timestamp`) VALUES
(25, 'Baltic Sea', 1716687410),
(26, 'North Sea', 1716687410),
(27, 'North Sea', 1716687410),
(28, 'North Atlantic Ocean', 1716687410),
(29, 'Irish Sea And St. George\'S Channel', 1716687410),
(30, 'Skagerrak', 1716687410),
(31, 'Baltic Sea', 1716687410),
(33, 'English Channel', 1716687410),
(34, 'Baltic Sea', 1716687410),
(35, 'Gulf Of Riga', 1716687410),
(36, 'Baltic Sea', 1716687410),
(37, 'North Sea', 1716687410),
(38, 'North Sea', 1716687411),
(40, 'Baltic Sea', 1716687411),
(42, NULL, 1716687411),
(43, 'North Sea', 1716687411),
(44, 'North Sea', 1716687411),
(46, 'North Sea', 1716687411),
(47, 'English Channel', 1716687411),
(51, 'Celtic Sea', 1716687411),
(52, 'Skagerrak', 1716689408),
(53, 'North Sea', 1716734574),
(54, 'Skagerrak', 1716743966),
(55, 'English Channel', 1716745842),
(56, 'Inner Seas Off The West Coast Of Scotland', 1716763330),
(57, 'Skagerrak', 1716844087),
(58, 'North Sea', 1716846011),
(59, 'North Atlantic Ocean', 1717367116),
(60, 'Baltic Sea', 1717603087);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ghostnets`
--

CREATE TABLE IF NOT EXISTS `ghostnets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `size` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `ghostnets`
--

INSERT INTO `ghostnets` (`id`, `latitude`, `longitude`, `size`) VALUES
(25, '54.456', '14.343', 1),
(26, '55.234', '3.389', 1),
(27, '54.682', '7.245', 3),
(28, '54.807', '-10.876', 2),
(29, '52.369', '-4.614', 1),
(30, '58.407', '10.085', 2),
(31, '55.135', '16.655', 1),
(33, '48.944', '-2.900', 2),
(34, '58.384', '18.589', 2),
(35, '57.851', '23.379', 2),
(36, '55.235', '20.193', 2),
(37, '58.084', '1.956', 3),
(38, '53.245', '4.087', 1),
(40, '56.080', '17.106', 1),
(42, '56.9375', '9.0115', 1),
(43, '58.6376', '2.6779', 1),
(44, '58.6373', '-2.6779', 1),
(46, '54.2307', '7.6657', 1),
(47, '50.7764', '0.8624', 1),
(51, '49.522', '-6.306', 2),
(52, '56.21343', '11.86523', 1),
(53, '53.96643', '7.99805', 1),
(54, '57.37631', '11.11816', 1),
(55, '48.87628', '-2.37305', 1),
(56, '58.04766', '-6.06445', 2),
(57, '59.47968', '10.52490', 1),
(58, '56.81050', '5.38330', 3),
(59, '60.87487', '-0.83496', 2),
(60, '54.51726', '10.30518', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ghostnetsizes`
--

CREATE TABLE IF NOT EXISTS `ghostnetsizes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `ghostnetsizes`
--

INSERT INTO `ghostnetsizes` (`id`, `label`) VALUES
(1, 'Small'),
(2, 'Medium'),
(3, 'Large');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `senderid` int(11) NOT NULL,
  `recipientid` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  `timestamp` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `senderid` (`senderid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `messages`
--

INSERT INTO `messages` (`id`, `senderid`, `recipientid`, `text`, `timestamp`) VALUES
(27, 4, 3, 'Chat started', 1717631392),
(28, 4, 3, 'Hello. I would like to participate in the recovery of the fishing net off Hirtshals.', 1717631504),
(29, 3, 4, 'Hello. Shall we discuss everything further on WhatsApp? My number is 555-4040.', 1717631609);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `reports`
--

CREATE TABLE IF NOT EXISTS `reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `ghostnet` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  KEY `ghostnet` (`ghostnet`),
  KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=439 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `reports`
--

INSERT INTO `reports` (`id`, `user`, `ghostnet`, `status`, `timestamp`) VALUES
(1, 3, 25, 1, 1716845523),
(2, 3, 26, 1, 1716845525),
(3, 3, 27, 1, 1716845527),
(4, 3, 28, 1, 1716845529),
(5, 3, 29, 1, 1716845531),
(6, 3, 30, 1, 1716845534),
(7, 3, 31, 1, 1716845538),
(8, 3, 33, 1, 1716845541),
(9, 3, 34, 1, 1716845544),
(10, 3, 35, 1, 1716845547),
(11, 3, 36, 1, 1716845550),
(12, 3, 37, 1, 1716845553),
(13, 3, 38, 1, 1716845556),
(14, 3, 40, 1, 1716845558),
(15, 3, 42, 1, 1716845562),
(16, 3, 43, 1, 1716845565),
(17, 3, 44, 1, 1716845569),
(18, 3, 46, 1, 1716845572),
(19, 3, 47, 1, 1716845577),
(20, 3, 51, 1, 1716845580),
(21, 3, 52, 1, 1716845583),
(22, 3, 53, 1, 1716845585),
(23, 3, 54, 1, 1716845589),
(24, 3, 55, 1, 1716845591),
(25, 3, 56, 1, 1716845596),
(26, 3, 57, 1, 1716845599),
(27, 3, 31, 2, 1716845638),
(28, 3, 40, 2, 1716845647),
(29, 3, 31, 3, 1716845654),
(30, 3, 40, 3, 1716845656),
(31, 3, 25, 2, 1716845666),
(32, 3, 52, 4, 1716845675),
(33, 4, 53, 4, 1716845707),
(34, 4, 46, 2, 1716845716),
(35, 4, 46, 3, 1716845720),
(36, 4, 38, 2, 1716845730),
(37, 4, 46, 1, 1716845754),
(38, 4, 46, 2, 1716845758),
(39, 4, 46, 3, 1716845771),
(40, 4, 47, 2, 1716845784),
(41, 4, 27, 2, 1716845790),
(42, 4, 44, 2, 1716845805),
(43, 4, 37, 2, 1716845810),
(44, 4, 43, 2, 1716845815),
(45, 4, 37, 3, 1716845828),
(46, 4, 38, 3, 1716845832),
(47, 4, 55, 2, 1716845842),
(48, 4, 51, 4, 1716845849),
(49, 4, 55, 3, 1716845854),
(50, 1, 51, 5, 1716845874),
(51, 1, 53, 5, 1716845875),
(52, 1, 52, 1, 1716845876),
(53, 3, 57, 2, 1716845906),
(54, 3, 58, 1, 1716845942),
(55, 4, 26, 4, 1716846037),
(56, 4, 34, 4, 1716846053),
(389, 1, 59, 1, 1717366500),
(429, 1, 28, 2, 1717532497),
(430, 1, 28, 1, 1717532500),
(431, 1, 28, 2, 1717532506),
(432, 1, 28, 1, 1717532509),
(433, 1, 25, 1, 1717602261),
(434, 1, 29, 2, 1717602491),
(435, 1, 29, 1, 1717602499),
(436, 2, 60, 1, 1717603073),
(437, 1, 25, 2, 1717605390),
(438, 1, 25, 1, 1717605397);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `statuscodes`
--

CREATE TABLE IF NOT EXISTS `statuscodes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `statuscodes`
--

INSERT INTO `statuscodes` (`id`, `label`) VALUES
(1, 'Reported'),
(2, 'Salvage pending'),
(3, 'Recovered'),
(4, 'Reported missing'),
(5, 'Missing');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userdetails`
--

CREATE TABLE IF NOT EXISTS `userdetails` (
  `userid` int(11) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  KEY `userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `userdetails`
--

INSERT INTO `userdetails` (`userid`, `firstname`, `lastname`, `phonenumber`) VALUES
(1, 'admin', 'admin', '555-1000'),
(2, '', '', ''),
(3, 'John', 'Doe', '555-3030'),
(4, 'Jane', 'Doe', '555-4034');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userroles`
--

CREATE TABLE IF NOT EXISTS `userroles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` int(11) NOT NULL,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `userroles`
--

INSERT INTO `userroles` (`id`, `role`, `label`) VALUES
(1, 1, 'Administrator'),
(2, 2, 'Anonymous'),
(3, 10, 'Hunter'),
(4, 20, 'Reporter');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `hashedpassword` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`id`, `role`, `username`, `comment`, `hashedpassword`, `salt`) VALUES
(1, 1, 'admin', 'admin', 'OqW/jtPxVf20pkBtco0re0cMnBmLNmnLwLybqWpbck0=', 'V04Pi7srsEdHxgvGrOVLzA=='),
(2, 2, 'Anonymous', NULL, NULL, NULL),
(3, 3, 'johndoe', 'john123', 'xoiwdOGPB54D87Z1BdwpDDviPaOYEnBKxUJKHTftrEM=', 'qwWnhK5AInx3HCcIl2PHlw=='),
(4, 4, 'janedoe', 'jane123', 'JNlFKNTLGSm+aBfFco463t5WYz4cMGN3bbq6rhpDRnY=', 'NwkEIZ3kAfeBfSxx8NaIqQ==');

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `geonamescache`
--
ALTER TABLE `geonamescache`
  ADD CONSTRAINT `geonamescache_ibfk_1` FOREIGN KEY (`ghostnetid`) REFERENCES `ghostnets` (`id`);

--
-- Constraints der Tabelle `ghostnets`
--
ALTER TABLE `ghostnets`
  ADD CONSTRAINT `ghostnets_ibfk_1` FOREIGN KEY (`size`) REFERENCES `ghostnetsizes` (`id`);

--
-- Constraints der Tabelle `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`senderid`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`senderid`) REFERENCES `users` (`id`);

--
-- Constraints der Tabelle `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reports_ibfk_2` FOREIGN KEY (`ghostnet`) REFERENCES `ghostnets` (`id`),
  ADD CONSTRAINT `reports_ibfk_3` FOREIGN KEY (`status`) REFERENCES `statuscodes` (`id`);

--
-- Constraints der Tabelle `userdetails`
--
ALTER TABLE `userdetails`
  ADD CONSTRAINT `userdetails_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`);

--
-- Constraints der Tabelle `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role`) REFERENCES `userroles` (`id`);
COMMIT;
