angular
    .module('isa-mrs-project')
    .controller('LoginController', LoginController);
    
LoginController.$inject = ['$http', '$window', 'loginService'];

function LoginController($http, $window, loginService) {
    // Var vm stands for ViewModel
    var loginVm = this;
    
    // Set bindable memebers at the top of the controller
    loginVm.credentials = {};
    loginVm.invalid = false;
    loginVm.login = login;
    
    // Implement functions later
    function login() {
        loginService.login(loginVm.credentials.email, loginVm.credentials.password)
            .then(function(token) {
                if (token !== undefined) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + token;
                    $window.localStorage.setItem('AUTH_TOKEN', token);
                    loginService.redirect();
                }
                else {
                    loginVm.invalid = true;
                    loginVm.credentials.password = '';
                }
            });
    };
}