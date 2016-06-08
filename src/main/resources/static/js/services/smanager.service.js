angular
    .module('isa-mrs-project')
    .factory('systemManagerService', systemManagerService);

systemManagerService.$inject = ['$http'];

function systemManagerService($http){
    var service = {
        getLoggedSM: getLoggedSM,
        getRestaurants: getRestaurants
    };

    return service;

    function getLoggedSM(){
        return $http.get('api/smanager')
            .success(function(data){
                return data;
            })
            .error(function(data) {
                console.log(data);
                console.log("Whaaat");
            });
    };

    function getRestaurants(id){
        return $http.get('api/system_manager/' + id + '/restaurants')
            .then(function(response){
                return response.data;
            });
    };

}
