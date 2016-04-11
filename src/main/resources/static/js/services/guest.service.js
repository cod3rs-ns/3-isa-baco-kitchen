angular
    .module('isa-mrs-project')
    .factory('guestService', guestService);

guestService.$inject = ['$http'];

function guestService($http) {
    var service = {
        getGuest: getGuest
    };

    return service;

    function getGuest() {
        return $http.get('api/user')
        .then(function(response) {
            return response.data;
        });
    };

}