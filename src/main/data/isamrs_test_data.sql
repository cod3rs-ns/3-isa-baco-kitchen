INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`) VALUES ('1', 'Sergio', 'Ramos', 'sr4@real.com', 'image.png', 'ramos');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`) VALUES ('2', 'Cristiano', 'Ronaldo', 'cr7@real.com', 'image.png', 'cristiano');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`) VALUES ('3', 'Jamie', 'Vardy', 'jv@leilcester.com', 'image.jpg', 'party');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`) VALUES ('4', 'Riyad', 'Mahrez', 'rm@leicester.com', 'image.gif', 'mahry');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`) VALUES ('5', 'David', 'Beckham', 'becks@legend.com', 'image.png', 'retired');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`) VALUES ('6', 'Zinedine', 'Zidane', 'zizu@manager.com', 'image.png', 'headshot');

INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`) VALUES ('1', 'Macchiato', 'Macchiato Liman vama na usluzi. Najlepši pogled na Limanski park u gradu xD.', 'Custom', '6', '23');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`) VALUES ('2', 'Orfei', 'Restoran Orfei vama na usluzi.', 'Custom', '7', '21');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`) VALUES ('3', 'Mašinac', 'Studenti na prvom mestu.', 'Studentski', '6', '18');

INSERT INTO `isa_mrs_project`.`guests` (`g_id`, `g_info`) VALUES ('1', 'Ramos biography.');
INSERT INTO `isa_mrs_project`.`guests` (`g_id`, `g_info`) VALUES ('2', 'Ronaldo biography.');
INSERT INTO `isa_mrs_project`.`restaurant_managers` (`rm_id`, `rm_info`, `rm_restaurant_id`) VALUES ('3', 'Vardy biography.', '2');
INSERT INTO `isa_mrs_project`.`restaurant_providers` (`rp_id`, `rp_info`) VALUES ('4', 'Mahrez biography.');
INSERT INTO `isa_mrs_project`.`restaurant_providers` (`rp_id`, `rp_info`) VALUES ('5', 'Becks biography.');
INSERT INTO `isa_mrs_project`.`sys_managers` (`sm_id`, `sm_info`) VALUES ('6', 'Zidane biography.');

