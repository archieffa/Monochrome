-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2024 at 02:11 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tmd_dpbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `tscore`
--

CREATE TABLE `tscore` (
  `username` varchar(255) NOT NULL,
  `score` int(255) NOT NULL,
  `up` int(255) NOT NULL,
  `down` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tscore`
--

INSERT INTO `tscore` (`username`, `score`, `up`, `down`) VALUES
('NalarJalan', 1330, 360, 270),
('UseYourLogic', 1180, 290, 220),
('NoJudgement', 1523, 553, 460),
('aku', 1080, 600, 480),
('beres', 1400, 730, 670),
('dokyeom', 3580, 2280, 1300),
('wonwoo', 2033, 1083, 950),
('seungkwan', 3117, 1721, 1396),
('nyoba', 6456, 3436, 3020),
('cello', 300, 200, 100),
('helga', 540, 310, 230),
('malio', 270, 150, 120),
('karina', 410, 260, 150),
('ruka', 340, 210, 130),
('mahen', 460, 240, 220),
('haikal', 400, 220, 180),
('hendery', 350, 200, 150),
('doky', 280, 170, 110),
('syifa', 70, 50, 20),
('daniel', 310, 180, 130),
('dira', 590, 290, 300),
('jia', 570, 310, 260),
('y', 130, 100, 30),
('dimas', 760, 390, 370);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
