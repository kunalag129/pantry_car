CREATE TABLE `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `pincode` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `stations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station_full_name` varchar(100) NOT NULL,
  `state` varchar(20) NOT NULL,
  `station_short_name` varchar(50) NOT NULL,
  `station_code` int(11) NOT NULL,
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `designation_id` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `bank_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_account_name` varchar(50) NOT NULL,
  `bank_account_email` varchar(50) NOT NULL,
  `bank_account_number` varchar(50) NOT NULL,
  `bank_name` varchar(50) NOT NULL,
  `bank_location_id` int(5) NOT NULL,
  `ifsc_code` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `tax_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_tax_number` varchar(50),
  `vat_no` varchar(50),
  `food_license` varchar(50),
  `FSSAI` varchar(50),
  `mou_acceptance` varchar(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `restaurants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `internal_id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `station_id` int(5) NOT NULL,
  `distance` decimal(18,2) NOT NULL,
  `contact_no` varchar(50) NOT NULL,
  `open_time` varchar(10) NOT NULL,
  `close_time` varchar(10) NOT NULL,
  `sla_details` int(5) NOT NULL,
  `minimum_order_value` decimal(18,2) NOT NULL,
  `delivery_charges` decimal(18,2) NOT NULL,
  `is_online` tinyint(1) NOT NULL,
  `bank_details_id` int(5),
  `tax_details_id` int(5),
  `location_id` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE contacts
ADD `restaurant_id` int(5);