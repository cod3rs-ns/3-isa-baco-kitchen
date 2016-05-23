angular
    .module('isa-mrs-project')
    .controller('SingleOfferController', SingleOfferController);

SingleOfferController.$inject = ['offerRequestService', 'providerResponseService', '$mdDialog', '$mdToast', 'offer'];

function SingleOfferController(offerRequestService, providerResponseService, $mdDialog, $mdToast, offer) {
    var singleOfferVm = this;
    singleOfferVm.offer = offer;
    singleOfferVm.responses = [];

    singleOfferVm.cancel = cancel;
    singleOfferVm.acceptResponse = acceptResponse;
    singleOfferVm.rejectResponse = rejectResponse;

    activate();

    function activate() {
        getProviderResponses()
            .then(function() {
                console.log('Providers responses retrieved.');
            });
    };

    function getProviderResponses() {
        return providerResponseService.findByOffer(singleOfferVm.offer.offerId)
            .then(function(data) {
                singleOfferVm.responses = data;
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
        $mdDialog.cancel();
    };

    function acceptResponse(response_id) {
        offerRequestService.acceptProviderResponse(singleOfferVm.offer.offerId, response_id)
            .then(function(data) {
                console.log(data);
                singleOfferVm.offer = data;
                singleOfferVm.responses = getProviderResponses();
                console.log("------------");
                console.log(singleOfferVm.offer);
                console.log(singleOfferVm.responses);
            });
    };

    function rejectResponse(response_id) {
        offerRequestService.rejectProviderResponse(singleOfferVm.offer.offerId, response_id)
            .then(function(data) {
                console.log(data);
                singleOfferVm.offer = data;
            });
    };
}
