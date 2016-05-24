angular
    .module('isa-mrs-project')
    .controller('SingleResponseController', SingleResponseController);

SingleResponseController.$inject = ['providerResponseService', '$mdDialog','$mdToast', 'to_edit', 'offer', 'provider'];

function SingleResponseController(providerResponseService, $mdDialog, $mdToast, to_edit, offer, provider) {
    var responseVm = this;
    responseVm.response = to_edit;
    responseVm.backup = {};
    responseVm.editState = false;
    responseVm.showToast = showToast;
    responseVm.cancel = cancel;
    responseVm.updateResponse = updateResponse;
    responseVm.sendResponse = sendResponse;
    responseVm.confirmedEdit = false;

    initState();

    function initState() {
        if (to_edit == null) {
            responseVm.response = {
                responseId: null,
                price: null,
                info: null,
                status: 'sent',
                provider: provider,
                offer: offer
            }

        } else {
            // copy reference
            responseVm.response = to_edit;
            // copy DATA for backup
            responseVm.backup = angular.copy(to_edit);
            // Set state to EDIT
            responseVm.editState = true;
        }
    };

    function sendResponse() {
        providerResponseService.createProviderResponse(responseVm.response)
            .then(function(data) {
                console.log(data);
                responseVm.showToast('Ponuda je uspešno poslata.');
                responseVm.cancel();
            })
    };

    function updateResponse() {
        providerResponseService.updateProviderResponse(responseVm.response)
            .then(function(data){
                responseVm.confirmedEdit = true;
                responseVm.showToast('Ponuda je uspešno ažurirana.');
                responseVm.cancel();
            });
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'top right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    function cancel() {
        // revert changes if EDIT has been cancelled
        if (!responseVm.confirmedEdit){
            responseVm.response.price = responseVm.backup.price;
            responseVm.response.info = responseVm.backup.info;
        }
        $mdDialog.cancel();
    };
}
