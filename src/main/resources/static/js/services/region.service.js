angular
    .module('isa-mrs-project')
    .factory('regionService', regionService);

regionService.$inject = ['$http'];

function regionService($http){
    var service = {
        getRegions: getRegions,
        getRegion: getRegion,
        updateRegion: updateRegion,
        deleteRegion: deleteRegion,
        createRegion: createRegion,
        getRegionsByRestaurant: getRegionsByRestaurant
    };

    return service;

    function getRegions(){
        return $http.get('api/regions')
        .then(function(response) {
            return response.data;
        });
    };

    function getRegion(id){
        return $http.get('api/regions/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateRegion(region, restaurant_id){
        return $http.put('/api/regions/r=' + restaurant_id, region)
        .then(function (response) {
            return response.data;
        })
    };

    function deleteRegion(id){
        return $http.delete('/api/regions/' +id)
        .then(function (response) {
            return response.data;
        })
    };

    function createRegion(region, restaurant_id){
        return $http.post('/api/regions/r=' + restaurant_id, region)
        .then(function (response) {
            return response.data;
        })
    };

    function getRegionsByRestaurant(restaurant_id){
        return $http.get('api/regions/r=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };
}
