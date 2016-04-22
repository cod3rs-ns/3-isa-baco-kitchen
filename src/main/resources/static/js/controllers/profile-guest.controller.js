angular
    .module('isa-mrs-project')
    .controller('GuestProfileController', GuestProfileController);
    
GuestProfileController.$inject = ['$mdDialog', 'guestService'];

function GuestProfileController($mdDialog, guestService) {
    var guestProfileVm = this;

    // Set bindable memebers at the top of the controller
    guestProfileVm.showSearch = false;
    guestProfileVm.editMode = false;
    guestProfileVm.user = {};
    
    guestProfileVm.friendRequests = [];
    guestProfileVm.friends = [];
    // Functions 
    guestProfileVm.editProfile = editProfile;
    guestProfileVm.saveChanges = saveChanges;
    guestProfileVm.cancel = cancel;
    
    guestProfileVm.acceptFriend = acceptFriend;
    guestProfileVm.rejectFriend = rejectFriend;

    activate();

    // Implement functions later
    function acceptFriend(id) {
        guestService.accept(id)
          .then(function (response) {
              console.log('Accepted friend ' + id);
          });
    };
    
    function rejectFriend(id) {
      guestService.reject(id)
        .then(function (response) {
            console.log('Rejected friend ' + id);
        });
    };
    
    function editProfile() {
        guestProfileVm.editMode = true;
    };
    
    function saveChanges() {
        guestService.updateGuest(guestProfileVm.user)
          .then(function (updatedUser) {
            guestProfileVm.user = updatedUser;
          });
        guestProfileVm.editMode = false;
    };
    
    function cancel() {
        guestProfileVm.editMode = false;
    };
    
    function activate() {
        getUser().then(function() {
            console.log('User loaded.');
        });
        
        getRequests().then(function() {
            console.log('Requests loaded.');
        });
        
        getFriends().then(function() {
            console.log('Friends loaded');
        });
    };

    function getUser() {
        return guestService.getGuest().then(function(data) {
            guestProfileVm.user = data;
            return guestProfileVm.user;
        });
    };
    
    function getRequests() {
        return guestService.getRequests().then(function(data) {
            guestProfileVm.friendRequests = data;
            return guestProfileVm.friendRequests;
        });
    };
    
    function getFriends() {
        return guestService.getFriends().then(function(data) {
          guestProfileVm.friends = data;
          return guestProfileVm.friends;
      });
    };
}