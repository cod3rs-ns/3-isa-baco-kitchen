angular
    .module('isa-mrs-project')
    .controller('SingleEmployeeController', SingleEmployeeController);

SingleEmployeeController.$inject = ['$mdDialog','$mdToast', 'to_edit'];

function SingleEmployeeController($mdDialog, $mdToast, to_edit) {
    var employeeVm = this;
    employeeVm.employee = {};
    employeeVm.backup = {};
    employeeVm.editState = false;
    employeeVm.confirmedEdit = false;
    employeeVm.showToast = showToast;
    employeeVm.cancel = cancel;
    employeeVm.update = update;
    employeeVm.create = create;

    initState();

    function initState() {
        if (to_edit == null) {
            employeeVm.employee = {

            };

        } else {
            // copy reference
            employeeVm.employee = to_edit;
            // copy DATA for backup
            employeeVm.backup = angular.copy(to_edit);
            // Set state to EDIT
            employeeVm.editState = true;
        }
    }

    function create() {
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
        if (!employeeVm.confirmedEdit && to_edit!=null){
        }
        $mdDialog.cancel();
    };
}