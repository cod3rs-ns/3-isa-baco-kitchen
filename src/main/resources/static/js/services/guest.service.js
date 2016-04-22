angular
    .module('isa-mrs-project')
    .factory('guestService', guestService);

guestService.$inject = ['$http'];

function guestService($http) {
    var service = {
        getGuest: getGuest,
        updateGuest: updateGuest,
        getRequests: getRequests
    };

    return service;

    function getGuest() {
        return $http.get('api/user')
        .then(function(response) {
            return response.data;
        });
    };
    
    function updateGuest(user) {
        return $http.put('api/user/update', user)
        .then(function(response) {
            return response.data;
        });
    };
    
    function getRequests() {
        return $http.get('api/guest/requests')
        .then(function(response) {
            return response.data;
        });
    };
}