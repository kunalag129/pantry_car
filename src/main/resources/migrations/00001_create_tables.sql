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
  `station_full_name` varchar(100) NOT NULL,
  `state` varchar(20) NOT NULL,
  `station_short_name` varchar(50) NOT NULL,
  `station_code` int(11) NOT NULL,
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