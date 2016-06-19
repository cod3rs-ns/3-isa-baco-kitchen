angular
    .module('isa-mrs-project')
    .controller('LoginController', LoginController);
    
LoginController.$inject = ['$http', '$window', '$rootScope', 'loginService'];

function LoginController($http, $window, $rootScope, loginService) {
    var loginVm = this;
    
    // Username and password
    loginVm.credentials = {};
    // Is login invalid
    loginVm.invalid = false;
    
    // Click on button 'Prijavi se'
    loginVm.login = login;
    
    // Implement functions later
    function login() {
        loginService.login(loginVm.credentials.email, loginVm.credentials.password)
            .then(function(response) {
                var token = response.data.token;
                
                if (token !== undefined) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + token;
                    $window.localStorage.setItem('AUTH_TOKEN', token);
                    $rootScope.show = true;
                    loginService.redirect();
                }
                else {
                    loginVm.invalid = true;
                    loginVm.credentials.password = '';
                }
            });
    };
}