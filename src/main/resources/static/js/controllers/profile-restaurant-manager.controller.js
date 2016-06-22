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
    rmanagerVm.employees = [];

    // Currently active tab
    rmanagerVm.tabs = {
        selected: 0
    }

    rmanagerVm.updateRestaurant = updateRestaurant;
    rmanagerVm.createNewEmployee = createNewEmployee;
    rmanagerVm.createMenuItem = createMenuItem;
    rmanagerVm.showRestaurantReviewReport = showRestaurantReviewReport;
    rmanagerVm.showRestaurantFinances = showRestaurantFinances;
    rmanagerVm.showMenuItemReport = showMenuItemReport;
    rmanagerVm.showWaiterFinances = showWaiterFinances;
    rmanagerVm.showVisitsChart = showVisitsChart;
    rmanagerVm.showAllWaiterFinances = showAllWaiterFinances;
    rmanagerVm.showWaiterRatingReport = showWaiterRatingReport;
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
        getLoggedRestaurantManager().then(function() {
            menuItemService.getAllActiveByType('food', rmanagerVm.rmanager.restaurant.restaurantId).success(function(data) {
                rmanagerVm.foodMenu = data;
            });

            menuItemService.getAllActiveByType('drink', rmanagerVm.rmanager.restaurant.restaurantId).success(function(data) {
                rmanagerVm.drinksMenu = data;
            });

            employeeService.getEmployeesByRestaurant(rmanagerVm.rmanager.restaurant.restaurantId).then(function(data) {
                rmanagerVm.employees = data;
            });
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
            clickOutsideToClose: true,
            fullscreen: false,
            locals: {
                to_edit : rmanagerVm.rmanager.restaurant,
                smanager : null
            }
        });
    };

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

    function showWaiterFinances(id, name, surname) {
        $mdDialog.show({
            controller: 'WaiterReportController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/date-picker-report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                waiter_id : id,
                waiter_name: name + ' ' + surname
            }
        });
    }

    function showAllWaiterFinances() {
        $mdDialog.show({
            controller: 'WaitersFinanceReportController',
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

    function showWaiterRatingReport(id, name, surname) {
        $mdDialog.show({
            controller: 'WaiterRatingReportController',
            controllerAs: 'reportVm',
            templateUrl: '/views/dialogs/report-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                waiter_id : id,
                waiter_name : name + ' ' + surname
            }
        });
    }

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

    function createMenuItem() {
        $mdDialog.show({
            controller: 'SingleMenuItemController',
            controllerAs: 'menuItemVm',
            templateUrl: '/views/dialogs/menu-item-form-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose: false,
            fullscreen: false,
            locals: {
                restaurant_id : rmanagerVm.rmanager.restaurant.restaurantId,
                drinks_menu_ref :  rmanagerVm.drinkMenu,
                food_menu_ref : rmanagerVm.foodMenu,
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
        })
        .finally(function() {
            employeeService.getEmployeesByRestaurant(rmanagerVm.rmanager.restaurant.restaurantId).then(function(data) {
                rmanagerVm.employees = data;
            });
        });
    };

}
