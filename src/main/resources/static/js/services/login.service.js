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
        $location.path('/');
    });
  };

   function logout() {
       $rootScope.show = false;
       $window.localStorage.setItem('AUTH_TOKEN', null);
       $http.defaults.headers.common.Authorization = '';
       $location.path('login');
   };

}