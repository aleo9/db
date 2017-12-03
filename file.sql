-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 03 dec 2017 kl 23:54
-- Serverversion: 10.1.28-MariaDB
-- PHP-version: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `filehosting`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `file`
--

CREATE TABLE `file` (
  `name` varchar(32) NOT NULL,
  `size` int(11) NOT NULL,
  `owner` varchar(32) NOT NULL,
  `public` tinyint(1) NOT NULL,
  `writable` tinyint(1) NOT NULL,
  `log` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `file`
--

INSERT INTO `file` (`name`, `size`, `owner`, `public`, `writable`, `log`) VALUES
('asdf.txt', 225, 'alex', 0, 0, ''),
('n.txt', 225, 'nisse', 0, 0, ''),
('qwer.txt', 225, 'alex', 0, 0, ' nisse edited'),
('x.txt', 225, 'alex', 1, 0, '');

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
