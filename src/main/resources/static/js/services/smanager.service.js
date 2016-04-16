angular
    .module('isa-mrs-project')
    .factory('systemManagerService', systemManagerService);

systemManagerService.$inject = ['$http'];

function systemManagerService($http){
    var service = {
        getRestaurants: getRestaurants
    };

    return service;

    function getRestaurants(id){
        return $http.get('api/system_manager/' + id + '/restaurants')
            .then(function(response){
                return response.data;
            });
    };

}
