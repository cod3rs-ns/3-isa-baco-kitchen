angular
    .module('isa-mrs-project')
    .controller('ProviderProfileController', ProviderProfileController);

function ProviderProfileController() {
    var providerProfileVm = this;

    // Set bindable memebers at the top of the controller
    providerProfileVm.name = 'Rober Downey Jr';
    providerProfileVm.image = 'images/profile-provider.png';
    providerProfileVm.email = 'head@stark.com';
    providerProfileVm.items = [
      {
        face : 'images/offer.svg',
        what: 'Brunch this weekend?',
        who: 'Generalna nabavka - JUN',
        when: '28. maj  2016',
        notes: "Potrebno toga i toga, toliko i toliko..."
      },
      {
        face : 'images/offer.svg',
        what: 'Brunch this weekend?',
        who: 'Nabavka pića - JUL',
        when: '28. jun  2016',
        notes: "Potrebno toga i toga, toliko i toliko..."
      },
      {
        face : 'images/offer.svg',
        what: 'Brunch this weekend?',
        who: 'Hrana za proslavu',
        when: '15. septembar  2016',
        notes: "Potrebno toga i toga, toliko i toliko..."
      },
    ];
    providerProfileVm.foo = foo;

    // Implement functions later
    function foo() {

    }
}
