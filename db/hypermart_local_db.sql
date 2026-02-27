-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.45 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.15.0.7171
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table hypermart_local.agents
CREATE TABLE IF NOT EXISTS `agents` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.agents: ~1 rows (approximately)
INSERT INTO `agents` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'A1', '2025-04-05 13:50:41', '2025-04-05 13:50:41');

-- Dumping structure for table hypermart_local.brands
CREATE TABLE IF NOT EXISTS `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.brands: ~1 rows (approximately)
INSERT INTO `brands` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'B1', '2025-04-05 13:47:20', '2025-04-05 13:47:20');

-- Dumping structure for table hypermart_local.cities
CREATE TABLE IF NOT EXISTS `cities` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `district_id` bigint unsigned DEFAULT NULL,
  `name_en` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name_si` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name_ta` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `sub_name_en` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `sub-name_si` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `sub_name_ta` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `post_code` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `latitude` decimal(8,2) NOT NULL,
  `longitude` decimal(8,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cities_district_id_foreign` (`district_id`),
  CONSTRAINT `cities_district_id_foreign` FOREIGN KEY (`district_id`) REFERENCES `districts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.cities: ~1 rows (approximately)
INSERT INTO `cities` (`id`, `district_id`, `name_en`, `name_si`, `name_ta`, `sub_name_en`, `sub-name_si`, `sub_name_ta`, `post_code`, `latitude`, `longitude`, `created_at`, `updated_at`) VALUES
	(1, 1, 'default', 'default', 'default', 'default', 'default', 'default', '23', 221.00, 22.00, '2024-11-09 09:32:36', '2024-11-09 09:32:36');

-- Dumping structure for table hypermart_local.colors
CREATE TABLE IF NOT EXISTS `colors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.colors: ~22 rows (approximately)
INSERT INTO `colors` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'Red', '2025-03-04 07:53:03', '2025-03-06 03:11:05'),
	(2, 'Blue', '2025-03-04 08:16:45', '2025-03-06 03:11:14'),
	(4, 'Green', '2025-03-05 07:02:26', '2025-03-06 03:11:29'),
	(5, 'Pink', '2025-03-05 12:03:15', '2025-03-06 03:11:42'),
	(6, 'Gray', '2025-03-05 12:03:36', '2025-03-06 03:11:48'),
	(11, 'Black', '2025-03-06 03:15:00', '2025-03-06 03:15:00'),
	(12, 'Yellow', '2025-03-06 03:15:10', '2025-03-06 03:15:10'),
	(13, 'Silver', '2025-03-06 03:15:20', '2025-03-06 03:15:20'),
	(14, 'Gold', '2025-03-06 03:15:26', '2025-03-06 03:15:26'),
	(15, 'Rose Gold', '2025-03-06 03:15:35', '2025-03-06 03:15:35'),
	(16, 'Midnight Green', '2025-03-06 03:15:53', '2025-03-06 03:15:53'),
	(17, 'Space Gray', '2025-03-06 03:16:11', '2025-03-06 03:16:11'),
	(18, 'Pacific Blue', '2025-03-06 03:16:27', '2025-03-06 03:16:27'),
	(19, 'Phantom Black', '2025-03-06 03:16:45', '2025-03-06 03:16:45'),
	(20, 'Mystic Bronze', '2025-03-06 03:17:01', '2025-03-06 03:17:01'),
	(21, 'Titanium', '2025-03-06 03:17:14', '2025-03-06 03:17:14'),
	(22, 'Graphite', '2025-03-06 03:17:31', '2025-03-06 03:17:31'),
	(23, 'Ocean Blue', '2025-03-06 03:17:40', '2025-03-06 03:17:40'),
	(24, 'Lavender', '2025-03-06 03:17:56', '2025-03-06 03:17:56'),
	(25, 'Mint Green', '2025-03-06 03:18:06', '2025-03-06 03:18:06'),
	(26, 'Coral', '2025-03-06 03:18:17', '2025-03-06 03:18:17'),
	(27, 'Bronze', '2025-03-06 03:18:38', '2025-03-06 03:18:38');

-- Dumping structure for table hypermart_local.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `contact_number` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `cities_id` bigint unsigned DEFAULT NULL,
  `status_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `address_line_1` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `address_line_2` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `due_amount` decimal(10,2) DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `city_name` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `customer_id` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL,
  `loyalty_points` decimal(10,2) DEFAULT NULL,
  `is_synced` tinyint(1) DEFAULT '0',
  `customer_code` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_by_user_id` int DEFAULT NULL,
  `pos_system_id` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_code` (`customer_code`),
  KEY `customers_cities_id_foreign` (`cities_id`),
  KEY `customers_status_id_foreign` (`status_id`),
  KEY `customers_user_id_foreign` (`user_id`),
  CONSTRAINT `customers_cities_id_foreign` FOREIGN KEY (`cities_id`) REFERENCES `cities` (`id`),
  CONSTRAINT `customers_status_id_foreign` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`),
  CONSTRAINT `customers_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.customers: ~24 rows (approximately)
INSERT INTO `customers` (`id`, `customer_name`, `contact_number`, `cities_id`, `status_id`, `created_at`, `updated_at`, `email`, `address_line_1`, `address_line_2`, `due_amount`, `user_id`, `city_name`, `customer_id`, `loyalty_points`, `is_synced`, `customer_code`, `created_by_user_id`, `pos_system_id`) VALUES
	(1, 'Customer', '0786835563', 1, 1, '2025-03-15 21:21:56', '2025-03-21 22:25:18', 'Customer@gmail.com', 'Elpitiya', NULL, 2000.00, 1, 'Elpitiya', '1', 0.00, 0, NULL, NULL, NULL),
	(2, 'Jane Smith', '0719876543', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(4, 'Customer', '0786835563', NULL, NULL, NULL, NULL, 'Customer@gmail.com', 'Elpitiya', NULL, 1500.00, NULL, 'Elpitiya', NULL, NULL, 1, NULL, NULL, NULL),
	(5, 'Jane Smith', '0719876543', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(6, 'a', 'a', NULL, NULL, NULL, NULL, 'a@gmail.com', 'a', NULL, 0.00, NULL, 'a', NULL, NULL, 1, 'SOU-1772115402629', 7, 'POS-SRN-677'),
	(7, 's', '0768799999', NULL, NULL, NULL, NULL, 's@gmail.com', 'Galle', NULL, 0.00, NULL, 'Galle', NULL, NULL, 1, 'SOU-1772116212913', 7, 'POS-SRN-677'),
	(8, 'Hello', '0787878787', NULL, NULL, NULL, NULL, 'hello@gmail.com', 'Galle', NULL, 1000.00, NULL, 'Galle', NULL, NULL, 1, 'SOU-1772116557843', 7, 'POS-SRN-677'),
	(9, 'H', '078989898', NULL, NULL, NULL, NULL, 'h@gmail.com', 'Galle', NULL, 0.00, NULL, 'Galle', NULL, NULL, 1, 'SOU-1772117118856', 7, 'POS-SRN-677'),
	(10, 'S', '078787656', NULL, NULL, NULL, NULL, 's@gmail.com', '', NULL, 0.00, NULL, 'aa', NULL, NULL, 1, NULL, NULL, NULL),
	(11, 'Z', '078765432', NULL, NULL, NULL, NULL, 'z@gmail.com', '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(12, 'S', '078787656', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, 'SOU-1772117641280', 7, 'POS-SRN-677'),
	(13, 'Z', '078765432', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(14, 'M', '0765654343', NULL, NULL, NULL, NULL, 'm@gmail.com', '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(15, 'Z', '078765432', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, 'SOU-1772118046952', 7, 'POS-SRN-677'),
	(16, 'M', '0765654343', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(17, 'aaaaaaaaaaaaaaaaaaaaa', '0769876765', NULL, NULL, NULL, NULL, 'a@gmail.com', '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(18, 'M', '0765654343', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(19, 'aaaaaaaaaaaaaaaaaaaaa', '0769876765', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(20, 'M', '0765654343', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(21, 'aaaaaaaaaaaaaaaaaaaaa', '0769876765', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(22, 'M', '0765654343', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(23, 'aaaaaaaaaaaaaaaaaaaaa', '0769876765', NULL, NULL, NULL, NULL, NULL, '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
	(24, 'new customer', '0768765454', NULL, NULL, NULL, NULL, 'newcus@gmail.com', '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, 'SOU-1772118839359', 7, 'POS-SRN-677'),
	(25, 'Test2', '0768765656', NULL, NULL, NULL, NULL, 'test2@gmail.com', '', NULL, 0.00, NULL, NULL, NULL, NULL, 1, 'SOU-1772119004334', 7, 'POS-SRN-677');

-- Dumping structure for table hypermart_local.dealers
CREATE TABLE IF NOT EXISTS `dealers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.dealers: ~1 rows (approximately)
INSERT INTO `dealers` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'De1', '2025-04-05 13:50:30', '2025-04-05 13:50:30');

