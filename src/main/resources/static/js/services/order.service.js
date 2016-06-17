angular
    .module('isa-mrs-project')
    .service('orderService', orderService);

orderService.$inject = ['$http'];

function orderService($http) {
    var service = {
        addOrder: addOrder,
        getOrders: getOrders,
        getOrder: getOrder,
        updateOrder : updateOrder,
        canEdit: canEdit,
        setWaiterId : setWaiterId,
        getOrdersFromReservation: getOrdersFromReservation,
        changeStatus: changeStatus
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

    function canEdit(orderId) {
        return $http.get('api/order/canEdit/' + orderId)
            .then(function (response) {
                return response.data;
            });
    }

    function setWaiterId(orderId){
        return $http.put('/api/orders/setWaiter/' + orderId)
            .then(function (response) {
                return response.data;
            });
    }

    function getOrdersFromReservation(tableId){
        return $http.get('api/orders/reserved/' + tableId)
            .then(function (response) {
                return response.data;
            });
    };


    function changeStatus(orderId){
        return $http.put('/api/orders/changeStatus/' + orderId)
            .then(function (response) {
                return response.data;
            });
    }
}

