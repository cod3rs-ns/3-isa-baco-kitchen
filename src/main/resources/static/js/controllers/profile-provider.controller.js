angular
    .module('isa-mrs-project')
    .controller('ProviderProfileController', ProviderProfileController);

ProviderProfileController.$inject = ['providerService', 'passService', '$mdDialog', '$mdToast', 'offerRequestService', 'providerResponseService'];

function ProviderProfileController(providerService, passService, $mdDialog, $mdToast, offerRequestService, providerResponseService, OfferRequestController) {
    var providerVm = this;
    providerVm.provider = {};
    providerVm.newOffers = [];
    providerVm.activeResponses = [];
    providerVm.historyResponses = [];

    providerVm.editSelected = editSelected;
    providerVm.showDetailed = showDetailed;
    providerVm.sendResponse = sendResponse;
    providerVm.initEditState = initEditState;
    providerVm.changePassword = changePassword;
    providerVm.showToast = showToast;
    providerVm.uploadImage = uploadImage;
    providerVm.deleteResponse = deleteResponse;

    activate();

    function activate() {
        getLoggedProvider();
        passService.isPasswordChanged()
            .then(function (data) {
                if (!data){
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
        providerVm.historyResponses = [];
        providerVm.activeResponses = [];
        providerResponseService.findByProvider(providerVm.provider.userId)
            .then(function(data) {
                responses = data;
                for (var i = 0; i < responses.length; i++) {
                    if (responses[i].status == 'accepted' || responses[i].offer.deadline < Date.now() || responses[i].offer.acceptedResponse != null) {
                        providerVm.historyResponses.push(responses[i]);
                    }else {
                        if(responses[i].status == 'rejected' && responses[i].offer.acceptedResponse == null){
                            providerVm.historyResponses.push(responses[i]);
                        } else {
                            providerVm.activeResponses.push(responses[i]);
                        }

                    };
                };
            });
    };

    function getNewOffersForProvider() {
        return offerRequestService.getNewOffersForProvider(providerVm.provider.userId)
            .then(function(data) {
                providerVm.newOffers = data;
            });
    };

    function initEditState() {
        $mdDialog.show(
            {
                controller: 'SingleProviderController',
                controllerAs: 'providerVm',
                templateUrl: '/views/dialogs/single-provider-tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                escapeToClose: false,
                fullscreen: false,
                locals: {
                    edit_state : true,
                    provider : providerVm.provider
                }
            }
        );
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

    function uploadImage($flow) {
        $flow.opts.target = 'api/upload/users/' + providerVm.provider.userId;
        $flow.upload();
        providerVm.provider.image = '/images/users/users_' + providerVm.provider.userId + '.png';
        providerService.updateProvider(providerVm.provider)
            .then(function(data) {
                providerVm.provider = data;
            });
    }

    function showDetailed(offer) {
        $mdDialog.show({
            controller: 'OfferRequestController',
            controllerAs: 'offerRequestVm',
            templateUrl: '/views/dialogs/offer-request-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                offer : offer
            }
        });
    };

    function sendResponse(offer_request){
        $mdDialog.show(
            {
                controller: 'SingleResponseController',
                controllerAs: 'responseVm',
                templateUrl: '/views/dialogs/single-response-tmpl.html',
                parent: angular.element(document.body),
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
                getResponses();
            });
    }

    function deleteResponse(response) {
        var confirm = $mdDialog.confirm()
            .title('Potvrda povlačenja ponude')
            .textContent('Da li ste sigurni da želite da povučete ponudu?')
            .ariaLabel('Response drawback')
            .ok('Da')
            .cancel('Ne');

        $mdDialog.show(confirm)
            .then(function() {
                providerResponseService.deleteProviderResponse(response.responseId)
                    .then(function(data) {
                        if (data) {
                            providerVm.showToast('Uspešno ste povukli ponudu.');
                        } else {
                            $mdDialog.show(
                                $mdDialog.alert()
                                    .parent(angular.element(document.body))
                                    .clickOutsideToClose(true)
                                    .title('Upozorenje.')
                                    .textContent('Status porudžbine je u međuvremenu promenjen. Podaci se osvežavaju.')
                                    .ariaLabel('Upozorenje.')
                                    .ok('OK')
                                );
                        };
                        getNewOffersForProvider();
                        getResponses();
                    })
            }, function() {
                $mdDialog.hide();
            });
    }

    function editSelected(response) {
        $mdDialog.show({
            controller: 'SingleResponseController',
            controllerAs: 'responseVm',
            templateUrl: '/views/dialogs/single-response-tmpl.html',
            parent: angular.element(document.body),
            clickOutsideToClose:true,
            fullscreen: false,
            locals: {
                to_edit : response,
                offer : response.offer,
                provider: providerVm.provider
            }
        })
        .finally(function() {
            getNewOffersForProvider();
            getResponses();
        });
    }
}
