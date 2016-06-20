angular
    .module('isa-mrs-project')
    .factory('providerService', providerService);

providerService.$inject = ['$http'];

function providerService($http) {
    var service = {
        getProviders: getProviders,
        getProvider: getProvider,
        updateProvider: updateProvider,
        deleteProvider: deleteProvider,
        createProvider: createProvider,
        getLoggedProvider: getLoggedProvider
    };
    return service;

    function getProviders() {
        return $http.get('api/providers')
            .then(function(response) {
                return response.data;
            });
    };

    function getProvider(id) {
        return $http.get('api/providers/' + id)
            .then(function(response){
                return response.data;
            });
    };

    function getLoggedProvider() {
        return $http.get('api/provider')
            .then(function(response){
                return response.data;
            });
    };

    function updateProvider(provider) {
        return $http.put('/api/providers/', provider)
            .then(function (response) {
                return response.data;
            });
    };

    function deleteProvider(id) {
        return $http.delete('api/providers/' + id)
            .then(function(response){
                return response.data;
            });
    };

    function createProvider(provider) {
        return $http.post('api/providers', angular.toJson(provider, false))
            .then(function(response){
                return response.data;
            });
    };

}
