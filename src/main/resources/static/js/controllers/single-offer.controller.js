angular
    .module('isa-mrs-project')
    .controller('SingleOfferController', SingleOfferController);

SingleOfferController.$inject = ['offerRequestService', 'providerResponseService', '$mdDialog', '$mdToast', 'offer'];

function SingleOfferController(offerRequestService, providerResponseService, $mdDialog, $mdToast, offer) {
    var singleOfferVm = this;
    singleOfferVm.offer = offer;
    singleOfferVm.finished = offer.status == 'closed' ? true : false;
    singleOfferVm.responses = [];

    singleOfferVm.cancel = cancel;
    singleOfferVm.acceptResponse = acceptResponse;
    singleOfferVm.rejectResponse = rejectResponse;
    singleOfferVm.withdrawOffer = withdrawOffer;

    activate();

    function activate() {
        getProviderResponses();
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
                showToast("Ponuda je prihvaćena.");
            });
            singleOfferVm.offer.acceptedResponse = response_id;
            singleOfferVm.offer.status = 'closed';
            singleOfferVm.responses = getProviderResponses();
            singleOfferVm.finished = true;
    };

    function rejectResponse(response_id) {
        offerRequestService.rejectProviderResponse(singleOfferVm.offer.offerId, response_id)
            .then(function(data) {
                singleOfferVm.offer = data;
            });
    };

    function withdrawOffer() {
        var confirm = $mdDialog.confirm()
            .title('Potvrda povlačenja')
            .textContent('Da li ste sigurni da želite da poništite porudžbinu?')
            .ariaLabel('Response drawback')
            .ok('Da')
            .cancel('Ne');

            $mdDialog.show(confirm)
                .then(function() {
                    for (var i = 0; i < singleOfferVm.responses.length; i++) {
                        singleOfferVm.rejectResponse(singleOfferVm.responses[i].responseId);
                        singleOfferVm.responses[i].status = 'rejected';
                    };
                    singleOfferVm.offer.status = 'closed';
                    offerRequestService.updateOfferRequest(singleOfferVm.offer).
                        then(function(data){
                            singleOfferVm.finished = true;
                        });
                    showToast('Porudžbina je poništena.')
                }, function() {
                    $mdDialog.hide();
                });
    };
}
