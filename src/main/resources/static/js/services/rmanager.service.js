angular
    .module('isa-mrs-project')
    .factory('restaurantManagerService', restaurantManagerService);

restaurantManagerService.$inject = ['$http'];

function restaurantManagerService($http) {
    var service = {
        getRestaurantManagers: getRestaurantManagers,
        getRestaurantManager: getRestaurantManager,
        updateRestaurantManager: updateRestaurantManager,
        deleteRestaurantManager: deleteRestaurantManager,
        createRestaurantManager: createRestaurantManager,
        getLoggedRestaurantManager: getLoggedRestaurantManager
    };

    return service;

    function getRestaurantManagers() {
        return $http.get('api/rmanagers')
        .then(function(response) {
            return response.data;
        });
    };

    function getRestaurantManager(id) {
        return $http.get('api/rmanagers/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function getLoggedRestaurantManager() {
        return $http.get('api/rmanager')
        .then(function(response){
            return response.data;
        });
    };

    function updateRestaurantManager(restaurant) {

    };

    function deleteRestaurantManager(id) {

    };

    function createRestaurantManager(manager, restaurant_id) {
        return $http.post('/api/rmanagers/' + restaurant_id, manager)
            .then(function (response) {
                return response.data;
            })
    };

}
