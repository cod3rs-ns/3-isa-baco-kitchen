angular
    .module('isa-mrs-project')
    .controller('RestaurantManagerController', RestaurantManagerController);

RestaurantManagerController.$inject = ['restaurantManagerService', '$mdDialog', 'menuItemService'];

function RestaurantManagerController(restaurantManagerService, $mdDialog, menuItemService, SingleRestaurantController,
                                     SingleDrinkController, SingleFoodController, SingleEmployeeController) {
    var rmanagerVm = this;
    rmanagerVm.rmanager = {};
    rmanagerVm.foodMenu = [];
    rmanagerVm.drinkMenu = [];
    rmanagerVm.updateRestaurant = updateRestaurant;
    rmanagerVm.createDrink = createDrink;
    rmanagerVm.createFood = createFood;
    rmanagerVm.createNewEmployee = createNewEmployee;
    // Currently active tab
    rmanagerVm.tabs = {
        selected: 0
    }

    activate();

    function activate() {
        // curently locked
        getLoggedRestaurantManager().then(function() {
            //alert('Restaurant retreived from database.')

        });
        menuItemService.getAllByType('food', 2).then(function(data) {
            console.log(data);
            rmanagerVm.foodMenu = data;
        });

        menuItemService.getAllByType('drink', 2).then(function(data) {
            console.log(data);
            rmanagerVm.drinksMenu = data;
        });
    }

    function getLoggedRestaurantManager() {
        return restaurantManagerService.getLoggedRestaurantManager()
            .then(function(data) {
                rmanagerVm.rmanager = data;
                return rmanagerVm.rmanager;
            });
    };

    function updateRestaurant() {
        $mdDialog.show({
            controller: 'SingleRestaurantController',
            controllerAs: 'restaurantVm',
            templateUrl: '/views/dialogs/restaurant-form-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : rmanagerVm.rmanager.restaurant,
                restaurants : null
            }
        });
    };

    function createDrink(restaurant_id, drinks_menu_ref) {
        $mdDialog.show({
            controller: 'SingleDrinkController',
            controllerAs: 'drinkVm',
            templateUrl: '/views/dialogs/drink-form-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                restaurant_id : rmanagerVm.rmanager.restaurant.restaurantId,
                drinks_menu_ref :  rmanagerVm.rmanager.restaurant.drinksMenu,
                tabs : rmanagerVm.tabs
            }
        });
    };

    function createFood() {
        $mdDialog.show({
            controller: 'SingleFoodController',
            controllerAs: 'foodVm',
            templateUrl: '/views/dialogs/food-form-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                restaurant_id : rmanagerVm.rmanager.restaurant.restaurantId,
                food_menu_ref : rmanagerVm.rmanager.restaurant.foodMenu,
                tabs : rmanagerVm.tabs
            }
        });
    };

    function createNewEmployee() {
        $mdDialog.show({
            controller: 'SingleEmployeeController',
            controllerAs: 'employeeVm',
            templateUrl: '/views/dialogs/single-employee-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : null
            }
        });
    };

}
