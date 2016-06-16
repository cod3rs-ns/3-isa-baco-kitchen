angular
    .module('isa-mrs-project')
    .factory('reportService', reportService);

reportService.$inject = ['$http'];

function reportService($http){
    var service = {
        getBillsByWaiter : getBillsByWaiter
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

}
