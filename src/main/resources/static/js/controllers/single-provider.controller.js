angular
    .module('isa-mrs-project')
    .controller('SingleProviderController', SingleProviderController);

SingleProviderController.$inject = ['providerService', '$mdToast', '$mdDialog']

function SingleProviderController(providerService, $mdToast, $mdDialog) {
    // Var vm stands for ViewModel
    var providerVm = this;

    providerVm.provider = {};
    providerVm.saveProvider = saveProvider;
    providerVm.showToast = showToast;
    providerVm.cancel = cancel;
    activate();

    function activate() {
        providerVm.provider = {
            firstName: '',
            lastName: '',
            email: '',
            image: '',
            password: 'somepass',
            type: 'provider',
            userId: null
        };
    };

    function showToast() {
        $mdToast.show({
          hideDelay : 3000,
          position  : 'top right',
          template  : '<md-toast><strong> Provajder je uspešno registrovan.<strong> </md-toast>'
        });
    };

    function saveProvider() {
        providerService.createProvider(providerVm.provider)
        .then(function(data) {
            //alert('Provider saved to DB: \n' + angular.toJson(data, true));
            providerVm.cancel();
            providerVm.showToast();
        });
    };

    function cancel() {
        $mdDialog.cancel();
    };

}
