angular
    .module('isa-mrs-project')
    .factory('offerRequestService', offerRequestService);

offerRequestService.$inject = ['$http'];

function offerRequestService($http) {
    var service = {
        getOfferRequests: getOfferRequests,
        getOfferRequest: getOfferRequest,
        updateOfferRequest: updateOfferRequest,
        deleteOfferRequest: deleteOfferRequest,
        createOfferRequest: createOfferRequest,
        getOfferRequestsByRestaurant: getOfferRequestsByRestaurant,
        acceptProviderResponse: acceptProviderResponse,
        rejectProviderResponse: rejectProviderResponse
    };
    return service;

    function getOfferRequests() {
        return $http.get('api/offers')
        .then(function(response) {
            return response.data;
        });
    };

    function getOfferRequest(id) {
        return $http.get('api/offers/' + id)
        .then(function(response){
            return response.data;
        });
    };

    function updateOfferRequest(offer) {
        return $http.put('/api/offers', offer)
        .then(function (response) {
            return response.data;
        });
    };

    function deleteOfferRequest(id) {
        return $http.delete('/api/offers/' + id)
        .then(function (response) {
            return response.data;
        });
    };

    function createOfferRequest(offer){
        return $http.post('/api/offers', offer)
        .then(function (response) {
            return response.data;
        });
    };

    function getOfferRequestsByRestaurant(restaurant_id){
        return $http.get('api/offers/r=' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };

    function acceptProviderResponse(offer_id, response_id) {
        return $http.get('api/offers_a/offer=' + offer_id +'&accept=' + response_id)
        .then(function(response) {
            return response.data;
        });
    };

    function rejectProviderResponse(offer_id, response_id) {
        return $http.get('api/offers_n/offer=' + offer_id +'&reject=' + response_id)
        .then(function(response) {
            return response.data;
        });
    };
}
