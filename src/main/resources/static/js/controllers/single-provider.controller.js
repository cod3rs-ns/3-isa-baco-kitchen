angular
    .module('isa-mrs-project')
    .controller('SingleProviderController', SingleProviderController);

SingleProviderController.$inject = ['providerService', '$mdToast', '$mdDialog', 'edit_state', 'provider']

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
                image: 'images/provider.png',
                password: 'somepass',
                type: 'provider',
                userId: null,
                info: ''
            };
        };
    };

    function showToast(message) {
        $mdToast.show({
          hideDelay : 3000,
          position  : 'top right',
          template  : '<md-toast><strong> ' + message + '<strong> </md-toast>'
        });
    };

    function saveProvider() {
        if (edit_state) {
            providerService.updateProvider(providerVm.provider)
                .then(function() {
                    providerVm.editState = false;
                    providerVm.cancel();
                    providerVm.showToast("Izmene profila uspešno sačuvane.")
                });
        }else {
            providerService.createProvider(providerVm.provider)
                .then(function() {
                    providerVm.editState = false;
                    providerVm.cancel();
                    providerVm.showToast("Provajder uspešno registrovan.")
                });
        }
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
