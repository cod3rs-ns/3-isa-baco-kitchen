angular
    .module('isa-mrs-project')
    .factory('waiterService', waiterService);

waiterService.$inject = ['$http'];

function waiterService($http){
    var service = {
        getWaiter: getWaiter,
        updateWaiter: updateWaiter,
        getLoggedWaiter: getLoggedWaiter,
        getTables: getTables
    };

    return service;

    function getWaiter(id){
        return $http.get('api/waiters/' + id )
            .then(function(response){
                return response.data;
            });
    };

    function updateWaiter(waiter){
        return $http.put('/api/waiter', waiter)
            .then(function (response) {
                return response.data;
            });
    };

    function getLoggedWaiter() {
        return $http.get('api/waiter')
            .then(function(response){
                return response.data;
            });
    };

    function getTables(){
        return $http.get('api/waiter/tables')
            .then(function (response) {
                return response.data;
            });
    }

}
