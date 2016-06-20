angular
    .module('isa-mrs-project')
    .controller('SingleProviderController', SingleProviderController);

SingleProviderController.$inject = ['providerService', '$mdToast', '$mdDialog', 'edit_state', 'provider'];

function SingleProviderController(providerService, $mdToast, $mdDialog, edit_state, provider) {
    var providerVm = this;
    providerVm.provider = provider;
    providerVm.editState = edit_state;
    providerVm.backup = angular.copy(provider);

    providerVm.saveProvider = saveProvider;
    providerVm.showToast = showToast;
    providerVm.cancel = cancel;

    activate();

    function activate() {
        if (!edit_state) {
            providerVm.provider = {
                firstName: '',
                lastName: '',
                email: '',
                image: '../images/no_image.gif',
                password: '',
                type: 'restaurant_provider',
                userId: null,
                info: ''
            };
        };
    };


    function showToast(text, delay) {
        var toast = $mdToast.show({
          hideDelay : delay,
          position  : 'top right',
          parent    : angular.element(document.querySelectorAll('#toast-box')),
          template  : '<md-toast>' + text  + '</md-toast>'
        });
        return toast;
    };

    function saveProvider() {
        var progress = "<md-progress-linear md-mode='indeterminate'></md-progress-linear>";
        var toast = providerVm.showToast(progress, 0);
        if (edit_state) {
            providerService.updateProvider(providerVm.provider)
                .then(function() {
                    providerVm.editState = false;
                    providerVm.cancel();
                    $mdToast.hide(toast);
                    providerVm.showToast("<strong>Sve promene su uspešno sačuvane.</strong>", 3000);
                });
        }else {
            $mdDialog.cancel();
            providerService.createProvider(providerVm.provider)
                .then(function() {
                    providerVm.editState = false;
                    providerVm.cancel();
                    $mdToast.hide(toast);
                    providerVm.showToast("<strong>Provajder je uspešno registrovan.</strong>", 3000);
                });
        };
    };

    function cancel() {
        if(providerVm.editState) {
            providerVm.provider.firstName = providerVm.backup.firstName;
            providerVm.provider.lastName = providerVm.backup.lastName;
            providerVm.provider.info = providerVm.backup.info;
            providerVm.backup = null;
            providerVm.editState = false;
        };
        $mdDialog.cancel();
    };

}
