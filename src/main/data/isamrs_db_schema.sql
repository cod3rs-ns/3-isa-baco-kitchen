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
  `u_type` VARCHAR(20) NOT NULL,
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
  `r_name` VARCHAR(45) NULL,
  `r_info` VARCHAR(100) NULL,
  `r_type` VARCHAR(45) NULL,
  `r_time_start` INT NULL,
  `r_time_end` INT NULL,
  `r_sm_id` INT NOT NULL,
  PRIMARY KEY (`r_id`),
  INDEX `fk_restaurants_sys_managers1_idx` (`r_sm_id` ASC),
  CONSTRAINT `fk_restaurants_sys_managers1`
    FOREIGN KEY (`r_sm_id`)
    REFERENCES `isa_mrs_project`.`sys_managers` (`sm_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
  CONSTRAINT `c_fid`
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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
