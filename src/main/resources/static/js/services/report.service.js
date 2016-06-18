angular
    .module('isa-mrs-project')
    .factory('reportService', reportService);

reportService.$inject = ['$http'];

function reportService($http){
    var service = {
        getBillsByWaiter : getBillsByWaiter,
        findReservationsByRestaurant: findReservationsByRestaurant,
        findBillsByRestaurant: findBillsByRestaurant,
        findReviewsByMenuItem: findReviewsByMenuItem,
        findReviewsByRestaurant: findReviewsByRestaurant
    };

    return service;

    function getBillsByWaiter(id, dates){
        var path = 'api/bills/report/' + id;
        return $http.post(path, JSON.stringify(dates))
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    };

    function findReservationsByRestaurant(restaurant_id) {
        return $http.get('/api/reservations/' + restaurant_id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    };

    function findBillsByRestaurant(restaurant_id) {
        return $http.get('/api/bills/report/restaurant/' + restaurant_id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    };

    function findReviewsByMenuItem(menu_item_id) {
        return $http.get('/api/reviews/report/' + menu_item_id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    };

    function findReviewsByRestaurant(restaurant_id) {
        return $http.get('/api/reviews/' + restaurant_id)
            .success(function(response) {
                return response.data;
            })
            .error(function(response) {
                return response.data;
            });
    };

}
