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
        getTablesByRegion: getTablesByRegion,
        getNextId: getNextId,
        getFreeTables: getFreeTables
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

    function getNextId(){
        return $http.get('api/tables/next')
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
        return $http.delete('/api/tables/' +id)
        .then(function (response) {
            return response.data;
        })
    };

    function createTable(table, region_id){
        return $http.post('/api/tables/r=' + region_id, table)
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
    
    function getFreeTables(restaurant_id, datetime, length) {
        return $http.get('api/reservation/free?id=' + restaurant_id + '&dt=' + datetime + '&len=' + length)
        .then(function(response) {
            return response.data;
        });
    }

}
