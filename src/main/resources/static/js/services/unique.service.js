angular
    .module('isa-mrs-project')
    .service('uniqueService', uniqueService);

uniqueService.$inject = ['$http'];

function uniqueService($http) {
    var service = {
        checkUnique: checkUnique
    };
    return service;

    function checkUnique(id, property, email) {
        return $http.get('api/users/' + email)
        .then(function (response) {
            return response.data;
        });
    };
}
