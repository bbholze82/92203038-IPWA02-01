-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 26. Mai 2024 um 19:23
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

DROP TABLE IF EXISTS `geonamescache`;
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
(54, 'Skagerrak', 1716743966);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ghostnets`
--

DROP TABLE IF EXISTS `ghostnets`;
CREATE TABLE IF NOT EXISTS `ghostnets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `size` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(54, '57.37631', '11.11816', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ghostnetsizes`
--

DROP TABLE IF EXISTS `ghostnetsizes`;
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
-- Tabellenstruktur für Tabelle `reports`
--

DROP TABLE IF EXISTS `reports`;
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
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `reports`
--

INSERT INTO `reports` (`id`, `user`, `ghostnet`, `status`, `timestamp`) VALUES
(284, 3, 25, 1, 1716743200),
(285, 3, 26, 1, 1716743202),
(286, 3, 27, 1, 1716743203),
(287, 3, 28, 1, 1716743205),
(288, 3, 29, 1, 1716743206),
(289, 3, 30, 1, 1716743208),
(290, 3, 31, 1, 1716743209),
(291, 3, 33, 1, 1716743212),
(292, 3, 34, 1, 1716743215),
(293, 3, 35, 1, 1716743218),
(294, 3, 36, 1, 1716743221),
(295, 3, 37, 1, 1716743224),
(296, 3, 38, 1, 1716743229),
(297, 3, 40, 1, 1716743231),
(298, 3, 42, 1, 1716743234),
(299, 3, 43, 1, 1716743237),
(300, 3, 44, 1, 1716743239),
(301, 3, 46, 1, 1716743242),
(302, 3, 47, 1, 1716743244),
(303, 3, 51, 1, 1716743246),
(304, 3, 52, 1, 1716743248),
(305, 3, 53, 1, 1716743250),
(306, 3, 42, 2, 1716743383),
(307, 3, 46, 2, 1716743414),
(308, 3, 46, 3, 1716743420),
(309, 3, 52, 2, 1716743469),
(310, 4, 47, 2, 1716743500),
(311, 4, 33, 2, 1716743505),
(312, 4, 51, 2, 1716743511),
(313, 4, 51, 3, 1716743517),
(314, 4, 33, 3, 1716743523),
(315, 4, 33, 1, 1716743538),
(316, 4, 33, 2, 1716743545),
(317, 4, 47, 3, 1716743550),
(318, 4, 38, 4, 1716743569),
(319, 4, 34, 4, 1716743583),
(320, 4, 35, 4, 1716743588),
(321, 1, 38, 5, 1716743922),
(322, 1, 34, 5, 1716743925),
(323, 1, 35, 1, 1716743927),
(324, 2, 54, 1, 1716743950),
(325, 3, 54, 2, 1716743985),
(326, 3, 54, 3, 1716743993),
(327, 3, 53, 4, 1716744037);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `statuscodes`
--

DROP TABLE IF EXISTS `statuscodes`;
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

DROP TABLE IF EXISTS `userdetails`;
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

DROP TABLE IF EXISTS `userroles`;
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

DROP TABLE IF EXISTS `users`;
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
