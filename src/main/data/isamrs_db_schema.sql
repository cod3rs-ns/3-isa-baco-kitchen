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
  `u_image` VARCHAR(100) NULL,
  `u_password` VARCHAR(45) NOT NULL,
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
-- Table `isa_mrs_project`.`restaurant_managers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurant_managers` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurant_managers` (
  `rm_id` INT NOT NULL,
  `rm_info` VARCHAR(100) NULL,
  PRIMARY KEY (`rm_id`),
  CONSTRAINT `rm_fid`
    FOREIGN KEY (`rm_id`)
    REFERENCES `isa_mrs_project`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
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
-- Table `isa_mrs_project`.`restaurants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa_mrs_project`.`restaurants` ;

CREATE TABLE IF NOT EXISTS `isa_mrs_project`.`restaurants` (
  `r_id` INT NOT NULL,
  `r_name` VARCHAR(45) NULL,
  `r_info` VARCHAR(100) NULL,
  `r_type` VARCHAR(45) NULL,
  `r_time_start` INT NULL,
  `r_time_end` INT NULL,
  `sysm_id` INT NOT NULL,
  PRIMARY KEY (`r_id`),
  INDEX `fk_restaurants_sys_managers1_idx` (`sysm_id` ASC),
  CONSTRAINT `fk_restaurants_sys_managers1`
    FOREIGN KEY (`sysm_id`)
    REFERENCES `isa_mrs_project`.`sys_managers` (`sm_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
