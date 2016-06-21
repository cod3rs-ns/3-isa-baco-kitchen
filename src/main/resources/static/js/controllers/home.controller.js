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
    
    activate();
    
    function showDetails(id) {
        $location.path('profile-restaurant/' + id);
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