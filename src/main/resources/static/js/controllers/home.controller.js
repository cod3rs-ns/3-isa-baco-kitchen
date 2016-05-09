angular
    .module('isa-mrs-project')
    .controller('HomeController', HomeController);
    
HomeController.$inject = ['$location', 'restaurantService'];

function HomeController($location, restaurantService) {
    var homeVm = this;
    
    homeVm.restaurants = [];
    
    homeVm.showDetails = showDetails;
    
    activate();
    
    function showDetails(id) {
        $location.path('profile-restaurant/' + id);
    }
    
    function activate() {
        getRestaurants().then(function() {
            console.log('Restaurants loaded.');
        });
    };
    
    function getRestaurants() {
        return restaurantService.getRestaurants()
          .then(function(data) {
            homeVm.restaurants = data;
            return homeVm.restaurants;
        });
    };
}