angular
    .module('isa-mrs-project')
    .controller('SingleRManagerController', SingleRManagerController);

SingleRManagerController.$inject = ['restaurantManagerService', '$mdDialog','$mdToast', 'to_edit', 'restaurant_id'];

function SingleRManagerController(restaurantManagerService, $mdDialog, $mdToast, to_edit, restaurant_id) {
    var rmanagerVm = this;
    rmanagerVm.rmanager = {};
    rmanagerVm.backup = {};
    rmanagerVm.editState = false;
    rmanagerVm.confirmedEdit = false;
    rmanagerVm.showToast = showToast;
    rmanagerVm.cancel = cancel;
    rmanagerVm.update = update;
    rmanagerVm.create = create;

    initState();

    function initState() {
        if (to_edit == null) {
            rmanagerVm.rmanager = {
                'userId' : null,
                'firstName' : '',
                'lastName' : '',
                'email' : '',
                'image' : '',
                'type' : 'restaurant_manager',
                'info' : '',
                'restaurant' : {}
            };

        } else {
            // copy reference
            rmanagerVm.rmanager = to_edit;
            // copy DATA for backup
            rmanagerVm.backup = angular.copy(to_edit);
            // Set state to EDIT
            rmanagerVm.editState = true;
        }
    }

    function create() {
        restaurantManagerService.createRestaurantManager(rmanagerVm.rmanager, restaurant_id)
            .then(function(data){
                rmanagerVm.showToast('Menadžer je uspješno kreiran.')
                rmanagerVm.cancel();
            });
    };

    function update() {

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
        if (!rmanagerVm.confirmedEdit && to_edit!=null){
            rmanagerVm.rmanager = rmanagerVm.backup;
        }
        $mdDialog.cancel();
    };
}