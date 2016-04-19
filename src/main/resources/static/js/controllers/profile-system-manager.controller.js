angular
    .module('isa-mrs-project')
    .controller('SystemManagerProfileController', SystemManagerProfileController);

SystemManagerProfileController.$inject = ['systemManagerService', '$mdDialog'];

function SystemManagerProfileController(systemManagerService, $mdDialog) {
    var systemManagerProfileVm = this;

    // Set bindable memebers at the top of the controller
    systemManagerProfileVm.name = 'Sergio dr Ramos ';
    systemManagerProfileVm.showSearch = false;
    systemManagerProfileVm.restaurants = [];

    //TODO get concrete manager
    systemManagerProfileVm.smanager = {
        'userId':null,
        'firstName':'Zinedine',
        'lastName':'Zidane',
        'email':'zizu@manager.com',
        'image':'image.png',
        'type':'system_manager',
        'verified':'verified',
        'info':'Zidane biography',
        'restaurants':[]
    };

    activate();

    function activate() {
        //TODO change id
        return getRestaurant(5).then(function() {
        });
    }

    function getRestaurant(id) {
        return systemManagerService.getRestaurants(id).then(function(data) {
            systemManagerProfileVm.restaurants = data;
            return systemManagerProfileVm.restaurants;
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
