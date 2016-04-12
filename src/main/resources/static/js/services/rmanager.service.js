angular
    .module('isa-mrs-project')
    .factory('restaurantManagerService', restaurantManagerService);

restaurantManagerService.$inject = ['$http'];

function restaurantManagerService($http){
    var service = {
        getRestaurantManagers: getRestaurantManagers,
        getRestaurantManager: getRestaurantManager,
        updateRestaurantManager: updateRestaurantManager,
        deleteRestaurantManager: deleteRestaurantManager,
        createRestaurantManager: createRestaurantManager
    };

    return service;

    function getRestaurantManagers(){
        return $http.get('api/rmanagers')
        .then(function(response) {
            return response.data;
        });
    };

    function getRestaurantManager(id){
        return $http.get('api/rmanagers/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateRestaurantManager(restaurant){

    };

    function deleteRestaurantManager(id){

    };

    function createRestaurantManager(){

    };

}
