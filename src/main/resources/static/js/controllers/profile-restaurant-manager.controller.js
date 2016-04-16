angular
    .module('isa-mrs-project')
    .controller('RestaurantManagerController', RestaurantManagerController);

RestaurantManagerController.$inject = ['restaurantManagerService', '$mdDialog']

function RestaurantManagerController(restaurantManagerService, $mdDialog, SingleRestaurantController) {
    var rmanagerVm = this;
    rmanagerVm.rmanager = {};
    rmanagerVm.updateRestaurant = updateRestaurant
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

    function updateRestaurant(to_edit) {
        $mdDialog.show({
            controller: 'SingleRestaurantController',
            controllerAs: 'restaurantVm',
            templateUrl: '/views/dialogs/restaurant-form-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : to_edit
            }
        });
    }
}
