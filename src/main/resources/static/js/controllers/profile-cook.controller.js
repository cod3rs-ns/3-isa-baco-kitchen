angular
    .module('isa-mrs-project')
    .controller('CookProfileController', CookProfileController);

function CookProfileController() {
    var cookProfileVm = this;
    
    // Set bindable memebers at the top of the controller
    cookProfileVm.name = 'Sergio dr Ramos ';
    cookProfileVm.foo = foo;
    
    // Implement functions later
    function foo() {
        
    }
}