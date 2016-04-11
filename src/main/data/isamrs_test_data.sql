INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('1', 'Sergio', 'Ramos', 'sr4@real.com', 'images/profile.jpg', 'ramos', 'guest');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('2', 'Alarcon', 'Isco', 'ia22@real.com', 'https://pbs.twimg.com/profile_images/557812407171813376/kYVGA4t5.jpeg', 'isco', 'guest');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('3', 'Jamie', 'Vardy', 'jv@leilcester.com', 'image.jpg', 'party', 'restaurant_manager');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('4', 'Riyad', 'Mahrez', 'rm@leicester.com', 'image.gif', 'mahry', 'restaurant_provider');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('5', 'David', 'Beckham', 'becks@legend.com', 'image.png', 'retired', 'restaurant_provider');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('6', 'Zinedine', 'Zidane', 'zizu@manager.com', 'image.png', 'headshot', 'system_manager');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('7', 'Hugo', 'Vieira', 'hugo@redstar.rs', 'image.png', 'redstar1', 'cook');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('8', 'Damien', 'le Tallec', 'tallec@redstar.rs', 'image.png', 'redstar2', 'bartender');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`) VALUES ('9', 'Michel', 'Donald', 'dm@redstar.rs', 'image.png', 'redstar3', 'waiter');

INSERT INTO `isa_mrs_project`.`sys_managers` (`sm_id`, `sm_info`) VALUES ('6', 'Zidane biography.');

INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`) VALUES ('1', 'Macchiato', 'Macchiato Liman vama na usluzi. Najlepši pogled na Limanski park u gradu xD.', 'Custom', '6', '23', '6');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`) VALUES ('2', 'Orfei', 'Restoran Orfei vama na usluzi.', 'Custom', '7', '21', '6');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`) VALUES ('3', 'Mašinac', 'Studenti na prvom mestu.', 'Studentski', '6', '18', '6');

INSERT INTO `isa_mrs_project`.`guests` (`g_id`, `g_info`) VALUES ('1', 'Ramos biography.');
INSERT INTO `isa_mrs_project`.`guests` (`g_id`, `g_info`) VALUES ('2', 'Ronaldo biography.');
INSERT INTO `isa_mrs_project`.`restaurant_managers` (`rm_id`, `rm_info`, `rm_restaurant_id`) VALUES ('3', 'Vardy biography.', '2');
INSERT INTO `isa_mrs_project`.`restaurant_providers` (`rp_id`, `rp_info`) VALUES ('4', 'Mahrez biography.');
INSERT INTO `isa_mrs_project`.`restaurant_providers` (`rp_id`, `rp_info`) VALUES ('5', 'Becks biography.');


INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`) VALUES ('7', '1988-06-15', 'L', '42b');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`) VALUES ('8', '1988-07-15', 'XXL', '43b');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`) VALUES ('9', '1988-08-15', 'M', '44b');

INSERT INTO `isa_mrs_project`.`cooks` (`c_id`) VALUES ('7');
INSERT INTO `isa_mrs_project`.`bartenders` (`b_id`) VALUES ('8');
INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('9');

