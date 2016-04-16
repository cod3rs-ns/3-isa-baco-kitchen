angular
    .module('isa-mrs-project')
    .controller('RestaurantProfileController', RestaurantProfileController);

RestaurantProfileController.$inject = ['restaurantService', '$mdDialog', '$routeParams'];

function RestaurantProfileController(restaurantService, $mdDialog, $routeParams, SingleDrinkController){
    var restaurantVm = this;
    restaurantVm.restaurant = {};

    activate();

    function activate() {
        return getRestaurant($routeParams.restaurantId).then(function() {
            //alert('Restaurant retreived from database.')
            restaurantVm.worktime = restaurantVm.restaurant.startTime + ' h : '
                                    + restaurantVm.restaurant.endTime + ' h'
        });
    };

    function getRestaurant(id) {
        return restaurantService.getRestaurant(id)
            .then(function(data) {
                restaurantVm.restaurant = data;
                return restaurantVm.restaurant;
            });
    };

    restaurantVm.meals = [
          {
            name: 'Kolač sa borovnicama',
    		img_src: 'images/meals/borovnica.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
          },
    	  {
            name: 'Štrudla sa makom',
    		img_src: 'images/meals/mak.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
          },
    	  {
            name: 'Štrudla sa makom',
    		img_src: 'images/meals/mak.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
          name: 'Kolač sa borovnicama',
          img_src: 'images/meals/borovnica.jpg',
          price: '150 RSD',
          info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        }
    ];

    restaurantVm.drinks = [
        {
            name: 'Sour',
            img_src: 'images/meals/sour.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
            name: 'Cosmopolitan',
            img_src: 'images/meals/cosmopolitan.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
            name: 'Sour',
            img_src: 'images/meals/sour.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        },
        {
            name: 'Cosmopolitan',
            img_src: 'images/meals/cosmopolitan.jpg',
            price: '150 RSD',
            info: 'Kratke informacije o piću/jelu. Npr. od čega se pravi, nešto specifično...'
        }
    ];

    restaurantVm.createDrink = createDrink;

    function createDrink(){
        $mdDialog.show({
            controller: 'SingleDrinkController',
            controllerAs: 'drinkVm',
            templateUrl: '/views/dialogs/drink-form-tmpl.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: false,
        });
    };

    restaurantVm.test = test;
    function test(){
        $mdDialog.show({
            templateUrl: '/views/dialogs/single-emloyee-tmpl.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: false,
        });
    };
}
