-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema isa_mrs_project
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `isa_mrs_project` ;

-- -----------------------------------------------------
-- Schema isa_mrs_project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `isa_mrs_project` DEFAULT CHARACTER SET utf8 ;
USE `isa_mrs_project` ;

-- -----------------------------------------------------
-- Table `isa_mrs_project`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`users` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`users` (
  `u_id` INT(11) NOT NULL AUTO_INCREMENT,
  `u_fname` VARCHAR(45) NOT NULL,
  `u_lname` VARCHAR(45) NOT NULL,
  `u_email` VARCHAR(45) NOT NULL,
  `u_image` VARCHAR(200) NOT NULL,
  `u_password` VARCHAR(45) NOT NULL,
  `u_type` VARCHAR(20) NOT NULL,
  `u_verified` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`u_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`sys_managers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`sys_managers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`sys_managers` (
  `sm_id` INT(11) NOT NULL,
  `sm_info` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`sm_id`),
  CONSTRAINT `sm_fid`
    FOREIGN KEY (`sm_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurants` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurants` (
  `r_id` INT(11) NOT NULL AUTO_INCREMENT,
  `r_name` VARCHAR(45) NOT NULL,
  `r_info` VARCHAR(100) NOT NULL,
  `r_type` VARCHAR(45) NOT NULL,
  `r_time_start` INT(11) NOT NULL,
  `r_time_end` INT(11) NOT NULL,
  `r_sm_id` INT(11) NULL,
  `r_address` VARCHAR(100) NOT NULL,
  `r_image` VARCHAR(100) NULL,
  PRIMARY KEY (`r_id`),
  INDEX `r_sm_fid_idx` (`r_sm_id` ASC),
  CONSTRAINT `r_sm_fid`
    FOREIGN KEY (`r_sm_id`)
    REFERENCES `isa_mrs_project`.`sys_managers` (`sm_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`employees`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`employees` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`employees` (
  `e_id` INT(11) NOT NULL,
  `e_bday` DATETIME NOT NULL,
  `e_dress_size` VARCHAR(45) NOT NULL,
  `e_shoes_size` VARCHAR(45) NOT NULL,
  `e_restaurant` INT(11) NOT NULL,
  PRIMARY KEY (`e_id`),
  INDEX `e_restaurant_fid_idx` (`e_restaurant` ASC),
  CONSTRAINT `e_fid`
    FOREIGN KEY (`e_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `e_restaurant_fid`
    FOREIGN KEY (`e_restaurant`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`bartenders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`bartenders` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`bartenders` (
  `b_id` INT(11) NOT NULL,
  PRIMARY KEY (`b_id`),
  CONSTRAINT `b_fid`
    FOREIGN KEY (`b_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`waiters`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`waiters` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`waiters` (
  `w_id` INT(11) NOT NULL,
  PRIMARY KEY (`w_id`),
  CONSTRAINT `w_fid`
    FOREIGN KEY (`w_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`bills`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`bills` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`bills` (
  `bl_id` INT(11) NOT NULL AUTO_INCREMENT,
  `bl_publish_date` DATETIME NOT NULL,
  `bl_total_amount` DOUBLE NOT NULL,
  `bl_waiter_id` INT(11) NOT NULL,
  PRIMARY KEY (`bl_id`),
  INDEX `fk_bills_waiters1_idx` (`bl_waiter_id` ASC),
  CONSTRAINT `bl_waiter_fid`
    FOREIGN KEY (`bl_waiter_id`)
    REFERENCES `isa_mrs_project`.`waiters` (`w_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`reservations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`reservations` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`reservations` (
  `rs_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rs_restaurant_id` INT(11) NOT NULL,
  `rs_duration` DATETIME NOT NULL,
  `rs_length` INT(11) NOT NULL,
  PRIMARY KEY (`rs_id`),
  INDEX `rs_restaurant_fid_idx` (`rs_restaurant_id` ASC),
  CONSTRAINT `rs_restaurant_fid`
  FOREIGN KEY (`rs_restaurant_id`)
  REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_regions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_regions` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_regions` (
  `rr_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rr_name` VARCHAR(45) NOT NULL,
  `rr_color` VARCHAR(45) NULL DEFAULT NULL,
  `rr_restaurant_id` INT(11) NOT NULL,
  `rr_region_no` INT(11) NOT NULL,
  PRIMARY KEY (`rr_id`),
  INDEX `tr_restaurant_fid_idx` (`rr_restaurant_id` ASC),
  CONSTRAINT `rr_restaurant_fid`
    FOREIGN KEY (`rr_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_tables`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_tables` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_tables` (
  `rt_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rt_datax` DOUBLE NOT NULL,
  `rt_datay` DOUBLE NOT NULL,
  `rt_width` DOUBLE NOT NULL,
  `rt_height` DOUBLE NOT NULL,
  `rt_positions` INT(11) NOT NULL,
  `rt_region_id` INT(11) NOT NULL,
  `rt_table_in_restaurant_no` INT(11) NOT NULL,
  PRIMARY KEY (`rt_id`),
  INDEX `rt_region_fid_idx` (`rt_region_id` ASC),
  CONSTRAINT `rt_region_fid`
    FOREIGN KEY (`rt_region_id`)
    REFERENCES `isa_mrs_project`.`restaurant_regions` (`rr_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`client_orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`client_orders` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`client_orders` (
  `co_id` INT(11) NOT NULL AUTO_INCREMENT,
  `co_date` DATETIME NOT NULL,
  `co_deadline` DATETIME NULL DEFAULT NULL,
  `co_reservation_id` INT(11) NULL DEFAULT NULL,
  `co_table_id` INT(11) NOT NULL,
  `co_bill_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`co_id`),
  INDEX `fk_client_orders_reservations1_idx` (`co_reservation_id` ASC),
  INDEX `fk_client_orders_restaurant_tables1_idx` (`co_table_id` ASC),
  INDEX `fk_client_orders_bills1_idx` (`co_bill_id` ASC),
  CONSTRAINT `co_bill_fid`
    FOREIGN KEY (`co_bill_id`)
    REFERENCES `isa_mrs_project`.`bills` (`bl_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `co_reservation_fid`
    FOREIGN KEY (`co_reservation_id`)
    REFERENCES `isa_mrs_project`.`reservations` (`rs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `co_table_fid`
    FOREIGN KEY (`co_table_id`)
    REFERENCES `isa_mrs_project`.`restaurant_tables` (`rt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`cooks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`cooks` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`cooks` (
  `c_id` INT(11) NOT NULL,
  PRIMARY KEY (`c_id`),
  CONSTRAINT `c_id`
    FOREIGN KEY (`c_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`daily_schedules`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`daily_schedules` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`daily_schedules` (
  `ds_id` INT(11) NOT NULL AUTO_INCREMENT,
  `ds_day` DATETIME NOT NULL,
  `ds_employee_id` INT(11) NOT NULL,
  `ds_region_id` INT(11) NULL,
  `ds_restaurant_id` INT(11) NOT NULL,
  `ds_start_h` INT(11) NOT NULL,
  `ds_start_m` INT(11) NOT NULL,
  `ds_end_h` INT(11) NOT NULL,
  `ds_end_m` INT(11) NOT NULL,
  `ds_merged_start` DATETIME NULL,
  `ds_merged_end` DATETIME NULL,
  PRIMARY KEY (`ds_id`),
  INDEX `ds_table_region_fid_idx` (`ds_region_id` ASC),
  INDEX `ds_employee_fid_idx` (`ds_employee_id` ASC),
  INDEX `ds_restaurant_fid_idx` (`ds_restaurant_id` ASC),
  CONSTRAINT `ds_employee_fid`
    FOREIGN KEY (`ds_employee_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `ds_region_fid`
    FOREIGN KEY (`ds_region_id`)
    REFERENCES `isa_mrs_project`.`restaurant_regions` (`rr_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `ds_restaurant_fid`
    FOREIGN KEY (`ds_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`guests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`guests` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`guests` (
  `g_id` INT(11) NOT NULL,
  `g_info` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`g_id`),
  CONSTRAINT `g_fid`
    FOREIGN KEY (`g_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`frienships`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`frienships` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`frienships` (
  `fs_id` INT(11) NOT NULL AUTO_INCREMENT,
  `fs_first` INT(11) NOT NULL,
  `fs_second` INT(11) NOT NULL,
  `fs_status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`fs_id`),
  INDEX `fs_firstid_idx` (`fs_first` ASC),
  INDEX `fs_secondid_idx` (`fs_second` ASC),
  CONSTRAINT `fs_firstid`
    FOREIGN KEY (`fs_first`)
    REFERENCES `isa_mrs_project`.`guests` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fs_secondid`
    FOREIGN KEY (`fs_second`)
    REFERENCES `isa_mrs_project`.`guests` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`guests_on_reservations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`guests_on_reservations` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`guests_on_reservations` (
  `gos_id` INT(11) NOT NULL AUTO_INCREMENT,
  `gos_guest_id` INT(11) NOT NULL,
  `gos_reservation_id` INT(11) NOT NULL,
  `gos_status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`gos_id`),
  INDEX `gos_guest_fid_idx` (`gos_guest_id` ASC),
  INDEX `gos_reservation_fid_idx` (`gos_reservation_id` ASC),
  CONSTRAINT `gos_guest_fid`
    FOREIGN KEY (`gos_guest_id`)
    REFERENCES `isa_mrs_project`.`guests` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `gos_reservation_fid`
    FOREIGN KEY (`gos_reservation_id`)
    REFERENCES `isa_mrs_project`.`reservations` (`rs_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`menu_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`menu_items` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`menu_items` (
  `mi_id` INT(11) NOT NULL AUTO_INCREMENT,
  `mi_info` VARCHAR(100) NULL DEFAULT NULL,
  `mi_name` VARCHAR(45) NOT NULL,
  `mi_price` DOUBLE NOT NULL,
  `mi_type` VARCHAR(10) NOT NULL,
  `mi_image` VARCHAR(100) NOT NULL,
  `mi_restaurant_id` INT(11) NOT NULL,
  `mi_spec_type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`mi_id`),
  INDEX `d_restaurant_fid_idx` (`mi_restaurant_id` ASC),
  CONSTRAINT `d_restaurant_fid`
    FOREIGN KEY (`mi_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`order_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`order_items` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`order_items` (
  `oi_id` INT(11) NOT NULL AUTO_INCREMENT,
  `oi_state` VARCHAR(45) NOT NULL,
  `oi_order_id` INT(11) NOT NULL,
  `oi_menu_item_id` INT(11) NOT NULL,
  `oi_amount` INT(11) NOT NULL,
  `oi_restaurant_id` INT(11) NOT NULL,
  `oi_version` INT(11) NOT NULL,
  `oi_employee` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`oi_id`),
  INDEX `fk_order_items_client_orders1_idx` (`oi_order_id` ASC),
  INDEX `oi_menu_item_fid_idx` (`oi_menu_item_id` ASC),
  INDEX `oi_employee_fid_idx` (`oi_employee` ASC),
  CONSTRAINT `oi_employee_fid`
    FOREIGN KEY (`oi_employee`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oi_menu_item_fid`
    FOREIGN KEY (`oi_menu_item_id`)
    REFERENCES `isa_mrs_project`.`menu_items` (`mi_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oi_order_fid`
    FOREIGN KEY (`oi_order_id`)
    REFERENCES `isa_mrs_project`.`client_orders` (`co_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`reservation_tables`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`reservation_tables` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`reservation_tables` (
  `rt_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rt_reservation_id` INT(11) NOT NULL,
  `rt_table_id` INT(11) NOT NULL,
  PRIMARY KEY (`rt_id`),
  INDEX `rt_reservation_fid_idx` (`rt_reservation_id` ASC),
  INDEX `rt_table_fid_idx` (`rt_table_id` ASC),
  CONSTRAINT `rt_reservation_fid`
    FOREIGN KEY (`rt_reservation_id`)
    REFERENCES `isa_mrs_project`.`reservations` (`rs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rt_table_fid`
    FOREIGN KEY (`rt_table_id`)
    REFERENCES `isa_mrs_project`.`restaurant_tables` (`rt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_managers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_managers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_managers` (
  `rm_id` INT(11) NOT NULL,
  `rm_info` VARCHAR(100) NULL DEFAULT NULL,
  `rm_restaurant_id` INT(11) NOT NULL,
  PRIMARY KEY (`rm_id`),
  INDEX `fk_restaurant_managers_restaurants1_idx` (`rm_restaurant_id` ASC),
  CONSTRAINT `rm_fid`
    FOREIGN KEY (`rm_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `rm_restaurants_rid`
    FOREIGN KEY (`rm_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_providers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_providers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_providers` (
  `rp_id` INT(11) NOT NULL,
  `rp_info` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`rp_id`),
  CONSTRAINT `rp_fid`
    FOREIGN KEY (`rp_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`reviews` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`reviews` (
  `rv_id` INT(11) NOT NULL AUTO_INCREMENT,
  `rv_info` VARCHAR(100) NULL DEFAULT NULL,
  `rv_food_rate` INT(11) NOT NULL,
  `rv_service_rate` INT(11) NOT NULL,
  `rv_restaurant_rate` INT(11) NOT NULL,
  `rv_guest_id` INT(11) NOT NULL,
  `rv_reservation_id` INT(11) NOT NULL,
  PRIMARY KEY (`rv_id`),
  INDEX `rv_guest_fid_idx` (`rv_guest_id` ASC),
  INDEX `rv_reservation_fid_idx` (`rv_reservation_id` ASC),
  CONSTRAINT `rv_guest_fid`
    FOREIGN KEY (`rv_guest_id`)
    REFERENCES `isa_mrs_project`.`guests` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rv_reservation_fid`
    FOREIGN KEY (`rv_reservation_id`)
    REFERENCES `isa_mrs_project`.`reservations` (`rs_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`shift_templates`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`shift_templates` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`shift_templates` (
  `sh_id` INT(11) NOT NULL AUTO_INCREMENT,
  `sh_name` VARCHAR(45) NOT NULL,
  `sh_start_h` INT(11) NOT NULL,
  `sh_start_m` INT(11) NOT NULL,
  `sh_end_h` INT(11) NOT NULL,
  `sh_end_m` INT(11) NOT NULL,
  `sh_restaurant_id` INT(11) NOT NULL,
  PRIMARY KEY (`sh_id`),
  INDEX `sh_restaurant_fid_idx` (`sh_restaurant_id` ASC),
  CONSTRAINT `sh_restaurant_fid`
    FOREIGN KEY (`sh_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`verification_tokens`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`verification_tokens` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`verification_tokens` (
  `vt_id` INT(11) NOT NULL AUTO_INCREMENT,
  `vt_token` CHAR(40) NOT NULL,
  `vt_expiry_date` DATETIME NOT NULL,
  `vt_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`vt_id`),
  INDEX `fk_verification_tokens_users1_idx` (`vt_user_id` ASC),
  CONSTRAINT `fk_verification_tokens_users1`
    FOREIGN KEY (`vt_user_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`working_times`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`working_times` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`working_times` (
  `wt_id` INT(11) NOT NULL AUTO_INCREMENT,
  `wt_reg_start_h` INT(11) NOT NULL,
  `wt_reg_start_m` INT(11) NOT NULL,
  `wt_reg_end_h` INT(11) NOT NULL,
  `wt_reg_end_m` INT(11) NOT NULL,
  `wt_sat_start_h` INT(11) NULL,
  `wt_sat_start_m` INT(11) NULL,
  `wt_sat_end_h` INT(11) NULL,
  `wt_sat_end_m` INT(11) NULL,
  `wt_sun_start_h` INT(11) NULL,
  `wt_sun_start_m` INT(11) NULL,
  `wt_sun_end_h` INT(11) NULL,
  `wt_sun_end_m` INT(11) NULL,
  `wt_working_on_sat` TINYINT(1) NOT NULL,
  `wt_working_on_sun` TINYINT(1) NOT NULL,
  `wt_reg_reversed` TINYINT(1) NOT NULL,
  `wt_sat_reversed` TINYINT(1) NOT NULL,
  `wt_sun_reversed` TINYINT(1) NOT NULL,
  `wt_restaurant_id` INT(11) NOT NULL,
  PRIMARY KEY (`wt_id`),
  INDEX `wt_restaurant_fid_idx` (`wt_restaurant_id` ASC),
  CONSTRAINT `wt_restaurant_fid`
    FOREIGN KEY (`wt_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`provider_responses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`provider_responses` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`provider_responses` (
  `pr_id` INT(11) NOT NULL AUTO_INCREMENT,
  `pr_price` DOUBLE NOT NULL,
  `pr_info` VARCHAR(50) NULL,
  `pr_status` VARCHAR(20) NOT NULL,
  `pr_provider_id` INT(11) NOT NULL,
  `pr_offer_id` INT(11) NOT NULL,
  PRIMARY KEY (`pr_id`),
  INDEX `pr_provider_fid_idx` (`pr_provider_id` ASC),
  INDEX `pr_offer_fid_idx` (`pr_offer_id` ASC),
  CONSTRAINT `pr_provider_fid`
    FOREIGN KEY (`pr_provider_id`)
    REFERENCES `isa_mrs_project`.`restaurant_providers` (`rp_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pr_offer_fid`
    FOREIGN KEY (`pr_offer_id`)
    REFERENCES `isa_mrs_project`.`offer_requests` (`or_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`offer_requests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`offer_requests` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`offer_requests` (
  `or_id` INT(11) NOT NULL AUTO_INCREMENT,
  `or_offer` VARCHAR(500) NOT NULL,
  `or_deadline` DATETIME NOT NULL,
  `or_status` VARCHAR(20) NOT NULL,
  `or_restaurant_id` INT(11) NOT NULL,
  `or_accepted_offer_id` INT(11) NULL,
  PRIMARY KEY (`or_id`),
  INDEX `or_restaurant_fid_idx` (`or_restaurant_id` ASC),
  INDEX `or_accepted_offer_fid_idx` (`or_accepted_offer_id` ASC),
  CONSTRAINT `or_restaurant_fid`
    FOREIGN KEY (`or_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `or_accepted_offer_fid`
    FOREIGN KEY (`or_accepted_offer_id`)
    REFERENCES `isa_mrs_project`.`provider_responses` (`pr_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
