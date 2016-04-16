angular
    .module('isa-mrs-project')
    .controller('SingleRestaurantController', SingleRestaurantController);

SingleRestaurantController.$inject = ['restaurantService', '$mdDialog','$mdToast', 'to_edit', 'restaurants'];

function SingleRestaurantController(restaurantService, $mdDialog, $mdToast, to_edit, restaurants) {
    var restaurantVm = this;
    restaurantVm.restaurant = {};
    restaurantVm.backup = {};
    restaurantVm.editState = false;
    restaurantVm.confirmedEdit = false;
    restaurantVm.showToast = showToast;
    restaurantVm.cancel = cancel;
    restaurantVm.update = update;
    restaurantVm.create = create;

    initState();

    function initState() {
        if (to_edit == null) {
            restaurantVm.restaurant = {
                restaurantId: null,
                name: '',
                info: '',
                type: '',
                startTime: 0,
                endTime: 24
            };

        } else {
            // copy reference
            restaurantVm.restaurant = to_edit;
            // copy DATA for backup
            restaurantVm.backup = angular.copy(to_edit);
            // Set state to EDIT
            restaurantVm.editState = true;
        }
    }

    function create() {
        // TODO: Change hardcoded 6 to current system_manager
        restaurantService.createRestaurant(restaurantVm.restaurant, 6)
            .then(function(addedRestaurant){
                restaurants.push(addedRestaurant);
                restaurantVm.showToast('Restoran je uspešno kreiran.')
                restaurantVm.cancel();
            });
    };

    function update() {
        // TODO: Change hardcoded 6 to current system_manager
        restaurantService.updateRestaurant(restaurantVm.restaurant, 6)
            .then(function(data){
                restaurantVm.confirmedEdit = true;
                restaurantVm.showToast('Restoran je uspešno izmenjen.');
                restaurantVm.cancel();
            });
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function cancel() {
        // revert changes if EDIT has been cancelled
        if (!restaurantVm.confirmedEdit){
            restaurantVm.restaurant.name = restaurantVm.backup.name;
            restaurantVm.restaurant.info = restaurantVm.backup.info;
            restaurantVm.restaurant.type = restaurantVm.backup.type;
            restaurantVm.restaurant.startTime = restaurantVm.backup.startTime;
            restaurantVm.restaurant.endTime = restaurantVm.backup.endTime;
        }
        $mdDialog.cancel();
    };
}