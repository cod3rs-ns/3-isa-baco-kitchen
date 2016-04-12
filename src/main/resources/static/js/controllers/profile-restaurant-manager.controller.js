angular
    .module('isa-mrs-project')
    .controller('RestaurantManagerController', RestaurantManagerController);

RestaurantManagerController.$inject = ['restaurantManagerService', '$mdDialog']

function RestaurantManagerController(restaurantManagerService, $mdDialog, EditRestaurantController) {
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
            controller: 'EditRestaurantController',
            controllerAs: 'restaurantVm',
            templateUrl: '/views/dialogs/edit-restaurant.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : to_edit
            }
        });
    }
}
