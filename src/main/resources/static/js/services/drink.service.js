angular
    .module('isa-mrs-project')
    .factory('drinkService', drinkService);

drinkService.$inject = ['$http'];

function drinkService($http){
    var service = {
        getDrinks: getDrinks,
        getDrink: getDrink,
        updateDrink: updateDrink,
        deleteDrink: deleteDrink,
        createDrink: createDrink,
        getDrinksByRestaurant: getDrinksByRestaurant
    };

    return service;

    function getDrinks(){
        return $http.get('api/drinks')
        .then(function(response) {
            return response.data;
        });
    };

    function getDrink(id){
        return $http.get('api/drinks/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateDrink(drink, restaurant_id){
        return $http.put('/api/drinks/r=' + restaurant_id, drink)
        .then(function (response) {
            return response.data;
        })
    };

    function deleteDrink(id){

    };

    function createDrink(drink, restaurant_id){
        return $http.post('/api/drinks/r=' + restaurant_id, drink)
        .then(function (response) {
            return response.data;
        })
    };

    function getDrinksByRestaurant(restaurant_id){
        return $http.get('api/drinks/r=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };
}
