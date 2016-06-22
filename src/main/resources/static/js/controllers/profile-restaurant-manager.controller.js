angular
    .module('isa-mrs-project')
    .controller('RestaurantManagerController', RestaurantManagerController);

RestaurantManagerController.$inject = ['restaurantManagerService', 'passService', '$mdDialog', '$mdToast', 'menuItemService', '$scope', 'employeeService'];

function RestaurantManagerController(restaurantManagerService, passService, $mdDialog, $mdToast, menuItemService, $scope, employeeService, SingleRestaurantController,
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
    rmanagerVm.changePassword = changePassword;
    rmanagerVm.upload = upload;
    rmanagerVm.uploadItemImage = uploadItemImage;
    rmanagerVm.editProfile = editProfile;
    rmanagerVm.editMenuItem = editMenuItem;
    rmanagerVm.deleteMenuItem = deleteMenuItem;

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

        passService.isPasswordChanged()
            .then(function (data) {
                if (!data){
                    rmanagerVm.changePassword(false);
                }
            });

    }

    function getLoggedRestaurantManager() {
        return restaurantManagerService.getLoggedRestaurantManager()
            .then(function(data) {
                rmanagerVm.rmanager = data;
                return rmanagerVm.rmanager;
            });
    };

    function upload($flow){
        $flow.opts.target = 'api/upload/users/' + rmanagerVm.rmanager.userId ;
        $flow.upload();
        rmanagerVm.rmanager.image = '/images/users/users_' + rmanagerVm.rmanager.userId + '.png';
        restaurantManagerService.updateRestaurantManager(rmanagerVm.rmanager)
            .then(function(data) {
                rmanagerVm.rmanager = data;
                showToast('Fotografija uspešno promenjena.', 3000);
            });
    }

    rmanagerVm.preventMultiple = preventMultiple;
    function preventMultiple(menu_item){
        console.log('TO');
        rmanagerVm.toUpload = menu_item;
    }

    function uploadItemImage($flow, menu_item){
        if(menu_item.menuItemId == rmanagerVm.toUpload.menuItemId){
            $flow.opts.target = 'api/upload/meals/' + menu_item.menuItemId;
            $flow.upload();
            menu_item.image = '/images/meals/meals_' +  menu_item.menuItemId + '.png';
            console.log(menu_item);
            menuItemService.update(menu_item, rmanagerVm.rmanager.restaurant.restaurantId)
                .then(function(data){
                    menu_item = data;
                    console.log('THis shit.')
                    showToast('Fotografija uspešno promenjena.', 3000);
                });
        }
    }

    function showToast(text, delay) {
        var toast = $mdToast.show({
          hideDelay : delay,
          position  : 'top right',
          parent    : angular.element(document.querySelectorAll('#toast-box')),
          template  : '<md-toast>' + text  + '</md-toast>'
        });
        return toast;
    };

    function changePassword(modal) {
        $mdDialog.show(
            {
                controller: 'ChangePasswordController',
                controllerAs: 'userVm',
                templateUrl: '/views/dialogs/change-password.html',
                parent: angular.element(document.body),
                clickOutsideToClose: modal,
                escapeToClose: modal,
                fullscreen: false,
                openFrom : angular.element(document.querySelector('#pass-option')),
                closeTo : angular.element(document.querySelector('#pass-option')),
                locals: {
                    modal : modal
                }
            }
        );
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

    function editMenuItem(menu_item) {
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
                tabs : rmanagerVm.tabs,
                to_edit : menu_item
            }
        });
    };

    function deleteMenuItem(menu_item_id) {
        var confirm = $mdDialog.confirm()
            .title('Potvrda uklanjanja stavke menija')
            .textContent('Da li ste sigurni da želite da uklonite stavku?')
            .ariaLabel('Menu item deletion')
            .ok('Da')
            .cancel('Ne');

            $mdDialog.show(confirm)
                .then(function() {
                    menuItemService.remove(menu_item_id)
                        .then(function (response) {
                            menuItemService.getAllActiveByType('food', rmanagerVm.rmanager.restaurant.restaurantId).success(function(data) {
                                rmanagerVm.foodMenu = data;
                            });

                            menuItemService.getAllActiveByType('drink', rmanagerVm.rmanager.restaurant.restaurantId).success(function(data) {
                                rmanagerVm.drinksMenu = data;
                            });
                            showToast('Stavka je uspešno uklonjena.', 3000);
                        });
                }, function() {
                    $mdDialog.hide();
                });
    }

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
                tabs : rmanagerVm.tabs,
                to_edit : null
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

    function editProfile(){
        $mdDialog.show({
            controller: 'SingleRManagerController',
            controllerAs: 'rmanagerVm',
            templateUrl: '/views/dialogs/single-rmanager-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : rmanagerVm.rmanager,
                restaurant_id : null
            }
        });
    }

}
