INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (1,'Sergio','Ramos','sr4@real.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','686726ec8996fd0eeb8faadfec351a1ab51eddb31633df031f8fa16489bc2833','guest','verified',1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (2,'Joshua','Kimmich','jk@bayern.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','939cc5c58b280d3cb879e60b73cf2fc775ea4e8365cb5f22d7c94755037d1638','guest','verified',1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (3,'Iker','Casillas','ic@porto.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','a6d0ae92be21e0b9685f16b44ff8e1980258942b590bb90031775d44bd9dc18a','guest','verified',1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (4,'Alarcon','Isco','ai@real.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','6c501b445e2ff910e0d4d28f2d484ea07f4e0b0ad9f2d807c5891ff7fda088ef','guest','verified',1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (5,'Alvaro','Morata','am@juventus.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','ff121f80a676a89eff93c44232998bab9a1fbcf7549b32a1c47817c8595f3fa9','guest','verified',1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (6,'James','Rodriguez','jr@real.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','ede1fd299ea082b4125944e52db4aaf4497c49316f6ee35f46a3ccbb5eb3fb23','guest','verified',1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`,`u_fname`,`u_lname`,`u_email`,`u_image`,`u_password`,`u_type`,`u_verified`,`u_first_login`) VALUES (7,'Xabi','Alonso','xa@bayern.com','https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png','01201c06b31254ecd60bf1509b1ef5670ebbc9904bb6a5880b07264a0550cbf9','guest','verified',1);

-- System manager
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('8', 'Zinedine', 'Zidane', 'zizu@manager.com', 'images/system-manager.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'system_manager', 'verified', 1);
INSERT INTO `isa_mrs_project`.`sys_managers` (`sm_id`, `sm_info`) VALUES ('8', 'Zidane biography.');

-- Cooks
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('9', 'Kyrie', 'Irving', 'ki@bacovakuhinja.rs', 'http://www.ohio.com/polopoly_fs/1.648704.1450566065!/image/image.jpg_gen/derivatives/landscape_500/cavs20cutweb.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'cook', 'verified', 1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('10', 'Hugo', 'Vieira', 'hugo@bacovakuhinja.rs', 'images/cook.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'cook', 'verified', 1);

-- Waiters
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('11', 'Klay', 'Thompson', 'kt@bacovakuhinja.rs', 'http://articlebio.com/uploads/bio/klay-thompson.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'waiter', 'verified', 1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('12', 'Mitchell', 'Donald', 'md@bacovakuhinja.rs', 'images/waiter.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'waiter', 'verified', 1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('13', 'LeBron', 'James', 'lj@bacovakuhinja.rs', 'https://s3.graphiq.com/sites/default/files/5882/media/images/t2/LeBron_James_4207513.png', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'system_manager', 'verified', 1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('14', 'Russell', 'Westbrook', 'rw@bacovakuhinja.rs', 'http://vlsportysexycool.com/wp-content/uploads/2015/12/russell-westbrook-foot-locker-SSC.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'waiter', 'verified', 1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('15', 'Kevin', 'Durant', 'kd@bacovakuhinja.rs', 'http://sportsdoinggood.com/wp-content/uploads/2013/01/Kevin-Durant.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'waiter', 'verified', 1);

-- Bartenders
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('16', 'Damien', 'le Tallec', 'tallec@bacovakuhinja.rs', 'images/bartender.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'bartender', 'verified', 1);
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('17', 'Andrew', 'Wiggins', 'aw@bacovakuhinja.rs', 'https://www.fanduel.com/insider/wp-content/uploads/92f8eec6c0965ec1c02bb41ef346f73b.jpg', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'bartender', 'verified', 1);

-- Restaurants
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`, `r_address`, `r_image`) VALUES ('1', 'Macchiato', 'Macchiato Liman vama na usluzi. Najlepši pogled na Limanski park u gradu.', 'Kafe restoran', '9', '17', '8', 'Narodnog Fronta 21', 'https://s-media-cache-ak0.pinimg.com/236x/cc/8f/0e/cc8f0e397f2ea91869b9e509bac4ed98.jpg');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`, `r_address`, `r_image`) VALUES ('2', 'Orfei', 'Restoran Orfei vama na usluzi.', 'Kafe restoran', '9', '22', '8', 'Zmaj Jovina 15', 'https://s-media-cache-ak0.pinimg.com/736x/c6/bd/c0/c6bdc0d3b4621702658704739dd17831.jpg');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`, `r_address`, `r_image`) VALUES ('3', 'Mašinac', 'Studenti na prvom mestu.', 'Studentski', '6', '18', '8', 'Trg Dositeja Obradovića 1', 'https://s-media-cache-ak0.pinimg.com/736x/55/8a/a9/558aa99c2a3d1e1b11ad44533ec17c92.jpg');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`, `r_address`, `r_image`) VALUES ('4', 'Paprika', 'Najbolja pljeskavica u vašem gradu.', 'Gril', '7', '24', '8', 'Bulevar oslobođenja 30', 'http://www.underworldmagazines.com/wp-content/uploads/2012/08/BRLEN21.jpg');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`, `r_address`, `r_image`) VALUES ('5', 'Vitorio', 'Elitni restoran u centru Novog Sada.', 'Kafe restoran', '7', '21', '8', 'Lala Gala 28', 'https://s-media-cache-ak0.pinimg.com/236x/8c/67/26/8c67265f315a22a680a4226c0ddd0873.jpg');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`, `r_address`, `r_image`) VALUES ('6', 'Berry', 'Dođi u Berry, ne beri brigu.', 'Restoran italijanske kuhinje', '6', '18', '8', 'Ćirila i Metodija 5', 'https://s-media-cache-ak0.pinimg.com/736x/9f/2b/cd/9f2bcd0601a2f768775a3b8ec03a9dd7.jpg');

-- Employees
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('9', '1988-06-15', 'L', '45', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('10', '1989-07-15', 'XXL', '46', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('11', '1990-08-15', 'M', '42', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('12', '1991-02-15', 'M', '41', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('13', '1982-08-12', 'M', '42', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('14', '1981-06-15', 'L', '43', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('15', '1980-07-15', 'XXL', '48', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('16', '1978-04-15', 'M', '41', '5');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`, `e_restaurant`) VALUES ('17', '1992-08-16', 'M', '42', '5');

INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('11');
INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('12');
INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('13');
INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('14');
INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('15');

INSERT INTO `isa_mrs_project`.`cooks` (`c_id`) VALUES ('9');
INSERT INTO `isa_mrs_project`.`cooks` (`c_id`) VALUES ('10');

INSERT INTO `isa_mrs_project`.`bartenders` (`b_id`) VALUES ('16');
INSERT INTO `isa_mrs_project`.`bartenders` (`b_id`) VALUES ('17');

-- Table Guests
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (1,NULL);
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (2,NULL);
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (3,NULL);
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (4,NULL);
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (5,NULL);
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (6,NULL);
INSERT INTO `isa_mrs_project`.`guests` (`g_id`,`g_info`) VALUES (7,NULL);

-- Table Friendships
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (1,4,1,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (2,5,1,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (3,6,1,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (4,1,3,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (5,4,5,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (6,4,6,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (7,3,5,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (8,3,7,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (9,5,7,'accepted');
INSERT INTO `isa_mrs_project`.`frienships` (`fs_id`,`fs_first`,`fs_second`,`fs_status`) VALUES (10,2,7,'accepted');




