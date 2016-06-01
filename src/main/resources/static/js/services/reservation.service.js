angular
    .module('isa-mrs-project')
    .factory('reservationService', reservationService);

reservationService.$inject = ['$http'];

function reservationService($http) {
    var service = {
        addReservation: addReservation,
        removeReservation: removeReservation,
        inviteFriend: inviteFriend,
        saveTables: saveTables,
        getInvite: getInvite
    };

    return service;

    function addReservation(reservation) {
        return $http.post('api/reservation', reservation)
        .then(function(response) {
            return response.data;
        });
    };
    
    function removeReservation(reservationId) {
        return $http.delete('api/reservation/' + reservationId)
        .then(function(response) {
            return response.data;
        });
    };
    
    function inviteFriend(reservationId, friendEmail) {
        return $http.head('api/reservation/invite?reservation=' + reservationId + '&friend=' + friendEmail)
        .then(function(response) {

        });
    };
    
    function saveTables(reservationId, tables) {
        return $http.post('/api/reservation/tables?reservation=' + reservationId, tables)
        .then(function(response) {
            return response.data;
        });
    };
    
    function getInvite(reservationId) {
        return $http.get('/api/reservation/invited/' + reservationId)
        .then(function(response) {
            return response.data;
        });
    }
}