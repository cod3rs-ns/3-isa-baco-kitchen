angular
    .module('isa-mrs-project')
    .controller('ProviderProfileController', ProviderProfileController);

ProviderProfileController.$inject = ['providerService', 'passService', '$mdDialog', '$mdToast', 'offerRequestService', 'providerResponseService'];

function ProviderProfileController(providerService, passService, $mdDialog, $mdToast, offerRequestService, providerResponseService, OfferRequestController) {
    var providerVm = this;
    providerVm.provider = {};
    providerVm.editState = false;
    providerVm.backup = {};

    providerVm.saveChanges = saveChanges;
    providerVm.cancelChanges = cancelChanges;
    providerVm.initEditState = initEditState;
    providerVm.showToast = showToast;

    providerVm.changePassword = changePassword;

    providerVm.newOffers = [];
    providerVm.activeResponses = [];
    providerVm.historyResponses = [];

    activate();

    function activate() {
        // TODO retrieve active Provider
        getLoggedProvider();
        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    providerVm.changePassword(false);
                }
            });

    };

    function getLoggedProvider() {
        providerService.getLoggedProvider()
            .then(function(data) {
                providerVm.provider = data;
                getNewOffersForProvider();
                getResponses();
            });
    };

    function getResponses() {
        var responses = [];
        // TODO
        providerResponseService.findByProvider(providerVm.provider.userId)
            .then(function(data) {
                responses = data;
                for (var i = 0; i < responses.length; i++) {
                    if(responses[i].status == 'accepted' || responses[i].offer.deadline < Date.now() || responses[i].offer.acceptedResponse != null){
                        providerVm.historyResponses.push(responses[i]);
                    }else {
                        providerVm.activeResponses.push(responses[i]);
                    }
                }
            });
    };

    function getNewOffersForProvider(){
        // TODO
        return offerRequestService.getNewOffersForProvider(4)
            .then(function(data) {
                providerVm.newOffers = data;
                console.log(data);
            });
    };

    function initEditState() {
        providerVm.editState = true;
        providerVm.backup = angular.copy(providerVm.provider);
    };

    function saveChanges() {
        providerVm.backup = null;
        providerVm.editState = false;
        providerService.updateProvider(providerVm.provider)
            .then(function(){
                providerVm.showToast("Izmene profila uspešno sačuvane.")
            });
    };

    function cancelChanges() {
        providerVm.provider = providerVm.backup;
        providerVm.backup = null;
        providerVm.editState = false;
    };

    function changePassword(modal) {
        $mdDialog.show(
            {
                controller: 'ChangePasswordController',
                controllerAs: 'userVm',
                templateUrl: '/views/dialogs/change-password.html',
                parent: angular.element(document.body),
                clickOutsideToClose: modal,
                escapeToClose: modal,
                fullscreen: false,
                openFrom : angular.element(document.querySelector('#pass-option')),
                closeTo : angular.element(document.querySelector('#pass-option')),
                locals: {
                    modal : modal
                }
            }
        );
    };

    function showToast(toast_message) {
        $mdToast.show({
          hideDelay : 3000,
          position  : 'top right',
          template  : '<md-toast><strong>' + toast_message +'</md-toast>'
        });
    };

    providerVm.showDetailed = showDetailed;
    // Implement functions later
    function showDetailed(offer) {
        $mdDialog.show({
          controller: 'OfferRequestController',
          controllerAs: 'offerRequestVm',
          templateUrl: '/views/dialogs/offer-request-tmpl.html',
          parent: angular.element(document.body),
          //targetEvent: ev,
          clickOutsideToClose:true,
          fullscreen: false,
          locals: {
              offer : offer
          }
      });
  };

  providerVm.sendResponse = sendResponse;
  function sendResponse(offer_request){
      $mdDialog.show({
        controller: 'SingleResponseController',
        controllerAs: 'responseVm',
        templateUrl: '/views/dialogs/single-response-tmpl.html',
        parent: angular.element(document.body),
        //targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: false,
        locals: {
            to_edit : null,
            offer : offer_request,
            provider: providerVm.provider
        }
    })
    .finally(function() {
        getNewOffersForProvider();
    })

  }

  providerVm.editSelected = editSelected;
  function editSelected(response) {
      $mdDialog.show({
        controller: 'SingleResponseController',
        controllerAs: 'responseVm',
        templateUrl: '/views/dialogs/single-response-tmpl.html',
        parent: angular.element(document.body),
        //targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: false,
        locals: {
            to_edit : response,
            offer : response.offer,
            provider: providerVm.provider
        }
    });

  }
}