-- Dumping structure for table hypermart_local.distributors
CREATE TABLE IF NOT EXISTS `distributors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.distributors: ~1 rows (approximately)
INSERT INTO `distributors` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'D1', '2025-04-05 13:50:20', '2025-04-05 13:50:20');

-- Dumping structure for table hypermart_local.districts
CREATE TABLE IF NOT EXISTS `districts` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `province_id` bigint unsigned DEFAULT NULL,
  `name_en` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name_si` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name_ta` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `districts_province_id_foreign` (`province_id`),
  CONSTRAINT `districts_province_id_foreign` FOREIGN KEY (`province_id`) REFERENCES `provinces` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.districts: ~1 rows (approximately)
INSERT INTO `districts` (`id`, `province_id`, `name_en`, `name_si`, `name_ta`, `created_at`, `updated_at`) VALUES
	(1, 1, 'default', 'default', 'default', '2024-11-09 09:32:36', '2024-11-09 09:32:36');

-- Dumping structure for table hypermart_local.expense_categories
CREATE TABLE IF NOT EXISTS `expense_categories` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `expense_categories_user_id_foreign` (`user_id`),
  CONSTRAINT `expense_categories_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.expense_categories: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.expenses
CREATE TABLE IF NOT EXISTS `expenses` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `expense_title` varchar(225) COLLATE utf8mb3_bin NOT NULL,
  `details` longtext COLLATE utf8mb3_bin NOT NULL,
  `expense_date` date NOT NULL,
  `amount` decimal(8,2) NOT NULL,
  `expense_categories_id` bigint unsigned DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `expenses_expense_categories_id_foreign` (`expense_categories_id`),
  KEY `expenses_user_id_foreign` (`user_id`),
  CONSTRAINT `expenses_expense_categories_id_foreign` FOREIGN KEY (`expense_categories_id`) REFERENCES `expense_categories` (`id`),
  CONSTRAINT `expenses_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.expenses: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.failed_jobs
CREATE TABLE IF NOT EXISTS `failed_jobs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `connection` text COLLATE utf8mb3_bin NOT NULL,
  `queue` text COLLATE utf8mb3_bin NOT NULL,
  `payload` longtext COLLATE utf8mb3_bin NOT NULL,
  `exception` longtext COLLATE utf8mb3_bin NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.failed_jobs: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.hold_order_items
