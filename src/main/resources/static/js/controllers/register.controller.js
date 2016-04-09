angular
    .module('isa-mrs-project')
    .controller('RegisterController', RegisterController);
    
function RegisterController() {
    // Var vm stands for ViewModel
    var registerVm = this;
    
    // Set bindable memebers at the top of the controller
    registerVm.user = {};
    registerVm.registerGuest = registerGuest;
    
    // Implement functions later
    function registerGuest() {
        alert(registerVm.user);
    }
}