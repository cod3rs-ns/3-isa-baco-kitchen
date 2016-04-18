angular
    .module('isa-mrs-project')
    .controller('RestaurantManagerController', RestaurantManagerController);

RestaurantManagerController.$inject = ['restaurantManagerService', '$mdDialog']

function RestaurantManagerController(restaurantManagerService, $mdDialog, SingleRestaurantController, SingleDrinkController, SingleFoodController) {
    var rmanagerVm = this;
    rmanagerVm.rmanager = {};
    rmanagerVm.updateRestaurant = updateRestaurant;
    rmanagerVm.createDrink = createDrink;
    rmanagerVm.createFood = createFood;
    // Currently active tab
    rmanagerVm.tabs = {
        selected: 0
    }
    activate();

    function activate() {
        // curently locked
        return getRestaurantManager(3).then(function() {
            //alert('Restaurant retreived from database.')

        });
    };

    function getRestaurantManager(id) {
        return restaurantManagerService.getRestaurantManager(id)
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

}
