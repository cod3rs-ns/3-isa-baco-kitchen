angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);

function GuestProfileController() {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.name = 'Sergio dr Ramos ';
    guestProfileVm.foo = foo;

    // Implement functions later
    function foo() {

    }
}
