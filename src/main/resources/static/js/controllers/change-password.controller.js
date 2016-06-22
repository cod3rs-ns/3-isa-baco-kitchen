angular
    .module('isa-mrs-project')
    .controller('ChangePasswordController', ChangePasswordController);

ChangePasswordController.$inject = ['passService', '$mdDialog','$mdToast', 'modal'];

function ChangePasswordController(passService, $mdDialog, $mdToast, modal) {
    var userVm = this;
    userVm.newPassword = '';
    userVm.repeaterdPassword = '';
    userVm.oldPassword = '';
    userVm.showCancel = modal;

    //closing dialog
    userVm.cancel = cancel;
    //confirm password changes
    userVm.confirm = confirm;

    userVm.showToast= showToast;
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            parent    : angular.element(document.querySelectorAll('#toast-box')),
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function cancel() {
        $mdDialog.cancel();
    };
    
    function confirm() {
        passService.changePass(userVm.newPassword)
            .then(function(data) {
                if (data == true)
                    userVm.showToast("Uspiješno zamijenjena šifra");
                    userVm.cancel();
            });
    };
    
    
}