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
        getAllActiveByType: getAllActiveByType
    };
    return service;

    function getAll() {
        return $http.get('api/menu_items')
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }

    function getOne(id){
        return $http.get('api/menu_items/' + id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }

    function update(menu_item, restaurant_id) {
        return $http.put('/api/menu_items/r=' + restaurant_id, menu_item)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }

    function remove(id){
        return $http.delete('api/menu_items/' + id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }

    function create(menu_item, restaurant_id) {
        return $http.post('/api/menu_items/r=' + restaurant_id, menu_item)
            .success(function (response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }

    function getAllByRestaurant(restaurant_id){
        return $http.get('api/menu_items/r=' + restaurant_id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }

    function getAllActiveByType(type, restaurant_id){
        return $http.get('api/menu_items/r=' + restaurant_id + '/t=' + type)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    }
}
