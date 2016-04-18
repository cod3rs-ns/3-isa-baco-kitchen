angular
    .module('isa-mrs-project')
    .controller('RestaurantProfileController', RestaurantProfileController);

RestaurantProfileController.$inject = ['restaurantService', 'userService', '$mdDialog', '$routeParams'];

function RestaurantProfileController(restaurantService, userService, $mdDialog, $routeParams, SingleDrinkController){
    var restaurantVm = this;
    restaurantVm.restaurant = {};
    restaurantVm.addManagerOption = false;

    activate();

    function activate() {
        getRestaurant($routeParams.restaurantId).then(function() {
            //alert('Restaurant retreived from database.')
            restaurantVm.worktime = restaurantVm.restaurant.startTime + ' h : '
                                    + restaurantVm.restaurant.endTime + ' h'
        });


        setPriorities().then(function(){
        });
    };

    function setPriorities(){
        return userService.getRegisteredUser()
            .then(function(data) {
                restaurantVm.addManagerOption = (data.type == "system_manager");
            });
    }

    function getRestaurant(id) {
        return restaurantService.getRestaurant(id)
            .then(function(data) {
                restaurantVm.restaurant = data;
                return restaurantVm.restaurant;
            });
    };

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

    restaurantVm.addRestaurantManager = addRestaurantManager;
    function addRestaurantManager() {
        $mdDialog.show({
            controller: 'SingleRManagerController',
            controllerAs: 'rmanagerVm',
            templateUrl: '/views/dialogs/single-rmanager-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : null,
                restaurant_id :  restaurantVm.restaurant.restaurantId
            }
        });
    }
}
