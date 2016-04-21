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

    userVm.showToast= showToast;
    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    userVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };
    
    userVm.confirm = confirm;
    function confirm() {
        passService.changePass(userVm.newPassword)
            .then(function(data) {
                if (data == true)
                    userVm.showToast("Uspiješno zamijenjena šifra");
                    userVm.cancel();
            });
    };
    
    
}