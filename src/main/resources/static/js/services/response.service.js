angular
    .module('isa-mrs-project')
    .factory('providerResponseService', providerResponseService);

providerResponseService.$inject = ['$http'];

function providerResponseService($http) {
    var service = {
        getProviderResponses: getProviderResponses,
        getProviderResponse: getProviderResponse,
        updateProviderResponse: updateProviderResponse,
        deleteProviderResponse: deleteProviderResponse,
        createProviderResponse: createProviderResponse,
        findByOffer: findByOffer,
        findByProvider: findByProvider
    };
    return service;

    function getProviderResponses() {
        return $http.get('api/provider_responses')
        .then(function(response) {
            return response.data;
        });
    };

    function getProviderResponse(id) {
        return $http.get('api/provider_responses/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateProviderResponse(offer) {
        return $http.put('/api/provider_responses', offer)
        .then(function (response) {
            return response.data;
        });
    };

    function deleteProviderResponse(id) {
        return $http.delete('/api/provider_responses/' + id)
        .then(function (response) {
            return response.data;
        });
    };

    function createProviderResponse(provider_response){
        return $http.post('/api/provider_responses', provider_response)
        .then(function (response) {
            return response.data;
        });
    };

    function findByOffer(offer_id){
        return $http.get('api/provider_responses/o=' + offer_id)
        .then(function(response) {
            return response.data;
        });
    };

    function findByProvider(provider_id){
        return $http.get('api/provider_responses/p=' + provider_id)
        .then(function(response) {
            return response.data;
        });
    };

}
