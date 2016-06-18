angular
    .module('isa-mrs-project')
    .factory('reservationService', reservationService);

reservationService.$inject = ['$http'];

function reservationService($http) {
    var service = {
        removeReservation: removeReservation,
        inviteFriend: inviteFriend,
        getInvite: getInvite,
        saveReservationWithTables: saveReservationWithTables
    };

    return service;
    
    function removeReservation(reservationId) {
        return $http.delete('api/reservation/' + reservationId)
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
    
    function inviteFriend(reservationId, friendEmail) {
        return $http.head('api/reservation/invite?reservation=' + reservationId + '&friend=' + friendEmail)
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
    
    function getInvite(reservationId) {
        return $http.get('/api/reservation/invited/' + reservationId)
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
    
    function saveReservationWithTables(reservation) {
        return $http.post('api/reservation', reservation)
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
}