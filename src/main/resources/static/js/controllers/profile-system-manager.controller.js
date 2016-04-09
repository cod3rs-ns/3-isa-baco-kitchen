angular
    .module('isa-mrs-project')
    .controller('SystemManagerProfileController', SystemManagerProfileController);

function SystemManagerProfileController() {
    var systemManagerProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    systemManagerProfileVm.name = 'Sergio dr Ramos ';
    systemManagerProfileVm.foo = foo;
    systemManagerProfileVm.showSearch = false;


    systemManagerProfileVm.restaurants = [
        {
            name: 'Restoran kod Baca',
            img_src: 'http://urbantastebud.com/wp-content/uploads/2015/08/nc-food-and-beverage-pub.jpg'
        },
        {
            name: 'Restoran kod Keky-a',
            img_src: 'http://www.comohotels.com/metropolitanbangkok/sites/default/files/styles/background_image/public/images/background/metbkk_bkg_nahm_restaurant.jpg?itok=GSmnYYaU'
        },
        {
            name: 'Restoran kod Dragutinka',
            img_src: 'http://www.thelalit.com/d/the-lalit-new-delhi/media/TheLalitNewDelhi/FoodBeverage_Restaurants/24_7Restaurant/24-7RestaurantDelhi.jpg'
        }
    ];

    // Implement functions later
    function foo() {
        
    }
}