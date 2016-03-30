angular
    .module('isa-mrs-project')
    .controller('SystemManagerProfileController', SystemManagerProfileController);

function SystemManagerProfileController() {
    var systemManagerProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    systemManagerProfileVm.name = 'Sergio dr Ramos ';
    systemManagerProfileVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
}