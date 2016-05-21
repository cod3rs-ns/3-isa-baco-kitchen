angular
    .module('isa-mrs-project')
    .factory('reservationService', reservationService);

reservationService.$inject = ['$http'];

function reservationService($http) {
    var service = {
        addReservation: addReservation,
        removeReservation: removeReservation
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
}