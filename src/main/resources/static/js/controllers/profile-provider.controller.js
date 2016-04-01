angular
    .module('isa-mrs-project')
    .controller('ProviderProfileController', ProviderProfileController);

ProviderProfileController.$inject = ['$mdDialog'];

function ProviderProfileController($mdDialog, OfferRequestController) {
    var providerProfileVm = this;

    // Test data, should be retreived from REST Service
    providerProfileVm.name = 'Rober Downey Jr';
    providerProfileVm.image = 'images/profile-provider.png';
    providerProfileVm.email = 'head@stark.com';
    providerProfileVm.items = [
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
    providerProfileVm.active = [
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
    providerProfileVm.history = [
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
    ]
    providerProfileVm.showDetailed = showDetailed;

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
