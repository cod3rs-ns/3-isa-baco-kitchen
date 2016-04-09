angular
    .module('isa-mrs-project')
    .controller('AddProviderController', AddProviderController);

function AddProviderController($mdDialog) {
    // Var vm stands for ViewModel
    var addProviderVm = this;

    addProviderVm.addingProvider = {
        f_name: '',
        l_name: '',
        email: '',
        img: '',
        pass: '',
        id: null
    };

    addProviderVm.cancel = cancel;
    function cancel() {
        $mdDialog.cancel();
    };

    addProviderVm.preview = preview;
    function preview() {
        alert(angular.toJson(addProviderVm.addingProvider, true));
    };
}