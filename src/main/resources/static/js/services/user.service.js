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
        .then(function(response) {
            return response.data;
        });
    };

    function getRegisteredUser() {
        return $http.get('api/user')
            .then(function(response) {
                return response.data;
            });
    }

    
}