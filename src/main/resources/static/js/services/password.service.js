angular
    .module('isa-mrs-project')
    .service('passService', passService);

passService.$inject = ['$http'];

function passService($http) {
    var service = {
        checkPass: checkPass,
        changePass: changePass,
        isPasswordChanged : isPasswordChanged
    };
    return service;

    function checkPass(password) {
        return $http.get('api/users/oldPassword/' + password)
            .then(function (response) {
                return response.data;
            });
    };

    function changePass(password){
        return $http.put('/api/users/pass', password)
            .then(function (response) {
                return response.data;
            });
    };
    
    function isPasswordChanged(){
        return $http.get('/api/users/passChanged/')
            .then(function (response) {
                return response.data;
            });
    }
}
