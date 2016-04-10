angular
    .module('isa-mrs-project')
    .service('loginService', loginService);
    
loginService.$inject = ['$http', '$location'];

function loginService($http, $location) {
  var service = {
      login: login,
      redirect: redirect
  };
  return service;
  
  function login(username, password) {
    return $http.post('api/authenticate?username=' + username + '&password=' + password)
    .then(function (response) {
        return response.data.token;
    });
  };
  
  function redirect(email) {
    return $http.get('api/user/' + email)
    .then(function (response) {
        loggedUser = response.data;
        if (loggedUser.email == 'sr4@real.com') {
            $location.path('profile-guest');
        }
        else {
            $location.path('profile-system-manager');
        }
    });
  };
}