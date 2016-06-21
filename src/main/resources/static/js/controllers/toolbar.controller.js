angular
    .module('isa-mrs-project')
    .controller('ToolbarController', ToolbarController);

ToolbarController.$inject = ['$scope', '$location', '$window', 'loginService', 'restaurantService'];


function ToolbarController($scope, $location, $window, loginService, restaurantService) {
    
    var bacoSlusajMeVm = this;
  
    bacoSlusajMeVm.isOpen = false;
    bacoSlusajMeVm.selectedMode = 'md-scale';
    bacoSlusajMeVm.selectedDirection = 'left';
    
    // Search parameters - query and result
    bacoSlusajMeVm.restaurantQuery = "";
    bacoSlusajMeVm.restaurantQueryResult = [];
    // Redirect to clicked restaurant
    bacoSlusajMeVm.toRestaurant = toRestaurant;
    // Do search
    bacoSlusajMeVm.restaurantSearch = restaurantSearch;

    bacoSlusajMeVm.logout = logout;
    function logout() {
        loginService.logout();
    };

    bacoSlusajMeVm.profile = profile;
    function profile() {
        loginService.redirectProfile();
    };
    
    // Redirect to restoraunt profile with this ID
    function toRestaurant(id) {
        bacoSlusajMeVm.restaurantQuery = "";
        bacoSlusajMeVm.restaurantQueryResult = [];
        $location.path('profile-restaurant/' + id);
    };
    
    function restaurantSearch() {
        if (bacoSlusajMeVm.restaurantQuery != '') {
            return restaurantService.getSearchResult(bacoSlusajMeVm.restaurantQuery)
              .then(function (response) {
                  bacoSlusajMeVm.restaurantQueryResult = response.data;
                  return bacoSlusajMeVm.restaurantQueryResult;
              });
        }
        else {
            bacoSlusajMeVm.restaurantQueryResult = [];
        }
    };


    var token = $window.localStorage.getItem('AUTH_TOKEN');
    if (token === null || token === "null"){
        angular.element(document.querySelectorAll('#loginButtons')).removeClass("hide");
        angular.element(document.querySelectorAll('#circleButtons')).addClass("hide");
    }
    else{
        angular.element(document.querySelectorAll('#loginButtons')).addClass("hide");
        angular.element(document.querySelectorAll('#circleButtons')).removeClass("hide");
    }
}