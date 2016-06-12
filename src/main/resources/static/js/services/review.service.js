angular
    .module('isa-mrs-project')
    .factory('reviewService', reviewService);

reviewService.$inject = ['$http'];
    
function reviewService($http) {
    var service = {
        getReviews: getReviews,
        addReview:  addReview,
        getReview:  getReview
    };
    
    return service;
    
    function getReviews(restaurantId) {
        return $http.get('api/reviews/' + restaurantId)
        .then(function(response) {
            return response.data;
        });
    };
    
    function addReview(review) {
        return $http.post('api/reviews/', review)
        .then(function(response) {
            return response.data;
        });
    };
    
    function getReview(reservationId) {
        return $http.get('api/reviews/reservation/' + reservationId)
        .then(function(response) {
            return response.data;
        });
    };
}