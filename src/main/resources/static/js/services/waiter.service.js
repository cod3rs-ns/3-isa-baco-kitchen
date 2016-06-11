angular
    .module('isa-mrs-project')
    .factory('waiterService', waiterService);

waiterService.$inject = ['$http'];

function waiterService($http){
    var service = {
        getWaiter: getWaiter,
        updateWaiter: updateWaiter,
        getLoggedWaiter: getLoggedWaiter,
        getTables: getTables,
        getWorkingRegion: getWorkingRegion,
        getFinishedOrders: getFinishedOrders,
        deliverOrder: deliverOrder,
        createBill: createBill,
        findBills: findBills
    };

    return service;

    function getWaiter(id){
        return $http.get('api/waiters/' + id )
            .then(function(response){
                return response.data;
            });
    };

    function updateWaiter(waiter){
        return $http.put('/api/waiter', waiter)
            .then(function (response) {
                return response.data;
            });
    };

    function getLoggedWaiter() {
        return $http.get('api/waiter')
            .then(function(response){
                return response.data;
            });
    };

    function getTables(regionId){
        return $http.get('/api/waiter/tables/region=' + regionId)
            .then(function (response) {
                return response.data;
            });
    }

    function getWorkingRegion(empId) {
        return $http.get('/api/employeeRegion/e=' + empId)
            .then(function (response) {
                return response.data;
            });
    }

    function getFinishedOrders(regId) {
        return $http.get('/api/orderItems/finished/region=' + regId)
            .then(function (response) {
                return response.data;
            });
    }

    function deliverOrder(itemId){
        return $http.put('/api/orderItems/deliver/i=' + itemId)
            .then(function (response) {
                return response.data;
            });
    }

    function createBill(tableId){
        return $http.post('/api/bills/t=' + tableId)
            .then(function (response) {
                return response.data;
            });
    }

    function findBills(){
        return $http.get('/api/waiter/bills')
            .then(function (response) {
                return response.data;
            });
    }
}
