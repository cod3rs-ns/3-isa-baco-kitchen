angular
    .module('isa-mrs-project')
    .controller('ProviderProfileController', ProviderProfileController);

ProviderProfileController.$inject = ['providerService', 'passService', '$mdDialog', '$mdToast'];

function ProviderProfileController(providerService, passService, $mdDialog, $mdToast, OfferRequestController) {
    var providerVm = this;
    providerVm.provider = {};
    providerVm.editState = false;
    providerVm.backup = {};

    providerVm.saveChanges = saveChanges;
    providerVm.cancelChanges = cancelChanges;
    providerVm.initEditState = initEditState;
    providerVm.showToast = showToast;

    providerVm.changePassword = changePassword;
    activate();

    function activate() {
        // TODO retrieve active Provider
        getLoggedProvider().then(function() {
            console.log("Provider retreived.");
        });

        passService.isPasswordChanged()
            .then(function (data) {
                if(data){
                    providerVm.changePassword(false);
                }
            });
    };

    function getLoggedProvider() {
        return providerService.getLoggedProvider()
            .then(function(data) {
                providerVm.provider = data;
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

    // TODO: Test data, should be retreived from REST Service
    providerVm.items = [
      {
        restaurant_name: 'Macchiato',
        offer_name: 'Generalna nabavka - JUN',
        due_date: '28. maj  2016',
        content: "Potrebno toga i toga, toliko i toliko..."
      },
      {
        restaurant_name: 'Macchiato',
        offer_name: 'Nabavka pića - JUL',
        due_date: '28. jun  2016',
        content: "Potrebno toga i toga, toliko i toliko..."
      },
      {
        restaurant_name: 'Macchiato',
        offer_name: 'Hrana za proslavu',
        due_date: '15. septembar  2016',
        content: "Potrebno toga i toga, toliko i toliko..."
      },
    ];
    providerVm.active = [
        {
            offer: 'Irish pub - porudžbina JUL',
            price: '15 000 RSD',
            date: '15. jul 2016'
        },
        {
            offer: 'London pub - porudžbina AVGUST',
            price: '28 000 RSD',
            date: '30. jul 2016'
        }
    ];
    providerVm.history = [
        {
            offer: 'Irish pub - porudžbina JUL',
            price: '15 000 RSD',
            date: '30. jul 2016',
            status: 'accepted'
        },
        {
            offer: 'London pub - porudžbina AVGUST',
            price: '28 000 RSD',
            date: '30. jul 2016',
            status: 'created'
        },
        {
            offer: 'London pub - porudžbina AVGUST',
            price: '28 000 RSD',
            date: '30. jul 2016',
            status: 'denied'
        }
    ];

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
    }
}
