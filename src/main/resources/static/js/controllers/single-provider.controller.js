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
            image: '../images/no_image.gif',
            password: '',
            type: 'restaurant_provider',
            userId: null
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
        providerVm.cancel();
        var progress = "<md-progress-linear md-mode='indeterminate'></md-progress-linear>";
        var toast = providerVm.showToast(progress, 0);

        providerService.createProvider(providerVm.provider)
        .then(function(data) {
            $mdToast.hide(toast);
            providerVm.showToast("<strong>Provajder je uspješno registrovan.</strong>", 3000);
        });
    };

    function cancel() {
        $mdDialog.cancel();
    };

}
