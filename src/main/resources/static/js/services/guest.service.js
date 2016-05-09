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
        reject: reject,
        isMy: isMy,
        addFriend: addFriend,
        removeFriend: removeFriend,
        isFriend: isFriend,
        getSearchResult: getSearchResult
    };

    return service;

    function getGuest(id) {
        return $http.get('api/guest/' + id)
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
    
    function getFriends(id) {
        return $http.get('api/guest/friends/' + id)
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
    
    function isMy(id) {
        return $http.get('api/guest/admin/' + id)
        .then(function(response) {
            return response.data;
        });
    };
    
    function addFriend(id) {
        return $http.post('api/guest/add-friend/' + id)
        .then(function(response) {
            return response.data;
        });
    };
    
    function removeFriend(id) {
        return $http.delete('api/guest/remove-friend/' + id)
        .then(function(response) {
            return response.data;
        });
    };
    
    function isFriend(id) {
        return $http.get('api/guest/friend/' + id)
        .then(function(response) {
            return response.data;
        });
    };
    
    function getSearchResult(query) {
        return $http.get('api/guest/users?query=' + query)
        .then(function(response) {
            return response.data;
        });
    };
}