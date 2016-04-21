INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('1', 'Sergio', 'Ramos', 'sr4@real.com', 'images/profile.jpg', 'ramosramos', 'guest', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('2', 'Alarcon', 'Isco', 'ia22@real.com', 'https://pbs.twimg.com/profile_images/557812407171813376/kYVGA4t5.jpeg', 'isco', 'guest', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('3', 'Jamie', 'Vardy', 'jv@leicester.com', 'images/restaurant-manager.jpg', 'vardyparty', 'restaurant_manager', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('4', 'David', 'Beckham', 'becks@legend.com', 'images/restaurant-provider.jpg', 'benditlikeme', 'restaurant_provider', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('5', 'Zinedine', 'Zidane', 'zizu@manager.com', 'images/system-manager.jpg', 'headshot', 'system_manager', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('6', 'Hugo', 'Vieira', 'hugo@redstar.rs', 'images/cook.jpg', 'redstar1', 'cook', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('7', 'Damien', 'le Tallec', 'tallec@redstar.rs', 'images/bartender.jpg', 'redstar2', 'bartender', 'verified');
INSERT INTO `isa_mrs_project`.`users` (`u_id`, `u_fname`, `u_lname`, `u_email`, `u_image`, `u_password`, `u_type`, `u_verified`) VALUES ('8', 'Mitchell', 'Donald', 'dm@redstar.rs', 'images/waiter.png', 'redstar3', 'waiter', 'verified');

INSERT INTO `isa_mrs_project`.`sys_managers` (`sm_id`, `sm_info`) VALUES ('5', 'Zidane biography.');

INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`) VALUES ('1', 'Macchiato', 'Macchiato Liman vama na usluzi. Najlepši pogled na Limanski park u gradu xD.', 'Custom', '6', '23', '5');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`) VALUES ('2', 'Orfei', 'Restoran Orfei vama na usluzi.', 'Orfei ima baštu. Dođite u baštu.', '7', '21', '5');
INSERT INTO `isa_mrs_project`.`restaurants` (`r_id`, `r_name`, `r_info`, `r_type`, `r_time_start`, `r_time_end`,  `r_sm_id`) VALUES ('3', 'Mašinac', 'Studenti na prvom mestu.', 'Studentski, studentski...', '6', '18', '5');

INSERT INTO `isa_mrs_project`.`guests` (`g_id`, `g_info`) VALUES ('1', 'Ramos biography.');
INSERT INTO `isa_mrs_project`.`guests` (`g_id`, `g_info`) VALUES ('2', 'Isco biography.');
INSERT INTO `isa_mrs_project`.`restaurant_managers` (`rm_id`, `rm_info`, `rm_restaurant_id`) VALUES ('3', 'Vardy biography.', '2');
INSERT INTO `isa_mrs_project`.`restaurant_providers` (`rp_id`, `rp_info`) VALUES ('4', 'Becks biography.');


INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`) VALUES ('6', '1988-06-15', 'L', '42b');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`) VALUES ('7', '1988-07-15', 'XXL', '43b');
INSERT INTO `isa_mrs_project`.`employees` (`e_id`, `e_bday`, `e_dress_size`, `e_shoes_size`) VALUES ('8', '1988-08-15', 'M', '44b');

INSERT INTO `isa_mrs_project`.`cooks` (`c_id`) VALUES ('6');
INSERT INTO `isa_mrs_project`.`bartenders` (`b_id`) VALUES ('7');
INSERT INTO `isa_mrs_project`.`waiters` (`w_id`) VALUES ('8');

INSERT INTO `isa_mrs_project`.`food` (`f_id`, `f_info`, `f_name`, `f_price`, `f_type`, `f_image`, `f_restaurant_id`) VALUES ('1', 'Ukusno.', 'Kolač s borovnicom.', '150', 'dessert', 'images/meals/borovnica.jpg', '1');
INSERT INTO `isa_mrs_project`.`food` (`f_id`, `f_info`, `f_name`, `f_price`, `f_type`, `f_image`, `f_restaurant_id`) VALUES ('2', 'Ukusno', 'Štrudla s makom.', '235', 'dessert', 'images/meals/mak.jpg', '1');
INSERT INTO `isa_mrs_project`.`food` (`f_id`, `f_info`, `f_name`, `f_price`, `f_type`, `f_image`, `f_restaurant_id`) VALUES ('3', 'Ukusno.', 'Kolač s borovnicom.', '150', 'dessert', 'images/meals/borovnica.jpg', '2');
INSERT INTO `isa_mrs_project`.`food` (`f_id`, `f_info`, `f_name`, `f_price`, `f_type`, `f_image`, `f_restaurant_id`) VALUES ('4', 'Ukusno.', 'Kolač s borovnicom.', '150', 'dessert', 'images/meals/borovnica.jpg', '3');

INSERT INTO `isa_mrs_project`.`drinks` (`d_id`, `d_info`, `d_name`, `d_price`, `d_type`, `d_image`, `d_restaurant_id`) VALUES ('1', 'Opis ovog pića.', 'Cosmopolitan', '350', 'alcoholic', 'images/meals/cosmopolitan.jpg', '1');
INSERT INTO `isa_mrs_project`.`drinks` (`d_id`, `d_info`, `d_name`, `d_price`, `d_type`, `d_image`, `d_restaurant_id`) VALUES ('2', 'Opis ovog pića.', 'Cosmopolitan', '350', 'alcoholic', 'images/meals/cosmopolitan.jpg', '2');
INSERT INTO `isa_mrs_project`.`drinks` (`d_id`, `d_info`, `d_name`, `d_price`, `d_type`, `d_image`, `d_restaurant_id`) VALUES ('3', 'Opis ovog pića.', 'Cosmopolitan', '350', 'alcoholic', 'images/meals/cosmopolitan.jpg', '3');

INSERT INTO `isa_mrs_project`.`restaurant_regions` (`rr_id`, `rr_name`, `rr_color`, `rr_restaurant_id`) VALUES ('1', 'basta', 'blue', '2');
INSERT INTO `isa_mrs_project`.`restaurant_regions` (`rr_id`, `rr_name`, `rr_color`, `rr_restaurant_id`) VALUES ('2', 'prostorija_1', 'green', '2');

INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`) VALUES ('1', '60', '50', '40', '130', '4', '1');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`) VALUES ('2', '220', '50', '90', '70', '4', '1');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`) VALUES ('3', '300', '110', '160', '80', '6', '1');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`) VALUES ('4', '150', '380', '40', '40', '2', '1');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`) VALUES ('5', '380', '520', '40', '40', '2', '2');
INSERT INTO `isa_mrs_project`.`restaurant_tables` (`rt_id`, `rt_datax`, `rt_datay`, `rt_width`, `rt_height`, `rt_positions`, `rt_region_id`) VALUES ('6', '500', '520', '40', '40', '2', '2');