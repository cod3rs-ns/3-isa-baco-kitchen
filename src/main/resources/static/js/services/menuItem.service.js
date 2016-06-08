angular
    .module('isa-mrs-project')
    .factory('menuItemService', menuItemService);

menuItemService.$inject = ['$http'];

function menuItemService($http) {
    var service = {
        getAll: getAll,
        getOne: getOne,
        update: update,
        remove: remove,
        create: create,
        getAllByRestaurant: getAllByRestaurant,
        getAllByType: getAllByType
    };
    return service;

    function getAll() {
        return $http.get('api/menu_items')
        .then(function(response) {
            return response.data;
        });
    }

    function getOne(id){
        return $http.get('api/menu_items/' + id)
        .then(function(response){
            return response.data;
        });
    }

    function update(menu_item, restaurant_id) {
        return $http.put('/api/menu_items/r=' + restaurant_id, menu_item)
        .then(function (response) {
            return response.data;
        });
    }

    // TODO implement this
    function remove(id){

    }

    function create(menu_item, restaurant_id) {
        return $http.post('/api/menu_item/r=' + restaurant_id, menu_item)
        .then(function (response) {
            return response.data;
        });
    }

    function getAllByRestaurant(restaurant_id){
        return $http.get('api/menu_items/r=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    }

    function getAllByType(type, restaurant_id){
        return $http.get('api/menu_items/r=' + restaurant_id + '/t=' + type)
            .then(function(response) {
                return response.data;
            });
    }
}
