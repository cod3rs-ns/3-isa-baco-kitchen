angular
    .module('isa-mrs-project')
    .service('loginService', loginService);
    
loginService.$inject = ['$http', '$location', '$window', '$rootScope'];

function loginService($http, $location, $window, $rootScope) {

    var service = {
        login: login,
        redirect: redirect,
        logout: logout,
        redirectProfile: redirectProfile
    };
    
    return service;
  
    function login(email, password) {
        return $http.post('api/authenticate?email=' + email + '&password=' + password)
            .success(function(data) {
                return data.token;
            })
            .error(function(data) {
                return data;
            });
        };

    function redirect() {
        return $http.get('api/user')
            .success(function(data) {
                $location.path("/");
            })
            .error(function(data) {
                return data;
            });
        };

     function logout() {
         $rootScope.show = false;
         $window.localStorage.setItem('AUTH_TOKEN', null);
         $http.defaults.headers.common.Authorization = '';
         $location.path('login');
     };
     
     function redirectProfile(){
        return $http.get('api/user')
            .then(function (response) {
            switch(response.data.type) {
                case 'guest':
                    $location.path('profile-guest/' + response.data.userId);
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
    }
}