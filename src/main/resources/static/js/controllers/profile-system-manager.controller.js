angular
    .module('isa-mrs-project')
    .controller('SystemManagerProfileController', SystemManagerProfileController);

SystemManagerProfileController.$inject = ['systemManagerService', '$mdDialog'];

function SystemManagerProfileController(systemManagerService, $mdDialog) {
    var systemManagerProfileVm = this;

    systemManagerProfileVm.showSearch = false;

    activate();

    function activate() {
        getSystemManager()
            .then(function() {
                getRestaurants(systemManagerProfileVm.smanager.userId).then(function() {
                });
            });
    }

    function getSystemManager(){
        return systemManagerService.getLoggedSM()
            .then(function(response) {
                var data = response.data;
                systemManagerProfileVm.smanager = data;
                return systemManagerProfileVm.smanager;
            });
    };

    function getRestaurants(id) {
        return systemManagerService.getRestaurants(id).then(function(data) {
            systemManagerProfileVm.smanager.restaurants = data;
            return systemManagerProfileVm.smanager.restaurants;
        });
    }

    
    systemManagerProfileVm.addRestaurant = addRestaurant;
    function addRestaurant() {
        $mdDialog.show({
            controller: 'SingleRestaurantController',
            controllerAs: 'restaurantVm',
            templateUrl: '/views/dialogs/restaurant-form-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : null,
                smanager: systemManagerProfileVm.smanager
            }
        });
    }

    systemManagerProfileVm.addProvider = addProvider;
    function addProvider() {
        $mdDialog.show({
            controller: 'SingleProviderController',
            controllerAs: 'providerVm',
            templateUrl: '/views/dialogs/single-provider-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false
        });
    };


    systemManagerProfileVm.changePassword = changePassword;
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

    systemManagerProfileVm.addRestaurantManager = addRestaurantManager;
    function addRestaurantManager(restaurantId) {
        $mdDialog.show({
            controller: 'SingleRManagerController',
            controllerAs: 'rmanagerVm',
            templateUrl: '/views/dialogs/single-rmanager-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : null,
                restaurant_id : restaurantId
            }
        });
    }

    systemManagerProfileVm.showRestaurantDetails = showRestaurantDetails;
    function showRestaurantDetails(restaurantId) {
        $location.path('profile-restaurant/' + restaurantId);
    }


}
