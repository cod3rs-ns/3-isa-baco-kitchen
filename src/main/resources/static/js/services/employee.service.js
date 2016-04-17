angular
    .module('isa-mrs-project')
    .factory('employeeService', employeeService);

employeeService.$inject = ['$http'];

function employeeService($http){
    var service = {
        getEmployee: getEmployee,
        updateEmployee: updateEmployee
    };

    return service;

    function getEmployee(id){
        return $http.get('api/employees/' + id )
            .then(function(response){
                return response.data;
            });
    };

    function updateEmployee(emp){
        return $http.put('/api/employee', emp)
            .then(function (response) {
                return response.data;
            })
    };
}
