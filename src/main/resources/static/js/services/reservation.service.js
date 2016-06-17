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
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
    
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
    
    function saveTables(reservationId, tables) {
        return $http.post('/api/reservation/tables?reservation=' + reservationId, tables)
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
}