angular
    .module('isa-mrs-project')
    .controller('RegisterController', RegisterController);
    
function RegisterController() {
    // Var vm stands for ViewModel
    var registerVm = this;
    
    // Set bindable memebers at the top of the controller
    registerVm.message = 'Welcome to Register page!';
    registerVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
}