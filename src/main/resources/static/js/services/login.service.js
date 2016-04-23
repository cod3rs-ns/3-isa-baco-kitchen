angular
    .module('isa-mrs-project')
    .service('loginService', loginService);
    
loginService.$inject = ['$http', '$location', '$window', '$rootScope'];

function loginService($http, $location, $window, $rootScope) {

    var service = {
        login: login,
        redirect: redirect,
        logout: logout
  };
  return service;
  
  function login(username, password) {
    return $http.post('api/authenticate?username=' + username + '&password=' + password)
    .then(function (response) {
        return response.data.token;
    });
  };


  function redirect() {
    return $http.get('api/user')
    .then(function (response) {
        switch(response.data.type) {
            case 'guest':
                $location.path('profile-guest');
                break;
            case 'system_manager':
                $location.path('profile-system-manager');
                break;
            case 'restaurant_provider':
                $location.path('profile-provider');
                break;
            case 'cook':
                $location.path('profile-cook');
                break;
            case 'bartender':
                $location.path('profile-barman');
                break;
            case 'waiter':
                $location.path('profile-waiter');
                break;
            case 'restaurant_manager':
                $location.path('profile-restaurant-manager');
                break;
            default:
                $location.path('login');
        }
    });
  };

   function logout() {
       $rootScope.show = false;
       $window.localStorage.setItem('AUTH_TOKEN', null);
       $http.defaults.headers.common.Authorization = '';
       $location.path('login');
   };

}