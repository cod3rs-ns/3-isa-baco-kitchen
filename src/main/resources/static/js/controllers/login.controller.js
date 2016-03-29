angular
    .module('isa-mrs-project')
    .controller('LoginController', LoginController);
    
// LoginController.$inject = ['$location', '$routeParams', 'common', 'dataservice'];

function LoginController(/*$location, $routeParams, common, dataservice*/) {
    // Var vm stands for ViewModel
    var loginVm = this;
    
    // Set bindable memebers at the top of the controller
    loginVm.message = 'Welcome to Login page!';
    loginVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
}