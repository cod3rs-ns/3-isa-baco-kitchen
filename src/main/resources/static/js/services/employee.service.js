angular
    .module('isa-mrs-project')
    .factory('employeeService', employeeService);

employeeService.$inject = ['$http'];

function employeeService($http) {
    var service = {
        getEmployee: getEmployee,
        updateEmployee: updateEmployee,
        getSchedule: getSchedule,
        createCook: createCook,
        createWaiter: createWaiter,
        createBartender: createBartender,
        getEmployees: getEmployees
    };

    return service;

    function getEmployee(id) {
        return $http.get('api/employees/' + id)
            .then(function(response){
                return response.data;
            });
    };

    function getEmployees() {
        return $http.get('api/employees')
            .then(function(response){
                return response.data;
            });
    };

    function updateEmployee(emp) {
        return $http.put('/api/employee', emp)
            .then(function (response) {
                return response.data;
            });
    };

    function getSchedule() {
        return $http.get('api/employee/schedule')
            .then(function(response) {
                return response.data;
            });
    };

    function createBartender(bartender) {
        return $http.post('api/bartender', bartender)
            .then(function(response) {
                return response.data;
            });
    };

    function createWaiter(waiter) {
        return $http.post('api/waiter', waiter)
            .then(function(response) {
                return response.data;
            });
    };

    function createCook(cook) {
        return $http.post('api/cook', cook)
            .then(function(response) {
                return response.data;
            });
    };
}
