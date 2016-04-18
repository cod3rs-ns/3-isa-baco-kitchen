angular
    .module('isa-mrs-project')
    .factory('foodService', foodService);

foodService.$inject = ['$http'];

function foodService($http){
    var service = {
        getFood: getFood,
        getSingle: getSingle,
        updateFood: updateFood,
        deleteFood: deleteFood,
        createFood: createFood
        getFoodByRestaurant: getFoodByRestaurant
    };

    return service;

    function getFood(){
        return $http.get('api/food')
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

    function createFood(restaurant, id){
        return $http.post('/api/food/r=' + restaurant_id, food)
        .then(function (response) {
            return response.data;
        })
    };

}
