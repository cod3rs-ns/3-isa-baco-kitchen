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
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`, `u_first_login`) VALUES ('13', 'LeBron', 'James', 'lj@bacovakuhinja.rs', 'https://s3.graphiq.com/sites/default/files/5882/media/images/t2/LeBron_James_4207513.png', 'c33dc0f552e1374c11ae773ed073e11b4934332634e9136df618577480eae743', 'waiter', 'verified', 1);
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

-- MenuItems
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('1', 'Ukusni voćni dezert.', 'Kolač s borovnicom', '150', 'food', 'images/meals/borovnica.jpg', '5', 'dezert', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('2', 'Domaća štrudla', 'Štrudla s makom', '200', 'food', 'images/meals/mak.jpg', '2', 'desert', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('3', 'Domaće pivo', 'Lav pivo', '150', 'drink', 'http://online.idea.rs/images/products/460/460103432l.gif', '2', 'pivo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('4', 'Domaće pivo', 'Jelen pivo', '150', 'drink', 'http://www.neretvakomerc.com/wp-content/uploads/2011/06/1183-Jelen-pivo-0.5l.jpg', '5', 'pivo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('5', 'Domaće pivo', 'Valjevsko pivo', '120', 'drink', 'http://online.idea.rs/images/products/460/460103431l.gif', '2', 'pivo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('6', 'Strano gorko pivo', 'Tuborg', '180', 'drink', 'http://www.jamyachtsupply.com/images/big/429_1.jpg', '5', 'pivo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('7', 'Strano slatko pivo', 'Corona', '180', 'drink', 'http://vignette4.wikia.nocookie.net/beer/images/6/6b/Corona.jpg/revision/latest?cb=20130322234121', '2', 'pivo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('8', 'Kafa po dobro poznatom receptu', 'Turska kafa', '50', 'drink', 'http://www.italiagroup.net/open2b/var/departments/587.jpg?1faf2f50', '2', 'topli napitak', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('9', 'Kafa za dobro jutro', 'Esspreso', '50', 'drink', 'http://www.espressoplanet.rs/wp-content/uploads/2015/11/how-to-brew-perfect-espresso-300x300.png', '5', 'topli napitak', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('10', 'Da uvek budete zdravi', 'Čaj', '50', 'drink', 'http://trudnocainfo.com/wp-content/uploads/2013/01/caj-od-kamilice-za-bebe.jpg', '5', 'topli napitak', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('11', 'Voda sa domaćih izvora', 'Aqua Viva', '100', 'drink', 'http://www.doozoric.com/image/cache/data/AQUA%20VIVA%200,25L-500x500.jpg', '5', 'voda', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('12', 'Mineralna voda', 'Knjaz Miloš', '120', 'drink', 'http://www.neretvakomerc.com/wp-content/uploads/2011/06/1499-Knjaz-Milos-0.25l.jpg', '5', 'voda', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('13', 'Škotski viski', 'Chivas Regal', '300', 'drink', 'http://dubaidutyfree.s3.amazonaws.com/images/2938596_t1_1200x1200.jpg', '2', 'zestoka pića', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('14', 'Red label', 'Johnnie Walker', '300', 'drink', 'https://www.real-drive.de/medias/sys_master/images/productimages/9/932379_1_1_detail.jpg', '2', 'zestoka pića', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('15', 'Tenesi viski', 'Jack Daniels', '300', 'drink', 'http://cenoteka.rs/img/articles/viski-jack-daniels-0-7l-1004389-medium.jpg', '5', 'zestoka pića', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('16', 'Osvežite se uz ovaj miks', 'Cosmopolitan', '350', 'drink', 'http://www.3drivers.com/upload/iblock/064/cosmopolitan-c-1.jpg', '5', 'cocktail', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('17', 'Osvežite se uz ovaj miks', 'Blue Lagoon', '350', 'drink', 'https://energydrink.vodka/wp-content/uploads/2015/10/blue-lagoon.jpg', '2', 'cocktail', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('18', 'Gazirani napitak', 'Coca Cola', '150', 'drink', 'http://manjr.com/wp-content/uploads/2010/10/coca-cola-can.jpg', '2', 'sokovi', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('19', 'Gazirani napitak', 'Sprite', '150', 'drink', 'http://www.mezincashandcarry.com/wp-content/uploads/2015/05/sprite.jpg', '2', 'sokovi', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('20', 'Samo za iskusne', 'Schweppes Tonic', '150', 'drink', 'http://www.distribucija.rs/images/artikli/schweppes-tonic-water-0.33l.jpg', '5', 'sokovi', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('21', 'Voćni sok', 'Next šumsko voće', '150', 'drink', 'http://www.distribucija.rs/images/artikli/next-jac-0.2l.jpg', '2', 'sokovi', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('22', 'Ukusni sendvič sa pečenicom i prilozima', 'Sendvič sa pečenicom', '150', 'food', 'https://www.gianteagle.com/ProductImages/PRODUCT_NODE_161/99283.jpg', '2', 'sendvič', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('23', 'Najbolje iz Srbije', 'Karađorva šnicla', '500', 'food', 'http://www.blueskytraveler.com/wp-content/uploads/2015/09/Serbia.Kara%C4%91or%C4%91eva.s%CC%8Cnicla.jpeg', '5', 'glavno jelo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('24', 'Ljuta piletina za prijatan obrok', 'Sendvič sa piletinom', '350', 'food', 'http://www.texaschickenme.com/dataegypt/modules/Cms/mealitems/f/80/image/original/Spicy_Chicken_E.png', '2', 'glavno jelo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('25', 'Savršena kombinacija za prijatan obrok', 'Piletina sa pirinčem', '400', 'food', 'http://luvo.tetherinc.netdna-cdn.com/wp-content/uploads/2014/02/luvo-frozen-tandoori-Inspired-spiced-chicken-meal.jpg', '2', 'glavno jelo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('26', 'Uživajte u zanimljivom spoju piletine i povrća', 'Piletina sa graškom', '450', 'food', 'http://luvo.tetherinc.netdna-cdn.com/wp-content/uploads/2015/07/Frozen-BBQ-Chicken-Meal2-483x483.jpg', '5', 'glavno jelo', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('27', 'Zanimljiva kombinacija', 'Salata sa škampima', '600', 'food', 'http://eccobistro.com.au/wp-content/uploads/2014/05/recipes-overview-prawn-salad-with-ruby-grapefruit-thumb-large-350x350.jpg', '5', 'salata', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('28', 'Salata sa piletinom', 'Iseckana pileća salata', '350', 'food', 'http://cdn-img.health.com/sites/default/files/styles/420x420/public/styles/main/public/spicy-chopped-chicken-salad-XL.jpg', '2', 'salata', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('29', 'Pileća supa', 'Supa', '250', 'food', 'http://s4.thcdn.com/productimg/0/600/600/34/10798934-1415813157-143216.jpg', '2', 'supa', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('30', 'Ukusan kolač sa voćem', 'Čizkejk sa voćem', '180', 'food', 'http://www.traiteurdeparis.co.uk/wp-content/uploads/2014/09/0329701-Cheesecake-Fruits-rouges.jpg', '5', 'dezert', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('31', 'Slasni dezert', 'Palačinke', '130', 'food', 'http://gonutrition.com/media/catalog/product/cache/3/thumbnail/9df78eab33525d08d6e5fb8d27136e95/p/a/pancakes-small.png', '2', 'dezert', '0');
INSERT INTO `isa_mrs_project`.`menu_items` (`mi_id`, `mi_info`, `mi_name`, `mi_price`, `mi_type`, `mi_image`, `mi_restaurant_id`, `mi_spec_type`, `mi_deleted`) VALUES ('32', 'Slasni kolač iz Francuske', 'Francuski kolač', '195', 'food', 'http://images.media-allrecipes.com/userphotos/250x250/41763.jpg', '5', 'dezert', '0');

-- RestaurantRegions
INSERT INTO `isa_mrs_project`.`restaurant_regions` (`rr_id`, `rr_name`, `rr_color`, `rr_restaurant_id`, `rr_region_no`) VALUES ('1', 'bašta', '#283593', '2', '1');
INSERT INTO `isa_mrs_project`.`restaurant_regions` (`rr_id`, `rr_name`, `rr_color`, `rr_restaurant_id`, `rr_region_no`) VALUES ('2', 'šank', '#00695C', '2', '2');
INSERT INTO `isa_mrs_project`.`restaurant_regions` (`rr_id`, `rr_name`, `rr_color`, `rr_restaurant_id`, `rr_region_no`) VALUES ('3', 'bašta', '#283593', '5', '1');
INSERT INTO `isa_mrs_project`.`restaurant_regions` (`rr_id`, `rr_name`, `rr_color`, `rr_restaurant_id`, `rr_region_no`) VALUES ('4', 'šank', '#00695C', '5', '2');

-- RestaurantTables
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('1', '7', '4', '10', '10', '4', '1', '1');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('2', '10', '20', '5', '5', '4', '1', '2');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('3', '10', '30', '10', '10', '6', '1', '3');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('4', '20', '40', '5', '5', '2', '1', '4');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('5', '50', '20', '10', '5', '2', '2', '5');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('6', '70', '40', '5', '10', '2', '2', '6');

INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('7', '7', '4', '10', '10', '4', '3', '1');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('8', '10', '20', '5', '5', '4', '3', '2');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('9', '10', '30', '10', '10', '6', '3', '3');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('10', '20', '40', '5', '5', '2', '4', '4');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('11', '50', '20', '10', '5', '2', '4', '5');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`, `rt_table_in_restaurant_no`) VALUES ('12', '70', '40', '5', '10', '2', '4', '6');

