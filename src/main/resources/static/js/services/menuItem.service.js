angular
    .module('isa-mrs-project')
    .factory('menuItemService', menuItemService);

menuItemService.$inject = ['$http'];

function menuItemService($http){
    var service = {
        getMenuItems: getMenuItems,
        getSingle: getSingle,
        updateFood: updateFood,
        deleteFood: deleteFood,
        createFood: createFood,
        getMenuItemsByRestaurant: getMenuItemsByRestaurant
    };

    return service;

    function getMenuItems(){
        return $http.get('api/menuItems')
        .then(function(response) {
            return response.data;
        });
    };

    function getSingle(id){
        return $http.get('api/food/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateFood(food, restaurant_id){
        return $http.put('/api/food/r=' + restaurant_id, food)
        .then(function (response) {
            return response.data;
        })
    };

    function deleteFood(id){

    };

    function createFood(food, restaurant_id){
        return $http.post('/api/food/r=' + restaurant_id, food)
        .then(function (response) {
            return response.data;
        })
    };

    function getMenuItemsByRestaurant(restaurant_id){
        return $http.get('api/menuItems/r=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };

}
