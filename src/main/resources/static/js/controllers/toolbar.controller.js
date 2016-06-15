angular
    .module('isa-mrs-project')
    .controller('ToolbarController', ToolbarController);

ToolbarController.$inject = ['$rootScope', '$location', 'loginService', 'restaurantService'];


function ToolbarController($rootScope, $location, loginService, restaurantService) {
    this.isOpen = false;
    this.selectedMode = 'md-scale';
    this.selectedDirection = 'left';
    
    // Search parameters - query and result
    this.restaurantQuery = "";
    this.restaurantQueryResult = [];
    // Redirect to clicked restaurant
    this.toRestaurant = toRestaurant;
    // Do search
    this.restaurantSearch = restaurantSearch;

    this.logout = logout;
    function logout() {
        loginService.logout();
    };

    this.profile = profile;
    function profile() {
        loginService.redirectProfile();
    };
    
    // Redirect to restoraunt profile with this ID
    function toRestaurant(id) {
        $location.path('profile-restaurant/' + id);
    };
    
    function restaurantSearch() {
        if (this.restaurantQuery != '') {
            return restaurantService.getSearchRestaurants(this.restaurantQuery)
              .then(function (response) {
                  this.restaurantQueryResult = response.data;
                  
                  var index = 0;
                  for (; index < this.restaurantQueryResult.length; ++index) {
                      console.log(this.restaurantQueryResult[index].name);
                  }
                  console.log("---------------------------------------------");
              });
        }
        else {
            this.restaurantQueryResult = [];
        }
    };

}