angular
    .module('isa-mrs-project')
    .controller('HomeController', HomeController);
    
HomeController.$inject = ['$location', '$mdDialog', 'restaurantService'];

function HomeController($location, $mdDialog, restaurantService) {
    var homeVm = this;
    
    // List of restaurants
    homeVm.restaurants = [];
    
    // Click on button 'Pogledaj detalje'
    homeVm.showDetails = showDetails;
    
    // Clickl on button 'Rezervisi'
    homeVm.showReservationDialog = showReservationDialog;
    
    activate();
    
    function showDetails(id) {
        $location.path('profile-restaurant/' + id);
    };
    
    function showReservationDialog() {
      $mdDialog.show({
          controller: 'RestaurantProfileController',
          controllerAs: 'restaurantVm',
          templateUrl: '/views/dialogs/reservation-form-tmpl.html',
          parent: angular.element(document.body),
          clickOutsideToClose: true,
          fullscreen: false
      });
    };
    
    function activate() {
        getRestaurants();
    };
    
    function getRestaurants() {
        return restaurantService.getRestaurants()
          .then(function(data) {
              homeVm.restaurants = data;
              return homeVm.restaurants;
        });
    };
}