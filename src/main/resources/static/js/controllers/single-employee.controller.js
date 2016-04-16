angular
    .module('isa-mrs-project')
    .controller('SingleEmployeeController', SingleEmployeeController);

SingleEmployeeController.$inject = ['cookService', '$mdDialog','$mdToast', 'to_edit'];

function SingleEmployeeController(cookService, $mdDialog, $mdToast, to_edit) {
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
        console.log(employeeVm.employee);
        switch (employeeVm.employee.type) {
            case "cook": cookService.
                updateCook(employeeVm.employee)
                    .then(function (data) {
                        employeeVm.showToast('Kuvar je uspješno izmijenjen.');
                    });
                break;
            default:
                alert("wrong type of employee");
        }
        employeeVm.confirmedEdit = true;
        employeeVm.cancel();
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
            employeeVm.employee.firstName = employeeVm.backup.firstName;
            employeeVm.employee.lastName = employeeVm.backup.lastName;
            employeeVm.employee.birthday = employeeVm.backup.birthday;
            employeeVm.employee.shoesSize = employeeVm.backup.shoesSize;
            employeeVm.employee.dressSize = employeeVm.backup.dressSize;
        }
        $mdDialog.cancel();
    };
}