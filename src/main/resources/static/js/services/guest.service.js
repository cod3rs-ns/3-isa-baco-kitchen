angular
    .module('isa-mrs-project')
    .factory('guestService', guestService);

guestService.$inject = ['$http'];

function guestService($http) {
    var service = {
        getGuest: getGuest,
        updateGuest: updateGuest,
        getRequests: getRequests,
        getFriends: getFriends,
        accept: accept,
        reject: reject
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
    
    function getFriends() {
        return $http.get('api/guest/friends')
        .then(function(response) {
            return response.data;
        });
    };
    
    function accept(id) {
        return $http.put('api/guest/accept-friend/' + id)
        .then(function(response) {
            return response.data;
        });
    };
    
    function reject(id) {
        return $http.put('api/guest/reject-friend/' + id)
        .then(function(response) {
            return response.data;
        });
    };
}