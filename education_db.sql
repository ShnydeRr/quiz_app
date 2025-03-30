-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 23 mars 2025 à 08:37
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `education_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `answers`
--

CREATE TABLE `answers` (
  `id` bigint(20) NOT NULL,
  `answer_text` varchar(255) DEFAULT NULL,
  `correct` bit(1) NOT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `is_correct` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `answers`
--

INSERT INTO `answers` (`id`, `answer_text`, `correct`, `question_id`, `is_correct`) VALUES
(84, 'Paris', b'1', 33, NULL),
(85, 'London', b'0', 33, NULL),
(86, 'Berlin', b'0', 33, NULL),
(87, 'Madrid', b'0', 33, NULL),
(88, 'Jupiter', b'1', 34, NULL),
(89, 'Saturn', b'0', 34, NULL),
(90, 'Mars', b'0', 34, NULL),
(91, 'Earth', b'0', 34, NULL),
(92, 'William Shakespeare', b'1', 35, NULL),
(93, 'Jane Austen', b'0', 35, NULL),
(94, 'Charles Dickens', b'0', 35, NULL),
(95, 'Mark Twain', b'0', 35, NULL),
(96, '8', b'1', 36, NULL),
(97, '4', b'0', 36, NULL),
(98, '3', b'0', 36, NULL),
(99, '2', b'0', 36, NULL),
(100, 'Leonardo da Vinci', b'0', 37, NULL),
(101, 'Vincent van Gogh', b'0', 37, NULL),
(102, 'Pablo Picasso', b'0', 37, NULL),
(103, 'Claude Monet', b'0', 37, NULL),
(104, '2', b'1', 38, NULL),
(105, '7', b'0', 38, NULL),
(106, '11', b'0', 38, NULL),
(107, '6', b'0', 39, NULL),
(108, '25', b'1', 39, NULL),
(109, '11', b'0', 39, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

CREATE TABLE `questions` (
  `id` bigint(20) NOT NULL,
  `question_text` varchar(255) DEFAULT NULL,
  `quiz_id` bigint(20) DEFAULT NULL,
  `correct_answer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `questions`
--

INSERT INTO `questions` (`id`, `question_text`, `quiz_id`, `correct_answer_id`) VALUES
(33, 'What is the capital of France?', 25, NULL),
(34, 'What is the largest planet in our solar system?', 25, NULL),
(35, 'Who wrote \"Romeo and Juliet\"?', 25, NULL),
(36, 'What is the square root of 64?', 25, NULL),
(37, 'Who painted the Mona Lisa?', 25, NULL),
(38, '1+1', 26, NULL),
(39, '20+5', 26, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `quiz`
--

CREATE TABLE `quiz` (
  `id` bigint(20) NOT NULL,
  `approved` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `quiz`
--

INSERT INTO `quiz` (`id`, `approved`, `title`, `user_id`) VALUES
(25, b'1', 'General Knowledge Quiz', 21),
(26, b'1', 'wifi', 24);

-- --------------------------------------------------------

--
-- Structure de la table `quiz_liked_by_users`
--

CREATE TABLE `quiz_liked_by_users` (
  `quiz_id` bigint(20) NOT NULL,
  `liked_by_users_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `firstname`, `lastname`, `password`, `role`, `username`) VALUES
(21, 'yass@gmail.com', 'a', 'a', '$2a$10$ZyD10WNTD6DKlVBkXf6yeOeupRVU7cjgEl2U3U8hSKe2sFR3LqpIa', 'normal_user', 'user1'),
(24, 'wifi@gmail.com', 'wifixxxxxxxxx', 'wifi', '$2a$10$tWrqsB1eqZPZfHtSKM5cVO9/dZkTae2Lu9XZ/q9tWTmNohbIxHeCC', 'normal_user', 'wifi'),
(25, 'wifii@gmail.com', 'wifi', 'wifi', '$2a$10$Wkp331CoLiJHc6/7XLnyRuBhQ9E4muoCGCJjbPiJ1ObQ5ONJMjVYe', 'normal_user', 'wifiadmin'),
(26, 'wifi@admin.com', 'wifi', 'lajmidd', '$2a$10$2Zu5qEwXPuOzYOBMQLeUBegIM7sTj8U0T16qIzDMex0nDTNWHeHH.', 'super_user', 'adminwifi');

-- --------------------------------------------------------

--
-- Structure de la table `user_scores`
--

CREATE TABLE `user_scores` (
  `id` bigint(20) NOT NULL,
  `score` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `quiz_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user_scores`
--

INSERT INTO `user_scores` (`id`, `score`, `user_id`, `quiz_id`) VALUES
(28, 0, 4, 10),
(29, 1, 24, 26),
(30, 1, 24, 25);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3erw1a3t0r78st8ty27x6v3g1` (`question_id`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnk4ftb5am9ubmkv1661h15ds9` (`user_id`);

--
-- Index pour la table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8ntcsbyfiiso3xxqm406wbwjp` (`quiz_id`);

--
-- Index pour la table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoj71coq74ryc5c9c9o482p8mm` (`user_id`);

--
-- Index pour la table `quiz_liked_by_users`
--
ALTER TABLE `quiz_liked_by_users`
  ADD KEY `FKmaopnkgmu0ebh2sme9vq13gac` (`liked_by_users_id`),
  ADD KEY `FKdj7y1egu87jb1fin7lafv0bc6` (`quiz_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Index pour la table `user_scores`
--
ALTER TABLE `user_scores`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `answers`
--
ALTER TABLE `answers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;

--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT pour la table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT pour la table `user_scores`
--
ALTER TABLE `user_scores`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `FK3erw1a3t0r78st8ty27x6v3g1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`);

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FKnk4ftb5am9ubmkv1661h15ds9` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FK8ntcsbyfiiso3xxqm406wbwjp` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`);

--
-- Contraintes pour la table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `FKoj71coq74ryc5c9c9o482p8mm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `quiz_liked_by_users`
--
ALTER TABLE `quiz_liked_by_users`
  ADD CONSTRAINT `FKdj7y1egu87jb1fin7lafv0bc6` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`),
  ADD CONSTRAINT `FKmaopnkgmu0ebh2sme9vq13gac` FOREIGN KEY (`liked_by_users_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
