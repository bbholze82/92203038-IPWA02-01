-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 20. Mai 2024 um 23:23
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Datenbank: `ghostnetdata`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ghostnets`
--

CREATE TABLE `ghostnets` (
  `id` int(11) NOT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `statuscode` int(11) DEFAULT NULL,
  `reportedby` int(11) DEFAULT NULL,
  `reportts` bigint(20) DEFAULT NULL,
  `claimedby` int(11) DEFAULT NULL,
  `claimedts` bigint(255) DEFAULT NULL,
  `lasteditts` bigint(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `ghostnets`
--

INSERT INTO `ghostnets` (`id`, `latitude`, `longitude`, `size`, `statuscode`, `reportedby`, `reportts`, `claimedby`, `claimedts`, `lasteditts`) VALUES
(25, '54.456', '14.343', 1, 3, NULL, 1704063600, NULL, NULL, 1704063600),
(26, '55.234', '3.389', 1, 2, NULL, 1704063600, 4, 1716239750, 1716239750),
(27, '54.682', '7.245', 3, 2, NULL, 1704063600, 4, 1716239753, 1716239753),
(28, '54.807', '-10.876', 2, 3, NULL, 1704063600, NULL, NULL, 1704063600),
(29, '52.369', '-4.614', 1, 4, NULL, 1704063600, NULL, NULL, 1704063600),
(30, '58.407', '10.085', 2, 2, NULL, 1704063600, 4, 1716239758, 1716239758),
(31, '55.135', '16.655', 1, 2, NULL, 1704063600, 4, 1704063600, 1704063600),
(32, '62.037', '-5.317', 3, 3, NULL, 1704063600, NULL, NULL, 1704063600),
(33, '48.944', '-2.900', 2, 1, NULL, 1704063600, NULL, NULL, 1704063600),
(34, '58.384', '18.589', 2, 1, NULL, 1704063600, NULL, NULL, 1704063600),
(35, '57.851', '23.379', 2, 1, 3, 1704063600, NULL, NULL, 1704063600),
(36, '55.235', '20.193', 2, 1, 3, 1704063600, NULL, NULL, 1704063600),
(37, '58.084', '1.956', 3, 1, 4, 1704063600, NULL, NULL, 1704063600),
(38, '53.245', '4.087', 1, 1, 4, 1704063600, NULL, NULL, 1704063600),
(40, '56.080', '17.106', 1, 3, NULL, 1704063600, NULL, 1716239798, 1716239798),
(41, '61.431', '4.801', 1, 1, 4, 1704063600, NULL, NULL, 1704063600),
(42, '56.9375', '9.0115', 1, 1, NULL, 1704063600, NULL, NULL, 1704063600),
(43, '58.6376', '2.6779', 1, 1, 3, 1704063600, NULL, NULL, 1704063600),
(44, '58.6373', '-2.6779', 2, 2, 4, 1704063600, 3, 1716239781, 1716239781),
(45, '54.845', '-10.217', 3, 2, 4, 1704063600, 3, 1716239785, 1716239785),
(46, '54.2307', '7.6657', 1, 2, NULL, 1716237849, 3, 1716239788, 1716239788),
(47, '50.7764', '0.8624', 1, 1, 4, 1716239694, NULL, NULL, 1716239724);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `statuscodes`
--

CREATE TABLE `statuscodes` (
  `id` int(11) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `statuscodes`
--

INSERT INTO `statuscodes` (`id`, `label`) VALUES
(1, 'Reported'),
(2, 'Salvage pending'),
(3, 'Recovered'),
(4, 'Missing');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `userroles`
--

CREATE TABLE `userroles` (
  `id` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  `label` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `userroles`
--

INSERT INTO `userroles` (`id`, `role`, `label`) VALUES
(1, 1, 'Administrator'),
(2, 10, 'Hunter'),
(3, 2, 'Anonymous');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `role` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `hashedpassword` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`id`, `role`, `username`, `comment`, `hashedpassword`, `salt`, `firstname`, `lastname`, `phonenumber`) VALUES
(1, 1, 'admin', 'admin', 'OqW/jtPxVf20pkBtco0re0cMnBmLNmnLwLybqWpbck0=', 'V04Pi7srsEdHxgvGrOVLzA==', 'admin', 'admin', '555 1000'),
(2, 3, 'Anonymous', NULL, NULL, NULL, '', '', ''),
(3, 2, 'johndoe', 'john123', 'xoiwdOGPB54D87Z1BdwpDDviPaOYEnBKxUJKHTftrEM=', 'qwWnhK5AInx3HCcIl2PHlw==', 'John', 'Doe', '555 3033'),
(4, 2, 'janedoe', 'jane123', 'JNlFKNTLGSm+aBfFco463t5WYz4cMGN3bbq6rhpDRnY=', 'NwkEIZ3kAfeBfSxx8NaIqQ==', 'Jane', 'Doe', '555 4044');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `ghostnets`
--
ALTER TABLE `ghostnets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reportedby` (`reportedby`),
  ADD KEY `earmarkedby` (`claimedby`);

--
-- Indizes für die Tabelle `statuscodes`
--
ALTER TABLE `statuscodes`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `userroles`
--
ALTER TABLE `userroles`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `ghostnets`
--
ALTER TABLE `ghostnets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT für Tabelle `statuscodes`
--
ALTER TABLE `statuscodes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `userroles`
--
ALTER TABLE `userroles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `ghostnets`
--
ALTER TABLE `ghostnets`
  ADD CONSTRAINT `ghostnets_ibfk_1` FOREIGN KEY (`reportedby`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `ghostnets_ibfk_2` FOREIGN KEY (`claimedby`) REFERENCES `users` (`id`);

--
-- Constraints der Tabelle `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role`) REFERENCES `userroles` (`id`);
COMMIT;
