angular
    .module('isa-mrs-project')
    .factory('userService', userService);

userService.$inject = ['$http'];

function userService($http) {
    var service = {
        registerUser: registerUser,
        getRegisteredUser: getRegisteredUser
    };

    return service;

    function registerUser(user) {
        return $http.post('api/user/register', user)
          .success(function(data) {
              return data;
          })
          .error(function(data) {
              return data;
          });
    };

    function getRegisteredUser() {
        return $http.get('api/user')
          .success(function(data) {
              return data;
          })
          .error(function(data) {
              return data;
          });
    };
}