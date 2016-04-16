angular
    .module('isa-mrs-project')
    .controller('SystemManagerProfileController', SystemManagerProfileController);

SystemManagerProfileController.$inject = ['systemManagerService', '$mdDialog'];

function SystemManagerProfileController(systemManagerService, $mdDialog, AddRestaurantController) {
    var systemManagerProfileVm = this;

    // Set bindable memebers at the top of the controller
    systemManagerProfileVm.name = 'Sergio dr Ramos ';
    systemManagerProfileVm.showSearch = false;
    systemManagerProfileVm.restaurants = [];

    activate();

    function activate() {
        return getRestaurant(6).then(function() {
        });
    }

    function getRestaurant(id) {
        return systemManagerService.getRestaurants(id).then(function(data) {
            systemManagerProfileVm.restaurants = data;
            return systemManagerProfileVm.restaurants;
        });
    }
    
    systemManagerProfileVm.addRestaurant = addRestaurant;
    // Implement functions later
    function addRestaurant() {
        $mdDialog.show({
            controller: 'AddRestaurantController',
            controllerAs: 'addRestaurantVm',
            templateUrl: '/views/dialogs/add-restaurant.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                restaurants: systemManagerProfileVm.restaurants
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
    }
}
