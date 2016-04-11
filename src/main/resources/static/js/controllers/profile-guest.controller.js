angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);
    
GuestProfileController.$inject = ['guestService'];

function GuestProfileController(guestService) {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.showSearch = false;
    guestProfileVm.user = {};
    guestProfileVm.foo = foo;

    activate();

    // Implement functions later
    function foo() {

    }
    
    function activate() {
        return getUser().then(function() {
            console.log('User loaded.');
        });
    }

    function getUser() {
        return guestService.getGuest().then(function(data) {
            guestProfileVm.user = data;
            return guestProfileVm.user;
        });
    }
}
