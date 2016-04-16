angular
    .module('isa-mrs-project')
    .factory('userService', userService);

userService.$inject = ['$http'];

function userService($http) {
    var service = {
        registerUser: registerUser
    };

    return service;

    function registerUser(user) {
        return $http.post('api/user/register', user)
        .then(function(response) {
            return response.data;
        });
    };

}