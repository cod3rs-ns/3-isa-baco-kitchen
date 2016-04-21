angular
    .module('isa-mrs-project')
    .factory('tableService', tableService);

tableService.$inject = ['$http'];

function tableService($http){
    var service = {
        getTables: getTables,
        getTable: getTable,
        updateTable: updateTable,
        deleteTable: deleteTable,
        createTable: createTable,
        getTablesByRestaurant: getTablesByRestaurant,
        getTablesByRegion: getTablesByRegion
    };

    return service;

    function getTables(){
        return $http.get('api/tables')
        .then(function(response) {
            return response.data;
        });
    };

    function getTable(id){
        return $http.get('api/tables/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateTable(table, region_id){
        return $http.put('/api/tables/r=' + region_id, table)
        .then(function (response) {
            return response.data;
        })
    };

    function deleteTable(id){

    };

    function createTable(table, region_id){
        return $http.post('/api/tables/r=' + region_id, region_id)
        .then(function (response) {
            return response.data;
        })
    };

    function getTablesByRestaurant(restaurant_id){
        return $http.get('api/tables/r=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };

    function getTablesByRegion(region_id){
    };

}
