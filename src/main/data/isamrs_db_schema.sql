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
  `u_id` INT NOT NULL AUTO_INCREMENT,
  `u_fname` VARCHAR(45) NOT NULL,
  `u_lname` VARCHAR(45) NOT NULL,
  `u_email` VARCHAR(45) NOT NULL,
  `u_image` VARCHAR(100) NOT NULL,
  `u_password` VARCHAR(45) NOT NULL,
  `u_type` VARCHAR(20) NOT NULL,
  `u_verified` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`u_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`guests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`guests` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`guests` (
  `g_id` INT NOT NULL,
  `g_info` VARCHAR(100) NULL,
  PRIMARY KEY (`g_id`),
  CONSTRAINT `g_fid`
    FOREIGN KEY (`g_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`employees`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`employees` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`employees` (
  `e_id` INT NOT NULL,
  `e_bday` DATETIME NOT NULL,
  `e_dress_size` VARCHAR(45) NOT NULL,
  `e_shoes_size` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`e_id`),
  CONSTRAINT `e_fid`
    FOREIGN KEY (`e_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`sys_managers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`sys_managers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`sys_managers` (
  `sm_id` INT NOT NULL,
  `sm_info` VARCHAR(100) NULL,
  PRIMARY KEY (`sm_id`),
  CONSTRAINT `sm_fid`
    FOREIGN KEY (`sm_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurants` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurants` (
  `r_id` INT NOT NULL AUTO_INCREMENT,
  `r_name` VARCHAR(45) NOT NULL,
  `r_info` VARCHAR(100) NOT NULL,
  `r_type` VARCHAR(45) NOT NULL,
  `r_time_start` INT NOT NULL,
  `r_time_end` INT NOT NULL,
  `r_sm_id` INT NULL,
  PRIMARY KEY (`r_id`),
  INDEX `r_sm_fid_idx` (`r_sm_id` ASC),
  CONSTRAINT `r_sm_fid`
    FOREIGN KEY (`r_sm_id`)
    REFERENCES `isa_mrs_project`.`sys_managers` (`sm_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_managers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_managers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_managers` (
  `rm_id` INT NOT NULL,
  `rm_info` VARCHAR(100) NULL,
  `rm_restaurant_id` INT NOT NULL,
  PRIMARY KEY (`rm_id`),
  INDEX `fk_restaurant_managers_restaurants1_idx` (`rm_restaurant_id` ASC),
  CONSTRAINT `rm_fid`
    FOREIGN KEY (`rm_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_restaurant_managers_restaurants1`
    FOREIGN KEY (`rm_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_providers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_providers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_providers` (
  `rp_id` INT NOT NULL,
  `rp_info` VARCHAR(100) NULL,
  PRIMARY KEY (`rp_id`),
  CONSTRAINT `rp_fid`
    FOREIGN KEY (`rp_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`frienships`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`frienships` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`frienships` (
  `fs_id` INT NOT NULL AUTO_INCREMENT,
  `fs_first` INT NOT NULL,
  `fs_second` INT NOT NULL,
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
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`reservations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`reservations` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`reservations` (
  `rs_id` INT NOT NULL AUTO_INCREMENT,
  `rs_duration` DATETIME NOT NULL,
  `rs_length` INT NOT NULL,
  PRIMARY KEY (`rs_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`guests_on_reservations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`guests_on_reservations` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`guests_on_reservations` (
  `gos_id` INT NOT NULL AUTO_INCREMENT,
  `gos_guest_id` INT NOT NULL,
  `gos_reservation_id` INT NOT NULL,
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
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`bartenders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`bartenders` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`bartenders` (
  `b_id` INT NOT NULL,
  PRIMARY KEY (`b_id`),
  CONSTRAINT `b_fid`
    FOREIGN KEY (`b_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`cooks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`cooks` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`cooks` (
  `c_id` INT NOT NULL,
  PRIMARY KEY (`c_id`),
  CONSTRAINT `c_id`
    FOREIGN KEY (`c_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`waiters`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`waiters` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`waiters` (
  `w_id` INT NOT NULL,
  PRIMARY KEY (`w_id`),
  CONSTRAINT `w_fid`
    FOREIGN KEY (`w_id`)
    REFERENCES `isa_mrs_project`.`employees` (`e_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`reviews` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`reviews` (
  `rv_id` INT NOT NULL,
  `rv_info` VARCHAR(100) NULL,
  `rv_food_rate` INT NOT NULL,
  `rv_service_rate` INT NOT NULL,
  `rv_restaurant_rate` INT NOT NULL,
  `rv_guest_id` INT NOT NULL,
  `rv_reservation_id` INT NOT NULL,
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
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`food`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`food` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`food` (
  `f_id` INT NOT NULL,
  `f_info` VARCHAR(100) NULL,
  `f_name` VARCHAR(45) NOT NULL,
  `f_price` DOUBLE NOT NULL,
  `f_type` VARCHAR(45) NOT NULL,
  `f_image` VARCHAR(100) NOT NULL,
  `f_restaurant_id` INT NOT NULL,
  PRIMARY KEY (`f_id`),
  INDEX `md_restaurant_fid_idx` (`f_restaurant_id` ASC),
  CONSTRAINT `f_restaurant_fid`
    FOREIGN KEY (`f_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`restaurant_tables`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_tables` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_tables` (
  `rst_id` INT NOT NULL,
  PRIMARY KEY (`rst_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`reservation_tables`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`reservation_tables` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`reservation_tables` (
  `rt_id` INT NOT NULL,
  `rt_reservation_id` INT NOT NULL,
  `rt_table_id` INT NOT NULL,
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
    REFERENCES `isa_mrs_project`.`restaurant_tables` (`rst_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`verification_tokens`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`verification_tokens` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`verification_tokens` (
  `vt_id` INT NOT NULL AUTO_INCREMENT,
  `vt_token` CHAR(40) NOT NULL,
  `vt_expiry_date` DATETIME NOT NULL,
  `vt_user_id` INT NOT NULL,
  PRIMARY KEY (`vt_id`),
  INDEX `fk_verification_tokens_users1_idx` (`vt_user_id` ASC),
  CONSTRAINT `fk_verification_tokens_users1`
    FOREIGN KEY (`vt_user_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa_mrs_project`.`drinks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`drinks` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`drinks` (
  `d_id` INT NOT NULL,
  `d_info` VARCHAR(100) NULL,
  `d_name` VARCHAR(45) NOT NULL,
  `d_price` DOUBLE NOT NULL,
  `d_type` VARCHAR(45) NOT NULL,
  `d_image` VARCHAR(100) NOT NULL,
  `d_restaurant_id` INT NOT NULL,
  PRIMARY KEY (`d_id`),
  INDEX `d_restaurant_fid_idx` (`d_restaurant_id` ASC),
  CONSTRAINT `d_restaurant_fid`
    FOREIGN KEY (`d_restaurant_id`)
    REFERENCES `isa_mrs_project`.`restaurants` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
