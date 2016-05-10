angular
    .module('isa-mrs-project')
    .factory('reviewService', reviewService);

reviewService.$inject = ['$http'];
    
function reviewService($http) {
    var service = {
        getReviews: getReviews,
        addReview:  addReview
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
}