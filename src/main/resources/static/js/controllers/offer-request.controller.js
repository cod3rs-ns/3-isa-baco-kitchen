angular
    .module('isa-mrs-project')
    .controller('OfferRequestController', OfferRequestController);

function OfferRequestController($mdDialog, offer) {
    var offerRequestVm = this;

    offerRequestVm.offer = offer;
    offerRequestVm.cancel = cancel;

    function cancel() {
        $mdDialog.cancel();
    }
}
