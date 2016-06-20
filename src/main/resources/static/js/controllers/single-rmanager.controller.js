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
                'image' : '../images/no_image.gif',
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
        rmanagerVm.cancel();
        var toast = progressToast();

        restaurantManagerService.createRestaurantManager(rmanagerVm.rmanager, restaurant_id)
            .then(function(data){
                $mdToast.hide(toast);
                rmanagerVm.showToast('Menadžer je uspješno kreiran.')
            });
    };

    function update() {

    };

    function progressToast(){
        var toast = $mdToast.show({
            hideDelay : 0,
            position  : 'top right',
            parent    : angular.element(document.querySelectorAll('#toast-box')),
            template  : '<md-toast><md-progress-linear md-mode="indeterminate"></md-progress-linear></md-toast>'
        });

        return toast;
    }


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