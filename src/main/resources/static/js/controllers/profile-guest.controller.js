angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);
    
GuestProfileController.$inject = ['guestService'];

function GuestProfileController(guestService) {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.showSearch = false;
    guestProfileVm.editMode = false;
    guestProfileVm.user = {};
    guestProfileVm.friendRequests = [];
    // Functions 
    guestProfileVm.editProfile = editProfile;
    guestProfileVm.saveChanges = saveChanges;
    guestProfileVm.cancel = cancel;

    activate();

    // Implement functions later
    function editProfile() {
        guestProfileVm.editMode = true;
    }
    
    function saveChanges() {
        guestService.updateGuest(guestProfileVm.user)
          .then(function (updatedUser) {
            guestProfileVm.user = updatedUser;
          });
        guestProfileVm.editMode = false;
    }
    
    function cancel() {
        guestProfileVm.editMode = false;
    }
    
    function activate() {
        return getUser().then(function() {
            console.log('User loaded.');
            return getRequests().then(function() {
                console.log('Requests loaded.');
            });
        });
    }

    function getUser() {
        return guestService.getGuest().then(function(data) {
            guestProfileVm.user = data;
            return guestProfileVm.user;
        });
    };
    
    function getRequests() {
        return guestService.getRequests().then(function(data) {
            guestProfileVm.friendRequests = data;
            return guestProfileVm.requests;
        });
    };
}
