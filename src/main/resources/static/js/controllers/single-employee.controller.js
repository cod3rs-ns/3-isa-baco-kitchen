angular
    .module('isa-mrs-project')
    .controller('SingleEmployeeController', SingleEmployeeController);

SingleEmployeeController.$inject = ['employeeService', '$mdDialog','$mdToast', 'to_edit'];

function SingleEmployeeController(employeeService, $mdDialog, $mdToast, to_edit) {
    var employeeVm = this;
    employeeVm.employee = {};
    employeeVm.backup = {};
    employeeVm.editState = false;
    employeeVm.confirmedEdit = false;
    employeeVm.showToast = showToast;
    employeeVm.cancel = cancel;
    employeeVm.update = update;
    employeeVm.create = create;
    employeeVm.sizeClicked = sizeClicked;

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
        employeeVm.employee.restaurantID = 2;
        console.log(employeeVm.employee);
        if (employeeVm.employee.type == 'waiter') {
            employeeVm.employee.image = 'images/waiter.png';
            employeeService.createWaiter(employeeVm.employee)
                .then(function(data){
                    console.log(data);
                });
            showToast('Uspešno ste kreirali profil konobara.');
        }else if (employeeVm.employee.type == 'cook') {
            employeeVm.employee.image = 'images/cook.png';
            employeeService.createCook(employeeVm.employee)
                .then(function(data){
                    console.log(data);
                });
            showToast('Uspešno ste kreirali profil kuvara.');
        }else if (employeeVm.employee.type == 'bartender') {
            employeeVm.employee.image = 'images/bartender.png';
            employeeService.createBartender(employeeVm.employee)
                .then(function(data){
                    console.log(data);
                });
            showToast('Uspešno ste kreirali profil šankera.');
        }else {
            console.log('Invalid employee type.')
            return;
        };
        $mdDialog.cancel();
    };

    function update() {
        employeeService.
            updateEmployee(employeeVm.employee)
                    .then(function (data) {
                        employeeVm.showToast('Uspješno ste izmijenili podatke profila.');
                        employeeVm.confirmedEdit = true;
                        employeeVm.cancel();
                    });
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            parent    : angular.element(document.querySelectorAll('#toast-box')),
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

    function sizeClicked(control){
        employeeVm.employee.dressSize = control;
    }
}
