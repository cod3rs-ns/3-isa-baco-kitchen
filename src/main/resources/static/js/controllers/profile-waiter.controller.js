angular
    .module('isa-mrs-project')
    .controller('WaiterProfileController', WaiterProfileController);

function WaiterProfileController() {
    var waiterProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    waiterProfileVm.name = 'Sergio dr Ramos ';
    waiterProfileVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
}