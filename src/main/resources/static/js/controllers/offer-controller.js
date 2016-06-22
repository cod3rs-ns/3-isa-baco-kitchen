angular
    .module('isa-mrs-project')
    .controller('OfferController', OfferController);

OfferController.$inject = ['offerRequestService', 'restaurantManagerService', '$mdToast', '$scope', '$mdDialog'];

function OfferController(offerRequestService, restaurantManagerService, $mdToast, $scope, $mdDialog) {
    var offerVm = this;
    offerVm.activeOffer = {};
    offerVm.offers = [];
    offerVm.initNewOffer = initNewOffer;
    offerVm.createNewOffer = createNewOffer;
    offerVm.minDate = new Date();

    activate();

    function activate() {
        restaurantManagerService.getLoggedRestaurantManager()
            .then(function(data) {
                offerVm.rmanager = data;
                getOffers()
                    .then(function() {
                        console.log("Offers retrieved.");
                    });
                offerVm.initNewOffer();
            });
    };

    function getOffers() {
        // TODO get real restaurant
        return offerRequestService.getOfferRequestsByRestaurant(offerVm.rmanager.restaurant.restaurantId)
            .then(function(data) {
                offerVm.offers = data;
            });
    };

    function initNewOffer() {
        offerVm.activeOffer = {
            offerId: null,
            offer: null,
            deadline: null,
            restaurantId: 2,
            status: 'active',
            acceptedResponse: null
        };
    };

    function createNewOffer() {
        offerRequestService.createOfferRequest(offerVm.activeOffer)
            .then(function(data) {
                offerVm.offers.push(data);
                showToast('Porudžbina je uspešno plasirana.');
                initNewOffer();
                $scope.offerForm.$setUntouched();
            })
    };

    function showToast(toast_message) {
        $mdToast.show({
            hideDelay : 3000,
            position  : 'bottom right',
            template  : '<md-toast><strong>' + toast_message + '<strong> </md-toast>'
        });
    };

    offerVm.showPickDialog = showPickDialog;
    // Implement functions later
    function showPickDialog(offer) {
        $mdDialog.show({
          controller: 'SingleOfferController',
          controllerAs: 'singleOfferVm',
          templateUrl: '/views/dialogs/accept-response-tmpl.html',
          parent: angular.element(document.body),
          //targetEvent: ev,
          clickOutsideToClose:true,
          fullscreen: false,
          locals: {
              offer : offer
          }
      });
  };
}
