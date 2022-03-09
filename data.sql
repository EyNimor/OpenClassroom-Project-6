-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 08 mars 2022 à 14:50
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `appdatabase`
--
CREATE DATABASE IF NOT EXISTS `appdatabase` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `appdatabase`;

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `number_id` bigint(20) NOT NULL,
  `giver_email` varchar(250) NOT NULL,
  `receiver_email` varchar(250) NOT NULL,
  `transaction_date` date NOT NULL,
  `amount` decimal(18,2) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`giver_email`,`receiver_email`,`number_id`) USING BTREE,
  KEY `fk_receiver_email` (`receiver_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `usernetwork`
--

DROP TABLE IF EXISTS `usernetwork`;
CREATE TABLE IF NOT EXISTS `usernetwork` (
  `user_email` varchar(250) NOT NULL,
  `friend_email` varchar(250) NOT NULL,
  PRIMARY KEY (`user_email`,`friend_email`),
  KEY `fk_friend_email` (`friend_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `email` varchar(250) NOT NULL,
  `first_Name` varchar(250) NOT NULL,
  `last_Name` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `iban` varchar(250) DEFAULT NULL,
  `wallet` decimal(18,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `fk_giver_email` FOREIGN KEY (`giver_email`) REFERENCES `users` (`email`),
  ADD CONSTRAINT `fk_receiver_email` FOREIGN KEY (`receiver_email`) REFERENCES `users` (`email`);

--
-- Contraintes pour la table `usernetwork`
--
ALTER TABLE `usernetwork`
  ADD CONSTRAINT `fk_friend_email` FOREIGN KEY (`friend_email`) REFERENCES `users` (`email`),
  ADD CONSTRAINT `fk_user_email` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
