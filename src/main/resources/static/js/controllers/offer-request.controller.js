angular
    .module('isa-mrs-project')
    .controller('OfferRequestController', OfferRequestController);

OfferRequestController.$inject = ['$mdDialog', 'restaurantService', 'offer'];

function OfferRequestController($mdDialog, restaurantService, offer) {
    var offerRequestVm = this;

    offerRequestVm.offer = offer;
    console.log(offer);
    offerRequestVm.cancel = cancel;
    offerRequestVm.restaurantName = '';

    init();

    function init() {
        restaurantService.getRestaurant(offerRequestVm.offer.restaurantId)
            .then(function(data) {
                offerRequestVm.restaurantName = data.name;
            });
    }

    function cancel() {
        $mdDialog.cancel();
    }
}
