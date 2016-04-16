angular
    .module('isa-mrs-project')
    .factory('cookService', cookService);

cookService.$inject = ['$http'];

function cookService($http){
    var service = {
        getCook: getCook,
        updateCook: updateCook
    };

    return service;

    function getCook(id){
        return $http.get('api/cooks/' + id )
            .then(function(response){
                return response.data;
            });
    };

    function updateCook(cook){
        return $http.put('/api/cook', cook)
            .then(function (response) {
                return response.data;
            })
    };


}
