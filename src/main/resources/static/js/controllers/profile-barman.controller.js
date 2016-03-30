angular
    .module('isa-mrs-project')
    .controller('BarmanProfileController', BarmanProfileController);

function BarmanProfileController() {
    var barmanProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    barmanProfileVm.name = 'Sergio dr Ramos ';
    barmanProfileVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
}