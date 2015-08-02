CREATE TABLE `migrations` (
  `id` int(5)
) ENGINE=InnoDB;

CREATE TABLE `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `pincode` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `stations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station_full_name` varchar(100),
  `state` varchar(20),
  `station_short_name` varchar(50),
  `station_code` VARCHAR(11),
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `close_dates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `designations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(50),
  `name` varchar(50) NOT NULL,
  `designation_id` int(5) NOT NULL,
  `contact_no` int(30),
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `bank_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_account_name` varchar(50) NOT NULL,
  `bank_account_email` varchar(50),
  `bank_account_number` varchar(50) NOT NULL,
  `bank_name` varchar(50) NOT NULL,
  `bank_location_id` int(5) NOT NULL,
  `ifsc_code` varchar(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `tax_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_tax_number` varchar(50),
  `vat_no` varchar(50),
  `food_license` varchar(50),
  `FSSAI` varchar(50),
  `mou_acceptance` varchar(50),
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `restaurants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `internal_id` varchar(20),
  `name` varchar(50) NOT NULL,
  `station_id` int(5),
  `distance` decimal(18,2),
  `contact_no` varchar(50) NOT NULL,
  `open_time` int(10) NOT NULL,
  `close_time` int(10) NOT NULL,
  `sla_details` int(5) NOT NULL,
  `minimum_order_value` decimal(18,2) NOT NULL,
  `delivery_charges` decimal(18,2) NOT NULL,
  `is_online` tinyint(1) NOT NULL,
  `bank_details_id` int(5),
  `tax_details_id` int(5),
  `location_id` int(5) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE contacts
ADD `restaurant_id` int(5);

INSERT into migrations VALUES (1);

#=================================================Migration 1 ends=======================

CREATE TABLE `close_dates_restaurant_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `close_date_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT into migrations VALUES (2);
#=================================================Migration 2 ends=======================

ALTER TABLE bank_details
ADD (`restaurant_id` int(5), `is_valid` TINYINT(1) DEFAULT 1);

ALTER TABLE tax_details
ADD (`restaurant_id` int(5), `is_valid` TINYINT(1) DEFAULT 1);

ALTER TABLE restaurants
DROP `bank_details_id`,DROP `tax_details_id`;

INSERT into migrations VALUES (3);

#=================================================Migration 3 ends=======================

CREATE TABLE `social_auth_tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token_type` varchar(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `bio` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT into migrations VALUES (4);

#=================================================Migration 4 ends=======================

ALTER TABLE social_auth_tokens
ADD `user_id` int(5);

INSERT into migrations VALUES (5);

#=================================================Migration 5 ends=======================

CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100),
  `contact_no` varchar(20),
  `email_id` varchar(50) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`email_id`)
) ENGINE=InnoDB;
INSERT into migrations VALUES (6);

#=================================================Migration 6 ends=======================

CREATE TABLE `passwords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` INT(11),
  `parent_type` varchar(20),
  `password` varchar(100),
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT into migrations VALUES (7);

#=================================================Migration 7 ends=======================

ALTER TABLE customers
ADD `remember_token` varchar(255);

INSERT into migrations VALUES (8);

#=================================================Migration 8 ends=======================

ALTER TABLE customers
ADD `verification_token` varchar(255) DEFAULT NULL;

ALTER TABLE customers
ADD `is_verified` tinyint(1) DEFAULT 0;

INSERT into migrations VALUES (9);

#=================================================Migration 9 ends=======================

CREATE TABLE `menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` INT(11),
  `menu_json` TEXT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT into migrations VALUES (10);

#=================================================Migration 10 ends=======================

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` INT(11),
  `restaurant_id` INT(11),
  `station_id` INT(11),
  `order_date` DATETIME,
  `delivery_date` DATETIME,
  `internal_id` VARCHAR(20),
  `status` VARCHAR(20),
  `comments` VARCHAR(255),
  `customer_instruction` VARCHAR(255),
  `mode_of_payment` VARCHAR(10),
  `amount_billed` decimal(18,2),
  `amount_received` DECIMAL(18,2),
  `pnr` VARCHAR(20),
  `train_no` VARCHAR(10),
  `seat_no` VARCHAR(20),
  `offer_id` INT(11) DEFAULT NULL,
  `discount_amount` DECIMAL(18,2),
  `discount_reason` VARCHAR(50),
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


CREATE TABLE `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11), NOT NULL,
  `name` VARCHAR(50),
  `description` VARCHAR(255),
  `quantity` INT(11),
  `per_item_cost` DECIMAL(18,2),
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


INSERT into migrations VALUES (11);

#=================================================Migration 11 ends=======================

CREATE TABLE `offers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_code` VARCHAR(11) NOT NULL,
  `name` VARCHAR(50),
  `description` VARCHAR(255),
  `used_offer_count` int(11),
  `available_offer_count` int(11),
  `offer_type` VARCHAR(50),
  `offer_value` DECIMAL(18,2),
  `max_offer_limit` DECIMAL(18,2),
  `start_date` datetime DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


INSERT into migrations VALUES (12);

#=================================================Migration 12 ends=======================

ALTER TABLE restaurants
ADD `friendly_url` VARCHAR(255) DEFAULT NULL;

INSERT into migrations VALUES (13);

#=================================================Migration 13 ends=======================

alter table restaurants MODIFY open_time time NOT NULL Default 0;
alter table restaurants MODIFY close_time time NOT NULL Default 0;

INSERT into migrations VALUES (14);

#=================================================Migration 14 ends=======================

ALTER TABLE customers
ADD `pass_reset_token` varchar(255) DEFAULT NULL;

INSERT into migrations VALUES (15);

#=================================================Migration 15 ends=======================


create table categories (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `preference_order` int(11) NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

create table items (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) NULL,
  `price` decimal(18,2) NOT NULL,
  `category_id` int(11) NOT NULL,
  `preference_order` int(11) NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE migrations
ADD PRIMARY KEY (id);

INSERT into migrations VALUES (16);

#=================================================Migration 16 ends=======================

ALTER TABLE order_items
ADD `menu_item_id` int(11);

INSERT into migrations VALUES (17);

#=================================================Migration 17 ends=======================
