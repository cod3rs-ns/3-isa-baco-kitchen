angular
    .module('isa-mrs-project')
    .service('orderService', orderService);

orderService.$inject = ['$http'];

function orderService($http) {
    var service = {
        addOrder: addOrder,
        getOrders: getOrders,
        getOrder: getOrder,
        updateOrder : updateOrder
    };
    return service;

    function addOrder(order, table_id, r_id){
        return $http.post('api/orders/' + r_id + '/' +table_id, order)
            .then(function(response){
                return response.data;
            });
    };

    function updateOrder(order, r_id){
        return $http.put('api/orders/' + r_id, order)
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

    function getOrder(orderId){
        return $http.get('api/orderItems/' + orderId)
            .then(function (response) {
                return response.data;
            });
    }
}