CREATE TABLE IF NOT EXISTS `hold_order_items` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `users_id` bigint unsigned DEFAULT NULL,
  `items_id` bigint unsigned DEFAULT NULL,
  `hold_orders_id` bigint unsigned DEFAULT NULL,
  `quantity` int NOT NULL,
  `discount_type` enum('FIXED','PERCENTAGE') COLLATE utf8mb3_bin NOT NULL DEFAULT 'FIXED',
  `discount` decimal(10,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hold_order_items_users_id_foreign` (`users_id`),
  KEY `hold_order_items_items_id_foreign` (`items_id`),
  KEY `hold_order_items_hold_orders_id_foreign` (`hold_orders_id`),
  CONSTRAINT `hold_order_items_hold_orders_id_foreign` FOREIGN KEY (`hold_orders_id`) REFERENCES `hold_orders` (`id`),
  CONSTRAINT `hold_order_items_items_id_foreign` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  CONSTRAINT `hold_order_items_users_id_foreign` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.hold_order_items: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.hold_orders
CREATE TABLE IF NOT EXISTS `hold_orders` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `customers_id` bigint unsigned DEFAULT NULL,
  `users_id` bigint unsigned DEFAULT NULL,
  `hold_reference` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `hold_status` enum('ACTIVE','DEACTIVE') COLLATE utf8mb3_bin NOT NULL DEFAULT 'ACTIVE',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hold_orders_customers_id_foreign` (`customers_id`),
  KEY `hold_orders_users_id_foreign` (`users_id`),
  CONSTRAINT `hold_orders_customers_id_foreign` FOREIGN KEY (`customers_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `hold_orders_users_id_foreign` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.hold_orders: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.inhouseitemstock
CREATE TABLE IF NOT EXISTS `inhouseitemstock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `qty` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.inhouseitemstock: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.item_categories
CREATE TABLE IF NOT EXISTS `item_categories` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `categories` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `description` varchar(500) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.item_categories: ~6 rows (approximately)
INSERT INTO `item_categories` (`id`, `categories`, `description`, `created_at`, `updated_at`) VALUES
	(1, 'Dairy Products', 'Milk, cheese, butter, yogurt, and other fresh dairy items.', '2025-07-16 00:25:01', '2025-07-16 00:25:01'),
	(2, 'Bakery & Eggs', 'Bread, pastries, eggs, and other bakery essentials.', '2025-07-16 00:25:02', '2025-07-16 00:25:02'),
	(3, 'Dry Goods & Pantry', 'Rice, sugar, flour, spices, and other non-perishable pantry staples.', '2025-07-16 00:26:55', '2025-07-16 00:26:55'),
	(4, 'Cooking Essentials', 'Oil, sauces, seasoning mixes, and cooking ingredients.', '2025-07-16 00:27:57', '2025-07-16 00:27:57'),
	(5, 'Canned & Packaged Goods', 'Canned meats, canned fish, ready-to-eat meals, and pantry items.', '2025-07-16 00:27:57', '2025-07-16 00:27:57'),
	(6, 'Household Supplies', 'Tissue, cleaning supplies, detergents, and other home essentials.', '2025-07-16 00:28:17', '2025-07-16 00:28:17');

-- Dumping structure for table hypermart_local.items
CREATE TABLE IF NOT EXISTS `items` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `item_code` varchar(225) COLLATE utf8mb3_bin NOT NULL,
  `barcode` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `item_name` varchar(225) COLLATE utf8mb3_bin NOT NULL,
  `item_name_2` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'Singlish name for searching',
  `scale_item` tinyint(1) NOT NULL DEFAULT '0',
  `scale_group_no` int DEFAULT NULL,
  `pos_order_no` int DEFAULT NULL,
  `suppliers_id` bigint unsigned DEFAULT NULL,
  `item_categories_id` bigint unsigned DEFAULT NULL,
  `quantity` int NOT NULL,
  `unit_type_id` int DEFAULT NULL,
  `minimum_qty` int DEFAULT NULL,
  `purchase_price` decimal(12,2) NOT NULL,
  `market_price` decimal(10,2) DEFAULT NULL,
  `retail_price` decimal(12,2) NOT NULL,
  `wholesale_price` decimal(12,2) NOT NULL,
  `additional_fees_percentage` decimal(5,2) NOT NULL DEFAULT '0.00',
  `additional_fees_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `start_qty` int DEFAULT NULL,
  `image_path` varchar(225) COLLATE utf8mb3_bin DEFAULT NULL,
  `status_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `description` text COLLATE utf8mb3_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `items_barcode_unique` (`barcode`),
  KEY `items_suppliers_id_foreign` (`suppliers_id`),
  KEY `items_status_id_foreign` (`status_id`),
  KEY `items_item_categories_id_foreign` (`item_categories_id`),
  CONSTRAINT `items_item_categories_id_foreign` FOREIGN KEY (`item_categories_id`) REFERENCES `item_categories` (`id`) ON DELETE SET NULL,
  CONSTRAINT `items_status_id_foreign` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`),
  CONSTRAINT `items_suppliers_id_foreign` FOREIGN KEY (`suppliers_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.items: ~5 rows (approximately)
INSERT INTO `items` (`id`, `item_code`, `barcode`, `item_name`, `item_name_2`, `scale_item`, `scale_group_no`, `pos_order_no`, `suppliers_id`, `item_categories_id`, `quantity`, `unit_type_id`, `minimum_qty`, `purchase_price`, `market_price`, `retail_price`, `wholesale_price`, `additional_fees_percentage`, `additional_fees_amount`, `start_qty`, `image_path`, `status_id`, `created_at`, `updated_at`, `description`) VALUES
	(1, 'TEST01', '1001', 'Fresh Milk 1L', NULL, 0, NULL, NULL, NULL, NULL, 975, NULL, NULL, 200.00, NULL, 250.00, 240.00, 0.00, 0.00, NULL, NULL, NULL, NULL, NULL, NULL),
	(2, 'I002', '1002', 'White Bread', NULL, 0, NULL, NULL, NULL, NULL, 992, NULL, NULL, 150.00, NULL, 180.00, 175.00, 0.00, 0.00, NULL, NULL, NULL, NULL, NULL, NULL),
	(3, 'I003', '1003', 'Sugar 1kg', NULL, 0, NULL, NULL, NULL, NULL, 988, NULL, NULL, 300.00, NULL, 350.00, 340.00, 0.00, 0.00, NULL, NULL, NULL, NULL, NULL, NULL),
	(4, 'I004', '1004', 'Tea Bags 50s', NULL, 0, NULL, NULL, NULL, NULL, 996, NULL, NULL, 400.00, NULL, 480.00, 460.00, 0.00, 0.00, NULL, NULL, NULL, NULL, NULL, NULL),
	(5, 'I005', '1005', 'Butter 200g', NULL, 0, NULL, NULL, NULL, NULL, 994, NULL, NULL, 500.00, NULL, 600.00, 580.00, 0.00, 0.00, NULL, NULL, NULL, NULL, NULL, NULL);

-- Dumping structure for table hypermart_local.loyalty_point_incomes
CREATE TABLE IF NOT EXISTS `loyalty_point_incomes` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `points_value` decimal(8,2) NOT NULL DEFAULT '0.00',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table hypermart_local.loyalty_point_incomes: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.migrations
CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `batch` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.migrations: ~39 rows (approximately)
INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
	(1, '2014_10_11_000000_create_statuses_table', 1),
	(2, '2014_10_11_000001_create_roles_table', 1),
	(3, '2014_10_12_000000_create_users_table', 1),
	(4, '2014_10_12_100000_create_password_reset_tokens_table', 1),
	(5, '2014_10_12_200000_add_two_factor_columns_to_users_table', 1),
	(6, '2019_08_19_000000_create_failed_jobs_table', 1),
	(7, '2019_12_14_000001_create_personal_access_tokens_table', 1),
	(8, '2024_11_25_132922_create_sessions_table', 1),
	(9, '2024_11_25_165000_create_suppliers_table', 1),
	(10, '2024_11_25_165647_create_items_table', 1),
	(11, '2024_11_25_170214_create_stock_updates_table', 1),
	(12, '2024_11_25_170325_create_expense_categories_table', 1),
	(13, '2024_11_25_170516_create_permissions_table', 1),
	(14, '2024_11_25_170605_create_roles_has_permissions_table', 1),
	(15, '2024_11_25_170644_create_expenses_table', 1),
	(16, '2024_11_25_170716_create_provinces_table', 1),
	(17, '2024_11_25_170802_create_districts_table', 1),
	(18, '2024_11_25_170823_create_cities_table', 1),
	(19, '2024_11_25_170842_create_customers_table', 1),
	(20, '2024_11_25_170905_create_settings_table', 1),
	(22, '2024_11_27_181716_create_sales_table', 2),
	(23, '2024_11_28_181801_create_sales_items_table', 2),
	(24, '2024_11_28_181823_create_hold_orders_table', 2),
	(25, '2024_11_28_181830_create_hold_order_items_table', 2),
	(26, '2024_11_29_151838_create_item_categories_table', 2),
	(29, '2024_12_09_174448_create_payments_table', 3),
	(30, '2025_12_11_141808_add_item_name_2_to_items_table', 4),
	(31, '2025_12_11_143634_add_category_id_to_items_table', 4),
	(32, '2025_12_11_175641_add_scale_item_to_items_table', 4),
	(33, '2025_12_11_205520_add_scale_group_no_to_items_table', 4),
	(34, '2025_12_14_175004_add_pos_order_no_to_items_table', 4),
	(35, '2025_12_16_104008_add_additional_fees_to_items_table', 4),
	(36, '2025_12_16_190543_add_market_price_to_items_table', 4),
	(37, '2025_12_17_124614_add_barcode_to_items_table', 4),
	(38, '2025_12_17_180650_add_bag_charge_to_payments_table', 4),
	(39, '2025_12_18_132747_add_service_charge_to_payments_table', 4),
	(40, '2025_12_19_181329_add_price_to_sales_items_table', 4),
	(41, '2025_12_28_013200_add_exp_date_to_stock_updates_table', 4),
	(42, '2026_01_04_175237_add_price_to_sales_return_items_table', 4);

-- Dumping structure for table hypermart_local.modells
CREATE TABLE IF NOT EXISTS `modells` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.modells: ~1 rows (approximately)
INSERT INTO `modells` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'M1', '2025-04-05 13:47:29', '2025-04-05 13:47:29');

-- Dumping structure for table hypermart_local.password_reset_tokens
CREATE TABLE IF NOT EXISTS `password_reset_tokens` (
  `email` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `token` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.password_reset_tokens: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.payments
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `sales_id` bigint unsigned DEFAULT NULL,
  `users_id` bigint unsigned DEFAULT NULL,
  `sub_total` decimal(14,2) DEFAULT NULL,
  `bag_charge` decimal(10,2) NOT NULL DEFAULT '0.00',
  `service_charge` decimal(10,2) NOT NULL DEFAULT '0.00',
  `grand_total` decimal(14,2) DEFAULT NULL,
  `paid_amount` decimal(14,2) DEFAULT NULL,
  `received_amount` decimal(14,2) DEFAULT NULL,
  `change_return_amount` decimal(14,2) DEFAULT NULL,
  `due_amount` decimal(14,2) DEFAULT NULL,
  `discount_type` enum('FIXED','PERCENTAGE') COLLATE utf8mb3_bin DEFAULT 'FIXED',
  `cheque_no` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `cheque_date` date DEFAULT NULL,
  `discount` decimal(14,2) DEFAULT NULL,
  `payment_type` enum('CASH','CARD','CHEQUE','CREDIT') COLLATE utf8mb3_bin DEFAULT 'CASH',
  `payment_status` enum('PAID','DUE','HOLD') COLLATE utf8mb3_bin DEFAULT 'PAID',
  `sales_note` longtext COLLATE utf8mb3_bin,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_synced` tinyint(1) NOT NULL DEFAULT '0',
  `pay_due_amount` decimal(14,2) DEFAULT '0.00',
  `paid_points` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `payments_sales_id_foreign` (`sales_id`),
  KEY `payments_users_id_foreign` (`users_id`),
  CONSTRAINT `payments_sales_id_foreign` FOREIGN KEY (`sales_id`) REFERENCES `sales` (`id`) ON DELETE CASCADE,
  CONSTRAINT `payments_users_id_foreign` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.payments: ~18 rows (approximately)
INSERT INTO `payments` (`id`, `sales_id`, `users_id`, `sub_total`, `bag_charge`, `service_charge`, `grand_total`, `paid_amount`, `received_amount`, `change_return_amount`, `due_amount`, `discount_type`, `cheque_no`, `cheque_date`, `discount`, `payment_type`, `payment_status`, `sales_note`, `created_at`, `updated_at`, `is_synced`, `pay_due_amount`, `paid_points`) VALUES
	(16, 16, 1, 610.00, 0.00, 0.00, 610.00, 610.00, 610.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(17, 17, 1, 1850.00, 0.00, 0.00, 1850.00, 1850.00, 1850.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(22, 22, 1, 1200.00, 0.00, 0.00, 1200.00, 1200.00, 1200.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(23, 23, 1, 430.00, 0.00, 0.00, 322.50, 0.00, 0.00, 0.00, 322.50, 'FIXED', NULL, NULL, 107.50, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(24, 24, 1, 680.00, 0.00, 0.00, 680.00, 680.00, 1000.00, 320.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(25, 25, 1, 1700.00, 0.00, 0.00, 1700.00, 1000.00, 1000.00, 0.00, 700.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(26, 26, 1, 950.00, 0.00, 0.00, 950.00, 950.00, 1000.00, 50.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(27, 27, 1, 880.00, 0.00, 0.00, 880.00, 880.00, 1000.00, 120.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(28, 28, 1, 500.00, 0.00, 0.00, 500.00, 500.00, 1000.00, 500.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(29, 29, 1, 880.00, 0.00, 0.00, 880.00, 880.00, 1000.00, 120.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(30, 30, 1, 610.00, 0.00, 0.00, 610.00, 610.00, 1000.00, 390.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(31, 31, 1, 1210.00, 0.00, 0.00, 1210.00, 1210.00, 1000.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(32, 32, 1, 4170.00, 0.00, 0.00, 4170.00, 4170.00, 2000.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(33, 33, 1, 1330.00, 0.00, 0.00, 1330.00, 1330.00, 1000.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(34, 34, 1, 1200.00, 0.00, 0.00, 1200.00, 1200.00, 1000.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(35, 35, 1, 1000.00, 0.00, 0.00, 1000.00, 1000.00, 500.00, 0.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00),
	(36, 36, 1, 500.00, 0.00, 0.00, 500.00, 0.00, 0.00, 0.00, 500.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'DUE', NULL, NULL, NULL, 0, 0.00, 0.00),
	(37, 37, 1, 960.00, 0.00, 0.00, 960.00, 960.00, 1000.00, 40.00, 0.00, 'FIXED', NULL, NULL, 0.00, 'CASH', 'PAID', NULL, NULL, NULL, 0, 0.00, 0.00);

-- Dumping structure for table hypermart_local.permissions
CREATE TABLE IF NOT EXISTS `permissions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `permissions_name` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.permissions: ~77 rows (approximately)
INSERT INTO `permissions` (`id`, `permissions_name`, `created_at`, `updated_at`) VALUES
	(1, 'aaaaa1111', '2024-12-16 08:08:26', '2024-12-16 08:08:40'),
	(10, 'dashboards', '2024-12-16 02:22:36', '2024-12-17 02:30:08'),
	(17, 'Access_Dashbord', '2024-12-16 04:14:57', '2024-12-16 04:14:57'),
	(18, 'Access_Billing', '2024-12-16 04:15:08', '2024-12-16 04:15:08'),
	(19, 'Access_Items', '2024-12-16 04:15:20', '2024-12-16 04:15:20'),
	(20, 'Access_Stock', '2024-12-16 04:15:31', '2024-12-16 04:15:31'),
	(21, 'Access_Sales', '2024-12-16 04:15:42', '2024-12-16 04:15:42'),
	(22, 'Access_Users', '2024-12-16 04:15:56', '2024-12-16 04:15:56'),
	(23, 'Access_Customers', '2024-12-16 04:16:12', '2024-12-16 04:16:12'),
	(24, 'Access_Suppliers', '2024-12-16 04:16:25', '2024-12-16 04:16:25'),
	(25, 'Access_Expenses', '2024-12-16 04:16:42', '2024-12-16 04:16:42'),
	(26, 'Access_Reports', '2024-12-16 04:16:55', '2024-12-16 04:16:55'),
	(27, 'Access_Settings', '2024-12-16 04:17:07', '2024-12-16 04:17:07'),
	(28, 'Add new Item', '2024-12-16 22:02:19', '2024-12-16 22:02:19'),
	(29, 'Add New User', '2024-12-17 00:55:52', '2024-12-17 00:55:52'),
	(30, 'Add New Role', '2024-12-17 00:55:59', '2024-12-17 00:55:59'),
	(31, 'Add New Permission', '2024-12-17 00:56:16', '2024-12-17 00:56:16'),
	(32, 'User List View', '2024-12-17 00:56:31', '2024-12-17 00:56:31'),
	(33, 'Role List View', '2024-12-17 00:56:43', '2024-12-17 00:56:43'),
	(34, 'Permission List View', '2024-12-17 00:56:56', '2024-12-17 00:56:56'),
	(35, 'User Status Control', '2024-12-17 00:57:31', '2024-12-17 00:57:31'),
	(36, 'User Update', '2024-12-17 00:57:38', '2024-12-17 00:57:38'),
	(37, 'Role Update', '2024-12-17 00:58:10', '2024-12-17 00:58:10'),
	(38, 'Permission Update', '2024-12-17 00:58:20', '2024-12-17 00:58:20'),
	(39, 'Add New Customers', '2024-12-17 02:41:41', '2024-12-17 02:41:41'),
	(40, 'View Customer List', '2024-12-17 02:41:55', '2024-12-16 18:30:00'),
	(41, 'Import Customers', '2024-12-16 21:12:10', '2024-12-16 21:12:10'),
	(42, 'Update Customers', '2024-12-16 21:12:19', '2024-12-16 21:12:19'),
	(43, 'Delete Customers', '2024-12-16 21:12:31', '2024-12-16 21:12:31'),
	(44, 'Add New Supplier', '2024-12-16 21:38:38', '2024-12-16 21:38:38'),
	(45, 'View Supplier List', '2024-12-16 21:38:55', '2024-12-16 21:38:55'),
	(46, 'Import Suppliers', '2024-12-16 21:39:14', '2024-12-16 21:39:14'),
	(47, 'Update Suppliers', '2024-12-16 21:39:24', '2024-12-16 21:39:24'),
	(48, 'Delete Suppliers', '2024-12-16 21:39:36', '2024-12-16 21:39:36'),
	(49, 'Add New Items', '2024-12-16 22:31:08', '2024-12-16 22:31:08'),
	(50, 'Add New Category', '2024-12-16 22:31:23', '2024-12-16 22:31:23'),
	(51, 'View Item List', '2024-12-16 22:31:37', '2024-12-16 22:31:37'),
	(52, 'View Item Category List', '2024-12-16 22:32:25', '2024-12-16 22:32:25'),
	(53, 'Update Item Category List', '2024-12-16 22:32:44', '2024-12-16 22:32:44'),
	(54, 'Delete Item Category', '2024-12-16 22:37:50', '2024-12-16 22:37:50'),
	(55, 'Delete Items', '2024-12-16 22:38:05', '2024-12-16 22:38:05'),
	(56, 'Update Items', '2024-12-16 22:41:56', '2024-12-16 22:41:56'),
	(57, 'Add Stock', '2024-12-17 02:45:46', '2024-12-17 02:45:46'),
	(58, 'Import Item', '2024-12-22 21:02:16', '2024-12-22 21:02:16'),
	(59, 'Suppliers Status Control', '2024-12-22 21:06:31', '2024-12-22 21:06:31'),
	(60, 'Billing', '2024-12-23 17:34:52', '2024-12-23 17:34:52'),
	(61, 'Add Sales Returns', '2024-12-23 17:35:05', '2024-12-23 17:35:05'),
	(62, 'Sales Items List', '2024-12-23 17:35:23', '2024-12-23 17:35:23'),
	(63, 'Sales Return List', '2024-12-23 17:35:37', '2024-12-23 17:35:37'),
	(64, 'Process Return', '2024-12-23 17:36:22', '2024-12-23 17:36:22'),
	(65, 'View Return List', '2024-12-26 17:50:49', '2024-12-26 17:50:49'),
	(66, 'Pay Due Amount', '2025-01-03 18:28:15', '2025-01-03 18:28:15'),
	(67, 'View Payment Details', '2025-01-03 18:28:28', '2025-01-03 18:28:28'),
	(68, 'Site Setting', '2025-01-03 18:28:37', '2025-01-03 18:28:37'),
	(69, 'Change Password', '2025-01-03 18:28:47', '2025-01-03 18:28:47'),
	(70, 'Add New Expense', '2025-01-05 00:27:57', '2025-01-05 00:27:57'),
	(71, 'Add New Expense Category', '2025-01-05 00:28:12', '2025-01-05 00:28:12'),
	(72, 'Expenses List', '2025-01-05 00:28:25', '2025-01-05 00:28:25'),
	(73, 'Expenses Category List', '2025-01-05 00:28:38', '2025-01-05 00:28:38'),
	(74, 'Edit Expenses', '2025-01-05 01:24:16', '2025-01-05 01:24:16'),
	(75, 'Delete Expenses', '2025-01-05 01:24:24', '2025-01-05 01:24:24'),
	(76, 'Edit Expenses Category', '2025-01-05 01:24:44', '2025-01-05 01:24:44'),
	(77, 'Delete Expenses Category', '2025-01-05 01:24:54', '2025-01-05 01:24:54'),
	(78, 'Access Due Amount', '2025-01-05 16:10:56', '2025-01-05 16:10:56'),
	(79, 'view sales report', '2025-01-12 02:15:29', '2025-01-12 02:15:29'),
	(80, 'view item report', '2025-01-12 02:16:27', '2025-01-12 02:16:27'),
	(81, 'view expenses report', '2025-01-12 02:16:38', '2025-01-12 02:16:38'),
	(82, 'Change Site Setting', '2025-01-20 02:13:32', '2025-01-20 02:13:32'),
	(83, 'Generate Stock Report', '2025-01-20 02:14:08', '2025-01-20 02:14:08'),
	(84, 'InHouse_admin', '2025-02-28 08:27:28', '2025-02-28 08:27:28'),
	(85, 'Add Mobile Items', '2025-03-06 11:28:06', '2025-03-06 11:28:06'),
	(86, 'View Mobile Items', '2025-03-06 11:28:20', '2025-03-06 11:28:20'),
	(87, 'Update Mobile Items', '2025-03-06 11:28:28', '2025-03-06 11:28:28'),
	(88, 'Add Mobile IMEI', '2025-03-06 11:28:38', '2025-03-06 11:28:38'),
	(89, 'Control Mobile Item Status', '2025-03-06 11:28:57', '2025-03-06 11:28:57'),
	(90, 'Access Mobile Billing', '2025-03-06 11:29:09', '2025-03-06 11:29:09'),
	(91, 'Access Mobile Section', '2025-03-06 11:30:25', '2025-03-06 11:30:25');

-- Dumping structure for table hypermart_local.personal_access_tokens
CREATE TABLE IF NOT EXISTS `personal_access_tokens` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tokenable_type` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `tokenable_id` bigint unsigned NOT NULL,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `token` varchar(64) COLLATE utf8mb3_bin NOT NULL,
  `abilities` text COLLATE utf8mb3_bin,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.personal_access_tokens: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.provinces
CREATE TABLE IF NOT EXISTS `provinces` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name_en` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name_si` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `name_ta` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.provinces: ~1 rows (approximately)
INSERT INTO `provinces` (`id`, `name_en`, `name_si`, `name_ta`, `created_at`, `updated_at`) VALUES
	(1, 'default', 'default', 'default', '2024-11-09 09:32:36', '2024-11-09 09:32:36');

-- Dumping structure for table hypermart_local.purchase_status
CREATE TABLE IF NOT EXISTS `purchase_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.purchase_status: ~2 rows (approximately)
INSERT INTO `purchase_status` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'Unsold', '2025-03-05 02:46:53', '2025-03-05 02:46:53'),
	(2, 'Sold', '2025-03-05 02:47:13', '2025-03-05 02:47:13');

-- Dumping structure for table hypermart_local.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.roles: ~3 rows (approximately)
INSERT INTO `roles` (`id`, `role_name`, `created_at`, `updated_at`) VALUES
	(1, 'admin', '2024-12-16 08:08:56', '2024-12-16 08:08:56'),
	(2, 'manager', '2024-12-21 12:51:52', '2024-12-21 12:51:52'),
	(3, 'Super Admin', '2025-01-10 10:44:11', '2025-01-10 10:44:11');

-- Dumping structure for table hypermart_local.roles_has_permissions
CREATE TABLE IF NOT EXISTS `roles_has_permissions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `roles_id` bigint unsigned DEFAULT NULL,
  `permissions_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roles_has_permissions_roles_id_foreign` (`roles_id`),
  KEY `roles_has_permissions_permissions_id_foreign` (`permissions_id`),
  CONSTRAINT `roles_has_permissions_permissions_id_foreign` FOREIGN KEY (`permissions_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `roles_has_permissions_roles_id_foreign` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.roles_has_permissions: ~107 rows (approximately)
INSERT INTO `roles_has_permissions` (`id`, `roles_id`, `permissions_id`, `created_at`, `updated_at`) VALUES
	(1, 1, 1, NULL, NULL),
	(2, 1, 10, NULL, NULL),
	(3, 1, 17, NULL, NULL),
	(4, 1, 18, NULL, NULL),
	(5, 1, 19, NULL, NULL),
	(6, 1, 20, NULL, NULL),
	(7, 1, 21, NULL, NULL),
	(8, 1, 22, NULL, NULL),
	(9, 1, 23, NULL, NULL),
	(10, 1, 24, NULL, NULL),
	(11, 1, 25, NULL, NULL),
	(12, 1, 26, NULL, NULL),
	(13, 1, 27, NULL, NULL),
	(14, 1, 28, NULL, NULL),
	(15, 1, 29, NULL, NULL),
	(16, 1, 30, NULL, NULL),
	(17, 1, 31, NULL, NULL),
	(18, 1, 32, NULL, NULL),
	(19, 1, 33, NULL, NULL),
	(20, 1, 34, NULL, NULL),
	(21, 1, 35, NULL, NULL),
	(22, 1, 36, NULL, NULL),
	(23, 1, 37, NULL, NULL),
	(24, 1, 38, NULL, NULL),
	(25, 1, 39, NULL, NULL),
	(26, 1, 40, NULL, NULL),
	(27, 2, 1, NULL, NULL),
	(28, 2, 10, NULL, NULL),
	(29, 2, 17, NULL, NULL),
	(30, 2, 18, NULL, NULL),
	(31, 2, 19, NULL, NULL),
	(32, 2, 20, NULL, NULL),
	(33, 2, 21, NULL, NULL),
	(34, 2, 22, NULL, NULL),
	(35, 2, 23, NULL, NULL),
	(36, 2, 24, NULL, NULL),
	(37, 2, 25, NULL, NULL),
	(38, 2, 26, NULL, NULL),
	(39, 2, 27, NULL, NULL),
	(40, 2, 28, NULL, NULL),
	(41, 2, 29, NULL, NULL),
	(42, 2, 30, NULL, NULL),
	(43, 2, 31, NULL, NULL),
	(44, 2, 32, NULL, NULL),
	(45, 2, 33, NULL, NULL),
	(46, 2, 34, NULL, NULL),
	(47, 2, 35, NULL, NULL),
	(48, 2, 36, NULL, NULL),
	(49, 2, 37, NULL, NULL),
	(50, 2, 38, NULL, NULL),
	(51, 2, 39, NULL, NULL),
	(52, 2, 40, NULL, NULL),
	(53, 1, 41, NULL, NULL),
	(54, 1, 42, NULL, NULL),
	(55, 1, 43, NULL, NULL),
	(56, 1, 44, NULL, NULL),
	(57, 1, 45, NULL, NULL),
	(58, 1, 46, NULL, NULL),
	(59, 1, 47, NULL, NULL),
	(60, 1, 48, NULL, NULL),
	(61, 1, 49, NULL, NULL),
	(62, 1, 50, NULL, NULL),
	(63, 1, 51, NULL, NULL),
	(64, 1, 52, NULL, NULL),
	(65, 1, 53, NULL, NULL),
	(66, 1, 54, NULL, NULL),
	(67, 1, 55, NULL, NULL),
	(68, 1, 56, NULL, NULL),
	(69, 1, 57, NULL, NULL),
	(70, 1, 58, NULL, NULL),
	(71, 1, 59, NULL, NULL),
	(72, 1, 60, NULL, NULL),
	(73, 1, 61, NULL, NULL),
	(74, 1, 62, NULL, NULL),
	(75, 1, 63, NULL, NULL),
	(76, 1, 64, NULL, NULL),
	(77, 1, 65, NULL, NULL),
	(78, 1, 66, NULL, NULL),
	(79, 1, 67, NULL, NULL),
	(80, 1, 68, NULL, NULL),
	(81, 1, 69, NULL, NULL),
	(82, 1, 70, NULL, NULL),
	(83, 1, 71, NULL, NULL),
	(84, 1, 72, NULL, NULL),
	(85, 1, 73, NULL, NULL),
	(86, 1, 74, NULL, NULL),
	(87, 1, 75, NULL, NULL),
	(88, 1, 76, NULL, NULL),
	(89, 1, 77, NULL, NULL),
	(90, 1, 78, NULL, NULL),
	(91, 3, 1, NULL, NULL),
	(92, 3, 10, NULL, NULL),
	(93, 3, 20, NULL, NULL),
	(94, 3, 21, NULL, NULL),
	(95, 1, 79, NULL, NULL),
	(96, 1, 80, NULL, NULL),
	(97, 1, 81, NULL, NULL),
	(98, 1, 82, NULL, NULL),
	(99, 1, 83, NULL, NULL),
	(100, 1, 84, NULL, NULL),
	(101, 1, 91, NULL, NULL),
	(102, 1, 85, NULL, NULL),
	(104, 1, 90, NULL, NULL),
	(105, 1, 86, NULL, NULL),
	(106, 1, 87, NULL, NULL),
	(107, 1, 88, NULL, NULL),
	(108, 1, 89, NULL, NULL);

-- Dumping structure for table hypermart_local.sales
CREATE TABLE IF NOT EXISTS `sales` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `sales_code` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `pos_system_id` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `hold_ref_name` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `customers_id` bigint unsigned DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status_id` int DEFAULT '1',
  `users_id` bigint unsigned DEFAULT NULL,
  `warranty_period` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `warranty_card_no` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_synced` tinyint(1) NOT NULL DEFAULT '0',
  `notes` text COLLATE utf8mb3_bin,
  `commission` decimal(14,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `sales_customers_id_foreign` (`customers_id`),
  KEY `sales_users_id_foreign` (`users_id`),
  CONSTRAINT `sales_customers_id_foreign` FOREIGN KEY (`customers_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `sales_users_id_foreign` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.sales: ~18 rows (approximately)
INSERT INTO `sales` (`id`, `sales_code`, `pos_system_id`, `hold_ref_name`, `customers_id`, `total_amount`, `date`, `status_id`, `users_id`, `warranty_period`, `warranty_card_no`, `created_at`, `updated_at`, `is_synced`, `notes`, `commission`) VALUES
	(16, 'SALE-1772029319618', 'POS-SRN-677', NULL, 1, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, NULL, 0.00),
	(17, 'SALE-1772029610349', 'POS-SRN-677', NULL, 1, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, NULL, 0.00),
	(22, 'SALE-1772033245108', 'POS-SRN-677', NULL, NULL, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, NULL, 0.00),
	(23, 'SALE-1772038913083', 'POS-SRN-677', NULL, NULL, NULL, NULL, 1, 1, '', '', NULL, NULL, 0, '', 0.00),
	(24, 'SALE-1772040180326', 'POS-SRN-677', NULL, NULL, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(25, 'SALE-1772042040396', 'POS-SRN-677', NULL, 1, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(26, 'SALE-1772076812317', 'POS-SRN-677', NULL, 1, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(27, 'SALE-1772078576641', 'POS-SRN-677', NULL, 1, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(28, 'SALE-1772117769571', 'POS-SRN-677', NULL, 8, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(29, 'SALE-1772118124389', 'POS-SRN-677', NULL, 15, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(30, 'SALE-1772118905394', 'POS-SRN-677', NULL, 24, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(31, 'SALE-1772119320409', 'POS-SRN-677', NULL, 25, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(32, 'SALE-1772121027995', 'POS-SRN-677', NULL, 25, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(33, 'SALE-1772123204023', 'POS-SRN-677', NULL, 25, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(34, 'SALE-1772123477929', 'POS-SRN-677', NULL, 24, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(35, 'SALE-1772124033335', 'POS-SRN-677', NULL, 24, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(36, 'SALE-1772127964545', 'POS-SRN-677', NULL, 1, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00),
	(37, 'SALE-1772128015384', 'POS-SRN-677', NULL, 25, NULL, NULL, 1, 1, '', '', NULL, NULL, 1, '', 0.00);

-- Dumping structure for table hypermart_local.sales_due_payments
CREATE TABLE IF NOT EXISTS `sales_due_payments` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `amount_paid` decimal(12,2) NOT NULL,
  `payment_type` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `sale_id` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `cheque_number` varchar(20) COLLATE utf8mb3_bin DEFAULT NULL,
  `cheque_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.sales_due_payments: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.sales_items
CREATE TABLE IF NOT EXISTS `sales_items` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `items_id` bigint unsigned DEFAULT NULL,
  `sales_id` bigint unsigned DEFAULT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `quantity` int DEFAULT NULL,
  `discount_type` enum('FIXED','PERCENTAGE') COLLATE utf8mb3_bin DEFAULT 'FIXED',
  `discount` decimal(10,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_synced` tinyint(1) NOT NULL DEFAULT '0',
  `return_quantity` int DEFAULT NULL,
  `status` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sales_items_items_id_foreign` (`items_id`),
  KEY `sales_items_sales_id_foreign` (`sales_id`),
  CONSTRAINT `sales_items_items_id_foreign` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  CONSTRAINT `sales_items_sales_id_foreign` FOREIGN KEY (`sales_id`) REFERENCES `sales` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.sales_items: ~34 rows (approximately)
INSERT INTO `sales_items` (`id`, `items_id`, `sales_id`, `price`, `quantity`, `discount_type`, `discount`, `created_at`, `updated_at`, `is_synced`, `return_quantity`, `status`) VALUES
	(41, 1, 16, 250.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(42, 2, 16, 180.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(43, 1, 17, 500.00, 3, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(44, 3, 17, 350.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(54, 1, 22, 250.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(55, 3, 22, 350.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(56, 1, 24, 250.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(57, 2, 24, 180.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(58, 1, 25, 250.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(59, 5, 25, 600.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(60, 1, 26, 250.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(61, 3, 26, 350.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(62, 2, 27, 180.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(63, 3, 27, 350.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(64, 1, 28, 250.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(65, 2, 29, 180.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(66, 3, 29, 350.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(67, 1, 30, 250.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(68, 2, 30, 180.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(69, 1, 31, 250.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(70, 4, 31, 480.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(71, 1, 32, 250.00, 6, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(72, 2, 32, 180.00, 3, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(73, 3, 32, 350.00, 3, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(74, 5, 32, 600.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(75, 4, 32, 480.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(76, 1, 33, 250.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(77, 4, 33, 480.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(78, 5, 33, 600.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(79, 5, 34, 600.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(80, 1, 35, 250.00, 4, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(81, 1, 36, 250.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(82, 2, 37, 180.00, 2, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL),
	(83, 5, 37, 600.00, 1, 'FIXED', 0.00, NULL, NULL, 0, NULL, NULL);

-- Dumping structure for table hypermart_local.sales_return_items
CREATE TABLE IF NOT EXISTS `sales_return_items` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `item_id` bigint unsigned NOT NULL,
  `sales_id` bigint unsigned NOT NULL,
  `return_quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `status` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sales_return_items_item_id_foreign` (`item_id`),
  KEY `sales_return_items_sales_id_foreign` (`sales_id`),
  CONSTRAINT `sales_return_items_item_id_foreign` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`),
  CONSTRAINT `sales_return_items_sales_id_foreign` FOREIGN KEY (`sales_id`) REFERENCES `sales` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.sales_return_items: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.sessions
CREATE TABLE IF NOT EXISTS `sessions` (
  `id` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `ip_address` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `user_agent` text COLLATE utf8mb3_bin,
  `payload` longtext COLLATE utf8mb3_bin NOT NULL,
  `last_activity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sessions_last_activity_index` (`last_activity`),
  KEY `sessions_user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.sessions: ~13 rows (approximately)
INSERT INTO `sessions` (`id`, `user_id`, `ip_address`, `user_agent`, `payload`, `last_activity`) VALUES
	('19nEfaOSFNMHjPYNydXfECINzmLp6BqhJS61awek', NULL, '112.135.67.39', 'WhatsApp/2.2606.101 W', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiTXlET0JQamVoenJ1WmlJb1hBWHJDd2d4NHBlTGltNUdZQm9GQXQ2VCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1771768693),
	('4WQOjHrloWlYELGQDnnHVznQRhWvcEDwXGsIaKGf', NULL, '154.28.229.131', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoidFd3TEdkSGIwRGxMbTZrNmhEU0txYlJ2ZjNNZjNibVBtd2pQVHBLRSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1771768173),
	('CAquWTMebX3IPE5NbAGPRfDFCRYLpQtOjWVeHNJE', 7, '111.223.186.219', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoiWldNMExCTTdTQ3hPS3J6OUJWOEt0ZEVuR0dRSG5sYlB6MHpLTmRpZiI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDg6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20vaXRlbS9hZGRfaXRlbSI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fXM6NTA6ImxvZ2luX3dlYl81OWJhMzZhZGRjMmIyZjk0MDE1ODBmMDE0YzdmNThlYTRlMzA5ODlkIjtpOjc7fQ==', 1771769213),
	('CD66oEZVDLcTGqxrP5xK9WwCRJ3hYbLs8XQHdBB7', NULL, '188.119.118.85', 'Mozilla/5.0 (iPhone; CPU iPhone OS 18_7_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/143.0.7499.151 Mobile/15E148 Safari/604.1', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoia1lrYTdSVnVYNkJpbEUwRU9kR3NydU1EWVVGODI5MlI1c3N5MjE1cCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6Mzg6Imh0dHBzOi8vd3d3Lmh5cGVybWFydC5vbmxpbmVzeXRlbXMuY29tIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1771768357),
	('FpFwc3CaYqP8LmPonT6k4e7kgZDYmLMmU8gss8er', 7, '116.206.244.144', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36 Edg/145.0.0.0', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoibHhrczYxdHQxaVpENXI2QnJzUlAzNlU1RjNIbm5lR3cxNUhvQ2R4eiI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20vZGFzaGJvYXJkIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319czo1MDoibG9naW5fd2ViXzU5YmEzNmFkZGMyYjJmOTQwMTU4MGYwMTRjN2Y1OGVhNGUzMDk4OWQiO2k6Nzt9', 1771768908),
	('ZHvdT5fXzXJPsJHZtUXRRFFTHWkYSEDZr3wr8Kzr', NULL, '162.43.228.50', 'Mozilla/5.0 (iPhone; CPU iPhone OS 18_7_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/143.0.7499.151 Mobile/15E148 Safari/604.1', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoibzEzNXBWNGhjRVNidG12VWxzQ0VLN0kyRXVxMElrYk1EYzBwcnI4OSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1771768288),
	('b8B7DvRUwZjwLCPTkQM1jTm3skRtKJijsvJg6TDJ', NULL, '104.252.191.52', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiYzQzMzhHUjlPRWoxMkdsdEZyemljaWNYSkZwVklYVTdUdTRQRGRQVyI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1771768175),
	('emOH15ncHuDBTBeimOt8OLHpP8KFnC3L27aIu8aV', NULL, '44.211.133.150', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiMHdkRDR1R2tZYnJISVVaZlR1TFk5eHQ2SWVreFZjRjI1bGlLZk5hNCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzM6Imh0dHA6Ly9oeXBlcm1hcnQub25saW5lc3l0ZW1zLmNvbSI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fX0=', 1771768473),
	('hTP8XnfzUcyVtlat9UV0LM9mGXQ6QQ3UyG43gJ4Z', NULL, '103.196.9.210', 'Mozilla/5.0 (iPhone; CPU iPhone OS 18_7_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/143.0.7499.151 Mobile/15E148 Safari/604.1', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiUE15ZzkwMHlleE5UV0tUMW5PVHRDZUNSbExLRjRJeGJITWhzSzhhSSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6Mzg6Imh0dHBzOi8vd3d3Lmh5cGVybWFydC5vbmxpbmVzeXRlbXMuY29tIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1771768290),
	('kK74vwhWAxlwRUMYZ277SlJqCxiNm4FjPuXm84vL', 7, '112.135.67.39', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoiS09JS1N1WWlxQXAyQ05tUWVWZDFOUVVvakFkSldEN0dtMUdXYlNaUyI7czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDg6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20vc2FsZXMvYmlsbGluZyI7fXM6NTA6ImxvZ2luX3dlYl81OWJhMzZhZGRjMmIyZjk0MDE1ODBmMDE0YzdmNThlYTRlMzA5ODlkIjtpOjc7fQ==', 1771769111),
	('lQ2EDyzBDO26RQF7Zelh3VVytYOgVEnWEPihV3zG', NULL, '116.206.244.144', 'WhatsApp/2.2605.103 W', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiNGk3RWFNUDZtS2RDeVB1WWtHb2poOFVsbEtNZ3FNblZlMTNRdzhmYiI7czo2OiJfZmxhc2giO2E6Mjp7czozOiJuZXciO2E6MDp7fXM6Mzoib2xkIjthOjA6e319czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO319', 1771768783),
	('nnsKdUkbmsohOm3bO1N3Hq3mYlEvso1xhRtcReWY', NULL, '104.168.71.98', 'Mozilla/5.0 (iPhone; CPU iPhone OS 18_7_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/143.0.7499.151 Mobile/15E148 Safari/604.1', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoienVDRXdLT2VTeksyeWs0Q0M3WTRMT2x5ZXNGbnBaeUZQU0xFaEducSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1771768288),
	('sAKyJkr7V4FgJYSfIYg10yTQwx3WDcQjtwwHLkZ4', NULL, '104.252.191.52', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoieGEyS3VVaTZXWkpSNDFxWWlZSnAxMGE3S0VYeFVxVWh3UGZaUXR4ayI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzQ6Imh0dHBzOi8vaHlwZXJtYXJ0Lm9ubGluZXN5dGVtcy5jb20iO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1771768163);

-- Dumping structure for table hypermart_local.settings
CREATE TABLE IF NOT EXISTS `settings` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `value` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.settings: ~16 rows (approximately)
INSERT INTO `settings` (`id`, `key`, `value`, `created_at`, `updated_at`) VALUES
	(1, 'Login Page Image', 'Company Logo/1771768350_unnamed.jpg', '2025-01-19 18:24:24', '2026-02-23 02:22:30'),
	(2, 'Login Page Background Color', '#11650b', '2025-01-19 18:24:45', '2025-12-08 12:09:53'),
	(3, 'Login Page Header Text Color', '#11650b', '2025-01-19 18:25:13', '2025-12-08 12:10:16'),
	(4, 'Login Button Color', '#11650b', '2025-01-19 18:26:00', '2025-10-28 15:30:25'),
	(5, 'Login Button Text Color', '#ffffff', '2025-01-19 18:27:06', '2025-03-03 23:55:06'),
	(6, 'Company Name', 'Hypermart', '2025-01-19 18:29:00', '2026-02-23 02:19:54'),
	(7, 'Header Color', '#11650b', '2025-01-19 18:29:18', '2025-12-08 12:11:53'),
	(8, 'Footer Color', '#11650b', '2025-01-19 18:29:49', '2025-12-08 12:12:04'),
	(9, 'Company Address', 'Kandy', '2025-01-19 18:30:34', '2025-10-28 15:31:05'),
	(10, 'Company Contact No', '07XXXXXXXX', '2025-01-19 18:30:54', '2025-10-28 15:31:30'),
	(11, 'Company Mobile No', '07XXXXXXXX', '2025-01-19 18:30:54', '2025-10-28 15:31:57'),
	(12, 'Company Web site URL', 'support@hypermart.onlinesytems.com', '2025-01-19 18:32:40', '2026-02-23 02:24:17'),
	(13, 'Header Icon', 'Company Logo/1771768982_unnamed.ico', NULL, '2026-02-23 02:33:02'),
	(14, 'Header Icon And Font Color', '#000000', NULL, '2025-12-08 12:13:50'),
	(15, 'Header Title Color', '#ffffff', NULL, '2025-01-21 23:53:29'),
	(16, 'Footer Text Color', '#ffffff', NULL, '2025-01-21 23:53:38');

-- Dumping structure for table hypermart_local.site_settings
CREATE TABLE IF NOT EXISTS `site_settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `sidebar_one_name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `sidebar_two_name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `contact_number` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `company_logo` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.site_settings: ~1 rows (approximately)
INSERT INTO `site_settings` (`id`, `site_name`, `sidebar_one_name`, `sidebar_two_name`, `contact_number`, `company_logo`, `created_at`, `updated_at`) VALUES
	(2, 'Hypermart', 'Hypermart', 'Hypermart', '0786835520', 'Company Logo/1771768360_unnamed.jpg', '2024-12-29 22:47:19', '2026-02-23 02:22:40');

-- Dumping structure for table hypermart_local.statuses
CREATE TABLE IF NOT EXISTS `statuses` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `status_name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.statuses: ~2 rows (approximately)
INSERT INTO `statuses` (`id`, `status_name`, `created_at`, `updated_at`) VALUES
	(1, 'Active', '2024-11-09 09:32:36', '2024-11-09 09:32:36'),
	(2, 'Inactive', '2024-11-09 09:32:36', '2024-11-09 09:32:36');

-- Dumping structure for table hypermart_local.stock_updates
CREATE TABLE IF NOT EXISTS `stock_updates` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `items_id` bigint unsigned DEFAULT NULL,
  `stock` int NOT NULL,
  `status` int NOT NULL,
  `note` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `exp_date` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stock_updates_user_id_foreign` (`user_id`),
  KEY `stock_updates_items_id_foreign` (`items_id`),
  CONSTRAINT `stock_updates_items_id_foreign` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  CONSTRAINT `stock_updates_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.stock_updates: ~0 rows (approximately)

-- Dumping structure for table hypermart_local.suppliers
CREATE TABLE IF NOT EXISTS `suppliers` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `contact_number` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `email` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `address` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `status_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  `city_name` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `suppliers_user_id_foreign` (`user_id`),
  KEY `suppliers_status_id_foreign` (`status_id`),
  CONSTRAINT `suppliers_status_id_foreign` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`),
  CONSTRAINT `suppliers_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.suppliers: ~2 rows (approximately)
INSERT INTO `suppliers` (`id`, `supplier_name`, `contact_number`, `email`, `address`, `user_id`, `status_id`, `created_at`, `updated_at`, `city_id`, `city_name`) VALUES
	(1, 'Supplier1', '0786835535', 'Supplier1@gmail.com', NULL, 1, 1, '2025-03-15 21:23:10', '2025-03-23 17:11:30', 1, NULL),
	(2, 'supplier 2', '0704688588', 'supplier2@gmail.com', '140,High level Road, Nugegoda', 7, 1, '2025-07-16 00:23:48', '2025-07-16 00:23:48', 1, NULL);

-- Dumping structure for table hypermart_local.unit_types
CREATE TABLE IF NOT EXISTS `unit_types` (
  `id` int NOT NULL,
  `unit_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_name` (`unit_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table hypermart_local.unit_types: ~8 rows (approximately)
INSERT INTO `unit_types` (`id`, `unit_name`, `created_at`, `updated_at`) VALUES
	(1, 'Pieces', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(2, 'Kg', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(3, 'g', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(4, 'Tin', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(5, '250g Tin', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(6, '500g Tin', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(7, 'L', '2025-11-13 17:44:02', '2025-11-13 17:44:02'),
	(8, 'm', '2025-11-13 17:44:02', '2025-11-13 17:44:02');

-- Dumping structure for table hypermart_local.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `email` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `number` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `two_factor_secret` text COLLATE utf8mb3_bin,
  `two_factor_recovery_codes` text COLLATE utf8mb3_bin,
  `two_factor_confirmed_at` timestamp NULL DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL,
  `roles_id` bigint unsigned DEFAULT NULL,
  `status_id` bigint unsigned DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `profile_photo_path` varchar(2048) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`),
  UNIQUE KEY `users_number_unique` (`number`),
  KEY `users_roles_id_foreign` (`roles_id`),
  KEY `users_status_id_foreign` (`status_id`),
  CONSTRAINT `users_roles_id_foreign` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `users_status_id_foreign` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table hypermart_local.users: ~3 rows (approximately)
INSERT INTO `users` (`id`, `name`, `email`, `number`, `email_verified_at`, `password`, `two_factor_secret`, `two_factor_recovery_codes`, `two_factor_confirmed_at`, `remember_token`, `roles_id`, `status_id`, `gender`, `profile_photo_path`, `created_at`, `updated_at`) VALUES
	(1, 'ddddd', 'admin@gmail.com', '0761234567', '2024-11-28 15:41:41', '$2y$12$P4IwpbZqpGRd4OtIPBwLNuDg9z0/lvuLkkUZfOYkgaNqdcqV1yCzG', NULL, NULL, NULL, 'C7r7WqUyyQvVm2Q4GS1TJg3AAn9XoLbCcN2PuPMnK8uNKcqmsTLpouY9IZ2V', 1, 1, 'male', NULL, NULL, '2024-12-30 10:47:57'),
	(5, 'Nimal', 'nimal@gmail.com', '0764534234', NULL, '$2y$12$hMV6iC87vD162.pFpcR3p.AGSxfjUYpsWNfAk32JmDTbLcocACcJy', NULL, NULL, NULL, 'viOQOz2t38UgaEEfuvOtNwh7cYJJZrGmgZtbSfkAvxX9KcrfTENLAbT5Sbq9', 3, 1, 'male', NULL, '2025-01-10 10:46:20', '2025-01-25 12:33:09'),
	(7, 'superadmin', 'superadmin@gmail.com', '0712626063', NULL, '$2y$12$z7FX0qEHIyBkHKwbttTLUe6MymV.8hSg9IcNmMaqq8Hhm6oZLP62K', NULL, NULL, NULL, 'IPEHd6mkUVuziEhcKrOhgnKBG9P8etdmJejikuxF0qxEovOwudTX1CHjQGWB', 1, 1, 'male', NULL, '2025-03-27 04:11:05', '2025-10-28 06:58:38');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
