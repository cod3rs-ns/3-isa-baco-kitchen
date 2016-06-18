angular
    .module('isa-mrs-project')
    .controller('RestaurantManagerController', RestaurantManagerController);

RestaurantManagerController.$inject = ['restaurantManagerService', '$mdDialog', 'menuItemService', '$scope', 'employeeService'];

function RestaurantManagerController(restaurantManagerService, $mdDialog, menuItemService, $scope, employeeService, SingleRestaurantController,
                                     SingleDrinkController, SingleFoodController, SingleEmployeeController) {
    var rmanagerVm = this;
    rmanagerVm.rmanager = {};
    rmanagerVm.foodMenu = [];
    rmanagerVm.drinkMenu = [];
    rmanagerVm.updateRestaurant = updateRestaurant;
    rmanagerVm.createDrink = createDrink;
    rmanagerVm.createFood = createFood;
    rmanagerVm.createNewEmployee = createNewEmployee;

    // TODO update on create new
    rmanagerVm.employees = [];
    // Currently active tab
    rmanagerVm.tabs = {
        selected: 0
    }

    rmanagerVm.upload = upload;
    function upload($flow){
        $flow.opts.target = 'api/upload/users/' + rmanagerVm.rmanager.userId ;
        $flow.upload();
        console.log($flow);
        rmanagerVm.rmanager.image = '/images/users/users_' + rmanagerVm.rmanager.userId + '.png';
        restaurantManagerService.updateRestaurantManager(rmanagerVm.rmanager)
            .then(function(data) {
                rmanagerVm.rmanager = data;
            })
    }

    activate();

    function activate() {
        // curently locked
        getLoggedRestaurantManager().then(function() {
            //alert('Restaurant retreived from database.')

        });
        menuItemService.getAllActiveByType('food', 2).success(function(data) {
            console.log(data);
            rmanagerVm.foodMenu = data;
        });

        menuItemService.getAllActiveByType('drink', 2).success(function(data) {
            console.log(data);
            rmanagerVm.drinksMenu = data;
        });

        employeeService.getEmployeesByRestaurant(2).then(function(data) {
            console.log(data);
            rmanagerVm.employees = data;
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

    rmanagerVm.showMenuItemReport = showMenuItemReport;
    function showMenuItemReport(menu_item_id, menu_item_name) {
        $mdDialog.show({
            controller: 'MenuItemReportController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                item_id : menu_item_id,
                item_name : menu_item_name
            }
        });
    };

    rmanagerVm.showEmployeeReportDialog = showEmployeeReportDialog;
    function showEmployeeReportDialog(id) {
        $mdDialog.show({
            controller: 'WaiterReportController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/date-picker-report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                item_id : id,
            }
        });
    }

    rmanagerVm.showVisitsChart = showVisitsChart;
    function showVisitsChart() {
        $mdDialog.show({
            controller: 'RestaurantVisitsController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/date-picker-report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                restaurant : rmanagerVm.rmanager.restaurant
            }
        });
    }

    rmanagerVm.showRestaurantFinances = showRestaurantFinances;
    function showRestaurantFinances() {
        $mdDialog.show({
            controller: 'FinancesController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/date-picker-report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                restaurant: rmanagerVm.rmanager.restaurant
            }
        });
    }

    rmanagerVm.showRestaurantReviewReport = showRestaurantReviewReport;
    function showRestaurantReviewReport() {
        $mdDialog.show({
            controller: 'RestaurantReviewReportController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                restaurant: rmanagerVm.rmanager.restaurant
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
