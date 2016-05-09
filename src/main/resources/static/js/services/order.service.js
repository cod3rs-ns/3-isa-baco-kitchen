angular
    .module('isa-mrs-project')
    .service('orderService', orderService);

orderService.$inject = ['$http'];

function orderService($http) {
    var service = {
        addOrder: addOrder,
        getOrders: getOrders
    };
    return service;

    function addOrder(order, table_id){
        return $http.post('api/orders/' + table_id, order)
            .then(function(response){
                return response.data;
            });
    };


    function getOrders(tableId){
        return $http.get('api/orders/' + tableId)
            .then(function (response) {
                return response.data;
            });
    };
}

