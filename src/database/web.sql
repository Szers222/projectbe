-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th10 22, 2024 lúc 11:48 AM
-- Phiên bản máy phục vụ: 8.3.0
-- Phiên bản PHP: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `mobileproject`
--

DELIMITER $$
--
-- Thủ tục
--
DROP PROCEDURE IF EXISTS `save_product_update_notification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `save_product_update_notification` (IN `product_id` BINARY(16), IN `product_name` VARCHAR(255), IN `product_price` DOUBLE, IN `product_quantity` INT, IN `product_sale` DOUBLE)   BEGIN
  -- Chèn một bản ghi mới vào bảng product_notifications
  INSERT INTO product_notifications (product_id, product_name, product_price, product_quantity, product_sale)
  VALUES (product_id, product_name, product_price, product_quantity, product_sale);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `carts`
--

DROP TABLE IF EXISTS `carts`;
CREATE TABLE IF NOT EXISTS `carts` (
  `cart_id` binary(16) NOT NULL,
  `cart_status` int DEFAULT '0',
  `guest_id` binary(16) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `UKo91mfhkn56hd7978a7jlmo9t3` (`guest_id`),
  KEY `FKb5o626f86h46m4s7ms6ginnop` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `carts`
--

INSERT INTO `carts` (`cart_id`, `cart_status`, `guest_id`, `user_id`) VALUES
(0x01000000000000000000000000000000, 1, NULL, 0x2605d82df28d4988ba577ecff7bb5803);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `carts_products`
--

DROP TABLE IF EXISTS `carts_products`;
CREATE TABLE IF NOT EXISTS `carts_products` (
  `carts_products_quantity` int NOT NULL DEFAULT '0',
  `cart_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  `product_size_id` binary(16) DEFAULT NULL,
  KEY `FK3nvguygrfbn661omvvu2uafu5` (`cart_id`),
  KEY `FKt3mepi19unnkcmw4683q5wr39` (`product_id`),
  KEY `FKa67i8aotf55o9f5uxel1pj04k` (`product_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `category_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_release` timestamp NOT NULL,
  `deletion_date` date DEFAULT NULL,
  `updated_at` timestamp NOT NULL,
  `parent_id` binary(16) DEFAULT NULL,
  `category_status_id` binary(16) NOT NULL,
  `category_img_path` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`),
  KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
  KEY `FKtqh6imciis386iuva8vls1aem` (`category_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`category_id`, `created_at`, `category_name`, `category_release`, `deletion_date`, `updated_at`, `parent_id`, `category_status_id`, `category_img_path`) VALUES
(0x01000000000000000000000000000000, '2024-10-11 12:02:31', 'Áo', '2024-10-11 12:02:31', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, 'https://png.pngtree.com/png-clipart/20210309/original/pngtree-smartphone-mockup-with-notch-camera-png-image_5884920.jpg'),
(0x01233131000000000000000000000000, '2024-10-26 12:43:06', 'Bột Năng Lượng', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0xf512dce8407b4571bdcfeebf517d15be, 0x01000000000000000000000000000000, '111.png'),
(0x01233132000000000000000000000000, '2024-10-26 12:43:06', 'Đồng hồ Coros', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x06000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233133000000000000000000000000, '2024-10-26 12:43:06', 'Đồng hồ Garmin', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x06000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233134000000000000000000000000, '2024-10-26 12:43:06', 'Đồng hồ Suunto', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x06000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x012331a0000000000000000000000000, '2024-10-26 12:43:06', 'Giày Địa Hình Nữ', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x04000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x012331b0000000000000000000000000, '2024-10-26 12:43:06', 'Kính', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x05000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x012331c0000000000000000000000000, '2024-10-26 12:43:06', 'Mũ Chạy Bộ', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x05000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x012331d0000000000000000000000000, '2024-10-26 12:43:06', 'Tất', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x05000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x012331e0000000000000000000000000, '2024-10-26 12:43:06', 'Gel', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0xf512dce8407b4571bdcfeebf517d15be, 0x01000000000000000000000000000000, '111.png'),
(0x012331f0000000000000000000000000, '2024-10-26 12:43:06', 'Bánh Năng Lượng', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0xf512dce8407b4571bdcfeebf517d15be, 0x01000000000000000000000000000000, '111.png'),
(0x01233300000000000000000000000000, '2024-10-26 11:41:41', 'Áo Nam', '2024-10-26 11:41:41', NULL, '2024-11-22 11:48:04', 0x01000000000000000000000000000000, 0x01000000000000000000000000000000, '123'),
(0x01233400000000000000000000000000, '2024-10-26 12:43:06', 'Áo Nữ', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x01000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233500000000000000000000000000, '2024-10-26 12:43:06', 'Quần Nam', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x02000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233600000000000000000000000000, '2024-10-26 12:43:06', 'Quần Nữ', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x02000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233700000000000000000000000000, '2024-10-26 12:43:06', 'Giày Chạy Nam', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x03000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233800000000000000000000000000, '2024-10-26 12:43:06', 'Giày Chạy Nữ', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x03000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x01233900000000000000000000000000, '2024-10-26 12:43:06', 'Giày Địa Hình Nam', '2024-10-26 12:43:06', NULL, '2024-11-22 11:48:04', 0x04000000000000000000000000000000, 0x01000000000000000000000000000000, '111.png'),
(0x02000000000000000000000000000000, '2024-10-11 12:02:48', 'Quần', '2024-10-11 12:02:48', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, 'https://png.pngtree.com/png-clipart/20201208/original/pngtree-laptop-mock-up-vector-png-image_5583348.jpg'),
(0x03000000000000000000000000000000, '2024-10-11 12:03:05', 'Giày Chạy', '2024-10-11 12:03:05', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, 'https://png.pngtree.com/png-clipart/20220429/original/pngtree-full-hd-smart-tv-led-screen-background-png-image_7587270.png'),
(0x04000000000000000000000000000000, '2024-10-11 12:03:05', 'Giày Địa Hình', '2024-10-11 12:03:05', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, 'https://png.pngtree.com/png-clipart/20220429/original/pngtree-full-hd-smart-tv-led-screen-background-png-image_7587270.png'),
(0x05000000000000000000000000000000, '2024-10-11 12:03:05', 'Phụ Kiện', '2024-10-11 12:03:05', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, 'https://png.pngtree.com/png-clipart/20220429/original/pngtree-full-hd-smart-tv-led-screen-background-png-image_7587270.png'),
(0x06000000000000000000000000000000, '2024-10-11 12:03:05', 'Đồng hồ', '2024-10-11 12:03:05', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, 'https://png.pngtree.com/png-clipart/20220429/original/pngtree-full-hd-smart-tv-led-screen-background-png-image_7587270.png'),
(0xf512dce8407b4571bdcfeebf517d15be, '2024-10-18 14:46:08', 'Dinh dưỡng', '2024-10-17 17:00:00', NULL, '2024-11-22 11:48:04', NULL, 0x01000000000000000000000000000000, '123');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories_products`
--

DROP TABLE IF EXISTS `categories_products`;
CREATE TABLE IF NOT EXISTS `categories_products` (
  `product_id` binary(16) NOT NULL,
  `category_id` binary(16) NOT NULL,
  PRIMARY KEY (`product_id`,`category_id`),
  KEY `FK2a3u5mbtmtq3d4s5abajhhksf` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `categories_products`
--

INSERT INTO `categories_products` (`product_id`, `category_id`) VALUES
(0xdd67a34ef646462ea3ce8d78d28460fe, 0x01233300000000000000000000000000),
(0xdd68a34ef646462ea3ce8d78d28460fe, 0x01233300000000000000000000000000),
(0xdd69a34ef646462ea3ce8d78d28460fe, 0x01233300000000000000000000000000),
(0xdd70a34ef646462ea3ce8d78d28460fe, 0x01233400000000000000000000000000),
(0xdd71a34ef646462ea3ce8d78d28460fe, 0x01233400000000000000000000000000),
(0xdfc75d0d719e45c3965bc90609a73789, 0x01233400000000000000000000000000),
(0x9bd6004055034dff95a9cc5112f49313, 0x01233500000000000000000000000000),
(0xa547c7cff93e483399fcb82ffd1c0242, 0x01233500000000000000000000000000),
(0xbaf72de39128453e8a20dcecce3f3305, 0x01233500000000000000000000000000),
(0xd7b72420564248f7895e6204b572dc51, 0x01233600000000000000000000000000),
(0xdd65a34ef646462ea3ce8d78d28460fe, 0x01233600000000000000000000000000),
(0xdd66a34ef646462ea3ce8d78d28460fe, 0x01233600000000000000000000000000),
(0x4b0899618c91486a996b7c4c3b5acf25, 0x01233700000000000000000000000000),
(0x50038924ade148bc833673b11255bfb5, 0x01233700000000000000000000000000),
(0x5c86e9953659473f9a700be465b2528b, 0x01233700000000000000000000000000),
(0x778640d814294255aa828374d5342a13, 0x01233800000000000000000000000000),
(0x9b083144cf724701a8fdbf013bbec402, 0x01233800000000000000000000000000),
(0x33330000000000000000000000000000, 0x01233900000000000000000000000000),
(0x33340000000000000000000000000000, 0x01233900000000000000000000000000),
(0x33350000000000000000000000000000, 0x01233900000000000000000000000000),
(0x33300000000000000000000000000000, 0x012331a0000000000000000000000000),
(0x31360000000000000000000000000000, 0x012331a0000000000000000000000000),
(0x33320000000000000000000000000000, 0x012331a0000000000000000000000000),
(0x01f3a501234567890000000000000012, 0x012331b0000000000000000000000000),
(0x01f3a501234567890000000000000013, 0x012331b0000000000000000000000000),
(0x01f3a501234567890000000000000014, 0x012331b0000000000000000000000000),
(0x32350000000000000000000000000000, 0x012331d0000000000000000000000000),
(0x32360000000000000000000000000000, 0x012331d0000000000000000000000000),
(0x31370000000000000000000000000000, 0x012331c0000000000000000000000000),
(0x31380000000000000000000000000000, 0x012331c0000000000000000000000000),
(0x31390000000000000000000000000000, 0x012331c0000000000000000000000000),
(0x32300000000000000000000000000000, 0x012331c0000000000000000000000000),
(0x32310000000000000000000000000000, 0x01233132000000000000000000000000),
(0x32320000000000000000000000000000, 0x01233132000000000000000000000000),
(0x32330000000000000000000000000000, 0x01233132000000000000000000000000),
(0x32340000000000000000000000000000, 0x01233132000000000000000000000000),
(0x32370000000000000000000000000000, 0x01233133000000000000000000000000),
(0x32380000000000000000000000000000, 0x01233133000000000000000000000000),
(0x32390000000000000000000000000000, 0x01233133000000000000000000000000),
(0x32e0ed1366fd4608b783b999cecfbd40, 0x01233133000000000000000000000000),
(0x01f3a501234567890000000000000001, 0x01233134000000000000000000000000),
(0x01f3a501234567890000000000000002, 0x01233134000000000000000000000000),
(0x01f3a501234567890000000000000007, 0x012331e0000000000000000000000000),
(0x01f3a501234567890000000000000008, 0x012331e0000000000000000000000000),
(0x01f3a501234567890000000000000009, 0x012331e0000000000000000000000000),
(0x01f3a501234567890000000000000010, 0x012331e0000000000000000000000000),
(0x01f3a501234567890000000000000011, 0x012331e0000000000000000000000000),
(0x01f3a501234567890000000000000003, 0x01233131000000000000000000000000),
(0x01f3a501234567890000000000000004, 0x01233131000000000000000000000000),
(0x01f3a501234567890000000000000005, 0x01233131000000000000000000000000),
(0x01f3a501234567890000000000000006, 0x01233131000000000000000000000000),
(0x01f3a501234567890000000000000015, 0x012331f0000000000000000000000000),
(0x1d489d1142454fc9b5f3a37e85c91b6d, 0x012331f0000000000000000000000000),
(0x235841dcd15a43a6ba68ef8f162786a2, 0x012331f0000000000000000000000000),
(0x31360000000000000000000000000000, 0x012331f0000000000000000000000000),
(0x00f81ac11e154e269aaca44c2873f4e8, 0x01233700000000000000000000000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category_status`
--

DROP TABLE IF EXISTS `category_status`;
CREATE TABLE IF NOT EXISTS `category_status` (
  `category_status_id` binary(16) NOT NULL,
  `category_status_name` varchar(255) NOT NULL,
  `category_status_type` int NOT NULL,
  PRIMARY KEY (`category_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `category_status`
--

INSERT INTO `category_status` (`category_status_id`, `category_status_name`, `category_status_type`) VALUES
(0x00000000000000000000000000000000, 'Nonactive', 0),
(0x01000000000000000000000000000000, 'Active', 1),
(0x02000000000000000000000000000000, 'Deleted', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coupons`
--

DROP TABLE IF EXISTS `coupons`;
CREATE TABLE IF NOT EXISTS `coupons` (
  `coupon_id` binary(16) NOT NULL,
  `coupon_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `coupon_expire` datetime(6) DEFAULT NULL,
  `coupon_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `coupon_per_hundred` float DEFAULT NULL,
  `coupon_price` double DEFAULT NULL,
  `coupon_quantity` int NOT NULL,
  `coupon_release` datetime(6) NOT NULL,
  `coupon_type` int DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `coupon_fee_ship` double DEFAULT NULL,
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `id_card`
--

DROP TABLE IF EXISTS `id_card`;
CREATE TABLE IF NOT EXISTS `id_card` (
  `card_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `card_date` varchar(10) DEFAULT NULL,
  `card_number` varchar(15) NOT NULL,
  `card_back_path` varchar(255) DEFAULT NULL,
  `card_image_front_path` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `id_card`
--

INSERT INTO `id_card` (`card_id`, `created_at`, `card_date`, `card_number`, `card_back_path`, `card_image_front_path`, `updated_at`) VALUES
(0x007a6757f5094a73ae6f553bb1221b05, '2024-11-01 12:43:58', '2024-10-21', '01234567892', 'image1.jpg', 'image2.jpg', '2024-11-01 12:43:58'),
(0x5cf353b3a8e9417986aa2fefa57a075e, '2024-11-01 12:39:33', '2024-10-21', '01234567891', 'image1.jpg', 'image2.jpg', '2024-11-01 12:39:33'),
(0xbe90b2617712430b81b44c4c5a9f4959, '2024-11-01 12:58:16', '2024-10-21', '01234567894', 'image1.jpg', 'image2.jpg', '2024-11-01 12:58:16'),
(0xc76a35133f3846daa434280c21a869aa, '2024-11-01 12:25:19', '2024-10-21', '0123456789', 'image1.jpg', 'image2.jpg', '2024-11-01 12:25:19'),
(0xf86f379ee6ea439db4005725f924ea0b, '2024-11-01 12:52:49', '2024-10-21', '01234567893', 'image1.jpg', 'image2.jpg', '2024-11-01 12:52:49');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `invalidated_token`
--

DROP TABLE IF EXISTS `invalidated_token`;
CREATE TABLE IF NOT EXISTS `invalidated_token` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `expires` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message_logs`
--

DROP TABLE IF EXISTS `message_logs`;
CREATE TABLE IF NOT EXISTS `message_logs` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `one_time_password`
--

DROP TABLE IF EXISTS `one_time_password`;
CREATE TABLE IF NOT EXISTS `one_time_password` (
  `one_time_password_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `one_time_password_code` varchar(6) NOT NULL,
  `one_time_password_wrong` int DEFAULT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`one_time_password_id`),
  KEY `FKo7cu5pvm7rbn6a1y1s18yr2nn` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `order_address` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_city` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_district` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_email` text COLLATE utf8mb4_unicode_ci,
  `order_fee_ship` double NOT NULL DEFAULT '0',
  `order_name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_note` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_payment` int NOT NULL DEFAULT '0',
  `order_phone` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_status` int NOT NULL DEFAULT '0',
  `order_ward` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_product_price` double NOT NULL DEFAULT '0',
  `updated_at` timestamp NOT NULL,
  `cart_id` binary(16) NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `UKs1sr8a1rkx80gwq9pl0952dar` (`cart_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_coupon`
--

DROP TABLE IF EXISTS `order_coupon`;
CREATE TABLE IF NOT EXISTS `order_coupon` (
  `order_id` binary(16) NOT NULL,
  `coupon_id` binary(16) NOT NULL,
  PRIMARY KEY (`order_id`,`coupon_id`),
  KEY `FK2wuerggqssjbqbucb6g7s3pn9` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (
  `permission_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `permission_name` varchar(255) NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `permissions`
--

INSERT INTO `permissions` (`permission_id`, `created_at`, `permission_name`, `updated_at`) VALUES
(0x223385b6f3a94f1fbd7b7029fac24dc1, '2024-11-01 12:28:33', 'PERMISSION_GETALL', '2024-11-01 12:28:33'),
(0x674bd611214e47fe9fa4a4d7a24e10db, '2024-11-01 12:27:53', 'PERMISSION_CREATE', '2024-11-01 12:27:53'),
(0xb69fe5e113ff4ad7ba39ea057f628cf6, '2024-11-01 12:28:22', 'PERMISSION_GETID', '2024-11-01 12:28:22'),
(0xcf103fe290d943e887af1d53ca6a6d35, '2024-11-01 12:27:28', 'PERMISSION_POST', '2024-11-01 12:27:28');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts`
--

DROP TABLE IF EXISTS `posts`;
CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` binary(16) NOT NULL,
  `post_content` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NOT NULL,
  `post_image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `post_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `post_release` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `post_status_id` binary(16) NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  `post_type` int DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FK2cgbv48uplf00occltdmartna` (`post_status_id`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `posts`
--

INSERT INTO `posts` (`post_id`, `post_content`, `created_at`, `post_image_path`, `post_name`, `post_release`, `updated_at`, `post_status_id`, `user_id`, `post_type`) VALUES
(0x01100000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01200000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01300000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01400000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01500000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01600000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01700000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1),
(0x01800000000000000000000000000000, '### Description\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliq.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n![Product Image](0) \n\n### Description 2\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n![Product Image](1) \n### Detail\n#### Từ : Việt Nam\n#### Material : Polime\n#### Địa Chỉ : Thành Phố Hồ Chí Minh\n\n![Product Image](2) \n### Kết Thúc Mô Tả', '2024-10-31 08:39:44', 'img/product01.png', 'Giới thiệu về Gel năng lượng Isotonic SiS', '2024-10-31 08:39:44', '2024-10-31 08:39:44', 0x03000000000000000000000000000000, 0x4e98028c21574568a9bcc21033bad79a, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `post_comments`
--

DROP TABLE IF EXISTS `post_comments`;
CREATE TABLE IF NOT EXISTS `post_comments` (
  `post_commnet_id` binary(16) NOT NULL,
  `post_comment_content` text,
  `post_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`post_commnet_id`),
  KEY `FKaawaqxjs3br8dw5v90w7uu514` (`post_id`),
  KEY `FKsnxoecngu89u3fh4wdrgf0f2g` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `post_status`
--

DROP TABLE IF EXISTS `post_status`;
CREATE TABLE IF NOT EXISTS `post_status` (
  `post_status_id` binary(16) NOT NULL,
  `post_status_name` varchar(255) NOT NULL,
  `post_status_type` int NOT NULL,
  PRIMARY KEY (`post_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `post_status`
--

INSERT INTO `post_status` (`post_status_id`, `post_status_name`, `post_status_type`) VALUES
(0x03000000000000000000000000000000, 'Name1', 1),
(0x04000000000000000000000000000000, 'Name2', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_price` double NOT NULL,
  `product_quantity` int NOT NULL DEFAULT '0',
  `product_rating` double DEFAULT '0',
  `product_sale` double DEFAULT '0',
  `updated_at` timestamp NOT NULL,
  `product_views` int DEFAULT '0',
  `product_year_of_manufacture` int DEFAULT '2000',
  `coupon_id` binary(16) DEFAULT NULL,
  `post_id` binary(16) DEFAULT NULL,
  `supplier_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `UK5ywxdmvnjpsyoxmeymullwkfo` (`coupon_id`),
  UNIQUE KEY `UKf1855893d62pbs4v1b7h4kmfi` (`post_id`),
  KEY `FK7iek4ivwkcno7o4dfbpccyg4f` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`product_id`, `created_at`, `product_name`, `product_price`, `product_quantity`, `product_rating`, `product_sale`, `updated_at`, `product_views`, `product_year_of_manufacture`, `coupon_id`, `post_id`, `supplier_id`) VALUES
(0x00f81ac11e154e269aaca44c2873f4e8, '2024-10-11 09:48:05', 'GIÀY CHẠY BỘ NAM HOKA CLIFTON 9 WIDE', 1650000, 20, 4, 5, '2024-10-11 09:48:05', 0, 0, NULL, 0x01100000000000000000000000000000, 0x06000000000000000000000000000000),
(0x01f3a501234567890000000000000001, '2024-10-11 03:10:00', 'Suunto UltraTrail', 165000, 20, 4, 5, '2024-10-11 03:10:00', 0, 2024, NULL, 0x01200000000000000000000000000000, 0x06000000000000000000000000000000),
(0x01f3a501234567890000000000000002, '2024-10-11 03:15:00', 'Suunto AquaDive', 1870000, 18, 4.2, 10, '2024-10-11 03:15:00', 0, 2024, NULL, 0x01300000000000000000000000000000, 0x06000000000000000000000000000000),
(0x01f3a501234567890000000000000003, '2024-10-11 03:20:00', 'Bột Năng Lượng Xtreme Energy', 154000, 30, 3.9, 0, '2024-10-11 03:20:00', 0, 2024, NULL, 0x01400000000000000000000000000000, 0x02000000000000000000000000000000),
(0x01f3a501234567890000000000000004, '2024-10-11 03:25:00', 'Bột Năng Lượng ProBoost', 1530000, 25, 4.8, 20, '2024-10-11 03:25:00', 0, 2024, NULL, 0x01500000000000000000000000000000, 0x06000000000000000000000000000000),
(0x01f3a501234567890000000000000005, '2024-10-11 03:30:00', 'Bột Năng Lượng ActiveMax', 1980000, 15, 4.5, 12, '2024-10-11 03:30:00', 0, 2024, NULL, 0x01600000000000000000000000000000, 0x02000000000000000000000000000000),
(0x01f3a501234567890000000000000006, '2024-10-11 03:35:00', 'Bột Năng Lượng EnduroFuel', 2000000, 20, 4.7, 25, '2024-10-11 03:35:00', 0, 2024, NULL, 0x01700000000000000000000000000000, 0x01000000000000000000000000000000),
(0x01f3a501234567890000000000000007, '2024-10-11 03:40:00', 'Gel Năng Lượng PowerUp', 176000, 10, 4.1, 8, '2024-10-11 03:40:00', 0, 2024, NULL, 0x01800000000000000000000000000000, 0x01000000000000000000000000000000),
(0x01f3a501234567890000000000000008, '2024-10-11 03:45:00', 'Gel Năng Lượng MaxRun', 200000, 12, 4.3, 7, '2024-10-11 03:45:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x01f3a501234567890000000000000009, '2024-10-11 03:50:00', 'Gel Năng Lượng EnduroBoost', 1870000, 9, 3.8, 0, '2024-10-11 03:50:00', 0, 2024, NULL, NULL, 0x04000000000000000000000000000000),
(0x01f3a501234567890000000000000010, '2024-10-11 03:55:00', 'Gel Năng Lượng RapidCharge', 231000, 17, 4.6, 18, '2024-10-11 03:55:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x01f3a501234567890000000000000011, '2024-10-11 04:00:00', 'Gel Năng Lượng MaxSport', 220000, 15, 4.2, 10, '2024-10-11 04:00:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x01f3a501234567890000000000000012, '2024-10-11 04:05:00', 'Kính SportVision', 242000, 22, 4.7, 5, '2024-10-11 04:05:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x01f3a501234567890000000000000013, '2024-10-11 04:10:00', 'Kính EnduroShield', 1430000, 8, 3.9, 0, '2024-10-11 04:10:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x01f3a501234567890000000000000014, '2024-10-11 04:15:00', 'Kính RaceGuard ', 264000, 20, 4.8, 15, '2024-10-11 04:15:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x01f3a501234567890000000000000015, '2024-10-11 04:20:00', 'Bánh Năng Lượng ActiveBar', 286000, 10, 4.5, 20, '2024-10-11 04:20:00', 0, 2024, NULL, NULL, 0x03000000000000000000000000000000),
(0x1d489d1142454fc9b5f3a37e85c91b6d, '2024-10-11 09:48:27', 'Bánh Năng Lượng UltraFuel', 275000, 40, 5, 20, '2024-10-11 09:48:27', 0, 0, NULL, NULL, 0x03000000000000000000000000000000),
(0x235841dcd15a43a6ba68ef8f162786a2, '2024-10-11 09:51:19', 'Bánh Năng Lượng RapidBoost', 1430000, 9, 4.2, 5, '2024-10-11 09:51:19', 0, 0, NULL, NULL, 0x03000000000000000000000000000000),
(0x31360000000000000000000000000000, '2024-10-11 03:00:00', 'Bánh Năng Lượng FitCrunch', 297000, 15, 4.3, 8, '2024-10-11 03:00:00', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x31370000000000000000000000000000, '2024-10-11 03:05:00', 'Mũ RunShield', 209000, 9, 4, 12, '2024-10-11 03:05:00', 0, 0, NULL, NULL, 0x04000000000000000000000000000000),
(0x31380000000000000000000000000000, '2024-10-11 03:10:00', 'Mũ SpeedFit', 242000, 25, 4.7, 0, '2024-10-11 03:10:00', 0, 0, NULL, NULL, 0x04000000000000000000000000000000),
(0x31390000000000000000000000000000, '2024-10-11 03:15:00', 'Mũ TrailBlaze', 1650000, 12, 4.1, 15, '2024-10-11 03:15:00', 0, 0, NULL, NULL, 0x04000000000000000000000000000000),
(0x32300000000000000000000000000000, '2024-10-11 03:20:00', 'Mũ AeroRun', 1540000, 20, 4.5, 10, '2024-10-11 03:20:00', 0, 0, NULL, NULL, 0x04000000000000000000000000000000),
(0x32310000000000000000000000000000, '2024-10-11 03:25:00', 'Coros EnduroX', 1430000, 17, 3.8, 5, '2024-10-11 03:25:00', 0, 0, NULL, NULL, 0x04000000000000000000000000000000),
(0x32320000000000000000000000000000, '2024-10-11 03:30:00', 'Coros ActiveTrail ', 231000, 14, 4.2, 0, '2024-10-11 03:30:00', 0, 0, NULL, NULL, 0x04000000000000000000000000000000),
(0x32330000000000000000000000000000, '2024-10-11 03:35:00', 'Coros GPS Elite', 275000, 10, 4.6, 18, '2024-10-11 03:35:00', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x32340000000000000000000000000000, '2024-10-11 03:40:00', 'Coros SportPulse', 1980000, 8, 4.8, 5, '2024-10-11 03:40:00', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x32350000000000000000000000000000, '2024-10-11 03:45:00', 'Tất RunFit', 1870000, 22, 4.9, 0, '2024-10-11 03:45:00', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x32360000000000000000000000000000, '2024-10-11 03:50:00', 'Tất SpeedStride', 1760000, 19, 4.2, 12, '2024-10-11 03:50:00', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x32370000000000000000000000000000, '2024-10-11 03:55:00', 'Garmin EnduroX', 319000, 7, 4, 10, '2024-10-11 03:55:00', 0, 0, NULL, NULL, 0x02000000000000000000000000000000),
(0x32380000000000000000000000000000, '2024-10-11 04:00:00', 'Garmin AquaFit', 1320000, 5, 3.9, 20, '2024-10-11 04:00:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x32390000000000000000000000000000, '2024-10-11 04:05:00', 'Garmin ActivePulse', 297000, 30, 4.5, 25, '2024-10-11 04:05:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x32e0ed1366fd4608b783b999cecfbd40, '2024-10-11 09:54:06', 'Garmin UltraEdge', 242000, 9, 4.1, 20, '2024-10-11 09:54:06', 0, 0, NULL, NULL, 0x02000000000000000000000000000000),
(0x33300000000000000000000000000000, '2024-10-11 04:10:00', 'Giày ĐH TrailMaster Nữ', 1100000, 16, 4.4, 15, '2024-10-11 04:10:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x33310000000000000000000000000000, '2024-10-11 04:15:00', 'Giày ĐH EnduroGrip Nữ', 253000, 18, 4.3, 12, '2024-10-11 04:15:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x33320000000000000000000000000000, '2024-10-11 04:20:00', 'Giày ĐH MountainX Nữ', 1760000, 15, 4.1, 8, '2024-10-11 04:20:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x33330000000000000000000000000000, '2024-10-11 04:30:00', 'Giày ĐH TrailPro Nam', 220000, 8, 4.6, 5, '2024-10-11 04:30:00', 0, 0, NULL, NULL, 0x02000000000000000000000000000000),
(0x33340000000000000000000000000000, '2024-10-11 04:25:00', 'Giày ĐH RockRunner Nam', 209000, 20, 4.7, 18, '2024-10-11 04:25:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x33350000000000000000000000000000, '2024-10-11 04:35:00', 'Giày ĐH RuggedRunner Nam', 275000, 10, 4.9, 20, '2024-10-11 04:35:00', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x4b0899618c91486a996b7c4c3b5acf25, '2024-10-11 09:54:13', 'Giày SpeedRunner Nam', 253000, 5, 4, 0, '2024-10-11 09:54:13', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x50038924ade148bc833673b11255bfb5, '2024-10-11 09:53:05', 'Giày SprintMax Nam', 220000, 7, 4.9, 15, '2024-10-11 09:53:05', 0, 0, NULL, NULL, 0x05000000000000000000000000000000),
(0x5c86e9953659473f9a700be465b2528b, '2024-10-11 09:52:49', 'Giày RunPro Nam', 209000, 10, 4.4, 5, '2024-10-11 09:52:49', 0, 0, NULL, NULL, 0x02000000000000000000000000000000),
(0x778640d814294255aa828374d5342a13, '2024-10-11 09:51:56', 'Giày MarathonGrace Nữ', 1540000, 11, 4.3, 12, '2024-10-11 09:51:56', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x9b083144cf724701a8fdbf013bbec402, '2024-10-11 09:48:13', 'Giày SpeedLuxe Nữ', 220000, 30, 3.5, 0, '2024-10-11 09:48:13', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0x9bd6004055034dff95a9cc5112f49313, '2024-10-11 09:51:11', 'Quần FlexMove Nam', 1320000, 12, 4.6, 8, '2024-10-11 09:51:11', 0, 0, NULL, NULL, 0x02000000000000000000000000000000),
(0xa547c7cff93e483399fcb82ffd1c0242, '2024-10-11 09:47:47', 'Quần ProFit Nam', 1100000, 10, 4.5, 10, '2024-10-11 09:47:47', 0, 0, NULL, NULL, 0x06000000000000000000000000000000),
(0xbaf72de39128453e8a20dcecce3f3305, '2024-10-11 09:52:27', 'Quần EnduroFlex Nam', 1760000, 8, 4.7, 7, '2024-10-11 09:52:27', 0, 0, NULL, NULL, 0x06000000000000000000000000000000),
(0xd7b72420564248f7895e6204b572dc51, '2024-10-11 09:49:01', 'Quần FlexFit Nữ', 330000, 8, 4.2, 15, '2024-10-11 09:49:01', 0, 0, NULL, NULL, 0x06000000000000000000000000000000),
(0xdd65a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Quần SpeedFit Nữ', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdd66a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Quần PowerMove Nữ', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdd67a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Áo FlexFit Nam', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdd68a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Áo SportFlex Nam', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdd69a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Áo GymMax Nam', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdd70a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Áo RunChic Nữ', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdd71a34ef646462ea3ce8d78d28460fe, '2024-10-11 09:52:43', 'Áo PowerMove Nữ', 1980000, 15, 4.8, 10, '2024-10-11 09:52:43', 0, 0, NULL, NULL, 0x01000000000000000000000000000000),
(0xdfc75d0d719e45c3965bc90609a73789, '2024-10-11 09:54:18', 'Áo SpeedRun Nữ', 275000, 6, 4.5, 18, '2024-10-11 09:54:18', 0, 0, NULL, NULL, 0x02000000000000000000000000000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products_sizes`
--

DROP TABLE IF EXISTS `products_sizes`;
CREATE TABLE IF NOT EXISTS `products_sizes` (
  `product_id` binary(16) NOT NULL,
  `product_size_id` binary(16) NOT NULL,
  `sizes_products_quantity` int DEFAULT '0',
  PRIMARY KEY (`product_id`,`product_size_id`),
  KEY `FK2orhe315micmx1dsqb48d6mjk` (`product_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `products_sizes`
--

INSERT INTO `products_sizes` (`product_id`, `product_size_id`, `sizes_products_quantity`) VALUES
(0x00f81ac11e154e269aaca44c2873f4e8, 0x00000000000000000000000000000000, 4),
(0x00f81ac11e154e269aaca44c2873f4e8, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x00f81ac11e154e269aaca44c2873f4e8, 0x002b5d28fdd1234b7c987d5432f23456, 0),
(0x00f81ac11e154e269aaca44c2873f4e8, 0x003c6e39fdd2345c8d876e6543f34567, 2),
(0x00f81ac11e154e269aaca44c2873f4e8, 0x06550000000000000000000000000000, 6),
(0x01f3a501234567890000000000000001, 0x001a3d48fdd1234a6b789c1234f12345, 3),
(0x01f3a501234567890000000000000002, 0x00000000000000000000000000000000, 8),
(0x01f3a501234567890000000000000002, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000002, 0x002b5d28fdd1234b7c987d5432f23456, 1),
(0x01f3a501234567890000000000000002, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x01f3a501234567890000000000000002, 0x06550000000000000000000000000000, 10),
(0x01f3a501234567890000000000000003, 0x00000000000000000000000000000000, 5),
(0x01f3a501234567890000000000000003, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000003, 0x002b5d28fdd1234b7c987d5432f23456, 5),
(0x01f3a501234567890000000000000003, 0x003c6e39fdd2345c8d876e6543f34567, 0),
(0x01f3a501234567890000000000000004, 0x00000000000000000000000000000000, 8),
(0x01f3a501234567890000000000000004, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000004, 0x002b5d28fdd1234b7c987d5432f23456, 1),
(0x01f3a501234567890000000000000004, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x01f3a501234567890000000000000004, 0x06550000000000000000000000000000, 10),
(0x01f3a501234567890000000000000005, 0x002b5d28fdd1234b7c987d5432f23456, 5),
(0x01f3a501234567890000000000000005, 0x003c6e39fdd2345c8d876e6543f34567, 4),
(0x01f3a501234567890000000000000006, 0x00000000000000000000000000000000, 1),
(0x01f3a501234567890000000000000006, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000006, 0x002b5d28fdd1234b7c987d5432f23456, 1),
(0x01f3a501234567890000000000000006, 0x003c6e39fdd2345c8d876e6543f34567, 4),
(0x01f3a501234567890000000000000006, 0x06550000000000000000000000000000, 1),
(0x01f3a501234567890000000000000007, 0x00000000000000000000000000000000, 0),
(0x01f3a501234567890000000000000007, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000007, 0x002b5d28fdd1234b7c987d5432f23456, 0),
(0x01f3a501234567890000000000000007, 0x003c6e39fdd2345c8d876e6543f34567, 8),
(0x01f3a501234567890000000000000007, 0x06550000000000000000000000000000, 0),
(0x01f3a501234567890000000000000008, 0x00000000000000000000000000000000, 5),
(0x01f3a501234567890000000000000008, 0x002b5d28fdd1234b7c987d5432f23456, 1),
(0x01f3a501234567890000000000000009, 0x00000000000000000000000000000000, 0),
(0x01f3a501234567890000000000000009, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000009, 0x002b5d28fdd1234b7c987d5432f23456, 0),
(0x01f3a501234567890000000000000009, 0x003c6e39fdd2345c8d876e6543f34567, 8),
(0x01f3a501234567890000000000000009, 0x06550000000000000000000000000000, 8),
(0x01f3a501234567890000000000000010, 0x00000000000000000000000000000000, 0),
(0x01f3a501234567890000000000000010, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000010, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x01f3a501234567890000000000000010, 0x003c6e39fdd2345c8d876e6543f34567, 8),
(0x01f3a501234567890000000000000010, 0x06550000000000000000000000000000, 8),
(0x01f3a501234567890000000000000011, 0x00000000000000000000000000000000, 3),
(0x01f3a501234567890000000000000012, 0x00000000000000000000000000000000, 0),
(0x01f3a501234567890000000000000012, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000012, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x01f3a501234567890000000000000012, 0x003c6e39fdd2345c8d876e6543f34567, 0),
(0x01f3a501234567890000000000000012, 0x06550000000000000000000000000000, 8),
(0x01f3a501234567890000000000000013, 0x00000000000000000000000000000000, 0),
(0x01f3a501234567890000000000000013, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x01f3a501234567890000000000000013, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x01f3a501234567890000000000000013, 0x003c6e39fdd2345c8d876e6543f34567, 0),
(0x01f3a501234567890000000000000013, 0x06550000000000000000000000000000, 0),
(0x01f3a501234567890000000000000014, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x01f3a501234567890000000000000015, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x1d489d1142454fc9b5f3a37e85c91b6d, 0x00000000000000000000000000000000, 6),
(0x1d489d1142454fc9b5f3a37e85c91b6d, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x235841dcd15a43a6ba68ef8f162786a2, 0x00000000000000000000000000000000, 1),
(0x235841dcd15a43a6ba68ef8f162786a2, 0x001a3d48fdd1234a6b789c1234f12345, 1),
(0x31360000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 1),
(0x31360000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 4),
(0x31360000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x31370000000000000000000000000000, 0x00000000000000000000000000000000, 0),
(0x31370000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x31370000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x31370000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 0),
(0x31370000000000000000000000000000, 0x06550000000000000000000000000000, 0),
(0x31380000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x31380000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x31380000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x31380000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x31380000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x31390000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x31390000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x31390000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x31390000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x31390000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32300000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32300000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32300000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32300000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32300000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32310000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32310000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32310000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32310000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32310000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32320000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32320000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32320000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32320000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32320000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32330000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32330000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32330000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32330000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32330000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32340000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32340000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32340000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32340000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32340000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32350000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32350000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32350000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32350000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32350000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32360000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32360000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32360000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32360000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32360000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32370000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x32370000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32370000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x32370000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x32370000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x32380000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x32390000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 5),
(0x32e0ed1366fd4608b783b999cecfbd40, 0x00000000000000000000000000000000, 5),
(0x32e0ed1366fd4608b783b999cecfbd40, 0x001a3d48fdd1234a6b789c1234f12345, 2),
(0x32e0ed1366fd4608b783b999cecfbd40, 0x002b5d28fdd1234b7c987d5432f23456, 5),
(0x32e0ed1366fd4608b783b999cecfbd40, 0x06550000000000000000000000000000, 5),
(0x33300000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 2),
(0x33310000000000000000000000000000, 0x00000000000000000000000000000000, 7),
(0x33320000000000000000000000000000, 0x06550000000000000000000000000000, 0),
(0x33330000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x33330000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x33330000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x33330000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x33330000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x33340000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x33340000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x33340000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x33340000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x33340000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x33350000000000000000000000000000, 0x00000000000000000000000000000000, 5),
(0x33350000000000000000000000000000, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x33350000000000000000000000000000, 0x002b5d28fdd1234b7c987d5432f23456, 8),
(0x33350000000000000000000000000000, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x33350000000000000000000000000000, 0x06550000000000000000000000000000, 5),
(0x4b0899618c91486a996b7c4c3b5acf25, 0x001a3d48fdd1234a6b789c1234f12345, 3),
(0x4b0899618c91486a996b7c4c3b5acf25, 0x06550000000000000000000000000000, 5),
(0x50038924ade148bc833673b11255bfb5, 0x06550000000000000000000000000000, 0),
(0x5c86e9953659473f9a700be465b2528b, 0x001a3d48fdd1234a6b789c1234f12345, 0),
(0x5c86e9953659473f9a700be465b2528b, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x5c86e9953659473f9a700be465b2528b, 0x06550000000000000000000000000000, 1),
(0x778640d814294255aa828374d5342a13, 0x001a3d48fdd1234a6b789c1234f12345, 1),
(0x778640d814294255aa828374d5342a13, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x778640d814294255aa828374d5342a13, 0x06550000000000000000000000000000, 3),
(0x9b083144cf724701a8fdbf013bbec402, 0x00000000000000000000000000000000, 0),
(0x9b083144cf724701a8fdbf013bbec402, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0x9b083144cf724701a8fdbf013bbec402, 0x002b5d28fdd1234b7c987d5432f23456, 4),
(0x9b083144cf724701a8fdbf013bbec402, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0x9bd6004055034dff95a9cc5112f49313, 0x00000000000000000000000000000000, 1),
(0xa547c7cff93e483399fcb82ffd1c0242, 0x001a3d48fdd1234a6b789c1234f12345, 2),
(0xa547c7cff93e483399fcb82ffd1c0242, 0x002b5d28fdd1234b7c987d5432f23456, 1),
(0xa547c7cff93e483399fcb82ffd1c0242, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0xbaf72de39128453e8a20dcecce3f3305, 0x00000000000000000000000000000000, 5),
(0xbaf72de39128453e8a20dcecce3f3305, 0x001a3d48fdd1234a6b789c1234f12345, 5),
(0xbaf72de39128453e8a20dcecce3f3305, 0x002b5d28fdd1234b7c987d5432f23456, 5),
(0xbaf72de39128453e8a20dcecce3f3305, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0xd7b72420564248f7895e6204b572dc51, 0x002b5d28fdd1234b7c987d5432f23456, 3),
(0xd7b72420564248f7895e6204b572dc51, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0xdd65a34ef646462ea3ce8d78d28460fe, 0x00000000000000000000000000000000, 4),
(0xdd65a34ef646462ea3ce8d78d28460fe, 0x001a3d48fdd1234a6b789c1234f12345, 4),
(0xdd65a34ef646462ea3ce8d78d28460fe, 0x002b5d28fdd1234b7c987d5432f23456, 2),
(0xdd65a34ef646462ea3ce8d78d28460fe, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0xdfc75d0d719e45c3965bc90609a73789, 0x001a3d48fdd1234a6b789c1234f12345, 1),
(0xdfc75d0d719e45c3965bc90609a73789, 0x003c6e39fdd2345c8d876e6543f34567, 5),
(0xdfc75d0d719e45c3965bc90609a73789, 0x06550000000000000000000000000000, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_images`
--

DROP TABLE IF EXISTS `product_images`;
CREATE TABLE IF NOT EXISTS `product_images` (
  `product_image_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `product_image_alt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_image_index` int DEFAULT NULL,
  `product_image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` timestamp NOT NULL,
  `product_id` binary(16) NOT NULL,
  PRIMARY KEY (`product_image_id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `product_images`
--

INSERT INTO `product_images` (`product_image_id`, `created_at`, `product_image_alt`, `product_image_index`, `product_image_path`, `updated_at`, `product_id`) VALUES
(0x00f81ac11e154e269aaca44c2873f4e8, '2024-10-11 02:48:05', 'Image of product_2', 1, 'img/20201208_ueMG4dr96VY9Q9CdWPHsV7ZX.jpg', '2024-10-11 02:48:05', 0x00f81ac11e154e269aaca44c2873f4e8),
(0x00f81ac11e154e269aaca44c2873f4e9, '2024-10-11 02:48:05', 'Image of product_2 variation 1', 2, 'img/20210410_zOS3hu3lOAH3RLHxZKRgrGz9.jpg', '2024-10-11 02:48:05', 0x00f81ac11e154e269aaca44c2873f4e8),
(0x00f81ac11e154e269aaca44c2873f4ea, '2024-10-11 02:48:05', 'Image of product_2 variation 2', 3, 'img/20210621_Vxr4WLQUcKZ0a2faTsVDu5Fr.jpg', '2024-10-11 02:48:05', 0x00f81ac11e154e269aaca44c2873f4e8),
(0x01f3a501234567890000000000000001, '2024-10-10 20:10:00', 'Image of product_36', 1, 'img/20210112_XafsJ0BVfHSY813lVuVF1GA3.jpg', '2024-10-10 20:10:00', 0x01f3a501234567890000000000000001),
(0x01f3a501234567890000000000000002, '2024-10-10 20:10:00', 'Image of product_36 variation 1', 2, 'img/20200824_TZsmPadWoS7CWo3v2VfVj3Sm.jpg', '2024-10-10 20:10:00', 0x01f3a501234567890000000000000001),
(0x01f3a501234567890000000000000003, '2024-10-10 20:10:00', 'Image of product_36 variation 2', 3, 'img/20201206_0JqWsPPbRlaL00ZWTSMuEuTX.jpg', '2024-10-10 20:10:00', 0x01f3a501234567890000000000000001),
(0x01f3a501234567890000000000000004, '2024-10-10 20:15:00', 'Image of product_37', 1, 'img/20210621_3hDCPYOVXzqv2z15MyZDgCh5.jpg', '2024-10-10 20:15:00', 0x01f3a501234567890000000000000002),
(0x01f3a501234567890000000000000005, '2024-10-10 20:15:00', 'Image of product_37 variation 1', 2, 'img/20201208_ueMG4dr96VY9Q9CdWPHsV7ZX.jpg', '2024-10-10 20:15:00', 0x01f3a501234567890000000000000002),
(0x01f3a501234567890000000000000006, '2024-10-10 20:15:00', 'Image of product_37 variation 2', 3, 'img/20210112_XafsJ0BVfHSY813lVuVF1GA3.jpg', '2024-10-10 20:15:00', 0x01f3a501234567890000000000000002),
(0x01f3a501234567890000000000000007, '2024-10-10 20:20:00', 'Image of product_38', 1, 'img/20210410_zOS3hu3lOAH3RLHxZKRgrGz9.jpg', '2024-10-10 20:20:00', 0x01f3a501234567890000000000000003),
(0x01f3a501234567890000000000000008, '2024-10-10 20:20:00', 'Image of product_38 variation 1', 2, 'img/20210621_Vxr4WLQUcKZ0a2faTsVDu5Fr.jpg', '2024-10-10 20:20:00', 0x01f3a501234567890000000000000003),
(0x01f3a501234567890000000000000009, '2024-10-10 20:20:00', 'Image of product_38 variation 2', 3, 'img/20200824_TZsmPadWoS7CWo3v2VfVj3Sm.jpg', '2024-10-10 20:20:00', 0x01f3a501234567890000000000000003),
(0x01f3a50123456789000000000000000a, '2024-10-10 20:25:00', 'Image of product_39', 1, 'img/20201206_0JqWsPPbRlaL00ZWTSMuEuTX.jpg', '2024-10-10 20:25:00', 0x01f3a501234567890000000000000004),
(0x01f3a50123456789000000000000000b, '2024-10-10 20:25:00', 'Image of product_39 variation 1', 2, 'img/20201208_ueMG4dr96VY9Q9CdWPHsV7ZX.jpg', '2024-10-10 20:25:00', 0x01f3a501234567890000000000000004),
(0x01f3a50123456789000000000000000c, '2024-10-10 20:25:00', 'Image of product_39 variation 2', 3, 'img/20210621_3hDCPYOVXzqv2z15MyZDgCh5.jpg', '2024-10-10 20:25:00', 0x01f3a501234567890000000000000004),
(0x01f3a50123456789000000000000000d, '2024-10-10 20:30:00', 'Image of product_40', 1, 'img/20210112_XafsJ0BVfHSY813lVuVF1GA3.jpg', '2024-10-10 20:30:00', 0x01f3a501234567890000000000000005),
(0x01f3a50123456789000000000000000e, '2024-10-10 20:30:00', 'Image of product_40 variation 1', 2, 'img/20210410_zOS3hu3lOAH3RLHxZKRgrGz9.jpg', '2024-10-10 20:30:00', 0x01f3a501234567890000000000000005),
(0x01f3a50123456789000000000000000f, '2024-10-10 20:30:00', 'Image of product_40 variation 2', 3, 'img/20210621_Vxr4WLQUcKZ0a2faTsVDu5Fr.jpg', '2024-10-10 20:30:00', 0x01f3a501234567890000000000000005),
(0x01f3a501234567890000000000000010, '2024-10-10 20:35:00', 'Image of product_41', 1, 'img/20201206_0JqWsPPbRlaL00ZWTSMuEuTX.jpg', '2024-10-10 20:35:00', 0x01f3a501234567890000000000000006),
(0x01f3a501234567890000000000000011, '2024-10-10 20:35:00', 'Image of product_41 variation 1', 2, 'img/20200824_TZsmPadWoS7CWo3v2VfVj3Sm.jpg', '2024-10-10 20:35:00', 0x01f3a501234567890000000000000006),
(0x01f3a501234567890000000000000012, '2024-10-10 20:35:00', 'Image of product_41 variation 2', 3, 'img/20201208_ueMG4dr96VY9Q9CdWPHsV7ZX.jpg', '2024-10-10 20:35:00', 0x01f3a501234567890000000000000006),
(0x01f3a501234567890000000000000013, '2024-10-10 20:40:00', 'Image of product_42', 1, 'img/20210621_3hDCPYOVXzqv2z15MyZDgCh5.jpg', '2024-10-10 20:40:00', 0x01f3a501234567890000000000000007),
(0x01f3a501234567890000000000000014, '2024-10-10 20:40:00', 'Image of product_42 variation 1', 2, 'img/20210112_XafsJ0BVfHSY813lVuVF1GA3.jpg', '2024-10-10 20:40:00', 0x01f3a501234567890000000000000007),
(0x01f3a501234567890000000000000015, '2024-10-10 20:40:00', 'Image of product_42 variation 2', 3, 'img/20210410_zOS3hu3lOAH3RLHxZKRgrGz9.jpg', '2024-10-10 20:40:00', 0x01f3a501234567890000000000000007),
(0x33323664393461642d383838302d3131, '2024-10-10 21:00:00', 'Image for product_46', 0, 'img/20210621_Vxr4WLQUcKZ0a2faTsVDu5Fr.jpg', '2024-10-10 21:00:00', 0x01f3a501234567890000000000000011),
(0x33323664396234372d383838302d3131, '2024-10-10 21:00:00', 'Image for product_46 - Variant 1', 1, 'img/20210621_3hDCPYOVXzqv2z15MyZDgCh5.jpg', '2024-10-10 21:00:00', 0x01f3a501234567890000000000000011),
(0x33323664396364662d383838302d3131, '2024-10-10 21:00:00', 'Image for product_46 - Variant 2', 2, 'img/20201206_0JqWsPPbRlaL00ZWTSMuEuTX.jpg', '2024-10-10 21:00:00', 0x01f3a501234567890000000000000011),
(0x33323664396530632d383838302d3131, '2024-10-10 21:05:00', 'Image for product_47', 0, 'img/product42_1.png', '2024-10-10 21:05:00', 0x01f3a501234567890000000000000012),
(0x33323664396636302d383838302d3131, '2024-10-10 21:05:00', 'Image for product_47 - Variant 1', 1, 'img/product42_1.png', '2024-10-10 21:05:00', 0x01f3a501234567890000000000000012),
(0x33323664613134662d383838302d3131, '2024-10-10 21:05:00', 'Image for product_47 - Variant 2', 2, 'img/product42_3.png', '2024-10-10 21:05:00', 0x01f3a501234567890000000000000012),
(0x33323664613335382d383838302d3131, '2024-10-10 21:10:00', 'Image for product_48', 0, 'img/product43_1.png', '2024-10-10 21:10:00', 0x01f3a501234567890000000000000013),
(0x33323664613535342d383838302d3131, '2024-10-10 21:10:00', 'Image for product_48 - Variant 1', 1, 'img/product43_1.png', '2024-10-10 21:10:00', 0x01f3a501234567890000000000000013),
(0x33323664613862312d383838302d3131, '2024-10-10 21:10:00', 'Image for product_48 - Variant 2', 2, 'img/product43_3.png', '2024-10-10 21:10:00', 0x01f3a501234567890000000000000013),
(0x33323664616137382d383838302d3131, '2024-10-10 21:15:00', 'Image for product_49', 0, 'img/product05_1.png', '2024-10-10 21:15:00', 0x01f3a501234567890000000000000014),
(0x33323664616337342d383838302d3131, '2024-10-10 21:15:00', 'Image for product_49 - Variant 1', 1, 'img/product05_1.png', '2024-10-10 21:15:00', 0x01f3a501234567890000000000000014),
(0x33323664616538342d383838302d3131, '2024-10-10 21:15:00', 'Image for product_49 - Variant 2', 2, 'img/product05_3.png', '2024-10-10 21:15:00', 0x01f3a501234567890000000000000014),
(0x33323664623039302d383838302d3131, '2024-10-10 21:20:00', 'Image for product_50', 0, 'img/product08.png', '2024-10-10 21:20:00', 0x01f3a501234567890000000000000015),
(0x33323664623238392d383838302d3131, '2024-10-10 21:20:00', 'Image for product_50 - Variant 1', 1, 'img/product07_1.png', '2024-10-10 21:20:00', 0x01f3a501234567890000000000000015),
(0x33323664623436362d383838302d3131, '2024-10-10 21:20:00', 'Image for product_50 - Variant 2', 2, 'img/product07_2.png', '2024-10-10 21:20:00', 0x01f3a501234567890000000000000015),
(0x35313537353430392d383838302d3131, '2024-10-11 02:48:27', 'Image for product_4', 0, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-11 02:48:27', 0x1d489d1142454fc9b5f3a37e85c91b6d),
(0x35313537353965352d383838302d3131, '2024-10-11 02:48:27', 'Image for product_4 - Variant 1', 1, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:48:27', 0x1d489d1142454fc9b5f3a37e85c91b6d),
(0x35313537356235302d383838302d3131, '2024-10-11 02:48:27', 'Image for product_4 - Variant 2', 2, 'img/20231130_qsqgN9onPc.jpeg', '2024-10-11 02:48:27', 0x1d489d1142454fc9b5f3a37e85c91b6d),
(0x35313537356337382d383838302d3131, '2024-10-11 02:51:19', 'Image for product_7', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:51:19', 0x235841dcd15a43a6ba68ef8f162786a2),
(0x35313537356461322d383838302d3131, '2024-10-11 02:51:19', 'Image for product_7 - Variant 1', 1, 'img/20231130_bbEzYrSTrP.jpeg', '2024-10-11 02:51:19', 0x235841dcd15a43a6ba68ef8f162786a2),
(0x35313537356565322d383838302d3131, '2024-10-11 02:51:19', 'Image for product_7 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:51:19', 0x235841dcd15a43a6ba68ef8f162786a2),
(0x35313537356666662d383838302d3131, '2024-10-10 20:00:00', 'Image for product_16', 0, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-10 20:00:00', 0x31360000000000000000000000000000),
(0x35313537363136382d383838302d3131, '2024-10-10 20:00:00', 'Image for product_16 - Variant 1', 1, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-10 20:00:00', 0x31360000000000000000000000000000),
(0x35313537363333632d383838302d3131, '2024-10-10 20:00:00', 'Image for product_16 - Variant 2', 2, 'img/20240331_JBpsr4bFgw.png', '2024-10-10 20:00:00', 0x31360000000000000000000000000000),
(0x35313537363438302d383838302d3131, '2024-10-10 20:05:00', 'Image for product_17', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-10 20:05:00', 0x31370000000000000000000000000000),
(0x35313537363730332d383838302d3131, '2024-10-10 20:05:00', 'Image for product_17 - Variant 1', 1, 'img/20231130_bbEzYrSTrP.jpeg', '2024-10-10 20:05:00', 0x31370000000000000000000000000000),
(0x35313537363838362d383838302d3131, '2024-10-10 20:05:00', 'Image for product_17 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-10 20:05:00', 0x31370000000000000000000000000000),
(0x35313537363962312d383838302d3131, '2024-10-10 20:10:00', 'Image for product_18', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-10 20:10:00', 0x31380000000000000000000000000000),
(0x35313537366166622d383838302d3131, '2024-10-10 20:10:00', 'Image for product_18 - Variant 1', 1, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-10 20:10:00', 0x31380000000000000000000000000000),
(0x35313537366339382d383838302d3131, '2024-10-10 20:10:00', 'Image for product_18 - Variant 2', 2, 'img/20231130_qsqgN9onPc.jpeg', '2024-10-10 20:10:00', 0x31380000000000000000000000000000),
(0x35633436336463312d383838312d3131, '2024-10-11 02:54:13', 'Image for product_14', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:54:13', 0x4b0899618c91486a996b7c4c3b5acf25),
(0x35633436343833662d383838312d3131, '2024-10-11 02:54:13', 'Image for product_14 - Variant 1', 1, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:54:13', 0x4b0899618c91486a996b7c4c3b5acf25),
(0x35633436346234632d383838312d3131, '2024-10-11 02:54:13', 'Image for product_14 - Variant 2', 2, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-11 02:54:13', 0x4b0899618c91486a996b7c4c3b5acf25),
(0x35633436346461612d383838312d3131, '2024-10-11 02:53:05', 'Image for product_12', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:53:05', 0x50038924ade148bc833673b11255bfb5),
(0x35633436346666632d383838312d3131, '2024-10-11 02:53:05', 'Image for product_12 - Variant 1', 1, 'img/20231130_qsqgN9onPc.jpeg', '2024-10-11 02:53:05', 0x50038924ade148bc833673b11255bfb5),
(0x35633436353532372d383838312d3131, '2024-10-11 02:53:05', 'Image for product_12 - Variant 2', 2, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-11 02:53:05', 0x50038924ade148bc833673b11255bfb5),
(0x35633436353737312d383838312d3131, '2024-10-11 02:52:49', 'Image for product_11', 0, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:52:49', 0x50038924ade148bc833673b11255bfb5),
(0x35633436353961332d383838312d3131, '2024-10-11 02:52:49', 'Image for product_11 - Variant 1', 1, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:52:49', 0x5c86e9953659473f9a700be465b2528b),
(0x35633436356265632d383838312d3131, '2024-10-11 02:52:49', 'Image for product_11 - Variant 2', 2, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-11 02:52:49', 0x5c86e9953659473f9a700be465b2528b),
(0x35633436356530662d383838312d3131, '2024-10-11 02:51:56', 'Image for product_8', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:51:56', 0x778640d814294255aa828374d5342a13),
(0x35633436363031642d383838312d3131, '2024-10-11 02:51:56', 'Image for product_8 - Variant 1', 1, 'img/20231130_qsqgN9onPc.jpeg', '2024-10-11 02:51:56', 0x778640d814294255aa828374d5342a13),
(0x35633436363230642d383838312d3131, '2024-10-11 02:51:56', 'Image for product_8 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:51:56', 0x778640d814294255aa828374d5342a13),
(0x35633436363430382d383838312d3131, '2024-10-11 02:48:13', 'Image for product_3', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:48:13', 0x9b083144cf724701a8fdbf013bbec402),
(0x35633436363631652d383838312d3131, '2024-10-11 02:48:13', 'Image for product_3 - Variant 1', 1, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-11 02:48:13', 0x9b083144cf724701a8fdbf013bbec402),
(0x35633436363833612d383838312d3131, '2024-10-11 02:48:13', 'Image for product_3 - Variant 2', 2, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:48:13', 0x9b083144cf724701a8fdbf013bbec402),
(0x35633436366237362d383838312d3131, '2024-10-11 02:51:11', 'Image for product_6', 0, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:51:11', 0x9bd6004055034dff95a9cc5112f49313),
(0x35633436366530382d383838312d3131, '2024-10-11 02:51:11', 'Image for product_6 - Variant 1', 1, 'img/20231130_bbEzYrSTrP.jpeg', '2024-10-11 02:51:11', 0x9bd6004055034dff95a9cc5112f49313),
(0x35633436373033662d383838312d3131, '2024-10-11 02:51:11', 'Image for product_6 - Variant 2', 2, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:51:11', 0x9bd6004055034dff95a9cc5112f49313),
(0x35633436653037662d383838312d3131, '2024-10-11 02:47:47', 'Image for product_1', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:47:47', 0xa547c7cff93e483399fcb82ffd1c0242),
(0x35633436653361322d383838312d3131, '2024-10-11 02:47:47', 'Image for product_1 - Variant 1', 1, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-11 02:47:47', 0xa547c7cff93e483399fcb82ffd1c0242),
(0x35633436653636352d383838312d3131, '2024-10-11 02:47:47', 'Image for product_1 - Variant 2', 2, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:47:47', 0xa547c7cff93e483399fcb82ffd1c0242),
(0x35633436653938352d383838312d3131, '2024-10-11 02:52:27', 'Image for product_9', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:52:27', 0xbaf72de39128453e8a20dcecce3f3305),
(0x35633436656334332d383838312d3131, '2024-10-11 02:52:27', 'Image for product_9 - Variant 1', 1, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-11 02:52:27', 0xbaf72de39128453e8a20dcecce3f3305),
(0x35633436656663622d383838312d3131, '2024-10-11 02:52:27', 'Image for product_9 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:52:27', 0xbaf72de39128453e8a20dcecce3f3305),
(0x35633436663231632d383838312d3131, '2024-10-11 02:49:01', 'Image for product_5', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:49:01', 0xd7b72420564248f7895e6204b572dc51),
(0x35633436663337342d383838312d3131, '2024-10-11 02:49:01', 'Image for product_5 - Variant 1', 1, 'img/20231130_qsqgN9onPc.jpeg', '2024-10-11 02:49:01', 0xd7b72420564248f7895e6204b572dc51),
(0x35633436663462322d383838312d3131, '2024-10-11 02:49:01', 'Image for product_5 - Variant 2', 2, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:49:01', 0xd7b72420564248f7895e6204b572dc51),
(0x37346364663037642d383838312d3131, '2024-10-11 02:52:43', 'Image for product_10', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:52:43', 0xdd65a34ef646462ea3ce8d78d28460fe),
(0x37346364663730632d383838312d3131, '2024-10-11 02:52:43', 'Image for product_10 - Variant 1', 1, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-11 02:52:43', 0xdd65a34ef646462ea3ce8d78d28460fe),
(0x37346364663836662d383838312d3131, '2024-10-11 02:52:43', 'Image for product_10 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-11 02:52:43', 0xdd65a34ef646462ea3ce8d78d28460fe),
(0x37346364663939322d383838312d3131, '2024-10-11 02:54:18', 'Image for product_15', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-11 02:54:18', 0xdfc75d0d719e45c3965bc90609a73789),
(0x37346364666161322d383838312d3131, '2024-10-11 02:54:18', 'Image for product_15 - Variant 1', 1, 'img/20231130_bbEzYrSTrP.jpeg', '2024-10-11 02:54:18', 0xdfc75d0d719e45c3965bc90609a73789),
(0x37346364666330322d383838312d3131, '2024-10-11 02:54:18', 'Image for product_15 - Variant 2', 2, 'img/20240331_JBpsr4bFgw.png', '2024-10-11 02:54:18', 0xdfc75d0d719e45c3965bc90609a73789),
(0x38623964613663652d383838302d3131, '2024-10-10 20:15:00', 'Image for product_19', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-10 20:15:00', 0x31390000000000000000000000000000),
(0x38623964616434322d383838302d3131, '2024-10-10 20:15:00', 'Image for product_19 - Variant 1', 1, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-10 20:15:00', 0x31390000000000000000000000000000),
(0x38623964616635332d383838302d3131, '2024-10-10 20:15:00', 'Image for product_19 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-10 20:15:00', 0x31390000000000000000000000000000),
(0x38623964623137332d383838302d3131, '2024-10-10 20:20:00', 'Image for product_20', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-10 20:20:00', 0x32300000000000000000000000000000),
(0x38623964623637652d383838302d3131, '2024-10-10 20:20:00', 'Image for product_20 - Variant 1', 1, 'img/20231130_qsqgN9onPc.jpeg', '2024-10-10 20:20:00', 0x32300000000000000000000000000000),
(0x38623964623838622d383838302d3131, '2024-10-10 20:20:00', 'Image for product_20 - Variant 2', 2, 'img/20240331_l6aLpOd7u7.png', '2024-10-10 20:20:00', 0x32300000000000000000000000000000),
(0x38623964626135392d383838302d3131, '2024-10-10 20:25:00', 'Image for product_21', 0, 'img/20240331_JBpsr4bFgw.png', '2024-10-10 20:25:00', 0x32310000000000000000000000000000),
(0x38623964626332392d383838302d3131, '2024-10-10 20:25:00', 'Image for product_21 - Variant 1', 1, 'img/20231130_bbEzYrSTrP.jpeg', '2024-10-10 20:25:00', 0x32310000000000000000000000000000),
(0x38623964626534302d383838302d3131, '2024-10-10 20:25:00', 'Image for product_21 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-10 20:25:00', 0x32310000000000000000000000000000),
(0x38623964633035392d383838302d3131, '2024-10-10 20:30:00', 'Image for product_22', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-10 20:30:00', 0x32320000000000000000000000000000),
(0x38623964633235622d383838302d3131, '2024-10-10 20:30:00', 'Image for product_22 - Variant 1', 1, 'img/20231130_9lWgd5JLu4.jpeg', '2024-10-10 20:30:00', 0x32320000000000000000000000000000),
(0x38623964633437382d383838302d3131, '2024-10-10 20:30:00', 'Image for product_22 - Variant 2', 2, 'img/20240331_JBpsr4bFgw.png', '2024-10-10 20:30:00', 0x32320000000000000000000000000000),
(0x38623964633635342d383838302d3131, '2024-10-10 20:35:00', 'Image for product_23', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-10 20:35:00', 0x32330000000000000000000000000000),
(0x38623964633831642d383838302d3131, '2024-10-10 20:35:00', 'Image for product_23 - Variant 1', 1, 'img/20231130_bbEzYrSTrP.jpeg', '2024-10-10 20:35:00', 0x32330000000000000000000000000000),
(0x38623964636132372d383838302d3131, '2024-10-10 20:35:00', 'Image for product_23 - Variant 2', 2, 'img/20240331_UT3Fmea0EM.png', '2024-10-10 20:35:00', 0x32330000000000000000000000000000),
(0x38623964636330362d383838302d3131, '2024-10-10 20:40:00', 'Image for product_24', 0, 'img/20240331_l6aLpOd7u7.png', '2024-10-10 20:40:00', 0x32340000000000000000000000000000),
(0x38623964636530372d383838302d3131, '2024-10-10 20:40:00', 'Image for product_24 - Variant 1', 1, 'img/20231130_8f4bSHPbTn.jpeg', '2024-10-10 20:40:00', 0x32340000000000000000000000000000),
(0x38623964636666632d383838302d3131, '2024-10-10 20:40:00', 'Image for product_24 - Variant 2', 2, 'img/20240331_JBpsr4bFgw.png', '2024-10-10 20:40:00', 0x32340000000000000000000000000000),
(0x38623964643231362d383838302d3131, '2024-10-10 20:45:00', 'Image for product_25', 0, 'img/20221217_pdxFhSaE019HwzZ4gn43g5J9.jpg', '2024-10-10 20:45:00', 0x32350000000000000000000000000000),
(0x38623964643432382d383838302d3131, '2024-10-10 20:45:00', 'Image for product_25 - Variant 1', 1, 'img/20231208_7LnI3iZkJX.jpeg', '2024-10-10 20:45:00', 0x32350000000000000000000000000000),
(0x38623964643631612d383838302d3131, '2024-10-10 20:45:00', 'Image for product_25 - Variant 2', 2, 'img/20230304_rkzwbDstkLriSEhu.jpeg', '2024-10-10 20:45:00', 0x32350000000000000000000000000000),
(0x38623964643834332d383838302d3131, '2024-10-10 20:50:00', 'Image for product_26', 0, 'img/20230304_9yEIrUoAkjIxrmbe.jpeg', '2024-10-10 20:50:00', 0x32360000000000000000000000000000),
(0x38623964646131342d383838302d3131, '2024-10-10 20:50:00', 'Image for product_26 - Variant 1', 1, 'img/20231208_bV5n629M1e.jpeg', '2024-10-10 20:50:00', 0x32360000000000000000000000000000),
(0x38623964646237612d383838302d3131, '2024-10-10 20:50:00', 'Image for product_26 - Variant 2', 2, 'img/20221217_S0qZzp4wr0yN3p3OFJGbSJy1.jpg', '2024-10-10 20:50:00', 0x32360000000000000000000000000000),
(0x38623964646338632d383838302d3131, '2024-10-10 20:55:00', 'Image for product_27', 0, 'img/20221217_pdxFhSaE019HwzZ4gn43g5J9.jpg', '2024-10-10 20:55:00', 0x32370000000000000000000000000000),
(0x38623964646461622d383838302d3131, '2024-10-10 20:55:00', 'Image for product_27 - Variant 1', 1, 'img/20231208_7LnI3iZkJX.jpeg', '2024-10-10 20:55:00', 0x32370000000000000000000000000000),
(0x38623964646562302d383838302d3131, '2024-10-10 20:55:00', 'Image for product_27 - Variant 2', 2, 'img/20230304_rkzwbDstkLriSEhu.jpeg', '2024-10-10 20:55:00', 0x32370000000000000000000000000000),
(0x38623964646664342d383838302d3131, '2024-10-10 21:00:00', 'Image for product_28', 0, 'img/20230304_9yEIrUoAkjIxrmbe.jpeg', '2024-10-10 21:00:00', 0x32380000000000000000000000000000),
(0x38623964653066302d383838302d3131, '2024-10-10 21:00:00', 'Image for product_28 - Variant 1', 1, 'img/20231208_bV5n629M1e.jpeg', '2024-10-10 21:00:00', 0x32380000000000000000000000000000),
(0x64656131346634302d383838302d3131, '2024-10-10 21:00:00', 'Image for product_28', 0, 'img/20221217_S0qZzp4wr0yN3p3OFJGbSJy1.jpg', '2024-10-10 21:00:00', 0x32380000000000000000000000000000),
(0x64656131353561342d383838302d3131, '2024-10-10 21:00:00', 'Image for product_28 - Variant 1', 1, 'img/20221217_pdxFhSaE019HwzZ4gn43g5J9.jpg', '2024-10-10 21:00:00', 0x32380000000000000000000000000000),
(0x64656131353732312d383838302d3131, '2024-10-10 21:00:00', 'Image for product_28 - Variant 2', 2, 'img/20230304_rkzwbDstkLriSEhu.jpeg', '2024-10-10 21:00:00', 0x32380000000000000000000000000000),
(0x64656131353861342d383838302d3131, '2024-10-10 21:05:00', 'Image for product_29', 0, 'img/20231208_7LnI3iZkJX.jpeg', '2024-10-10 21:05:00', 0x32390000000000000000000000000000),
(0x64656131353966652d383838302d3131, '2024-10-10 21:05:00', 'Image for product_29 - Variant 1', 1, 'img/20230304_9yEIrUoAkjIxrmbe.jpeg', '2024-10-10 21:05:00', 0x32390000000000000000000000000000),
(0x64656131356239312d383838302d3131, '2024-10-10 21:05:00', 'Image for product_29 - Variant 2', 2, 'img/20221217_S0qZzp4wr0yN3p3OFJGbSJy1.jpg', '2024-10-10 21:05:00', 0x32390000000000000000000000000000),
(0x64656131356363642d383838302d3131, '2024-10-11 02:54:06', 'Image for product_13', 0, 'img/20221217_pdxFhSaE019HwzZ4gn43g5J9.jpg', '2024-10-11 02:54:06', 0x32e0ed1366fd4608b783b999cecfbd40),
(0x64656131356530322d383838302d3131, '2024-10-11 02:54:06', 'Image for product_13 - Variant 1', 1, 'img/20230304_9yEIrUoAkjIxrmbe.jpeg', '2024-10-11 02:54:06', 0x32e0ed1366fd4608b783b999cecfbd40),
(0x64656131356632612d383838302d3131, '2024-10-11 02:54:06', 'Image for product_13 - Variant 2', 2, 'img/20231208_7LnI3iZkJX.jpeg', '2024-10-11 02:54:06', 0x32e0ed1366fd4608b783b999cecfbd40),
(0x64656131363035342d383838302d3131, '2024-10-10 21:10:00', 'Image for product_30', 0, 'img/20231208_bV5n629M1e.jpeg', '2024-10-10 21:10:00', 0x33300000000000000000000000000000),
(0x64656131363163332d383838302d3131, '2024-10-10 21:10:00', 'Image for product_30 - Variant 1', 1, 'img/20230304_rkzwbDstkLriSEhu.jpeg', '2024-10-10 21:10:00', 0x33300000000000000000000000000000),
(0x64656131363332372d383838302d3131, '2024-10-10 21:10:00', 'Image for product_30 - Variant 2', 2, 'img/20221217_S0qZzp4wr0yN3p3OFJGbSJy1.jpg', '2024-10-10 21:10:00', 0x33300000000000000000000000000000),
(0x64656131363466352d383838302d3131, '2024-10-10 21:15:00', 'Image for product_31', 0, 'img/20230304_9yEIrUoAkjIxrmbe.jpeg', '2024-10-10 21:15:00', 0x33310000000000000000000000000000),
(0x64656131363634332d383838302d3131, '2024-10-10 21:15:00', 'Image for product_31 - Variant 1', 1, 'img/20231208_7LnI3iZkJX.jpeg', '2024-10-10 21:15:00', 0x33310000000000000000000000000000),
(0x64656131363739642d383838302d3131, '2024-10-10 21:15:00', 'Image for product_31 - Variant 2', 2, 'img/20231208_bV5n629M1e.jpeg', '2024-10-10 21:15:00', 0x33310000000000000000000000000000),
(0x64656131363935332d383838302d3131, '2024-10-10 21:20:00', 'Image for product_32', 0, 'img/20221217_pdxFhSaE019HwzZ4gn43g5J9.jpg', '2024-10-10 21:20:00', 0x33320000000000000000000000000000),
(0x64656131366162302d383838302d3131, '2024-10-10 21:20:00', 'Image for product_32 - Variant 1', 1, 'img/20230304_9yEIrUoAkjIxrmbe.jpeg', '2024-10-10 21:20:00', 0x33320000000000000000000000000000),
(0x64656131366330652d383838302d3131, '2024-10-10 21:20:00', 'Image for product_32 - Variant 2', 2, 'img/20231208_7LnI3iZkJX.jpeg', '2024-10-10 21:20:00', 0x33320000000000000000000000000000),
(0x64656131366461382d383838302d3131, '2024-10-10 21:25:00', 'Image for product_33', 0, 'img/20231208_bV5n629M1e.jpeg', '2024-10-10 21:25:00', 0x33340000000000000000000000000000),
(0x64656131366633372d383838302d3131, '2024-10-10 21:25:00', 'Image for product_33 - Variant 1', 1, 'img/20231211_WucMBrbVGP.jpg', '2024-10-10 21:25:00', 0x33340000000000000000000000000000),
(0x64656131373037332d383838302d3131, '2024-10-10 21:25:00', 'Image for product_33 - Variant 2', 2, 'img/20231212_Jj8r8Idiec.jpeg', '2024-10-10 21:25:00', 0x33340000000000000000000000000000),
(0x64656131373164612d383838302d3131, '2024-10-10 21:30:00', 'Image for product_34', 0, 'img/20231223_vx9JS4MK9x.jpeg', '2024-10-10 21:30:00', 0x33330000000000000000000000000000),
(0x64656131373332632d383838302d3131, '2024-10-10 21:30:00', 'Image for product_34 - Variant 1', 1, 'img/20231226_70t2vfYo6b.jpeg', '2024-10-10 21:30:00', 0x33330000000000000000000000000000),
(0x64656131373437622d383838302d3131, '2024-10-10 21:30:00', 'Image for product_34 - Variant 2', 2, 'img/20240110_VBp9yLNzMi.jpeg', '2024-10-10 21:30:00', 0x33330000000000000000000000000000),
(0x64656131373562632d383838302d3131, '2024-10-10 21:35:00', 'Image for product_35', 0, 'img/20240216_5yXbztAVwH.jpeg', '2024-10-10 21:35:00', 0x33350000000000000000000000000000),
(0x64656131373665332d383838302d3131, '2024-10-10 21:35:00', 'Image for product_35 - Variant 1', 1, 'img/20240308_hsm23neJ1D.jpeg', '2024-10-10 21:35:00', 0x33350000000000000000000000000000),
(0x64656131373766612d383838302d3131, '2024-10-10 21:35:00', 'Image for product_35 - Variant 2', 2, 'img/20200225_y0nRhnwGDyzKR7hOKlPIMuqN.jpg', '2024-10-10 21:35:00', 0x33350000000000000000000000000000),
(0x66303238376461632d383837662d3131, '2024-10-10 20:45:00', 'Image for product_43', 0, 'img/20201208_vAbrrfhrtOdFdnTys8UShvg0.jpg', '2024-10-10 20:45:00', 0x01f3a501234567890000000000000008),
(0x66303238383266652d383837662d3131, '2024-10-10 20:45:00', 'Image for product_43 - Variant 1', 1, 'img/product43_1.png', '2024-10-10 20:45:00', 0x01f3a501234567890000000000000008),
(0x66303238383438632d383837662d3131, '2024-10-10 20:45:00', 'Image for product_43 - Variant 2', 2, 'img/product43_2.png', '2024-10-10 20:45:00', 0x01f3a501234567890000000000000008),
(0x66303238383632372d383837662d3131, '2024-10-10 20:50:00', 'Image for product_44', 0, 'img/product44.png', '2024-10-10 20:50:00', 0x01f3a501234567890000000000000009),
(0x66303238383764382d383837662d3131, '2024-10-10 20:50:00', 'Image for product_44 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0x01f3a501234567890000000000000009),
(0x66303238383938622d383837662d3131, '2024-10-10 20:50:00', 'Image for product_44 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0x01f3a501234567890000000000000009),
(0x66303238383632472d383837662d3131, '2024-10-10 20:50:00', 'Image for product_46', 0, 'img/product44.png', '2024-10-10 20:50:00', 0xdd67a34ef646462ea3ce8d78d28460fe),
(0x66303238383764582d383837662d3131, '2024-10-10 20:50:00', 'Image for product_46 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0xdd67a34ef646462ea3ce8d78d28460fe),
(0x66303238383938612d383837662d3131, '2024-10-10 20:50:00', 'Image for product_46 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0xdd67a34ef646462ea3ce8d78d28460fe),
(0x66303238383632472d383837662d6131, '2024-10-10 20:50:00', 'Image for product_47', 0, 'img/product44.png', '2024-10-10 20:50:00', 0xdd68a34ef646462ea3ce8d78d28460fe),
(0x66303238383764582d383837662d4131, '2024-10-10 20:50:00', 'Image for product_47 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0xdd68a34ef646462ea3ce8d78d28460fe),
(0x66303238383938612d383837662d5131, '2024-10-10 20:50:00', 'Image for product_47 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0xdd68a34ef646462ea3ce8d78d28460fe),
(0x66303238383632472d383838662d6131, '2024-10-10 20:50:00', 'Image for product_48', 0, 'img/product44.png', '2024-10-10 20:50:00', 0xdd69a34ef646462ea3ce8d78d28460fe),
(0x66303238383764582d383839662d4131, '2024-10-10 20:50:00', 'Image for product_48 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0xdd69a34ef646462ea3ce8d78d28460fe),
(0x66303238383938612d383840662d5131, '2024-10-10 20:50:00', 'Image for product_48 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0xdd69a34ef646462ea3ce8d78d28460fe),
(0x66303238383632472d383838662d6132, '2024-10-10 20:50:00', 'Image for product_49', 0, 'img/product44.png', '2024-10-10 20:50:00', 0xdd66a34ef646462ea3ce8d78d28460fe),
(0x66303238383764582d383839662d4133, '2024-10-10 20:50:00', 'Image for product_49 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0xdd66a34ef646462ea3ce8d78d28460fe),
(0x66303238383938612d383840662d5134, '2024-10-10 20:50:00', 'Image for product_49 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0xdd66a34ef646462ea3ce8d78d28460fe),
(0x66303238383632472d383838662d6135, '2024-10-10 20:50:00', 'Image for product_50', 0, 'img/product44.png', '2024-10-10 20:50:00', 0xdd70a34ef646462ea3ce8d78d28460fe),
(0x66303238383764582d383839662d4136, '2024-10-10 20:50:00', 'Image for product_50 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0xdd70a34ef646462ea3ce8d78d28460fe),
(0x66303238383938612d383840662d5137, '2024-10-10 20:50:00', 'Image for product_50 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0xdd70a34ef646462ea3ce8d78d28460fe),
(0x66303238383632472d383838662d6138, '2024-10-10 20:50:00', 'Image for product_51', 0, 'img/product44.png', '2024-10-10 20:50:00', 0xdd71a34ef646462ea3ce8d78d28460fe),
(0x66303238383764582d383839662d4139, '2024-10-10 20:50:00', 'Image for product_51 - Variant 1', 1, 'img/product44_1.png', '2024-10-10 20:50:00', 0xdd71a34ef646462ea3ce8d78d28460fe),
(0x66303238383938612d383840662d5140, '2024-10-10 20:50:00', 'Image for product_51 - Variant 2', 2, 'img/product44_2.png', '2024-10-10 20:50:00', 0xdd71a34ef646462ea3ce8d78d28460fe),
(0x66303238386163322d383837662d3131, '2024-10-10 20:55:00', 'Image for product_45', 0, 'img/product45.png', '2024-10-10 20:55:00', 0x01f3a501234567890000000000000010),
(0x66303238386331362d383837662d3131, '2024-10-10 20:55:00', 'Image for product_45 - Variant 1', 1, 'img/product45_1.png', '2024-10-10 20:55:00', 0x01f3a501234567890000000000000010),
(0x66303238386437342d383837662d3131, '2024-10-10 20:55:00', 'Image for product_45 - Variant 2', 2, 'img/product45_2.png', '2024-10-10 20:55:00', 0x01f3a501234567890000000000000010);

-- --------------------------------------------------------


--
-- Cấu trúc bảng cho bảng `product_logs`
--

DROP TABLE IF EXISTS `product_logs`;
CREATE TABLE IF NOT EXISTS `product_logs` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `action` varchar(50) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `new_data` json DEFAULT NULL,
  `old_data` json DEFAULT NULL,
  `product_id` varbinary(255) NOT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_notifications`
--

DROP TABLE IF EXISTS `product_notifications`;
CREATE TABLE IF NOT EXISTS `product_notifications` (
  `notification_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_price` double NOT NULL,
  `product_quantity` int NOT NULL,
  `product_sale` double NOT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_notifications_seq`
--

DROP TABLE IF EXISTS `product_notifications_seq`;
CREATE TABLE IF NOT EXISTS `product_notifications_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `product_notifications_seq`
--

INSERT INTO `product_notifications_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_sizes`
--

DROP TABLE IF EXISTS `product_sizes`;
CREATE TABLE IF NOT EXISTS `product_sizes` (
  `product_size_id` binary(16) NOT NULL,
  `product_size_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_size_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`product_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `product_sizes`
--

INSERT INTO `product_sizes` (`product_size_id`, `product_size_name`, `product_size_code`) VALUES
(0x00000000000000000000000000000000, 'XL', 'X-Large'),
(0x001a3d48fdd1234a6b789c1234f12345, 'S', 'Small'),
(0x002b5d28fdd1234b7c987d5432f23456, 'M', 'Medium'),
(0x003c6e39fdd2345c8d876e6543f34567, 'L', 'Large'),
(0x06550000000000000000000000000000, 'XXL', 'XX-Large');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_suppliers`
--

DROP TABLE IF EXISTS `product_suppliers`;
CREATE TABLE IF NOT EXISTS `product_suppliers` (
  `supplier_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `supplier_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supplier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `product_suppliers`
--

INSERT INTO `product_suppliers` (`supplier_id`, `created_at`, `description`, `supplier_logo`, `supplier_name`, `updated_at`) VALUES
(0x01000000000000000000000000000000, '2024-10-11 11:57:10', '1', '1', 'Adidas', '2024-10-11 11:57:10'),
(0x02000000000000000000000000000000, '2024-10-11 11:57:33', '2', '2', 'GU', '2024-10-11 11:57:33'),
(0x03000000000000000000000000000000, '2024-11-01 14:31:41', '123', '123', 'Tailwind', '2024-11-01 14:31:41'),
(0x04000000000000000000000000000000, '2024-11-01 14:31:41', '345', '345', 'KOO', '2024-11-01 14:31:41'),
(0x05000000000000000000000000000000, '2024-11-01 14:31:41', '123', '123', 'NAAK', '2024-11-01 14:31:41'),
(0x06000000000000000000000000000000, '2024-11-01 14:31:41', '666', '666', 'Naked', '2024-11-01 14:31:41');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`role_id`, `created_at`, `role_name`, `updated_at`) VALUES
(0x4a79ce30c0a64ecf93e4263c41a24d3d, '2024-11-01 12:35:00', 'ADMIN', '2024-11-01 12:35:00'),
(0x9e50a2d4d2cc46e3a040f3da335309fd, '2024-11-01 12:51:31', 'USER', '2024-11-01 12:51:31');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE IF NOT EXISTS `roles_permissions` (
  `role_id` binary(16) NOT NULL,
  `permission_id` binary(16) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKbx9r9uw77p58gsq4mus0mec0o` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `roles_permissions`
--

INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES
(0x4a79ce30c0a64ecf93e4263c41a24d3d, 0x223385b6f3a94f1fbd7b7029fac24dc1),
(0x4a79ce30c0a64ecf93e4263c41a24d3d, 0x674bd611214e47fe9fa4a4d7a24e10db),
(0x4a79ce30c0a64ecf93e4263c41a24d3d, 0xb69fe5e113ff4ad7ba39ea057f628cf6),
(0x9e50a2d4d2cc46e3a040f3da335309fd, 0xb69fe5e113ff4ad7ba39ea057f628cf6),
(0x4a79ce30c0a64ecf93e4263c41a24d3d, 0xcf103fe290d943e887af1d53ca6a6d35);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `shipments`
--

DROP TABLE IF EXISTS `shipments`;
CREATE TABLE IF NOT EXISTS `shipments` (
  `shipment_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `shipment_date` timestamp(6) NOT NULL,
  `shipment_discount` float DEFAULT '0',
  `shipment_ship_cost` float DEFAULT '0',
  `updated_at` timestamp NOT NULL,
  `supplier_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`shipment_id`),
  KEY `FK90n261qlrb49h9xn48ql8yxbe` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `shipments_products`
--

DROP TABLE IF EXISTS `shipments_products`;
CREATE TABLE IF NOT EXISTS `shipments_products` (
  `shipments_products_price` double NOT NULL DEFAULT '0',
  `shipments_products_quantity` int NOT NULL DEFAULT '0',
  `product_id` binary(16) NOT NULL,
  `product_size_id` binary(16) NOT NULL,
  `shipment_id` binary(16) NOT NULL,
  PRIMARY KEY (`product_id`,`product_size_id`,`shipment_id`),
  KEY `FKocitskht28lhebxr3rks6oh19` (`product_size_id`),
  KEY `FK7a3qxmqf2odcixfuht9lds6ho` (`shipment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `slideshow_images`
--

DROP TABLE IF EXISTS `slideshow_images`;
CREATE TABLE IF NOT EXISTS `slideshow_images` (
  `slideshow_image_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `slideshow_image_alt` varchar(255) DEFAULT NULL,
  `slideshow_image_index` int NOT NULL,
  `slideshow_image_url` varchar(255) NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`slideshow_image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` binary(16) NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `user_birthday` timestamp NULL DEFAULT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_first_name` varchar(255) NOT NULL,
  `user_image_path` varchar(255) DEFAULT NULL,
  `user_last_name` varchar(255) NOT NULL,
  `user_money` double NOT NULL DEFAULT '0',
  `user_password` varchar(255) NOT NULL,
  `user_password_level_2` varchar(255) DEFAULT NULL,
  `user_phone` varchar(15) NOT NULL,
  `user_point` int DEFAULT '0',
  `user_status` int DEFAULT '0',
  `user_wrong_password` int DEFAULT '0',
  `card_id` binary(16) DEFAULT NULL,
  `user_address_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKqvssa6h1me5k6ohvd67jexrh8` (`card_id`),
  UNIQUE KEY `UK7aefo5eehy02e1u256a83il34` (`user_address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `created_at`, `updated_at`, `user_birthday`, `user_email`, `user_first_name`, `user_image_path`, `user_last_name`, `user_money`, `user_password`, `user_password_level_2`, `user_phone`, `user_point`, `user_status`, `user_wrong_password`, `card_id`, `user_address_id`) VALUES
(0x2605d82df28d4988ba577ecff7bb5803, '2024-11-14 10:12:07', '2024-11-14 10:18:10', '1990-01-01 00:00:00', 'dinhanngo058@gmail.com', '1', NULL, '111', 0, '$2a$10$iLWixQhF6JtjcRfyGnsH2Ob6H5ukzSmbHVWYX2Bq3DNhDfEpUX4Gi', NULL, '+1234567890', 0, 1, 0, NULL, NULL),
(0x4e98028c21574568a9bcc21033bad79a, '2024-11-10 13:45:47', '2024-11-10 13:51:27',  '1990-01-01 00:00:00', 'admin@gmail.com', '1', NULL, 'admin', 0, '$2a$10$oG4Dur0YJWCFKBi3dITgh.AFKJM8166uFeYUwGiVNyl1.35QMecwK', NULL, '+1234567890', 0, 1, 0, NULL, NULL),
(0x53bfa11504e34a9d8345a81e6dd086ed, '2024-11-11 09:03:39', '2024-11-11 09:05:08', NULL, 'thiet1@gmail.com', '', NULL, '', 0, '', NULL, '', 0, 1, 0, NULL, NULL),
(0x66dec756b1ec45c9a6a520a62b429982, '2024-11-10 13:37:00', '2024-11-11 06:16:25', '1990-01-01 00:00:00', '22211tt4420@mail.tdc.edu.vn', 'thiet', NULL, '1', 0, '$2a$10$ZZf8eHsVFn9IAyb0FoA/nOHtUew0PXTlk1i9fb5ddqtJcXCpvESYm', NULL, '+1234567890', 0, 1, 0, NULL, NULL),
(0x66fed65f32eb492b8bca673121da64b0, '2024-11-11 06:29:41', '2024-11-11 06:29:41', NULL, '', '', NULL, '', 0, '', NULL, '', 0, 0, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE IF NOT EXISTS `users_roles` (
  `user_id` binary(16) NOT NULL,
  `role_id` binary(16) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(0x4e98028c21574568a9bcc21033bad79a, 0x4a79ce30c0a64ecf93e4263c41a24d3d),
(0x2605d82df28d4988ba577ecff7bb5803, 0x9e50a2d4d2cc46e3a040f3da335309fd),
(0x66dec756b1ec45c9a6a520a62b429982, 0x9e50a2d4d2cc46e3a040f3da335309fd);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_address`
--

DROP TABLE IF EXISTS `user_address`;
CREATE TABLE IF NOT EXISTS `user_address` (
  `user_address_id` binary(16) NOT NULL,
  `user_address_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_city` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_commune` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_district` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_address` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_ward` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`user_address_id`),
  UNIQUE KEY `UKkfu0161nvirkey6fwd6orucv7` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_otps`
--

DROP TABLE IF EXISTS `user_otps`;
CREATE TABLE IF NOT EXISTS `user_otps` (
  `otp_id` binary(16) NOT NULL,
  `otp` varchar(15) NOT NULL,
  `otp_time` timestamp NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`otp_id`),
  UNIQUE KEY `UKq432i0ll8i5a3pnk2e9a38x5w` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `user_otps`
--

INSERT INTO `user_otps` (`otp_id`, `otp`, `otp_time`, `user_id`) VALUES
(0x95f0d94224ba4a14a2c6acf45cc14f4e, '887047', '2024-11-14 10:12:07', 0x2605d82df28d4988ba577ecff7bb5803);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `carts_products`
--
ALTER TABLE `carts_products`
  ADD CONSTRAINT `FK3nvguygrfbn661omvvu2uafu5` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`),
  ADD CONSTRAINT `FKa67i8aotf55o9f5uxel1pj04k` FOREIGN KEY (`product_size_id`) REFERENCES `product_sizes` (`product_size_id`),
  ADD CONSTRAINT `FKt3mepi19unnkcmw4683q5wr39` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`category_id`),
  ADD CONSTRAINT `FKtqh6imciis386iuva8vls1aem` FOREIGN KEY (`category_status_id`) REFERENCES `category_status` (`category_status_id`);

--
-- Các ràng buộc cho bảng `categories_products`
--
ALTER TABLE `categories_products`
  ADD CONSTRAINT `FK2a3u5mbtmtq3d4s5abajhhksf` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  ADD CONSTRAINT `FK2tnk948b1lgpg3uggwyi2kwfq` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `one_time_password`
--
ALTER TABLE `one_time_password`
  ADD CONSTRAINT `FKo7cu5pvm7rbn6a1y1s18yr2nn` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK594fgx8wpklcf3t41jq3grhlh` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`);

--
-- Các ràng buộc cho bảng `order_coupon`
--
ALTER TABLE `order_coupon`
  ADD CONSTRAINT `FK2wuerggqssjbqbucb6g7s3pn9` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`coupon_id`),
  ADD CONSTRAINT `FKr5oexo1rm317sadmtqa6nqmk5` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Các ràng buộc cho bảng `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `FK2cgbv48uplf00occltdmartna` FOREIGN KEY (`post_status_id`) REFERENCES `post_status` (`post_status_id`),
  ADD CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `post_comments`
--
ALTER TABLE `post_comments`
  ADD CONSTRAINT `FKaawaqxjs3br8dw5v90w7uu514` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`),
  ADD CONSTRAINT `FKsnxoecngu89u3fh4wdrgf0f2g` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKbqwuv88p21uen98pftog39e92` FOREIGN KEY (`supplier_id`) REFERENCES `product_suppliers` (`supplier_id`),
  ADD CONSTRAINT `FKkf5dpt7yowdqukggeecu0una7` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`),
  ADD CONSTRAINT `FKn0clu2dx9j9uotmj3jxsb3pgc` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`coupon_id`);

--
-- Các ràng buộc cho bảng `products_sizes`
--
ALTER TABLE `products_sizes`
  ADD CONSTRAINT `FK2orhe315micmx1dsqb48d6mjk` FOREIGN KEY (`product_size_id`) REFERENCES `product_sizes` (`product_size_id`),
  ADD CONSTRAINT `FKddbtdcgrf05hypy7y2rol12tc` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `roles_permissions`
--
ALTER TABLE `roles_permissions`
  ADD CONSTRAINT `FKbx9r9uw77p58gsq4mus0mec0o` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`),
  ADD CONSTRAINT `FKqi9odri6c1o81vjox54eedwyh` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

--
-- Các ràng buộc cho bảng `shipments`
--
ALTER TABLE `shipments`
  ADD CONSTRAINT `FK90n261qlrb49h9xn48ql8yxbe` FOREIGN KEY (`supplier_id`) REFERENCES `product_suppliers` (`supplier_id`);

--
-- Các ràng buộc cho bảng `shipments_products`
--
ALTER TABLE `shipments_products`
  ADD CONSTRAINT `FK1d3392ibaqejg2jn9edpm37lx` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FK7a3qxmqf2odcixfuht9lds6ho` FOREIGN KEY (`shipment_id`) REFERENCES `shipments` (`shipment_id`),
  ADD CONSTRAINT `FKocitskht28lhebxr3rks6oh19` FOREIGN KEY (`product_size_id`) REFERENCES `product_sizes` (`product_size_id`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKpyp72ld6dqk4t7t6w2j4ey47j` FOREIGN KEY (`card_id`) REFERENCES `id_card` (`card_id`);

--
-- Các ràng buộc cho bảng `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

--
-- Các ràng buộc cho bảng `user_address`
--
ALTER TABLE `user_address`
  ADD CONSTRAINT `FKrmincuqpi8m660j1c57xj7twr` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `user_otps`
--
ALTER TABLE `user_otps`
  ADD CONSTRAINT `FK6b7wl9e3l4ry3m4afynki929` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
