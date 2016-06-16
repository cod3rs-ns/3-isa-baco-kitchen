angular
    .module('isa-mrs-project')
    .factory('reportService', reportService);

reportService.$inject = ['$http'];

function reportService($http){
    var service = {
        getBillsByWaiter : getBillsByWaiter,
        findReservationsByRestaurant: findReservationsByRestaurant,
        findBillsByRestaurant: findBillsByRestaurant
    };

    return service;

    function getBillsByWaiter(id, dates){
        console.log(dates);
        var path = 'api/bills/report/' + id;
        return $http.post(path, JSON.stringify(dates))
            .then(function(response){
                return response.data;
            });
    };

    function findReservationsByRestaurant(restaurant_id) {
        return $http.get('/api/reservations/' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };

    function findBillsByRestaurant(restaurant_id) {
        return $http.get('/api/bills/report/restaurant/' + restaurant_id)
        .then(function(response) {
            return response.data;
        });
    };

}
