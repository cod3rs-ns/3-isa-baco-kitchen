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
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
    
    function addReview(review) {
        return $http.post('api/reviews/', review)
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
    
    function getReview(reservationId) {
        return $http.get('api/reviews/reservation/' + reservationId)
          .success(function(response) {
              return response;
          })
          .error(function(response) {
              return response;
          });
    };
}